package frequentpatternsminer.closed;


import ca.pfv.spmf.algorithms.sequentialpatterns.clospan_AGP.AlgoCloSpan;
import ca.pfv.spmf.algorithms.sequentialpatterns.clospan_AGP.items.SequenceDatabase;
import ca.pfv.spmf.algorithms.sequentialpatterns.clospan_AGP.items.creators.AbstractionCreator;
import ca.pfv.spmf.algorithms.sequentialpatterns.clospan_AGP.items.creators.AbstractionCreator_Qualitative;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 * Example of how to use the algorithm CloSpan, saving the results in a given
 * file
 * @author agomariz
 */
public class CloSpanAlgo {

    /**
     * @param args the command line arguments
     */
	public static String sessionID = "2327";
	public static String tracesTypes = "IncorrectBalanced_";
	public static String inputFilePath = "C:\\TeleosTraces\\EncodedTraces\\ItemsetSeparation\\Encoded_IS_"+tracesTypes+sessionID+"_AnnotatedTraces.txt";
	public static String outputFilePath = "C:\\TeleosTraces\\PatternedTraces\\SequentialPatterns\\CloSpan\\CloSpan_"+tracesTypes+sessionID+"_Patterns.txt";
	
	
    public static void main(String[] args) throws IOException {
        // Load a sequence database
        double support = (double) 0.1;
        
        boolean keepPatterns = true;
        boolean verbose = false;
        boolean findClosedPatterns = true;
        boolean executePruningMethods = true;
        
        AbstractionCreator abstractionCreator = AbstractionCreator_Qualitative.getInstance();
        
        SequenceDatabase sequenceDatabase = new SequenceDatabase();
        
        sequenceDatabase.loadFile(inputFilePath, support);
        //sequenceDatabase.loadFile(fileToPath("contextCloSpan.txt"), support);
        //sequenceDatabase.loadFile(fileToPath("gazelle.txt"), support);
        
        //System.out.println(sequenceDatabase.toString());
        
        AlgoCloSpan algorithm = new AlgoCloSpan(support, abstractionCreator, findClosedPatterns,executePruningMethods);

        algorithm.runAlgorithm(sequenceDatabase, keepPatterns, verbose, outputFilePath);
        System.out.println(algorithm.getNumberOfFrequentPatterns() + " pattern found.");

        if (keepPatterns) {
            System.out.println(algorithm.printStatistics());
        }
    }

    public static String fileToPath(String filename) throws UnsupportedEncodingException {
        URL url = CloSpanAlgo.class.getResource(filename);
        return java.net.URLDecoder.decode(url.getPath(), "UTF-8");
    }
}
