package frequentpatternsminer.sequential;

import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URL;

import ca.pfv.spmf.algorithms.sequentialpatterns.fournier2008_seqdim.AlgoFournierViger08;
import ca.pfv.spmf.algorithms.sequentialpatterns.fournier2008_seqdim.SequenceDatabase;

/**
 * Example of sequential pattern mining with time constraints.
 * @author Philippe Fournier-Viger
 */
public class FVSequentialPatternMinerAlgo {

	static String sessionID = "all";
	static String annotationType = "IncorrectBalanced_";
	
	static double minsup = 0.5;
	
	static String inputFilePath = "C:\\TeleosTraces\\EncodedTraces\\TemporalItemsetFormat\\Encoded_TemporalItemsetFormat_"+sessionID+"_AnnotatedTraces.txt";
	static String outputFilePath = "C:\\TeleosTraces\\PatternedTraces\\SequentialPatterns\\FournierVigier\\FV_TIF_new_"+sessionID+"_Patterns.txt";
	
	public static void main(String [] arg) throws IOException{    
				// Load a sequence database
		SequenceDatabase sequenceDatabase = new SequenceDatabase(); 
		sequenceDatabase.loadFile(inputFilePath);
		// Create an instance of the algorithm
		AlgoFournierViger08 algo
		  = new AlgoFournierViger08(minsup, 0, 1, 0, Double.MAX_VALUE, null,  true, true);
		
		// execute the algorithm
		algo.runAlgorithm(sequenceDatabase, outputFilePath);    
		algo.printStatistics();
	}
	
/*	public static String fileToPath(String filename) throws UnsupportedEncodingException{
		URL url = MainTestSequentialPatternMining2_saveToMemory.class.getResource(filename);
		 return java.net.URLDecoder.decode(url.getPath(),"UTF-8");
	}*/
}




