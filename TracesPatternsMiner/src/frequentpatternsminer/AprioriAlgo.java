package frequentpatternsminer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import ca.pfv.spmf.algorithms.frequentpatterns.apriori.AlgoApriori;

/**
 * Example of how to use APRIORI algorithm from the source code.
 * @author Philippe Fournier-Viger (Copyright 2008)
 */
public class AprioriAlgo {
	//public static String inputFileFolder = "C:\\TeleosTraces\\EncodedTraces\\EncodedSPMF\\";
	public static String sessionID = "2269";
	public static String inputFilePath = "C:\\TeleosTraces\\EncodedTraces\\BlankSeparation\\Encoded_BS_"+sessionID+"_Traces.txt";
	public static String outputFilePath = "C:\\TeleosTraces\\PatternedTraces\\FrequentPatterns\\APriori\\APriori_"+sessionID+"_Patterns.txt";
	//public static String outputFileName = "Test_APrioriFP_SPMF.txt";
	
	public static void main(String [] arg) throws IOException{

		//String input = fileToPath(inputFilePath);
		//String output = outputFilePath;  // the path for saving the frequent itemsets found
		
		double minsup = 0.1; // means a minsup of 2 transaction (we used a relative support)
		
		// Applying the Apriori algorithm
		AlgoApriori apriori = new AlgoApriori();
		apriori.runAlgorithm(minsup, inputFilePath, outputFilePath);
		apriori.printStats();
	}
	
	public static String fileToPath(String filename) throws UnsupportedEncodingException{
		URL url = AprioriAlgo.class.getResource(filename);
		 return java.net.URLDecoder.decode(url.getPath(),"UTF-8");
	}
}
