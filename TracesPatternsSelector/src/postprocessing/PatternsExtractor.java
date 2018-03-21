package postprocessing;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PatternsExtractor {
	
	public PatternsExtractor(){
		
	}
	
	public List <String> extractPatterns(String tracesFile){
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
		//sequencesList.remove(0); enlever l'entete
	return sequencesList;
	}

	
	/*public List<String> filterPatterns(List<String>frequentPatternsList, String patternsType){
		List<String> extractedPatternsList = new ArrayList<>();
		
		if(patternsType.equalsIgnoreCase("sequential patterns")){
		//TODO procédure de séparation des patterns des autres infos
		}
		if(patternsType.equalsIgnoreCase("sequential rules")){
				
		}
		
		return extractedPatternsList;
	}*/
	
}
