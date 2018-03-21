package frequentpatternsminer.sequential;


import ca.pfv.spmf.algorithms.sequentialpatterns.spade_spam_AGP.AlgoCMSPADE;
import ca.pfv.spmf.algorithms.sequentialpatterns.spade_spam_AGP.candidatePatternsGeneration.CandidateGenerator;
import ca.pfv.spmf.algorithms.sequentialpatterns.spade_spam_AGP.candidatePatternsGeneration.CandidateGenerator_Qualitative;
import ca.pfv.spmf.algorithms.sequentialpatterns.spade_spam_AGP.dataStructures.creators.AbstractionCreator;
import ca.pfv.spmf.algorithms.sequentialpatterns.spade_spam_AGP.dataStructures.creators.AbstractionCreator_Qualitative;
import ca.pfv.spmf.algorithms.sequentialpatterns.spade_spam_AGP.dataStructures.database.SequenceDatabase;
import ca.pfv.spmf.algorithms.sequentialpatterns.spade_spam_AGP.idLists.creators.IdListCreator;
import ca.pfv.spmf.algorithms.sequentialpatterns.spade_spam_AGP.idLists.creators.IdListCreator_FatBitmap;

import java.io.IOException;

/**
 * Example of how to use the algorithm SPADE, saving the results in a given
 * file
 * @author agomariz
 */

public class CMSpadeAlgo {

    /**
     * @param args the command line arguments
     */
	static String sessionID = "all";
	static String tracesType = "IncorrectBalanced_";
	
	static double support = 0.1;
	
	static String inputFilePath = "C:\\TeleosTraces\\EncodedTraces\\ItemsetSeparation\\Encoded_IS_"+tracesType+sessionID+"_AnnotatedTraces.txt"; //the data base
	static String outputFilePath = "C:\\TeleosTraces\\PatternedTraces\\SequentialRules\\CMSpade_TEST"+tracesType+sessionID+"_Patterns_"+Double.toString(support)+".txt";
	
    public static void main(String[] args) throws IOException {
        
    	boolean keepPatterns = true;
        boolean verbose = false;
        
        AbstractionCreator abstractionCreator = AbstractionCreator_Qualitative.getInstance();
        boolean dfs=true;
        
        IdListCreator idListCreator = IdListCreator_FatBitmap.getInstance();
                
        CandidateGenerator candidateGenerator = CandidateGenerator_Qualitative.getInstance();
        
        SequenceDatabase sequenceDatabase = new SequenceDatabase(abstractionCreator, idListCreator);
        
        sequenceDatabase.loadFile(inputFilePath, support);
        
        System.out.println(sequenceDatabase.toString());
        
        AlgoCMSPADE algorithm = new AlgoCMSPADE(support,dfs,abstractionCreator);
        
        algorithm.runAlgorithm(sequenceDatabase, candidateGenerator,keepPatterns,verbose,outputFilePath);

        System.out.println("Relative Minimum support = "+support);
        System.out.println(algorithm.getNumberOfFrequentPatterns()+ " frequent patterns.");
        
        System.out.println(algorithm.printStatistics());
    }
}
