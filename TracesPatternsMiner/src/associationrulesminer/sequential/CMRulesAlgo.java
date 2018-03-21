package associationrulesminer.sequential;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import ca.pfv.spmf.algorithms.sequential_rules.cmrules.AlgoCMRules;
//import ca.pfv.spmf.test.MainTestCMRULES;


/**
 * Example of how to use the CMRULES algorithm from the source code.
 * @author Philippe Fournier-Viger (Copyright 2010)
 */
public class CMRulesAlgo {
	static String sessionID = "2325";
	//static String tracesType = "IncorrectBalanced_";
	static double minSup = 0.1;
	static double minConf = 0.7;
	static int minLeft = 2;
	static int minRight = 1;
	
	static String config = Double.toString(minSup)+"_"+Double.toString(minConf)+"_L"+minLeft+"R"+minRight;
	
	//static String inputFilePath = "C:\\TeleosTraces\\EncodedTraces\\EncodedSPMF\\EncodedSPMF_"+sessionID+"_Traces.txt"; //the data base
	//static String inputFilePath = "C:\\Users\\BEM\\workspace\\spmf\\src\\ca\\pfv\\spmf\\test\\contextClaSP.txt";
	static String inputFilePath = "C:\\TeleosTraces\\EncodedTraces\\ItemsetFormat\\Encoded_ItemsetFormat_"+sessionID+"_AnnotatedTraces.txt"; //the data base
	static String outputFilePath = "C:\\TeleosTraces\\PatternedTraces\\SequentialRules\\CMRules\\CMRules_IF_"+sessionID+"_Patterns_"+config+".txt";
	
	//Sequential rules from sequential patterns
	//static String inputFilePath = "C:\\TeleosTraces\\PatternedTraces\\SequentialPatterns\\FournierVigier\\FV_TIF_"+sessionID+"_Patterns.txt"; //the data base
	//static String outputFilePath = "C:\\TeleosTraces\\PatternedTraces\\SequentialRules\\CMRules\\FromPatterns\\CMRules_FromPatterns_"+sessionID+"_Patterns_"+Double.toString(minSup)+"_"+Double.toString(minConf)+".txt";
	
	public static void main(String [] arg) throws IOException{
		// Load database
		
		//String input = fileToPath("contextTest_2277.txt");  // the database
		//String  output = ".//output.txt";  // the path for saving the frequent itemsets found
		
		AlgoCMRules algo = new AlgoCMRules();
		
		// TO SET MINIMUM / MAXIMUM SIZE CONSTRAINTS you can use the following lines:
		algo.setMinLeftSize(minLeft);
		algo.setMaxLeftSize(500);
		algo.setMinRightSize(minRight);
		algo.setMaxRightSize(500);
		
		algo.runAlgorithm(inputFilePath, outputFilePath, minSup, minConf);
		
		algo.printStats();
	}
	public static String fileToPath(String filename) throws UnsupportedEncodingException{
		URL url = CMRulesAlgo.class.getResource(filename);
		 return java.net.URLDecoder.decode(url.getPath(),"UTF-8");
	}
}
