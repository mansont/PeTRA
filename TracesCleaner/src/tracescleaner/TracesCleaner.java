package tracescleaner;

import java.util.ArrayList;
import java.util.List;


public class TracesCleaner {
	
	static List<String> rawSequencesList = new ArrayList<String>();
	static List<String> cleanedSequencesList = new ArrayList<String>();
	
	static RawTracesExtractor rawTrex = new RawTracesExtractor();
	static RawTracesAnalyzer rawTran = new RawTracesAnalyzer();
	
	//static String _HEADER = "Entete";
	static String sessionID = "2327";
	
	static String inputFilePath = "C:\\TeleosTraces\\RawTraces\\"+sessionID+"_Trace.txt";
	static String outputFilePath = "C:\\TeleosTraces\\CleanedTraces\\Cleaned_"+sessionID+"_Trace.txt";

	
	public TracesCleaner(){
		
	}
	
	public static void main(String[] args) {
		System.out.print("Extraction des séquences brutes...");
		rawSequencesList = rawTrex.extractSequences(inputFilePath);
		System.out.println("SUCCES");
		System.out.print("Nettoyage des séquences brutes...");
		cleanedSequencesList = rawTran.truncateUselessColumns(rawSequencesList);
		System.out.println("SUCCES");
		System.out.println("Sauvegarde des séquences nettoyées...");
		rawTran.saveRetainedSequences(cleanedSequencesList, outputFilePath);
		System.out.println("SUCCES");

	}

}
