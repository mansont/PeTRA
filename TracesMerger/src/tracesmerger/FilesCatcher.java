package tracesmerger;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class FilesCatcher {

	public FilesCatcher(){
		
	}
	
	//verified
	public List<List<String>> parseFiles(List <String> filesToParse){
		List <List<String>> filesSequencesLists = new ArrayList<List<String>>();
		try{
			for(int i=0; i< filesToParse.size(); i++){
				filesSequencesLists.add(extractSequences(filesToParse.get(i)));
			}
		}
		catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
		//Listing extracted sequences for verification
		/*System.out.println();
		System.out.println("LIST OF EXTRACTED SEQUENCES");
		System.out.println("****************************");
		for(List<String> seqList : filesSequencesLists){
			for(String seq : seqList){
				System.out.println(seq);
			}
		}*/
		return filesSequencesLists;
	}
	
	//verified
	private List <String> extractSequences(String tracesFile){
		List <String> sequencesList = new ArrayList<String>();
		String strLine;
		try{
			FileInputStream fstream = new FileInputStream(tracesFile);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			while ((strLine = br.readLine()) != null)  {
				sequencesList.add(strLine);
			}
			in.close();
		}
		catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	return sequencesList;
	}
	
	
	/*public void extractHeader(List<String>amalgamatedSequencesList){
		int amalSeqSize = amalgamatedSequencesList.size();
		
		
		
	}*/
	
	
	
}
