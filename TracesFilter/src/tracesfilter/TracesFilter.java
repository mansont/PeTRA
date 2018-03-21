package tracesfilter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class TracesFilter {
	static final File srcDirectory = new File("C:\\TeleosTraces\\RawTraces"); 
	static String destDirectory = "C:\\TeleosTraces\\FilteredTraces\\";
	
	static String srcFileName;
	static String destFileName = "";
	
	static String srcFile = "";
	static String destFile= "";
	
	static CleanedSequencesExtractor fp = new CleanedSequencesExtractor();
	static CleanedSequencesFilter sf = new CleanedSequencesFilter();
	
	static List<String> sequencesList = new ArrayList<String>();
	static List<String> filteredSequencesList = new ArrayList<String>();
	
	static int startColumn = 2;
	static int endColumn = 20;
	
	public static void main(String[] args) {
		for(final File fileEntry: srcDirectory.listFiles()){
			
			srcFileName = fileEntry.getName();
			
			if(srcFileName.matches("[0-9]{4}_Trace.txt")){
				srcFile = srcDirectory+"\\"+srcFileName;
				
				System.out.println("Fichier en cours de traitement: "+srcFile);
				
				sequencesList = fp.getSequences(srcFile);
				
				filteredSequencesList = sf.truncateUnwantedColumns(sequencesList, startColumn, endColumn);
				//System.out.println("SEQUENCES FILTREES: "+filteredSequencesList);
				destFileName = "Filtered_"+srcFileName;
				destFile = destDirectory+destFileName;
				sf.saveFilteredSequences(filteredSequencesList, destFile);
				filteredSequencesList.clear();
				sequencesList.clear();
			}
		}
	}

}
