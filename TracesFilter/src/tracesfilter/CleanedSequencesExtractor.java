package tracesfilter;
import java.util.*;
import java.io.*;

/**
 * A common class for traces files analyses
 * 
 * @author Ben Toussaint
 * 
 */

public class CleanedSequencesExtractor {
	
	Collections collections;
	
	List <String> sequences = new ArrayList<String>();
	int numbSequences = 0;
	
	public CleanedSequencesExtractor() {
		// TODO Auto-generated constructor stub
	}
	
	public List <String> getSequences(String tracesFile){
		try{
			FileInputStream fstream = new FileInputStream(tracesFile);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)  {
				sequences.add(strLine);
			}
			System.out.println(sequences.size());
			in.close();
		}
		catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	return sequences;
	}
}