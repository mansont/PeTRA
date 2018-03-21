package tracescleaner;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RawTracesAnalyzer {

	
	public RawTracesAnalyzer() {
		// TODO Auto-generated constructor stub
	}
	
	public List<String> truncateUselessColumns(List<String> rawSequencesList){
		List <String> truncatedSequencesList = new ArrayList<String>();
		String [] rawSequenceElementsTab;
		String rawSeqElt = "";
		String truncatedSequence = "";
		for (String rawSeq: rawSequencesList){
			truncatedSequence = "";
			rawSequenceElementsTab = rawSeq.split(";");
			for(int i= 0; i < rawSequenceElementsTab.length-3; i++){
				if (!rawSequenceElementsTab[i].isEmpty()){
					rawSeqElt = rawSequenceElementsTab[i];
					truncatedSequence = truncatedSequence.concat(rawSeqElt+";");
				}
			}
			truncatedSequencesList.add(truncatedSequence);
		}
	return truncatedSequencesList;
	}
	
	private String extractTimecode(String sequence){
		String timecode;
		String [] sequenceElements;
		
		sequenceElements = sequence.split(";");
		timecode = sequenceElements[0];
		
		return timecode;
	}
	
	//Fusionner les séquences simultanées
	public List<String> mergeSimultaneousSequences(List<String> sequencesList){
		String timecode;
		String currentSequence;
		List <String> simultaneousSequencesList = new ArrayList<String>();
		List <String> retainedSequencesList = new ArrayList<String>();
		String retainedSeq;
		int sizeSimultSeq = 0;
		
		Collections.sort(sequencesList);
		
			for(int i=0; i<sequencesList.size(); i++){
				currentSequence = sequencesList.get(i);
				timecode = extractTimecode(currentSequence);
				
				if(simultaneousSequencesList.isEmpty()){
					simultaneousSequencesList.add(currentSequence);
					continue;
				}
				else{
					if(timecode.equals(extractTimecode(sequencesList.get(i-1)))){
						simultaneousSequencesList.add(currentSequence);
					}
					else{
						sizeSimultSeq = simultaneousSequencesList.size();
						retainedSeq = simultaneousSequencesList.get(sizeSimultSeq-1);
						retainedSequencesList.add(retainedSeq);
						simultaneousSequencesList.clear();
						simultaneousSequencesList.add(currentSequence);
					}
				}
			}
		return retainedSequencesList;
	}
	
	public void saveRetainedSequences(List <String> cleanedSequencesList, String outputFilepath){
		try {
			PrintStream fCleanedSequences = new PrintStream(outputFilepath);
			System.setOut(fCleanedSequences);
			
			//fCleanedSequences.println(_HEADER);
		      for(String clSeq : cleanedSequencesList){
		    	 fCleanedSequences.println(clSeq);
				}
		      fCleanedSequences.close();
		   }
		      catch(IOException e1) {
		        System.out.println("Error during writing");
		   }
	}
}
