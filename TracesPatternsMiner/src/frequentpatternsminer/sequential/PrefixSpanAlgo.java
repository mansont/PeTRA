package frequentpatternsminer.sequential;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import ca.pfv.spmf.algorithms.sequentialpatterns.BIDE_and_prefixspan.AlgoPrefixSpan;
import ca.pfv.spmf.input.sequence_database_list_integers.SequenceDatabase;


/**
 * Example of how to use the PrefixSpan algorithm in source code.
 * @author Philippe Fournier-Viger
 */
public class PrefixSpanAlgo {

	static String sessionID = "all";
	static String tracesType = "IncorrectBalanced_";
	
	static double minsup = 0.1;
	
	static String inputFilePath = "C:\\TeleosTraces\\EncodedTraces\\ItemsetSeparation\\Encoded_IS_"+tracesType+sessionID+"_AnnotatedTraces.txt"; //the data base
	static String outputFilePath = "C:\\TeleosTraces\\PatternedTraces\\SequentialPatterns\\PrefixSpan\\PrefixSpan_"+tracesType+sessionID+"_Patterns_"+Double.toString(minsup)+".txt";
	
	public static void main(String [] arg) throws IOException{    
		//String outputPath = ".//output.txt";
		// Load a sequence database
		SequenceDatabase sequenceDatabase = new SequenceDatabase(); 
		//sequenceDatabase.loadFile(fileToPath("contextPrefixSpan.txt"));
		sequenceDatabase.loadFile(inputFilePath);
		// print the database to console
		sequenceDatabase.print();
		
		// Create an instance of the algorithm with minsup = 50 %
		AlgoPrefixSpan algo = new AlgoPrefixSpan(); 
		
		//int minsup = 2; // we use a minimum support of 2 sequences.
		
		// execute the algorithm
		algo.runAlgorithm(sequenceDatabase, minsup, outputFilePath);    
		algo.printStatistics(sequenceDatabase.size());
	}
	
	public static String fileToPath(String filename) throws UnsupportedEncodingException{
		URL url = PrefixSpanAlgo.class.getResource(filename);
		 return java.net.URLDecoder.decode(url.getPath(),"UTF-8");
	}
}