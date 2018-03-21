package tracesemanticdefiner;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TracesExtractor {
	
	public TracesExtractor(){
		
	}
	
	public List <String> extractSequences(String tracesFile){
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
		sequencesList.remove(0);
	return sequencesList;
	}
}