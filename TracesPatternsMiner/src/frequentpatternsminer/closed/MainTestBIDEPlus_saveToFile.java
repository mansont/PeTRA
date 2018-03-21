package frequentpatternsminer.closed;

import java.io.IOException;
import ca.pfv.spmf.algorithms.sequentialpatterns.BIDE_and_prefixspan.AlgoBIDEPlus;
import ca.pfv.spmf.input.sequence_database_list_integers.SequenceDatabase;
/*
 * Example of how to use the BIDE+ algorithm, from the source code.
 */
public class MainTestBIDEPlus_saveToFile {

	static String sessionID = "all";
	static String tracesType = "IncorrectBalanced_";
	
	static int minsup = 2;
	
	static String inputFilePath = "C:\\TeleosTraces\\EncodedTraces\\EncodedSPMF\\CMR\\EncodedCMR_TEST"+tracesType+sessionID+"_AnnotatedTraces.txt"; //the data base
	static String outputFilePath = "C:\\TeleosTraces\\PatternedTraces\\SequentialRules\\BIDE+\\BIDE_TEST"+tracesType+sessionID+"_Patterns_"+Integer.toString(minsup)+".txt";
	
	public static void main(String [] arg) throws IOException{    
		// Load a sequence database
		SequenceDatabase sequenceDatabase = new SequenceDatabase(); 
		sequenceDatabase.loadFile(inputFilePath);
		sequenceDatabase.print();
		
		AlgoBIDEPlus algo  = new AlgoBIDEPlus();  //
		
		// execute the algorithm
		algo.runAlgorithm(sequenceDatabase, outputFilePath, minsup);    
		algo.printStatistics(sequenceDatabase.size());
	}
}