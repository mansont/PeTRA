package tracesemanticdefiner;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import tracesemanticdefiner.VectorsAnalyzer;

public class TracesSemanticDefiner {
	
	static String _HEADER = "HEADER";
	static char _seqEnd = ' ';
	static String sessionID ="2327";
	//Traces utilisées: sans traitement zones en doublons...
	static String tracesFilepath = "C:\\TeleosTraces\\MergedTraces\\MergedETSimTraces\\Merged_Test_Align2_"+sessionID+"_SimETTraces.txt";
	//static String tracesFilePath = "C:\\TeleosTraces\\TestTracesRepository\\"+sessionID+"_Test_SimET.txt";
	static String tracesSaveFilepath = "C:\\TeleosTraces\\SemanticTraces\\Semantic_Test_"+sessionID+"_Traces.txt";
	static String [] sequenceElementsTab;
	
	static List<String> sequencesLists = new ArrayList<String>();
	static List<String> semanticSequencesList = new ArrayList<String>();
	static List<String> cleanedSemanticSequencesList = new ArrayList<String>();
	//public static List<String> tracesSemanticInfoList = new ArrayList<String>();
	static String semanticSequence = "";
	
	static List<List<Double>> previousSequenceElements = new ArrayList<List<Double>>();
	static List<List<Double>> currentSequenceElements = new ArrayList<List<Double>>();
	static List<String> trocarMovesSemantic = new ArrayList<String>();
	static List <String> trocarOrientationSemantic = new ArrayList<String>();
	static List<String> fluoroscopeFaceMovesSemantic = new ArrayList<String>();
	static List<String> fluoroscopeProfileMovesSemantic = new ArrayList<String>();
	static List<String> fluoroscopeFaceOrientationSemantic = new ArrayList<String>();
	static List<String> fluoroscopeProfileOrientationSemantic = new ArrayList<String>();
	static List<String> transverseMarkSemantic = new ArrayList<String>();
	static List<String> rightMarkSemantic = new ArrayList<String>();
	static List<String> leftMarkSemantic = new ArrayList<String>();
	static String fixationSemantic;
	
	static String timecode;
	//static String aoi;
	//static String [] aoi_poi;
	static String actionName;
	static String fixationString;
	
	static List<Double> fluoroscopeFace_params_c = new ArrayList<Double>();
	static List<Double> fluoroscopeProfile_params_c = new ArrayList<Double>();
	static List<Double> fluoroscopeFace_params_p = new ArrayList<Double>();
	static List<Double> fluoroscopeProfile_params_p = new ArrayList<Double>();
	static List<Double> cutaneousMarks_params_p = new ArrayList<Double>();
	static List<Double> cutaneousMarks_params_c = new ArrayList<Double>();
	static List<Double> trocar_params_p = new ArrayList<Double>();
	static List<Double> trocar_params_c = new ArrayList<Double>();
	static List<Double> speed_params = new ArrayList<Double>();
	static List<Double> force_params = new ArrayList<Double>();
	static String fixations = "";
	
	static int indexFfparams		= 0;
	static int indexFpparams		= 1;
	static int indexTparams 		= 2;
	static int indexSpparams 	= 3;
	static int indexFoparams 	= 4;
	static int indexCMparams 	= 5;
	//static int indexAOIPOI 	= 6;
	
	static String _sequence = "";
	static TracesExtractor tAnalyzer = new TracesExtractor();
	static VectorsAnalyzer vAnalyzer = new VectorsAnalyzer();
	static ToolsCoordinatesExtractor tCoordExtractor = new ToolsCoordinatesExtractor();

