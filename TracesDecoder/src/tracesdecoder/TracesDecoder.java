package tracesdecoder;

import java.util.ArrayList;
import java.util.List;

import tracesdecoder.EncodedTracesAnalyzer;
import tracesdecoder.EncodedTracesExtractor;

public class TracesDecoder {

	public static EncodedTracesExtractor encTrex = new EncodedTracesExtractor();
	public static EncodedTracesAnalyzer encTrAn = new EncodedTracesAnalyzer();
	
	public static String _HEADER = "HEADER";
	public static String sessionID = "all";
	//public static String inputTracesType = "IncorrectBalanced_";
	public static String patternsType = "rules";
	public static String miningAlgo = "CMRules_";
	public static String params = "0.1_0.7_L2R1";
	//CMRules
	//public static String encodedTracesFilePath = "C:\\TeleosTraces\\PatternedTraces\\SequentialRules\\CMRules\\"+miningAlgo+"IF_"+sessionID+"_Patterns_"+params+".txt";
	//APriori public static String encodedTracesFilePath = "C:\\TeleosTraces\\PatternedTraces\\APriori\\APriori_"+sessionID+"_Patterns.txt";
	//PrefixSpan
	public static String encodedTracesFilePath = "C:\\TeleosTraces\\PatternedTraces\\PostProcessedPatterns\\"+miningAlgo+"IF_Flex_"+sessionID+"_SelectedPatterns_"+params+".txt";
	
	public static String decodedTracesFilepath = "C:\\TeleosTraces\\DecodedTraces\\Decoded_"+miningAlgo+sessionID+"_Patterns_"+params+".txt";
	public static String formattedTracesFilepath = "C:\\TeleosTraces\\DecodedTraces\\Decoded_Formatted_"+miningAlgo+sessionID+"_Patterns_"+params+".txt";
	
	public static List<String> encodedSequencesList = new ArrayList<String>();
	public static List<String> decodedSequencesList = new ArrayList<String>();
	public static List<String> formattedSequencesList = new ArrayList<String>();
	
	public static void main(String[] args) {
		System.out.print("Extractions des séquences codées... ");
		encodedSequencesList = encTrex.extractSequences(encodedTracesFilePath);
		System.out.println("SUCCES");
		
		/*System.out.print("Décodage des séquences... ");
		decodedSequencesList = encTran.decodeSequenceSPMF(encodedSequencesList);
		System.out.println("SUCCES");
		*/
		System.out.print("Décodage des séquences... ");
		//decodage CMR
		if(patternsType.equals("rules")){
			decodedSequencesList = encTrAn.decodeAssociationRules(encodedSequencesList);
			formattedSequencesList = encTrAn.formatDecodedAssociationRules(decodedSequencesList);
		}
		else if (patternsType.equals("sequential patterns")){
			decodedSequencesList = encTrAn.decodeTimeExtendedSequences(encodedSequencesList);
		}
		else if(patternsType.equals("frequent patterns")){
			decodedSequencesList = encTrAn.decodePatternsPrefixSpan(encodedSequencesList);
		}
		else if(patternsType.equals("spmf")){
			decodedSequencesList = encTrAn.decodeSequenceSPMF(encodedSequencesList);
		}
		
		/*for(String str: decodedSequencesList){
			System.out.println(str);
		}*/
		System.out.println("SUCCES");
		
		System.out.print("Sauvegarde des séquences décodées... ");
		encTrAn.saveDecodedTraces(decodedSequencesList, decodedTracesFilepath);
		if(formattedSequencesList.size()!=0)
			encTrAn.saveDecodedTraces(formattedSequencesList, formattedTracesFilepath);
	}

}
