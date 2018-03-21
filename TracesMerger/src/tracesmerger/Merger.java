package tracesmerger;
import java.util.ArrayList;
import java.util.List;


public class Merger {

	//TODO adapter la fusion si les fixations sont annotéesou non avec une durée
	static boolean fixationsWithDuration = false;
	
	static FilesCatcher fCatcher = new FilesCatcher();
	static FilesAnalyzer fAnalyzer = new FilesAnalyzer();
	
	public static String sessionID = "2327";
	static List<List<String>> sequencesLists = new ArrayList<List<String>>();
	static List <String> extendedSequencesList = new ArrayList<String>();
	static List <String> filesToParse = new ArrayList<String>();
	static String extendedSequencesFilepath;
	//static String saveFilePath = "C:\\TeleosTraces\\MergedTraces\\MergedETSimTraces\\Merged_"+sessionID+"_SimETTraces.txt";
	static String saveFilepath = "C:\\TeleosTraces\\MergedTraces\\MergedETSimTraces\\Merged_Test_Align2_"+sessionID+"_SimETTraces.txt";
	
	public Merger(){
		
	}
	
	public static void main(String[] args){
		
		filesToParse.add("C:\\TeleosTraces\\CleanedTraces\\Cleaned_"+sessionID+"_Trace.txt");
		filesToParse.add("C:\\TeleosTraces\\MergedTraces\\MergedEyeTrackingTraces\\"+sessionID+"_EyeTrackingFixFiZ.txt");
		//filesToParse.add("C:\\TeleosTraces\\BruteTraces\\TestTraces\\2269_TraceHaptique_TestVersion.txt");
		//filesToParse.add("C:\\Users\\BEM\\workspace\\TCClustering\\src\\data\\2269_EyeTrackingFiZ.txt");
		
		sequencesLists = fCatcher.parseFiles(filesToParse);
		extendedSequencesList = fAnalyzer.extendSequences(sequencesLists);
		
		
		/*String seq = "";
		for(int xtSeq= 0; xtSeq < extendedSequencesList.size(); xtSeq++){
			seq = extendedSequencesList.get(xtSeq);
			seq = seq.replaceAll(";;", ";");
			extendedSequencesList.set(xtSeq, seq);
		}*/
		System.out.println("Merging process ended successfully!");
		extendedSequencesFilepath = fAnalyzer.saveMergedSequences(extendedSequencesList, saveFilepath);
		
		filesToParse.remove(0);
		filesToParse.remove(0);
		while (!filesToParse.isEmpty()){
			filesToParse.add(0, extendedSequencesFilepath);
			sequencesLists = fCatcher.parseFiles(filesToParse);
			extendedSequencesList = fAnalyzer.extendSequences(sequencesLists);
			extendedSequencesFilepath = fAnalyzer.saveMergedSequences(extendedSequencesList, saveFilepath);
			
			filesToParse.remove(0);
			filesToParse.remove(0);
		}
		
	}
	
}