	/*public static String cleanSemanticSequence(String semanticSequence, String noise, String replacement){
		String unnoisedSemanticSequence = "";
		String cleanedSemanticSequence = "";
		String [] sequenceTokens;
		String currentRepetition = "";
		unnoisedSemanticSequence = semanticSequence.replaceAll(noise, replacement);
		
		sequenceTokens = unnoisedSemanticSequence.split(";");
		
		cleanedSemanticSequence = cleanedSemanticSequence.concat(sequenceTokens[0]+";");
		for(int i=1; i< sequenceTokens.length-1; i++){
			if(sequenceTokens[i].equals(sequenceTokens[i-1])){
				continue;
			}
			else{
				cleanedSemanticSequence = cleanedSemanticSequence.concat(sequenceTokens[i]+";");
			}
		}
		
		if(!sequenceTokens[sequenceTokens.length-1].equals(sequenceTokens[sequenceTokens.length-2])){
			cleanedSemanticSequence = cleanedSemanticSequence.concat(sequenceTokens[sequenceTokens.length-1]);
		}
		
		return cleanedSemanticSequence;
	}*/
	
	public static void saveSemantifiedTraces(List<String>semanticInfo){
		String filePath = tracesSaveFilepath;
		//Collections.sort(mergedSequencesList);
		try {
			PrintStream fTracesSemanticInfo = new PrintStream(filePath);
			System.setOut(fTracesSemanticInfo);
			
			//fTracesSemanticInfo.println(_HEADER);
	        //Write semantic sequences to file
		      for(String semInfo : semanticInfo){
		    	 fTracesSemanticInfo.println(semInfo);
				}
		      fTracesSemanticInfo.close();
		   }
		      catch(IOException e1) {
		        System.out.println("Error during writing");
		   }
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sequencesLists = tAnalyzer.extractSequences(tracesFilepath);
		boolean check_change = false;
		//String previousTimecode;
		//String previousActionName;
		for(int iSeq=0; iSeq < sequencesLists.size(); iSeq++){
			if(iSeq-1 == 0){
				timecode = tCoordExtractor.getTimecode();
				actionName = tCoordExtractor.getActionName();
				fixations = tCoordExtractor.getFixations();
				semanticSequence = semanticSequence.concat(timecode+";"+actionName+";"+fixations);
				semanticSequencesList.add(semanticSequence);
				continue;
			}
			if(iSeq>0){
				//extract previous sequence coordinates elements
				previousSequenceElements = tCoordExtractor.extractSequenceTokens(sequencesLists.get(iSeq-1));
				//previousAOI = tCoordExtractor.getAOI();
				//previousTimecode = tCoordExtractor.getTimecode();
				
				
				trocar_params_p = previousSequenceElements.get(indexTparams);
				fluoroscopeFace_params_p = previousSequenceElements.get(indexFfparams);
				fluoroscopeProfile_params_p = previousSequenceElements.get(indexFpparams);
				cutaneousMarks_params_p = previousSequenceElements.get(indexCMparams);
				
				//extract current sequence coordinates elements
				currentSequenceElements = tCoordExtractor.extractSequenceTokens(sequencesLists.get(iSeq));
				timecode = tCoordExtractor.getTimecode();
				actionName = tCoordExtractor.getActionName();
				//aoi = tCoordExtractor.getAOI();
				trocar_params_c = currentSequenceElements.get(indexTparams);
				fluoroscopeFace_params_c = currentSequenceElements.get(indexFfparams);
				fluoroscopeProfile_params_c = currentSequenceElements.get(indexFpparams);
				cutaneousMarks_params_c = currentSequenceElements.get(indexCMparams);
				fixations = tCoordExtractor.getFixations();
				//define each tool change semantic
				trocarMovesSemantic = vAnalyzer.defineTrocarMoves(trocar_params_p, trocar_params_c);
				trocarOrientationSemantic = vAnalyzer.defineTrocarOrientation(trocar_params_p, trocar_params_c);
				fluoroscopeFaceMovesSemantic = vAnalyzer.defineFluoroscopeFaceMoves(fluoroscopeFace_params_p, fluoroscopeFace_params_c);
				fluoroscopeProfileMovesSemantic = vAnalyzer.defineFluoroscopeProfileMoves(fluoroscopeProfile_params_p, fluoroscopeProfile_params_c);
				fluoroscopeFaceOrientationSemantic = vAnalyzer.defineFluoroscopeFaceOrientation(fluoroscopeFace_params_p, fluoroscopeFace_params_c);
				fluoroscopeProfileOrientationSemantic = vAnalyzer.defineFluoroscopeProfileOrientation(fluoroscopeProfile_params_p, fluoroscopeProfile_params_c);
				
				transverseMarkSemantic = vAnalyzer.defineTransverseMarkMoves(cutaneousMarks_params_p, cutaneousMarks_params_c);
				rightMarkSemantic = vAnalyzer.defineRightMarkMoves(cutaneousMarks_params_p, cutaneousMarks_params_c);
				leftMarkSemantic = vAnalyzer.defineLeftMarkMoves(cutaneousMarks_params_p, cutaneousMarks_params_c);
				
				fixationSemantic = vAnalyzer.defineFixationSemantic(fixations);
				System.out.println(fixationSemantic);
				if(trocarMovesSemantic.size()> 0
					|| trocarOrientationSemantic.size()>0
					|| fluoroscopeFaceMovesSemantic.size()>0
					|| fluoroscopeFaceOrientationSemantic.size()>0
					|| fluoroscopeProfileMovesSemantic.size()>0
					|| fluoroscopeProfileOrientationSemantic.size()>0
					|| transverseMarkSemantic.size()>0
					|| leftMarkSemantic.size()>0
					|| rightMarkSemantic.size()>0)
					check_change = true;
				else check_change = false;
				
				if(check_change) {
					if(!semanticSequence.isEmpty())
						semanticSequencesList.add(semanticSequence);
					
					semanticSequence = "";
					semanticSequence = semanticSequence.concat(timecode+";");
					semanticSequence = semanticSequence.concat(actionName+";");
				
				if(fluoroscopeFaceMovesSemantic.size()>0){
					for(String semInfo: fluoroscopeFaceMovesSemantic){
						semanticSequence = semanticSequence.concat(semInfo+";");
					}
				}
				if(fluoroscopeFaceOrientationSemantic.size()>0){
					for(String semInfo: fluoroscopeFaceOrientationSemantic){
						semanticSequence = semanticSequence.concat(semInfo+";");
					}
				}
				if(fluoroscopeProfileMovesSemantic.size()>0){
					for(String semInfo: fluoroscopeProfileMovesSemantic){
						semanticSequence = semanticSequence.concat(semInfo+";");
					}
				}
				if(fluoroscopeProfileOrientationSemantic.size()>0){
					for(String semInfo: fluoroscopeProfileOrientationSemantic){
						semanticSequence = semanticSequence.concat(semInfo+";");
					}
				}
				if(trocarMovesSemantic.size()>0){
					for(String semInfo: trocarMovesSemantic){
						semanticSequence = semanticSequence.concat(semInfo+";");
					}
				}
				if(trocarOrientationSemantic.size()>0){
					for(String semInfo: trocarOrientationSemantic){
						semanticSequence = semanticSequence.concat(semInfo+";");
					}
				}
				if(transverseMarkSemantic.size()>0){
					for(String semInfo: transverseMarkSemantic){
						semanticSequence = semanticSequence.concat(semInfo+";");
					}
				}
				if(rightMarkSemantic.size()>0){
					for(String semInfo: rightMarkSemantic){
						semanticSequence = semanticSequence.concat(semInfo+";");
					}
				}
				if(leftMarkSemantic.size()>0){
					for(String semInfo: leftMarkSemantic){
						semanticSequence = semanticSequence.concat(semInfo+";");
					}
				}
			}
			if(!fixationSemantic.isEmpty())
				semanticSequence = semanticSequence.concat(fixationSemantic);
			
		}
			
	}
		//System.out.println(fixationSemantic);
		System.out.println(semanticSequencesList);
		System.out.println("Semantization process ended successfully!");
		
		//Correction d'un bug : O_manip_Face; O_manip_Reglage => fusion
		
		/*for(String semSeq: semanticSequencesList){
			semSeq = cleanSemanticSequence(semSeq, "O_manipFace;O_manipReglage;", "O_manipReglage;");
			cleanedSemanticSequencesList.add(semSeq);
		}*/
		
		saveSemantifiedTraces(semanticSequencesList);
		
	}

}
