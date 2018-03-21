package tracesencoder;

import java.util.ArrayList;
import java.util.List;

public class SemanticTracesAnalyzer {

	public SemanticTracesAnalyzer() {
		// TODO Auto-generated constructor stub
	}
	
	public List<String> encodeSequence(List<String> sequences){
		String timecode = "";
		String encodedSequence = "";
		String action = "";
		List<String> encodedSequencesList = new ArrayList<String>();
		String [] sequenceActions;
		String vsi_evaluation;
		
		for(String seq: sequences){
			timecode = extractTimecode(seq);
			encodedSequence = timecode+";";
			sequenceActions = seq.split(";");
			
			vsi_evaluation = sequenceActions[sequenceActions.length-2]+";"+sequenceActions[sequenceActions.length-1];
			
			for(int i=1; i < sequenceActions.length-2; i++){
				action = sequenceActions[i];
				encodedSequence = encodedSequence.concat(encodeAction(action)+";");
			}
			//action = sequenceActions[sequenceActions.length-1];
			//encodedSequence = encodedSequence.concat(encodeAction(action));
			
			encodedSequence = encodedSequence.concat(encodeAction(vsi_evaluation));
			
			encodedSequencesList.add(encodedSequence);
		}
		
		//Collections.sort(encodedSequencesList);
		
	return encodedSequencesList;
	}
	
	public List<String> encodeSequenceForSPMF(List<String> sequences){
		//String timecode = "";
		String encodedSequence = "";
		String action = "";
		List<String> encodedSequencesForSPMFList = new ArrayList<String>();
		String [] sequenceActions;
		String vsi_evaluation;
		
		for(String seq: sequences){
			//timecode = extractTimecode(seq);
			encodedSequence ="";
			sequenceActions = seq.split(";");
			
			vsi_evaluation = sequenceActions[sequenceActions.length-2]+";"+sequenceActions[sequenceActions.length-1];
			
			for(int i=1; i < sequenceActions.length-2; i++){
				action = sequenceActions[i];
				encodedSequence = encodedSequence.concat(encodeAction(action)+" ");
			}
			//action = sequenceActions[sequenceActions.length-1];
			//encodedSequence = encodedSequence.concat(encodeAction(action));
			
			encodedSequence = encodedSequence.concat(encodeAction(vsi_evaluation));
			
			encodedSequencesForSPMFList.add(encodedSequence);
		}
		
		//Collections.sort(encodedSequencesForSPMFList);
		
	return encodedSequencesForSPMFList;
	}
	
	public List<String> encodeSequenceForSPMFCMRules(List<String> sequences){
		String indexCurrentCode = "";
		String indexPreviousCode = "";
		String encodedCMRSequence = "";
		String currentAction = "";
		String previousAction = "";
		String currentActionCode = "";
		String previousActionCode = "";
		List<String> encodedSequencesForCMRList = new ArrayList<String>();
		String [] sequenceActions;
		String vsi_evaluation;
		
		for(String seq: sequences){
			
			encodedCMRSequence ="";
			sequenceActions = seq.split(";");
			
			vsi_evaluation = sequenceActions[sequenceActions.length-2]+";"+sequenceActions[sequenceActions.length-1];
			
			for(int i=1; i < sequenceActions.length-2; i++){
				currentAction = sequenceActions[i];
				currentActionCode = encodeAction(currentAction);
				indexCurrentCode = extractCodeIndex(currentActionCode);
				if(i==1) encodedCMRSequence = encodedCMRSequence.concat(currentActionCode+" ");
				
				if(i>1){
					previousAction = sequenceActions[i-1];
					previousActionCode = encodeAction(previousAction);
					indexPreviousCode = extractCodeIndex(previousActionCode);
					if(indexCurrentCode.equals(indexPreviousCode)){
						encodedCMRSequence = encodedCMRSequence.concat(currentActionCode+" ");
						//continue;
					}
					else{
						encodedCMRSequence = encodedCMRSequence.concat("-1"+" "+currentActionCode+" ");
					}
				}
				
			}
			
			encodedCMRSequence = encodedCMRSequence.concat("-1"+" "+encodeAction(vsi_evaluation)+" ");
			encodedCMRSequence = encodedCMRSequence.concat("-1 -2");
			
			encodedSequencesForCMRList.add(encodedCMRSequence);
		}
		
		//Collections.sort(encodedSequencesForSPMFList);
		
	return encodedSequencesForCMRList;
	}
	
