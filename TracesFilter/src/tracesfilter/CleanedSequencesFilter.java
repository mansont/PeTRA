package tracesfilter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;


public class CleanedSequencesFilter {

	
	public CleanedSequencesFilter(){
		
	}
	
	public List <String> truncateUnwantedColumns(List <String> sequencesList, int startColumn, int endColumn){
		List<String> filteredSequencesList = new ArrayList<String>();
		String [] sequenceElements;
		String [] headerElements;
		String filteredSequence= "";
		String filteredHeader ="";
		
		for(String seq : sequencesList){
			
			if(seq.equals(sequencesList.get(0))){
				headerElements = seq.split(";");
				for(int i=0; i < headerElements.length; i++){
					if(i < startColumn || i > endColumn){
						filteredHeader = filteredHeader.concat(headerElements[i]+";");
					}
				}
			filteredSequencesList.add(filteredHeader);
			}
			else{
				sequenceElements = seq.split(";");
				filteredSequence ="";
				for(int i=0; i < sequenceElements.length-3; i++){
					if(i < startColumn || i > endColumn){
						filteredSequence = filteredSequence.concat(sequenceElements[i]+";");
					}
				}
			filteredSequencesList.add(filteredSequence);
			}
		}
		
		return filteredSequencesList;
	}
	
	public void saveFilteredSequences (List<String> filteredSequencesList, String tracesDestinationFilePath){
		//String tracesSrcFile = "C:\\TeleosTraces\\FilteredTraces\\";
		//Collections.sort(filteredSequencesList);
		String fsl="";
		try {
			PrintStream filteredSeqFile = new PrintStream(tracesDestinationFilePath);
			System.setOut(filteredSeqFile);
			//System.out.println(filteredSequencesList);
			filteredSeqFile.println(filteredSequencesList.get(0));
		    for(int i = 1; i < filteredSequencesList.size(); i++){
		    	fsl = filteredSequencesList.get(i);
		    	if(!fsl.isEmpty()) filteredSeqFile.println(fsl);
			}
		    filteredSeqFile.close();
		    filteredSequencesList.clear();
		}
		catch(IOException e1) {
			System.out.println("Writing error");
		}
	}
	
	
}
