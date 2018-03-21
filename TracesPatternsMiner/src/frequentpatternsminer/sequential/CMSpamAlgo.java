package frequentpatternsminer.sequential;

import java.io.IOException;

import ca.pfv.spmf.algorithms.sequentialpatterns.spam.AlgoCMSPAM;


/**
 * Example of how to use the SPAM algorithm in source code.
 * @author Philippe Fournier-Viger
 */

public class CMSpamAlgo {

	static String sessionID = "all";
	static String tracesType = "IncorrectBalanced_";
	
	static double minsup = 0.2;
	
	static String inputFilePath = "C:\\TeleosTraces\\EncodedTraces\\ItemsetSeparation\\Encoded_IS"+tracesType+sessionID+"_AnnotatedTraces.txt"; //the data base
	static String outputFilePath = "C:\\TeleosTraces\\PatternedTraces\\SequentialPatterns\\CMSpam\\CMSPAM_TEST"+tracesType+sessionID+"_Patterns_"+Double.toString(minsup)+".txt";
	
	public static void main(String [] arg) throws IOException{    
		
		// Create an instance of the algorithm 
		AlgoCMSPAM algo = new AlgoCMSPAM(); 
		//algo.setMaximumPatternLength(3);
		
		// execute the algorithm with minsup = 2 sequences  (50 %)
		algo.runAlgorithm(inputFilePath, outputFilePath, minsup);     // minsup = 106   k = 1000   BMS
		algo.printStatistics();
	}
}