	private String extractTimecode(String sequence){
		String timecode;
		String [] sequenceElements;
		
		sequenceElements = sequence.split(";");
		timecode = sequenceElements[0];
	return timecode;
	}
	
	private String extractCodeIndex(String actionCode){
		String indexCode = "";
		indexCode = actionCode.substring(0, 1);
	return indexCode;
	}
	
	private String encodeAction(String action){
		String sCode= "";
		//int iCode = 0;
		
		/*
		 * ACTION SIMULATEUR
		 */
		if (action.equals("Controle_Face"))		sCode = "100"; if (action.equals("Definir_Face"))		sCode = "101";
		if (action.equals("Controle_Profil"))	sCode = "102"; if (action.equals("Definir_Profil"))		sCode = "103";
		if (action.equals("Controle_Inlet"))	sCode = "104"; if (action.equals("Definir_Inlet"))		sCode = "105";
		if (action.equals("Controle_Outlet"))	sCode = "106"; if (action.equals("Definir_Outlet"))		sCode = "107";
		if (action.equals("Repere_Cutane"))		sCode = "108"; if (action.equals("Valider_Repere_T"))	sCode = "109";
		if (action.equals("Valider_Repere_D"))	sCode = "110"; if (action.equals("Valider_Repere_G"))	sCode = "111";
		if (action.equals("Valider_Repere_S"))	sCode = "112"; if (action.equals("Valider_Repere_L"))	sCode = "113";
		if (action.equals("Placer_Trocart"))	sCode = "114"; if (action.equals("Pousser_Trocart"))	sCode = "115";
		if (action.equals("Impacter_Trocart"))	sCode = "116"; if (action.equals("Entree_CV"))			sCode = "117";
		if (action.equals("Sortie_CV"))			sCode = "118"; if (action.equals("Sortie_Osseuse"))		sCode = "119"; 
		if (action.equals("Recommencer_Trajectoire"))	sCode = "120";
		
		
		/*
		 * ACTIONS TROCART
		 */
		if (action.equals("Trocar_move_up"))	sCode = "200"; if (action.equals("Trocar_move_down"))	sCode = "201";
		if (action.equals("Trocar_move_right"))	sCode = "202"; if (action.equals("Trocar_move_left"))	sCode = "203";
		if (action.equals("Trocar_move_front"))	sCode = "204"; if (action.equals("Trocar_move_back"))	sCode = "205";
		if (action.equals("Trocar_lean_back"))	sCode = "206"; if (action.equals("Trocar_lean_front"))	sCode = "207";
		if (action.equals("Trocar_lean_right"))	sCode = "208"; if (action.equals("Trocar_lean_left"))	sCode = "209";
		/*if (action.equals("Trocar_transverse_unchanged")) sCode = "110"; if (action.equals("Trocar_frontal_unchanged"))	sCode = "110";
		if (action.equals("Trocar_sagittal_unchanged")) sCode = "110"; if (action.equals("Trocar_unchanged")) sCode = "110";*/

		
		/*
		 * ACTIONS FLUOROSCOPE
		 */
		if (action.equals("FluoroscopeFace_move_right"))	sCode = "300"; if (action.equals("FluoroscopeFace_move_left"))		sCode = "301";
		if (action.equals("FluoroscopeFace_move_up")) 		sCode = "302"; if (action.equals("FluoroscopeFace_move_down"))		sCode = "303";
		if (action.equals("FluoroscopeProfile_move_right"))	sCode = "304"; if (action.equals("FluoroscopeProfile_move_left"))	sCode = "305";
		if (action.equals("FluoroscopeProfile_move_up"))	sCode = "306"; if (action.equals("FluoroscopeProfile_move_down"))	sCode = "307";
		if (action.equals("Fluoroscope_move_front"))		sCode = "308"; if (action.equals("Fluoroscope_move_back"))			sCode = "309";
		if (action.equals("FluoroscopeFace_lean_right"))	sCode = "310"; if (action.equals("FluoroscopeFace_lean_left"))		sCode = "311";
		if (action.equals("FluoroscopeFace_lean_front"))	sCode = "312"; if (action.equals("FluoroscopeFace_lean_back"))		sCode = "313";
		if (action.equals("FluoroscopeProfile_lean_right"))	sCode = "314"; if (action.equals("FluoroscopeProfile_lean_left"))	sCode = "315";
		if (action.equals("FluoroscopeProfile_lean_front"))	sCode = "316"; if (action.equals("FluoroscopeProfile_lean_back"))	sCode = "317";
		//if (action.equals("FluoroscopeFace_transverse_unchanged")) sCode = "220"; //if (action.equals("FluoroscopeFace_cephaliccaudal_unchanged"))	sCode = "220";
		//FluoroscopeProfile_anteroposterior_unchanged;
		//FluoroscopeProfile_cephaliccaudal_unchanged;
		
		/*
		 * ACTIONS REPERES CUTANES
		 */
		if (action.equals("transverseMark_move_right"))			sCode = "400"; if (action.equals("transverseMark_move_left"))			sCode = "401";
		if (action.equals("rightMark_move_right"))				sCode = "402"; if (action.equals("rightMark_move_left"))				sCode = "403";
		if (action.equals("leftMark_move_right"))				sCode = "404"; if (action.equals("leftMark_move_left"))					sCode = "405";
		
		if (action.equals("transverseMark_oblique_leanLeft")) 	sCode = "406"; if (action.equals("transverseMark_oblique_leanRight")) 	sCode = "407";
		if (action.equals("rightMark_oblique_leanLeft"))		sCode = "408"; if (action.equals("rightMark_oblique_leanRight"))		sCode = "409";
		if (action.equals("leftMark_oblique_leanLeft"))			sCode = "410"; if (action.equals("leftMark_oblique_leanRight"))			sCode = "411";
		
		/*
		 * ACTIONS OCULOMETRE
		 */
		if (action.equals("O_vue3D"))			sCode = "500";
		if (action.equals("O_outil_vue3D"))		sCode = "501"; if (action.equals("O_outil_vueRadio"))	sCode = "502";
		
		if (action.equals("O_vueRadioFace"))	sCode = "503"; if (action.equals("O_vueRadioFace2"))	sCode = "504";
		if (action.equals("O_vueRadioProfil"))	sCode = "505"; if (action.equals("O_vueRadioProfil2"))	sCode = "506";
		if (action.equals("O_vueRadioOutlet"))	sCode = "507"; if (action.equals("O_vueRadioOutlet2"))	sCode = "508";
		if (action.equals("O_vueRadioInlet"))	sCode = "509"; if (action.equals("O_vueRadioInlet2"))	sCode = "510";
		
		if (action.equals("O_manipReglage"))	sCode = "511"; 
		if (action.equals("O_manipFace"))		sCode = "512"; if (action.equals("O_manipProfil"))		sCode = "513";
		
		if (action.equals("O_epineuse"))		sCode = "514";
		if (action.equals("O_pedic_centD"))		sCode = "515"; if (action.equals("O_pedic_centG"))		sCode = "516";
		if (action.equals("O_pedic_caudalD"))	sCode = "517"; if (action.equals("O_pedic_caudalG"))	sCode = "518";
		if (action.equals("O_pedic_extD"))		sCode = "519"; if (action.equals("O_pedic_extG"))		sCode = "520";
		if (action.equals("O_coteD"))			sCode = "521"; if (action.equals("O_coteG"))			sCode = "522";
		if (action.equals("O_plateauA"))		sCode = "523"; if (action.equals("O_plateauP"))			sCode = "524";
		if (action.equals("O_plateauD"))		sCode = "525"; if (action.equals("O_plateauG"))			sCode = "526";
		if (action.equals("O_facetteD"))		sCode = "527"; if (action.equals("O_facetteG"))			sCode = "528";
		if (action.equals("O_transD"))			sCode = "529"; if (action.equals("O_transG"))			sCode = "530";
		
		if (action.equals("NAOI"))				sCode = "531";
		
		
		/*
		 * VARIABLES DE SITUATION
		 
		
		if (action.equals("RP_centrageVertebre"))	sCode = "500"; 					if (action.equals("RP_parallelismePlateaux"))	sCode = "501"; 
		if (action.equals("RP_superpositionArches"))	sCode = "502"; 				if (action.equals("RP_superpositionCotes"))	sCode = "503";
		if (action.equals("RP_superpositionInterlignes"))	sCode = "504"; 			if (action.equals("RF_centrageVertebre"))	sCode = "505";
		if (action.equals("RF_symetriePedicEpineuse"))	sCode = "506"; 				if (action.equals("RF_disquesVisibles"))	sCode = "507";
		if (action.equals("RP_EC_trocartDirectionPedic"))	sCode = "508"; 			if (action.equals("RP_EO_trocartExtremiteProcessus"))	sCode = "509";
		if (action.equals("RP_ECV_trocartMiHauteurPedic"))	sCode = "510"; 			if (action.equals("RP_VT_trocartPartieAnterieureCV"))	sCode = "511";
		if (action.equals("RP_VT_trocartSortiePedic"))	sCode = "512"; 				if (action.equals("RP_EO_trocartCentrePedic"))	sCode = "513";
		if (action.equals("RF_EC_trocartInclinaison"))	sCode = "514"; 				if (action.equals("RF_EO_trocartEntreProcessusFacette"))	sCode = "515";
		if (action.equals("RF_EO_trocartExternePedic"))	sCode = "516"; 				if (action.equals("RF_ECV_trocartLimiteCentrePedic"))	sCode = "517";
		if (action.equals("RF_ECV_trocartAvantCentrePedic"))	sCode = "518"; 		if (action.equals("RF_EO_trocartSurProcessus"))	sCode = "519";
		if (action.equals("RF_VT_trocartNiveauEpineuse"))	sCode = "520"; 			if (action.equals("RF_VT_trocartCentrePedic"))	sCode = "521";
		if (action.equals("RF_EC_trocartExterieurLigneLongitudinal"))	sCode = "522"; if (action.equals("VRT_paralleleAxeTransversal"))	sCode = "523";
		if (action.equals("VRD_superpositionAxePedicD"))	sCode = "524"; 			if (action.equals("VRD_paralleleAxePedicD"))	sCode = "525";
		if (action.equals("VRG_superpositionAxePedicG"))	sCode = "526"; 			if (action.equals("VRG_paralleleAxePedicG"))	sCode = "527";
		if (action.equals("VRT_superpositionAxeTransversal"))	sCode = "528"; 		if (action.equals("VRT_superpositionAxeTransveral"))	sCode = "529";
		
		/*
		 * EVALUATION VARIABLE DE SITUATION
		 
		if (action.equals("correct"))	sCode = "630"; 				if (action.equals("incorrect"))	sCode = "631";
		if (action.equals("incorrect_bon_sens"))	sCode = "632"; 	if (action.equals("incorrect_mauvais_sens"))	sCode = "633";
		*/
		
		if (action.equals("RP_centrageVertebre;incorrect"))						sCode = "6000";
		if (action.equals("RP_centrageVertebre;incorrect_bon_sens"))			sCode = "6001";
		if (action.equals("RP_centrageVertebre;incorrect_mauvais_sens"))		sCode = "6002";
		if (action.equals("RP_centrageVertebre;correct"))						sCode = "6003";
		
		if (action.equals("RP_parallelismePlateaux;incorrect"))					sCode = "6100";
		if (action.equals("RP_parallelismePlateaux;incorrect_bon_sens"))		sCode = "6101";
		if (action.equals("RP_parallelismePlateaux;incorrect_mauvais_sens"))	sCode = "6102";
		if (action.equals("RP_parallelismePlateaux;correct"))					sCode = "6103";
		
		if (action.equals("RP_superpositionArches;incorrect"))					sCode = "6200";
		if (action.equals("RP_superpositionArches;incorrect_bon_sens"))			sCode = "6201";
		if (action.equals("RP_superpositionArches;incorrect_mauvais_sens"))		sCode = "6202";
		if (action.equals("RP_superpositionArches;correct"))					sCode = "6203";
		
		if (action.equals("RP_superpositionCotes;incorrect"))					sCode = "6300";
		if (action.equals("RP_superpositionCotes;incorrect_bon_sens"))			sCode = "6301";
		if (action.equals("RP_superpositionCotes;incorrect_mauvais_sens"))		sCode = "6302";
		if (action.equals("RP_superpositionCotes;correct"))						sCode = "6303";
		
		if (action.equals("RP_superpositionInterlignes;incorrect"))				sCode = "6400";
		if (action.equals("RP_superpositionInterlignes;incorrect_bon_sens"))	sCode = "6401";
		if (action.equals("RP_superpositionInterlignes;incorrect_mauvais_sens"))sCode = "6402";
		if (action.equals("RP_superpositionInterlignes;correct"))				sCode = "6403";
		
		if (action.equals("RF_centrageVertebre;incorrect"))						sCode = "6500";
		if (action.equals("RF_centrageVertebre;incorrect_bon_sens"))			sCode = "6501";
		if (action.equals("RF_centrageVertebre;incorrect_mauvais_sens"))		sCode = "6502";
		if (action.equals("RF_centrageVertebre;correct"))						sCode = "6503";
		
		if (action.equals("RF_symetriePedicEpineuse;incorrect"))				sCode = "6600";
		if (action.equals("RF_symetriePedicEpineuse;incorrect_bon_sens"))		sCode = "6601";
		if (action.equals("RF_symetriePedicEpineuse;incorrect_mauvais_sens"))	sCode = "6602";
		if (action.equals("RF_symetriePedicEpineuse;correct"))					sCode = "6603";
		
		if (action.equals("RF_disquesVisibles;incorrect"))						sCode = "6700";
		if (action.equals("RF_disquesVisibles;incorrect_bon_sens"))				sCode = "6701";
		if (action.equals("RF_disquesVisibles;incorrect_mauvais_sens"))			sCode = "6702";
		if (action.equals("RF_disquesVisibles;correct"))						sCode = "6703";
		
		if (action.equals("RP_EC_trocartDirectionPedic;incorrect"))				sCode = "6800";
		if (action.equals("RP_EC_trocartDirectionPedic;incorrect_bon_sens"))	sCode = "6801";
		if (action.equals("RP_EC_trocartDirectionPedic;incorrect_mauvais_sens"))sCode = "6802";
		if (action.equals("RP_EC_trocartDirectionPedic;correct"))				sCode = "6803";
		
		if (action.equals("RP_EO_trocartExtremiteProcessus;incorrect"))			sCode = "6900";
		if (action.equals("RP_EO_trocartExtremiteProcessus;incorrect_bon_sens"))sCode = "6901";
		if (action.equals("RP_EO_trocartExtremiteProcessus;incorrect_mauvais_sens"))sCode = "6902";
		if (action.equals("RP_EO_trocartExtremiteProcessus;correct"))			sCode = "6903";
		
		if (action.equals("RP_ECV_trocartMiHauteurPedic;incorrect"))			sCode = "7000";
		if (action.equals("RP_ECV_trocartMiHauteurPedic;incorrect_bon_sens"))	sCode = "7001";
		if (action.equals("RP_ECV_trocartMiHauteurPedic;incorrect_mauvais_sens"))sCode = "7002";
		if (action.equals("RP_ECV_trocartMiHauteurPedic;correct"))				sCode = "7003";
		
		if (action.equals("RP_VT_trocartPartieAnterieureCV;incorrect"))			sCode = "7100";
		if (action.equals("RP_VT_trocartPartieAnterieureCV;incorrect_bon_sens"))sCode = "7101";
		if (action.equals("RP_VT_trocartPartieAnterieureCV;incorrect_mauvais_sens"))sCode = "7102";
		if (action.equals("RP_VT_trocartPartieAnterieureCV;correct"))			sCode = "7103";
		
		if (action.equals("RP_VT_trocartSortiePedic;incorrect"))				sCode = "7200";
		if (action.equals("RP_VT_trocartSortiePedic;incorrect_bon_sens"))		sCode = "7201";
		if (action.equals("RP_VT_trocartSortiePedic;incorrect_mauvais_sens"))	sCode = "7202";
		if (action.equals("RP_VT_trocartSortiePedic;correct"))					sCode = "7203";
		
		if (action.equals("RP_EO_trocartCentrePedic;incorrect"))				sCode = "7300";
		if (action.equals("RP_EO_trocartCentrePedic;incorrect_bon_sens"))		sCode = "7301";
		if (action.equals("RP_EO_trocartCentrePedic;incorrect_mauvais_sens"))	sCode = "7302";
		if (action.equals("RP_EO_trocartCentrePedic;correct"))					sCode = "7303";
		
		if (action.equals("RF_EC_trocartInclinaison;incorrect"))				sCode = "7400";
		if (action.equals("RF_EC_trocartInclinaison;incorrect_bon_sens"))		sCode = "7401";
		if (action.equals("RF_EC_trocartInclinaison;incorrect_mauvais_sens"))	sCode = "7402";
		if (action.equals("RF_EC_trocartInclinaison;correct"))					sCode = "7403";
		
		if (action.equals("RF_EO_trocartEntreProcessusFacette;incorrect"))		sCode = "7500";
		if (action.equals("RF_EO_trocartEntreProcessusFacette;incorrect_bon_sens"))	sCode = "7501";
		if (action.equals("RF_EO_trocartEntreProcessusFacette;incorrect_mauvais_sens"))	sCode = "7502";
		if (action.equals("RF_EO_trocartEntreProcessusFacette;correct"))		sCode = "7503";
		
		if (action.equals("RF_EO_trocartExternePedic;incorrect"))				sCode = "7600";
		if (action.equals("RF_EO_trocartExternePedic;incorrect_bon_sens"))		sCode = "7601";
		if (action.equals("RF_EO_trocartExternePedic;incorrect_mauvais_sens"))	sCode = "7602";
		if (action.equals("RF_EO_trocartExternePedic;correct"))					sCode = "7603";
		
		if (action.equals("RF_ECV_trocartLimiteCentrePedic;incorrect"))			sCode = "7700";
		if (action.equals("RF_ECV_trocartLimiteCentrePedic;incorrect_bon_sens"))sCode = "7701";
		if (action.equals("RF_ECV_trocartLimiteCentrePedic;incorrect_mauvais_sens"))sCode = "7702";
		if (action.equals("RF_ECV_trocartLimiteCentrePedic;correct"))			sCode = "7703";
		
		if (action.equals("RF_ECV_trocartAvantCentrePedic;incorrect"))			sCode = "7800";
		if (action.equals("RF_ECV_trocartAvantCentrePedic;incorrect_bon_sens"))	sCode = "7801";
		if (action.equals("RF_ECV_trocartAvantCentrePedic;incorrect_mauvais_sens"))sCode = "7802";
		if (action.equals("RF_ECV_trocartAvantCentrePedic;correct"))			sCode = "7803";
		
		if (action.equals("RF_EO_trocartSurProcessus;incorrect"))				sCode = "7900";
		if (action.equals("RF_EO_trocartSurProcessus;incorrect_bon_sens"))		sCode = "7901";
		if (action.equals("RF_EO_trocartSurProcessus;incorrect_mauvais_sens"))	sCode = "7902";
		if (action.equals("RF_EO_trocartSurProcessus;correct"))					sCode = "7903";
		
		if (action.equals("RF_VT_trocartNiveauEpineuse;incorrect"))				sCode = "8000";
		if (action.equals("RF_VT_trocartNiveauEpineuse;incorrect_bon_sens"))	sCode = "8001";
		if (action.equals("RF_VT_trocartNiveauEpineuse;incorrect_mauvais_sens"))sCode = "8002";
		if (action.equals("RF_VT_trocartNiveauEpineuse;correct"))				sCode = "7003";
		
		if (action.equals("RF_VT_trocartCentrePedic;incorrect"))				sCode = "8100";
		if (action.equals("RF_VT_trocartCentrePedic;incorrect_bon_sens"))		sCode = "8101";
		if (action.equals("RF_VT_trocartCentrePedic;incorrect_mauvais_sens"))	sCode = "8102";
		if (action.equals("RF_VT_trocartCentrePedic;correct"))					sCode = "8103";
		
		if (action.equals("RF_EC_trocartExterieurLigneLongitudinal;incorrect"))	sCode = "8200";
		if (action.equals("RF_EC_trocartExterieurLigneLongitudinal;incorrect_bon_sens"))sCode = "8201";
		if (action.equals("RF_EC_trocartExterieurLigneLongitudinal;incorrect_mauvais_sens"))sCode = "8202";
		if (action.equals("RF_EC_trocartExterieurLigneLongitudinal;correct"))	sCode = "8203";
		
		if (action.equals("VRT_paralleleAxeTransversal;incorrect"))				sCode = "8300";
		if (action.equals("VRT_paralleleAxeTransversal;incorrect_bon_sens"))	sCode = "8301";
		if (action.equals("VRT_paralleleAxeTransversal;incorrect_mauvais_sens"))sCode = "8302";
		if (action.equals("VRT_paralleleAxeTransversal;correct"))				sCode = "8303";
		
		if (action.equals("VRD_superpositionAxePedicD;incorrect"))				sCode = "8400";
		if (action.equals("VRD_superpositionAxePedicD;incorrect_bon_sens"))		sCode = "8401";
		if (action.equals("VRD_superpositionAxePedicD;incorrect_mauvais_sens"))	sCode = "8402";
		if (action.equals("VRD_superpositionAxePedicD;correct"))				sCode = "8403";
		
		if (action.equals("VRD_paralleleAxePedicD;incorrect"))					sCode = "8500";
		if (action.equals("VRD_paralleleAxePedicD;incorrect_bon_sens"))			sCode = "8501";
		if (action.equals("VRD_paralleleAxePedicD;incorrect_mauvais_sens"))		sCode = "8502";
		if (action.equals("VRD_paralleleAxePedicD;correct"))					sCode = "8503";
		
		if (action.equals("VRG_superpositionAxePedicG;incorrect"))				sCode = "8600";
		if (action.equals("VRG_superpositionAxePedicG;incorrect_bon_sens"))		sCode = "8601";
		if (action.equals("VRG_superpositionAxePedicG;incorrect_mauvais_sens"))	sCode = "8602";
		if (action.equals("VRG_superpositionAxePedicG;correct"))				sCode = "8602";
		
		if (action.equals("VRG_paralleleAxePedicG;incorrect"))					sCode = "8700";
		if (action.equals("VRG_paralleleAxePedicG;incorrect_bon_sens"))			sCode = "8701";
		if (action.equals("VRG_paralleleAxePedicG;incorrect_mauvais_sens"))		sCode = "8702";
		if (action.equals("VRG_paralleleAxePedicG;correct"))					sCode = "8703";
		
		if (action.equals("VRT_superpositionAxeTransversal;incorrect"))			sCode = "8800";
		if (action.equals("VRT_superpositionAxeTransversal;incorrect_bon_sens"))sCode = "8801";
		if (action.equals("VRT_superpositionAxeTransversal;incorrect_mauvais_sens"))sCode = "8802";
		if (action.equals("VRT_superpositionAxeTransversal;correct"))			sCode = "8803";
		
	return sCode;
	}
	
}
