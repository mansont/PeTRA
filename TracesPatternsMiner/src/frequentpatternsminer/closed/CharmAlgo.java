package frequentpatternsminer.closed;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import ca.pfv.spmf.algorithms.frequentpatterns.eclat_and_charm.AlgoCharm;
import ca.pfv.spmf.input.transaction_database_list_integers.TransactionDatabase;
//import ca.pfv.spmf.patterns.itemset_set_integers_with_tids.Itemsets;

/**
 * Example of how to use the CHARM algorithm from the source code.
 * @author Philippe Fournier-Viger (Copyright 2009)
 */
public class CharmAlgo {
	
	public static String sessionID = "all";
	public static String tracesTypes = "IncorrectBalanced_";
	public static String inputFilePath = "C:\\TeleosTraces\\EncodedTraces\\BlankSeparation\\Encoded_BS_"+tracesTypes+sessionID+"_AnnotatedTraces.txt";
	public static String outputFilePath = "C:\\TeleosTraces\\PatternedTraces\\FrequentPatterns\\Charm\\Charm_"+tracesTypes+sessionID+"_Patterns.txt";
	
	public static double minsup = 0.1; // means a minsup of 2 transaction (we used a relative support)
	
	public static void main(String [] arg) throws IOException{

		// Loading the transaction database
		TransactionDatabase database = new TransactionDatabase();
		try {
			database.loadFile(inputFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Applying the Charm algorithm
		AlgoCharm algo = new AlgoCharm();
		algo.runAlgorithm(outputFilePath, database, 100000, minsup, false);
		algo.printStats();
		// NOTE 1: if you  use "true" in the line above, CHARM will use
		// a triangular matrix  for counting support of itemsets of size 2.
		// For some datasets it should make the algorithm faster.
		
		// NOTE 2:  1000000 is the hashtable size used by CHARM for
		// storing itemsets.  Most users don't
		// need to change this parameter.
	}
	
	public static String fileToPath(String filename) throws UnsupportedEncodingException{
		URL url = CharmAlgo.class.getResource(filename);
		 return java.net.URLDecoder.decode(url.getPath(),"UTF-8");
	}
}
