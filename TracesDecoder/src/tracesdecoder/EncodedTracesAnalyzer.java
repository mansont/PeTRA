package tracesdecoder;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EncodedTracesAnalyzer {

	public EncodedTracesAnalyzer() {
		// TODO Auto-generated constructor stub
	}
	
	public List<String> decodeSequence(List<String> sequences){
		String timecode = "";
		String decodedSequence = "";
		String sCode = "";
		List<String> decodedSequencesList = new ArrayList<String>();
		String [] sequenceCodes;
		
		for(String seq: sequences){
			timecode = extractTimecode(seq);
			decodedSequence = timecode+";";
			sequenceCodes = seq.split(";");
			
			for(int i=1; i < sequenceCodes.length-1; i++){
				sCode = sequenceCodes[i];
				decodedSequence = decodedSequence.concat(decodeAction(sCode)+";");
			}
			sCode = sequenceCodes[sequenceCodes.length-1];
			decodedSequence = decodedSequence.concat(decodeAction(sCode));
			decodedSequencesList.add(decodedSequence);
		}
		
		Collections.sort(decodedSequencesList);
		
	return decodedSequencesList;
	}
	
public List<String> decodeTimeExtendedSequences(List<String> sequences){
		
		String decodedSequence;
		String sCode = "";
		List<String> decodedSequencesList = new ArrayList<String>();
		String [] sequenceCodes;
		
		for(String seq: sequences){
			decodedSequence = "";
			sequenceCodes = seq.split(" ");
			//supFlag = false;
			for(int i=0; i < sequenceCodes.length; i++){
				if(!sequenceCodes[i].isEmpty()){
					if(sequenceCodes[i].contains("<")||
					sequenceCodes[i].equals("-1")||
					sequenceCodes[i].equals("-2")){ 
						decodedSequence = decodedSequence.concat(sequenceCodes[i]+" ");
						continue;
					}
					else{
						sCode = sequenceCodes[i];
						if(!sCode.equals("#SUP:")) decodedSequence = decodedSequence.concat(decodeAction(sCode)+" ");
						else {
							decodedSequence = decodedSequence.concat(" SUPPORT: "+ sequenceCodes[i+1]);
							break;
						}
					}
				}
			}
			//sCode = sequenceCodes[sequenceCodes.length-1];
			decodedSequence = decodedSequence.concat(decodeAction(sCode));
			decodedSequencesList.add(decodedSequence);
		}
		
		//Collections.sort(decodedSequencesList);
		
	return decodedSequencesList;
	}
	public List<String> decodeSequenceSPMF(List<String> sequences){
		
		String decodedSequence = "";
		String sCode = "";
		List<String> decodedSequencesList = new ArrayList<String>();
		String [] sequenceCodes;
	//	boolean supFlag = false;
		
		for(String seq: sequences){
			decodedSequence = "";
			sequenceCodes = seq.split(" ");
			//supFlag = false;
			for(int i=0; i < sequenceCodes.length; i++){
				if(!sequenceCodes[i].isEmpty()){
					sCode = sequenceCodes[i];
					if(!sCode.equals("#SUP:")) decodedSequence = decodedSequence.concat(decodeAction(sCode)+";");
					else {
						decodedSequence = decodedSequence.concat(" SUPPORT: "+ sequenceCodes[i+1]);
						break;
					}
				}
			}
			
			//sCode = sequenceCodes[sequenceCodes.length-1];
			decodedSequence = decodedSequence.concat(decodeAction(sCode));
			decodedSequencesList.add(decodedSequence);
		}
		
		//Collections.sort(decodedSequencesList);
		
	return decodedSequencesList;
	}
	

public List<String> decodeAssociationRules(List<String> encodedAssociationRules){
		
		String decodedSequence = "";
		String sCode = "";
		List<String> decodedAssociationRulesList = new ArrayList<String>();
		String [] sequenceCodes;
		boolean supFlag = false;
		for(String seq: encodedAssociationRules){
			seq = seq.replaceAll(",", " ");
			decodedSequence = "";
			sequenceCodes = seq.split(" ");
			String toBeDecoded = "";
			
			for(int i=0; i < sequenceCodes.length; i++){
				
				if(!sequenceCodes[i].isEmpty()){
					sCode = sequenceCodes[i];
					
					if(supFlag) toBeDecoded = decodeAction(sCode);
					else toBeDecoded = sCode;
					
					if(!sCode.equals("#SUP:") && !sCode.equals("#CONF:")){
						if(sCode.equals("==>"))decodedSequence = decodedSequence.concat(" "+toBeDecoded+" ");
						else{
							decodedSequence = decodedSequence.concat(decodeAction(toBeDecoded)+", ");
						}
					}
					else {
						if (sCode.equals("#SUP:")){
							supFlag = true;
							decodedSequence = decodedSequence.concat(" #SUPPORT:"+ sequenceCodes[i+1]);
						}
						if (sCode.equals("#CONF:")){
							decodedSequence = decodedSequence.concat(" #CONFIANCE:"+ sequenceCodes[i+1]);
							decodedSequence = decodedSequence.replaceAll(",  ", " ");
							supFlag = false;
							break;
						}
						
					}
				}
			}
			
			//sCode = sequenceCodes[sequenceCodes.length-1];
			decodedSequence = decodedSequence.concat(decodeAction(sCode));
			decodedAssociationRulesList.add(decodedSequence);
		}
		
		//Collections.sort(decodedSequencesList);
		
	return decodedAssociationRulesList;
	}

public List<String> formatDecodedAssociationRules(List<String> decodedAssociationRules){
	
	String formattedSequence = "";
	String sCode = "";
	List<String> formattedAssociationRulesList = new ArrayList<String>();
	String [] sequenceCodes;
	String [] supportTokens;
	String support = "";
	boolean rightSide = false;
	for(String decodedSeq: decodedAssociationRules){
		decodedSeq = decodedSeq.replaceAll(", ", " ");
		formattedSequence = "";
		sequenceCodes = decodedSeq.split(" ");
		//String toBeDecoded = "";
		rightSide = false;
		for(int i=0; i < sequenceCodes.length; i++){
			if(!sequenceCodes[i].isEmpty()){
				sCode = sequenceCodes[i];
				
				if(sCode.equals("==>")){
					formattedSequence = formattedSequence.concat(" "+sCode+" ");
					rightSide = true;
					continue;
				}
				
				if(rightSide){
					if(sCode.contains("#")){
						if(i== sequenceCodes.length-2){
							formattedSequence = formattedSequence.concat("\n\t"+sCode+" ");
							supportTokens = sCode.split(":");
							support = supportTokens[1];
							if (support.length() == 1) support = "00"+support;
							else if (support.length() == 2) support = "0"+support;
						}
						else
							formattedSequence = formattedSequence.concat(sCode+"\n");
					}
					else{
						formattedSequence = formattedSequence.concat("\n\t"+sCode);
					}
				}
				else{
					formattedSequence = formattedSequence.concat(sCode+", ");
				}
			}
		}
		formattedSequence = formattedSequence.concat(decodeAction(sCode));
		formattedSequence = support+" "+formattedSequence;
		formattedAssociationRulesList.add(formattedSequence);
	}
	
	Collections.sort(formattedAssociationRulesList);
	
return formattedAssociationRulesList;
}

public List<String> decodePatternsPrefixSpan(List<String> encodedCmrSequences){
	
	String decodedSequence = "";
	String sCode = "";
	List<String> decodedCmrSequencesList = new ArrayList<String>();
	String [] sequenceCodes;
	boolean supFlag = false;
	for(String seq: encodedCmrSequences){
		decodedSequence = "";
		String toBeDecoded = "";
		seq = seq.replaceAll("-1 ", "");
		sequenceCodes = seq.split(" ");
				
		for(int i = 0; i < sequenceCodes.length; i++){
			
			if(!sequenceCodes[i].isEmpty()){
				
				//Le sCode courant
				System.out.print("Le sCode courant: ");
				System.out.println(sequenceCodes[i]);
				
				sCode = sequenceCodes[i];
				
				if(supFlag) toBeDecoded = decodeAction(sCode);
				else toBeDecoded = sCode;
				
				if(!sCode.equals("#SUP:")){
					decodedSequence = decodedSequence.concat(decodeAction(toBeDecoded)+",");
				}
				else {
					if (sCode.equals("#SUP:")){
						supFlag = true;
						decodedSequence = decodedSequence.concat(" #SUPPORT: "+ sequenceCodes[i+1]);
					}
				}
			}
		}
		
		//sCode = sequenceCodes[sequenceCodes.length-1];
		decodedSequence = decodedSequence.concat(decodeAction(sCode));
		decodedCmrSequencesList.add(decodedSequence);
		supFlag = false;
	}
	
	//Collections.sort(decodedSequencesList);
	
return decodedCmrSequencesList;
}
	private String extractTimecode(String sequence){
		String timecode;
		String [] sequenceElements;
		
		sequenceElements = sequence.split(";");
		timecode = sequenceElements[0];
	return timecode;
	}
	
	private String decodeAction(String sCode){
		String action = "";
		//int iCode = 0;
		
		/*
		 * ACTIONS SIMULATEUR
		 */
		if (sCode.equals("100"))	action = "Controle_Face"; 	if (sCode.equals("101"))	action = "Definir_Face";
		if (sCode.equals("102"))	action = "Controle_Profil"; if (sCode.equals("103"))	action = "Definir_Profil";
		if (sCode.equals("104"))	action = "Controle_Inlet"; 	if (sCode.equals("105"))	action = "Definir_Inlet";
		if (sCode.equals("106"))	action = "Controle_Outlet"; if (sCode.equals("107"))	action = "Definir_Outlet";
		if (sCode.equals("108"))	action = "Repere_Cutane"; 	if (sCode.equals("109"))	action = "Valider_Repere_T";
		if (sCode.equals("110"))	action = "Valider_Repere_D";if (sCode.equals("111"))	action = "Valider_Repere_G";
		if (sCode.equals("112"))	action = "Valider_Repere_S";if (sCode.equals("113"))	action = "Valider_Repere_L";
		if (sCode.equals("114"))	action = "Placer_Trocart"; 	if (sCode.equals("115"))	action = "Pousser_Trocart";
		if (sCode.equals("116"))	action = "Impacter_Trocart";if (sCode.equals("117"))	action = "Entree_CV";
		if (sCode.equals("118"))	action = "Sortie_CV"; 		if (sCode.equals("119"))	action = "Sortie_Osseuse";  
		if (sCode.equals("120")) 	action = "Recommencer_Trajectoire"; 
		
			
		/*
		 * ACTIONS TROCART
		 */
		if (sCode.equals("200"))	action = "Trocar_move_up"; 		if (sCode.equals("201"))	action = "Trocar_move_down";
		if (sCode.equals("202"))	action = "Trocar_move_right";	if (sCode.equals("203"))	action = "Trocar_move_left";
		if (sCode.equals("204"))	action = "Trocar_move_front"; 	if (sCode.equals("205"))	action = "Trocar_move_back";
		if (sCode.equals("206"))	action = "Trocar_lean_back"; 	if (sCode.equals("207"))	action = "Trocar_lean_front";
		if (sCode.equals("208"))	action = "Trocar_lean_right"; 	if (sCode.equals("209"))	action = "Trocar_lean_left";
		
		/*
		 * ACTIONS FLUOROSCOPE
		 */
		if (sCode.equals("300"))	action = "FluoroscopeFace_move_right"; 		if (sCode.equals("301"))		action = "FluoroscopeFace_move_left";
		if (sCode.equals("302")) 	action = "FluoroscopeFace_move_up"; 		if (sCode.equals("303"))		action = "FluoroscopeProfile_move_down";
		if (sCode.equals("304"))	action = "FluoroscopeProfile_move_right"; 	if (sCode.equals("305"))	action = "FluoroscopeProfile_move_left";
		if (sCode.equals("306"))	action = "FluoroscopeProfile_move_up"; 		if (sCode.equals("307"))	action = "FluoroscopeProfile_move_down";
		if (sCode.equals("308"))	action = "Fluoroscope_move_front"; 			if (sCode.equals("309"))			action = "Fluoroscope_move_back";
		if (sCode.equals("310"))	action = "FluoroscopeFace_lean_right"; 		if (sCode.equals("311"))		action = "FluoroscopeFace_lean_left";
		if (sCode.equals("312"))	action = "FluoroscopeFace_lean_front"; 		if (sCode.equals("313"))		action = "FluoroscopeFace_lean_back";
		if (sCode.equals("314"))	action = "FluoroscopeProfile_lean_right"; 	if (sCode.equals("315"))	action = "FluoroscopeProfile_lean_left";
		if (sCode.equals("316"))	action = "FluoroscopeProfile_lean_front"; 	if (sCode.equals("317"))	action = "FluoroscopeProfile_lean_back";
								
		/*
		 * ACTIONS REPERES CUTANES
		 */
		if (sCode.equals("400"))	action = "transverseMark_move_right"; if (sCode.equals("401"))		action = "transverseMark_move_left";
		if (sCode.equals("402"))	action = "rightMark_move_right"; if (sCode.equals("403"))			action = "rightMark_move_left";
		if (sCode.equals("404"))	action = "leftMark_move_right"; if (sCode.equals("405"))			action = "leftMark_move_left";
		
		if (sCode.equals("406")) 	action = "transverseMark_oblique_leanLeft"; if (sCode.equals("407"))action = "transverseMark_oblique_leanRight";
		if (sCode.equals("408"))	action = "rightMark_oblique_leanLeft"; if (sCode.equals("409"))		action = "rightMark_oblique_leanRight";
		if (sCode.equals("410"))	action = "leftMark_oblique_leanLeft"; if (sCode.equals("411"))		action = "leftMark_oblique_leanRight";
		
		
		/*
		 * ACTIONS OCULOMETRE
		 */
		if (sCode.equals("500"))	action = "O_vue3D";
		if (sCode.equals("501"))	action = "O_outil_vue3D"; 	if (sCode.equals("502"))	action = "O_outil_vueRadio";
		
		if (sCode.equals("503"))	action = "O_vueRadioFace"; 	if (sCode.equals("504"))	action = "O_vueRadioFace2";
		if (sCode.equals("505"))	action = "O_vueRadioProfil";if (sCode.equals("506"))	action = "O_vueRadioProfil2";
		if (sCode.equals("507"))	action = "O_vueRadioOutlet";if (sCode.equals("508"))	action = "O_vueRadioOutlet2";
		if (sCode.equals("509"))	action = "O_vueRadioInlet"; if (sCode.equals("510"))	action = "O_vueRadioInlet2";
		 
		if (sCode.equals("511"))	action = "O_manipReglage";
		if (sCode.equals("512"))	action = "O_manipFace"; 	if (sCode.equals("513"))	action = "O_manipProfil";
		
		if (sCode.equals("514"))	action = "O_epineuse";
		if (sCode.equals("515"))	action = "O_pedic_centD"; 	if (sCode.equals("516"))	action = "O_pedic_centG"; if (sCode.equals("415"))	action = "O_pedic_caudalG";
		if (sCode.equals("517"))	action = "O_pedic_caudalD"; if (sCode.equals("518"))	action = "O_pedic_caudalG";  
		if (sCode.equals("519"))	action = "O_pedic_extD"; 	if (sCode.equals("520"))	action = "O_pedic_extG";
		if (sCode.equals("521"))	action = "O_coteD"; 		if (sCode.equals("522"))	action = "O_coteG"; 
		if (sCode.equals("523"))	action = "O_plateauA"; 		if (sCode.equals("524"))	action = "O_plateauP";
		if (sCode.equals("525"))	action = "O_plateauD"; 		if (sCode.equals("526"))	action = "O_plateauG";
		if (sCode.equals("527"))	action = "O_facetteD"; 		if (sCode.equals("528"))	action = "O_facetteG";
		if (sCode.equals("529"))	action = "O_transD";  		if (sCode.equals("530"))	action = "O_transD";
		
		if (sCode.equals("531"))	action = "NAOI";
		
		
		/*
		 * VARIABLES DE SITUATION
		
		
		if (sCode.equals("500"))	action = "RP_centrageVertebre"; 			if (sCode.equals("501"))	action = "RP_parallelismePlateaux"; 
		if (sCode.equals("502"))	action = "RP_superpositionArches"; 			if (sCode.equals("503"))	action = "RP_superpositionCotes";
		if (sCode.equals("504"))	action = "RP_superpositionInterlignes"; 	if (sCode.equals("505"))	action = "RF_centrageVertebre";
		if (sCode.equals("506"))	action = "RF_symetriePedicEpineuse"; 		if (sCode.equals("507"))	action = "RF_disquesVisibles";
		if (sCode.equals("508"))	action = "RP_EC_trocartDirectionPedic"; 	if (sCode.equals("509"))	action = "RP_EO_trocartExtremiteProcessus";
		if (sCode.equals("510"))	action = "RP_ECV_trocartMiHauteurPedic";	if (sCode.equals("511"))	action = "RP_VT_trocartPartieAnterieureCV";
		if (sCode.equals("512"))	action = "RP_VT_trocartSortiePedic"; 		if (sCode.equals("513"))	action = "RP_EO_trocartCentrePedic";
		if (sCode.equals("514"))	action = "RF_EC_trocartInclinaison"; 		if (sCode.equals("515"))	action = "RF_EO_trocartEntreProcessusFacette";
		if (sCode.equals("516"))	action = "RF_EO_trocartExternePedic"; 		if (sCode.equals("517"))	action = "RF_ECV_trocartLimiteCentrePedic";
		if (sCode.equals("518"))	action = "RF_ECV_trocartAvantCentrePedic"; 	if (sCode.equals("519"))	action = "RF_EO_trocartSurProcessus";
		if (sCode.equals("520"))	action = "RF_VT_trocartNiveauEpineuse"; 	if (sCode.equals("521"))	action = "RF_VT_trocartCentrePedic";
		if (sCode.equals("522"))	action = "RF_EC_trocartExterieurLigneLongitudinal"; if (sCode.equals("523"))	action = "VRT_paralleleAxeTransversal";
		if (sCode.equals("524"))	action = "VRD_superpositionAxePedicD"; 		if (sCode.equals("525"))	action = "VRD_paralleleAxePedicD";
		if (sCode.equals("526"))	action = "VRG_superpositionAxePedicG"; 		if (sCode.equals("527"))	action = "VRG_paralleleAxePedicG";
		if (sCode.equals("528"))	action = "VRT_superpositionAxeTransversal"; if (sCode.equals("529"))	action = "VRT_superpositionAxeTransveral";

		/*
		 * EVALUATION VARIABLE DE SITUATION
		 
		if (sCode.equals("530"))	action = "correct"; 			if (sCode.equals("531"))	action = "incorrect";
		if (sCode.equals("532"))	action = "incorrect_bon_sens"; 	if (sCode.equals("533"))	action = "incorrect_mauvais_sens";
		//if (sCode.equals("534"))	action = "correct_bon_sens"; 	if (sCode.equals("535"))	action = "correct_mauvais_sens";
		*/
		
		if (sCode.equals("6000"))	action = "RP_centrageVertebre;incorrect";
		if (sCode.equals("6001"))	action = "RP_centrageVertebre;incorrect_bon_sens";
		if (sCode.equals("6002"))	action = "RP_centrageVertebre;incorrect_mauvais_sens";
		if (sCode.equals("6003"))	action = "RP_centrageVertebre;correct";
		
		if (sCode.equals("6100"))	action = "RP_parallelismePlateaux;incorrect";
		if (sCode.equals("6101"))	action = "RP_parallelismePlateaux;incorrect_bon_sens";
		if (sCode.equals("6102"))	action = "RP_parallelismePlateaux;incorrect_mauvais_sens";
		if (sCode.equals("6103"))	action = "RP_parallelismePlateaux;correct";
		
		if (sCode.equals("6200"))	action = "RP_superpositionArches;incorrect";
		if (sCode.equals("6201"))	action = "RP_superpositionArches;incorrect_bon_sens";
		if (sCode.equals("6202"))	action = "RP_superpositionArches;incorrect_mauvais_sens";
		if (sCode.equals("6203"))	action = "RP_superpositionArches;correct";
		
		if (sCode.equals("6300"))	action = "RP_superpositionCotes;incorrect";
		if (sCode.equals("6301"))	action = "RP_superpositionCotes;incorrect_bon_sens";
		if (sCode.equals("6302"))	action = "RP_superpositionCotes;incorrect_mauvais_sens";
		if (sCode.equals("6303"))	action = "RP_superpositionCotes;correct";
		
		if (sCode.equals("6400"))	action = "RP_superpositionInterlignes;incorrect";
		if (sCode.equals("6401"))	action = "RP_superpositionInterlignes;incorrect_bon_sens";
		if (sCode.equals("6402"))	action = "RP_superpositionInterlignes;incorrect_mauvais_sens";
		if (sCode.equals("6403"))	action = "RP_superpositionInterlignes;correct";
		
		if (sCode.equals("6500"))	action = "RF_centrageVertebre;incorrect";
		if (sCode.equals("6501"))	action = "RF_centrageVertebre;incorrect_bon_sens";
		if (sCode.equals("6502"))	action = "RF_centrageVertebre;incorrect_mauvais_sens";
		if (sCode.equals("6503"))	action = "RF_centrageVertebre;correct";
		
		if (sCode.equals("6600"))	action = "RF_symetriePedicEpineuse;incorrect";
		if (sCode.equals("6601"))	action = "RF_symetriePedicEpineuse;incorrect_bon_sens";
		if (sCode.equals("6602"))	action = "RF_symetriePedicEpineuse;incorrect_mauvais_sens";
		if (sCode.equals("6603"))	action = "RF_symetriePedicEpineuse;correct";
		
		if (sCode.equals("6700"))	action = "RF_disquesVisibles;incorrect";
		if (sCode.equals("6701"))	action = "RF_disquesVisibles;incorrect_bon_sens";
		if (sCode.equals("6702"))	action = "RF_disquesVisibles;incorrect_mauvais_sens";
		if (sCode.equals("6703"))	action = "RF_disquesVisibles;correct";
		
		if (sCode.equals("6800"))	action = "RP_EC_trocartDirectionPedic;incorrect";
		if (sCode.equals("6801"))	action = "RP_EC_trocartDirectionPedic;incorrect_bon_sens";
		if (sCode.equals("6802"))	action = "RP_EC_trocartDirectionPedic;incorrect_mauvais_sens";
		if (sCode.equals("6803"))	action = "RP_EC_trocartDirectionPedic;correct";
		
		if (sCode.equals("6900"))	action = "RP_EO_trocartExtremiteProcessus;incorrect";
		if (sCode.equals("6901"))	action = "RP_EO_trocartExtremiteProcessus;incorrect_bon_sens";
		if (sCode.equals("6902"))	action = "RP_EO_trocartExtremiteProcessus;incorrect_mauvais_sens";
		if (sCode.equals("6903"))	action = "RP_EO_trocartExtremiteProcessus;correct";
		
		if (sCode.equals("7000"))	action = "RP_ECV_trocartMiHauteurPedic;incorrect";
		if (sCode.equals("7001"))	action = "RP_ECV_trocartMiHauteurPedic;incorrect_bon_sens";
		if (sCode.equals("7002"))	action = "RP_ECV_trocartMiHauteurPedic;incorrect_mauvais_sens";
		if (sCode.equals("7003"))	action = "RP_ECV_trocartMiHauteurPedic;correct";
		
		if (sCode.equals("7100"))	action = "RP_VT_trocartPartieAnterieureCV;incorrect";
		if (sCode.equals("7101"))	action = "RP_VT_trocartPartieAnterieureCV;incorrect_bon_sens";
		if (sCode.equals("7102"))	action = "RP_VT_trocartPartieAnterieureCV;incorrect_mauvais_sens";
		if (sCode.equals("7103"))	action = "RP_VT_trocartPartieAnterieureCV;correct";
		
		if (sCode.equals("7200"))	action = "RP_VT_trocartSortiePedic;incorrect";
		if (sCode.equals("7201"))	action = "RP_VT_trocartSortiePedic;incorrect_bon_sens";
		if (sCode.equals("7202"))	action = "RP_VT_trocartSortiePedic;incorrect_mauvais_sens";
		if (sCode.equals("7203"))	action = "RP_VT_trocartSortiePedic;correct";
		
		if (sCode.equals("7300"))	action = "RP_EO_trocartCentrePedic;incorrect";
		if (sCode.equals("7301"))	action = "RP_EO_trocartCentrePedic;incorrect_bon_sens";
		if (sCode.equals("7302"))	action = "RP_EO_trocartCentrePedic;incorrect_mauvais_sens";
		if (sCode.equals("7303"))	action = "RP_EO_trocartCentrePedic;correct";
		
		if (sCode.equals("7400"))	action = "RF_EC_trocartInclinaison;incorrect";
		if (sCode.equals("7401"))	action = "RF_EC_trocartInclinaison;incorrect_bon_sens";
		if (sCode.equals("7402"))	action = "RF_EC_trocartInclinaison;incorrect_mauvais_sens";
		if (sCode.equals("7403"))	action = "RF_EC_trocartInclinaison;correct";
		
		if (sCode.equals("7500"))	action = "RF_EO_trocartEntreProcessusFacette;incorrect";
		if (sCode.equals("7501"))	action = "RF_EO_trocartEntreProcessusFacette;incorrect_bon_sens";
		if (sCode.equals("7502"))	action = "RF_EO_trocartEntreProcessusFacette;incorrect_mauvais_sens";
		if (sCode.equals("7503"))	action = "RF_EO_trocartEntreProcessusFacette;correct";
		
		if (sCode.equals("7600"))	action = "RF_EO_trocartExternePedic;incorrect";
		if (sCode.equals("7601"))	action = "RF_EO_trocartExternePedic;incorrect_bon_sens";
		if (sCode.equals("7602"))	action = "RF_EO_trocartExternePedic;incorrect_mauvais_sens";
		if (sCode.equals("7603"))	action = "RF_EO_trocartExternePedic;correct";
		
		if (sCode.equals("7700"))	action = "RF_ECV_trocartLimiteCentrePedic;incorrect";
		if (sCode.equals("7701"))	action = "RF_ECV_trocartLimiteCentrePedic;incorrect_bon_sens";
		if (sCode.equals("7702"))	action = "RF_ECV_trocartLimiteCentrePedic;incorrect_mauvais_sens";
		if (sCode.equals("7703"))	action = "RF_ECV_trocartLimiteCentrePedic;correct";
		
		if (sCode.equals("7800"))	action = "RF_ECV_trocartAvantCentrePedic;incorrect";
		if (sCode.equals("7801"))	action = "RF_ECV_trocartAvantCentrePedic;incorrect_bon_sens";
		if (sCode.equals("7802"))	action = "RF_ECV_trocartAvantCentrePedic;incorrect_mauvais_sens";
		if (sCode.equals("7803"))	action = "RF_ECV_trocartAvantCentrePedic;correct";
		
		if (sCode.equals("7900"))	action = "RF_EO_trocartSurProcessus;incorrect";
		if (sCode.equals("7901"))	action = "RF_EO_trocartSurProcessus;incorrect_bon_sens";
		if (sCode.equals("7902"))	action = "RF_EO_trocartSurProcessus;incorrect_mauvais_sens";
		if (sCode.equals("7903"))	action = "RF_EO_trocartSurProcessus;correct";
		
		if (sCode.equals("8000"))	action = "RF_VT_trocartNiveauEpineuse;incorrect";
		if (sCode.equals("8001"))	action = "RF_VT_trocartNiveauEpineuse;incorrect_bon_sens";
		if (sCode.equals("8002"))	action = "RF_VT_trocartNiveauEpineuse;incorrect_mauvais_sens";
		if (sCode.equals("7003"))	action = "RF_VT_trocartNiveauEpineuse;correct";
		
		if (sCode.equals("8100"))	action = "RF_VT_trocartCentrePedic;incorrect";
		if (sCode.equals("8101"))	action = "RF_VT_trocartCentrePedic;incorrect_bon_sens";
		if (sCode.equals("8102"))	action = "RF_VT_trocartCentrePedic;incorrect_mauvais_sens";
		if (sCode.equals("8103"))	action = "RF_VT_trocartCentrePedic;correct";
		
		if (sCode.equals("8200"))	action = "RF_EC_trocartExterieurLigneLongitudinal;incorrect";
		if (sCode.equals("8201"))	action = "RF_EC_trocartExterieurLigneLongitudinal;incorrect_bon_sens";
		if (sCode.equals("8202"))	action = "RF_EC_trocartExterieurLigneLongitudinal;incorrect_mauvais_sens";
		if (sCode.equals("8203"))	action = "RF_EC_trocartExterieurLigneLongitudinal;correct";
		
		if (sCode.equals("8300"))	action = "VRT_paralleleAxeTransversal;incorrect";
		if (sCode.equals("8301"))	action = "VRT_paralleleAxeTransversal;incorrect_bon_sens";
		if (sCode.equals("8302"))	action = "VRT_paralleleAxeTransversal;incorrect_mauvais_sens";
		if (sCode.equals("8303"))	action = "VRT_paralleleAxeTransversal;correct";
		
		if (sCode.equals("8400"))	action = "VRD_superpositionAxePedicD;incorrect";
		if (sCode.equals("8401"))	action = "VRD_superpositionAxePedicD;incorrect_bon_sens";
		if (sCode.equals("8402"))	action = "VRD_superpositionAxePedicD;incorrect_mauvais_sens";
		if (sCode.equals("8403"))	action = "VRD_superpositionAxePedicD;correct";
		
		if (sCode.equals("8500"))	action = "VRD_paralleleAxePedicD;incorrect";
		if (sCode.equals("8501"))	action = "VRD_paralleleAxePedicD;incorrect_bon_sens";
		if (sCode.equals("8502"))	action = "VRD_paralleleAxePedicD;incorrect_mauvais_sens";
		if (sCode.equals("8503"))	action = "VRD_paralleleAxePedicD;correct";
		
		if (sCode.equals("8600"))	action = "VRG_superpositionAxePedicG;incorrect";
		if (sCode.equals("8601"))	action = "VRG_superpositionAxePedicG;incorrect_bon_sens";
		if (sCode.equals("8602"))	action = "VRG_superpositionAxePedicG;incorrect_mauvais_sens";
		if (sCode.equals("8602"))	action = "VRG_superpositionAxePedicG;correct";
		
		if (sCode.equals("8700"))	action = "VRG_paralleleAxePedicG;incorrect";
		if (sCode.equals("8701"))	action = "VRG_paralleleAxePedicG;incorrect_bon_sens";
		if (sCode.equals("8702"))	action = "VRG_paralleleAxePedicG;incorrect_mauvais_sens";
		if (sCode.equals("8703"))	action = "VRG_paralleleAxePedicG;correct";
		
		if (sCode.equals("8800"))	action = "VRT_superpositionAxeTransversal;incorrect";
		if (sCode.equals("8801"))	action = "VRT_superpositionAxeTransversal;incorrect_bon_sens";
		if (sCode.equals("8802"))	action = "VRT_superpositionAxeTransversal;incorrect_mauvais_sens";
		if (sCode.equals("8803"))	action = "VRT_superpositionAxeTransversal;correct";
		
	return action;
	}
	
	public void saveDecodedTraces (List<String> decodedTraces, String filepath){
		try {
			PrintStream fDecodedSequences = new PrintStream(filepath);
			System.setOut(fDecodedSequences);
			
			//fEncodedSequences.println(_HEADER);
		      for(String encTrace : decodedTraces){
		    	 fDecodedSequences.println(encTrace);
				}
		      fDecodedSequences.close();
		   }
		      catch(IOException e1) {
		        System.out.println("Error during writing");
		   }
	}
}
