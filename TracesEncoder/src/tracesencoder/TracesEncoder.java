package tracesencoder;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class TracesEncoder {
	
	public static SemanticTracesExtractor semTrex = new SemanticTracesExtractor();
	public static SemanticTracesAnalyzer semTran = new SemanticTracesAnalyzer();
	
	public static String sessionID = "2269";
	public static String tracesType = "IncorrectBalanced_";
	
	public static String semanticTracesFilePath = "C:\\TeleosTraces\\AnnotatedTraces\\"+tracesType+sessionID+"_AnnotatedTraces.txt";
	public static String outputEncodedBlankSeparationFilePath = "C:\\TeleosTraces\\EncodedTraces\\BlankSeparation\\Encoded_BS_"+tracesType+sessionID+"_AnnotatedTraces.txt";
	public static String outputEncodedItemsetSeparationFilePath = "C:\\TeleosTraces\\EncodedTraces\\ItemsetSeparation\\Encoded_IS_"+tracesType+sessionID+"_AnnotatedTraces.txt";
	public static String outputEncodedBasicFormatFilePath = "C:\\TeleosTraces\\EncodedTraces\\BasicFormat\\Encoded_BF_"+tracesType+sessionID+"_AnnotatedTraces.txt";
	
	public static List<String> semanticSequencesList = new ArrayList<String>();
	public static List<String> encodedBasicFormatSequencesList = new ArrayList<String>();
	public static List<String> encodedBlankSeparationSequencesList = new ArrayList<String>();
	public static List<String> encodedItemsetSeparationSequencesList = new ArrayList<String>();
	
	public static void saveEncodedBasicFormatTraces (String filepath, List<String> encodedBasicFormatTraces){
		
		try {
			PrintStream fEncodedSequences = new PrintStream(filepath);
			System.setOut(fEncodedSequences);
			
			//fEncodedSequences.println(_HEADER);
		      for(String encTrace : encodedBasicFormatTraces){
		    	 fEncodedSequences.println(encTrace);
				}
		      fEncodedSequences.close();
		   }
		      catch(IOException e1) {
		        System.out.println("Error during writing");
		   }
	}
	
	public static void saveEncodedBlankSeparationTraces (List<String> encodedBlankSeparationTraces){
		
		try {
			PrintStream fEncodedSequences = new PrintStream(outputEncodedBlankSeparationFilePath);
			System.setOut(fEncodedSequences);
			
			//fEncodedSequences.println(_HEADER);
		      for(String encTrace : encodedBlankSeparationTraces){
		    	 fEncodedSequences.println(encTrace);
				}
		      fEncodedSequences.close();
		   }
		      catch(IOException e1) {
		        System.out.println("Error during writing");
		   }
	}
	
	public static void saveEncodedItemsetSeparationTraces (List<String> encodedItemsetSeparationTraces){
		
		try {
			PrintStream fEncodedSequences = new PrintStream(outputEncodedItemsetSeparationFilePath);
			System.setOut(fEncodedSequences);
			
		      for(String encTrace : encodedItemsetSeparationTraces){
		    	 fEncodedSequences.println(encTrace);
				}
		      fEncodedSequences.close();
		   }
		      catch(IOException e1) {
		        System.out.println("Error during writing");
		   }
	}
	
	public static void main(String[] args) {
		System.out.print("Extractions des séquences... ");
		semanticSequencesList = semTrex.extractSequences(semanticTracesFilePath);
		System.out.println("SUCCES");
		
		System.out.print("Encodage des séquences... ");
		encodedBasicFormatSequencesList = semTran.encodeSequence(semanticSequencesList);
		System.out.println("SUCCES");
		
		System.out.print("Encodage des séquences au format SPMF... ");
		encodedBlankSeparationSequencesList = semTran.encodeSequenceForSPMF(semanticSequencesList);
		System.out.println("SUCCES");
		
		System.out.print("Encodage des séquences au format SPMF CMR... ");
		encodedItemsetSeparationSequencesList = semTran.encodeSequenceForSPMFCMRules(semanticSequencesList);
		System.out.println("SUCCES");
		
		System.out.print("Sauvegarde des séquences encodées... ");
		saveEncodedBasicFormatTraces(outputEncodedBasicFormatFilePath,encodedBasicFormatSequencesList);
		
		System.out.print("Sauvegarde des séquences encodées au format SPMF... ");
		saveEncodedBasicFormatTraces(outputEncodedBlankSeparationFilePath,encodedBlankSeparationSequencesList);
		
		System.out.print("Sauvegarde des séquences encodées au format CMR... ");
		saveEncodedBasicFormatTraces(outputEncodedItemsetSeparationFilePath,encodedItemsetSeparationSequencesList);
	}

}
