package tracesmerger;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilesAnalyzer {

	public FilesAnalyzer(){
		
	}
	
	List <List<String>> sequencesLists = new ArrayList<List<String>>();
	
	
	private List<List<String>> labelSequences(){
		List <List<String>> labeledSequencesLists = new ArrayList<List<String>>();
		List <String> sequencesFileX = new ArrayList<String>();
		List <String> sequencesFileY = new ArrayList<String>();
		
		sequencesFileX = sequencesLists.get(0);
		sequencesFileY = sequencesLists.get(1);
		
		String seq;
		
		//Remove header X
		sequencesFileX.remove(0);
		for(int x=0; x<sequencesFileX.size(); x++){
			seq = sequencesFileX.get(x)+";X";
			sequencesFileX.set(x, seq);
		}
		labeledSequencesLists.add(sequencesFileX);
		
		//Remove header Y
		sequencesFileY.remove(0);
		for(int y=0; y<sequencesFileY.size(); y++){
			seq = sequencesFileY.get(y)+";Y";
			sequencesFileY.set(y, seq);
		}
		labeledSequencesLists.add(sequencesFileY);
		
		//Listing labeled sequences for verification
		/*System.out.println();System.out.println();
		System.out.println("LIST OF LABELED SEQUENCES");
		System.out.println("****************************");
		for(List<String> labSeqList : labeledSequencesLists){
			for(String labSeq : labSeqList){
				System.out.println(labSeq);
			}
		}*/
		return labeledSequencesLists;
	}
	
	
	public List<String> mixSequences(){
		List<List<String>> labeledSequencesToMergeList = new ArrayList<List<String>>();
		List<String> mixedSequencesList = new ArrayList<String>();
		
		labeledSequencesToMergeList = labelSequences();
		
		List <String> labeledSequencesFileX = new ArrayList<String>();
		List <String> labeledSequencesFileY = new ArrayList<String>();
		
		labeledSequencesFileX = labeledSequencesToMergeList.get(0);
		labeledSequencesFileY = labeledSequencesToMergeList.get(1);
		
		for(String lbSeqX : labeledSequencesFileX){
			mixedSequencesList.add(lbSeqX);
		}
		for(String lbSeqY : labeledSequencesFileY){
			mixedSequencesList.add(lbSeqY);
		}
		Collections.sort(mixedSequencesList);
		
	return mixedSequencesList;
	}
	
public List<String> mergeSequences(List<List<String>> sequencesLists){
	
	this.sequencesLists = sequencesLists;
	
	List <String> bufferX = new ArrayList<String>();
	List <String> bufferY = new ArrayList<String>();
	List <String> mixedSequences = new ArrayList<String>();
	List <String> mergedSequencesList = new ArrayList<String>();
	String label;
	String timecode;
		
	mixedSequences = mixSequences();
		
	for(String mixedSeq : mixedSequences){
		label = extractLabel(mixedSeq);
		if(!label.equals("X")){
			bufferY.add(mixedSeq);
			continue;
		}
		if(label.equals("X") && !bufferY.isEmpty()){
			String truncatedSeqX;
			String truncatedSeqY;
			//String noLabeledSeq;
			timecode = "";
			for(String yElt : bufferY){
				timecode = extractTimecode(yElt);
				truncatedSeqX = truncateSequence(mixedSeq);
				truncatedSeqY = truncateSequence(yElt);
				mergedSequencesList.add(timecode+";"+truncatedSeqX+truncatedSeqY);
			}
			timecode = extractTimecode(mixedSeq);
			truncatedSeqX = truncateSequence(mixedSeq);
			truncatedSeqY = truncateSequence(bufferY.get(bufferY.size()-1));
			mergedSequencesList.add(timecode+";"+truncatedSeqX+truncatedSeqY);
			if(!bufferX.isEmpty()){
				for(String xElt : bufferX){
					timecode = extractTimecode(bufferY.get(0));
					truncatedSeqX = truncateSequence(xElt);
					truncatedSeqY = truncateSequence(bufferY.get(0));
					mergedSequencesList.add(timecode+";"+truncatedSeqX+truncatedSeqY);
				}
				bufferX.clear();
			}
		}
		if(label.equals("X") && bufferY.isEmpty()){
			bufferX.add(mixedSeq);
			continue;
		}
		bufferY.clear();
	}
	return mergedSequencesList;
	}
	
	public List<String> extendSequences(List<List<String>> sequencesLists){
		this.sequencesLists = sequencesLists;
		List <String> bufferX = new ArrayList<String>();
		List <String> bufferY = new ArrayList<String>();
		List <String> mixedSequences = new ArrayList<String>();
		List <String> extendedSequencesList = new ArrayList<String>();
		String targettedSequenceX = "";
		String extendedSequence = "";
		String unlabelledSequence = "";
		String truncatedSequence = "";
		String [] sequenceTokens;
		String simultaneousFixations = "";
		String label;
		mixedSequences = mixSequences();
		
		for(String mixedSeq : mixedSequences){
			label = extractLabel(mixedSeq);
			if(label.equals("Y") && !bufferX.isEmpty()){
				bufferY.add(mixedSeq);
				continue;
			}
			else if(label.equals("X") && !bufferX.isEmpty()){
				targettedSequenceX = bufferX.get(0);
				unlabelledSequence = removeLabel(targettedSequenceX);
				extendedSequence = unlabelledSequence+";";
				if(!bufferY.isEmpty()){
					for(String yElt : bufferY){
						truncatedSequence = truncateSequence(yElt);
						sequenceTokens = truncatedSequence.split(";");
					
						if(sequenceTokens.length > 1){
							simultaneousFixations = "(";
							for(int i=0; i < sequenceTokens.length-1; i++){
								simultaneousFixations = simultaneousFixations.concat(sequenceTokens[i]+" ");
							}
							simultaneousFixations = simultaneousFixations.concat(sequenceTokens[sequenceTokens.length-1]+")");
							extendedSequence = extendedSequence.concat(simultaneousFixations+";");
						}
						else{
							extendedSequence = extendedSequence.concat(truncatedSequence+";");
						}
					}
					
					extendedSequencesList.add(extendedSequence);
					bufferX.clear();
					bufferX.add(mixedSeq);
					bufferY.clear();
					extendedSequence = "";
				}
				else{
					extendedSequencesList.add(unlabelledSequence);
					bufferX.clear();
					bufferX.add(mixedSeq);
				}
			}
			else if(label.equals("X") && bufferX.isEmpty()){
				bufferX.add(mixedSeq);
				continue;
			}
		}
		extendedSequencesList = cleanPunctuation(extendedSequencesList);
	return extendedSequencesList;
	}


	private String extractLabel(String sequence){
		String label;
		String [] sequenceElements;
		
		sequenceElements = sequence.split(";");
		label = sequenceElements[sequenceElements.length-1];
		
		return label;
	}
	
	private String extractTimecode(String sequence){
		String timecode;
		String [] sequenceElements;
		
		sequenceElements = sequence.split(";");
		timecode = sequenceElements[0];
		
		return timecode;
	}
	
	private String truncateSequence(String sequence){
		String subString="";
		String [] sequenceElements;
		
		sequenceElements = sequence.split(";");
		
		for(int i=1; i<=sequenceElements.length-3; i++){
			subString = subString.concat(sequenceElements[i]+";");
		}
		subString = subString.concat(sequenceElements[sequenceElements.length-2]);
		return subString;
	}
	
	private String removeLabel(String sequence){
		String subStringNolabel="";
		String [] sequenceElements;
		sequence = sequence.replaceAll(";;", ";");
		sequenceElements = sequence.split(";");
		
		for(int i=0; i < sequenceElements.length-2; i++){
			subStringNolabel = subStringNolabel.concat(sequenceElements[i]+";");
		}
		subStringNolabel = subStringNolabel.concat(sequenceElements[sequenceElements.length-2]);
		return subStringNolabel;
	}
	
	private List<String> cleanPunctuation(List<String> seqList){
		List<String> cleanedSequencesList = new ArrayList<String>();
		String [] seqElts;
		String cleanedSeq;
		
		for(String seq: seqList){
			cleanedSeq = "";
			seq = seq.replaceAll(";;", ";");
			seqElts = seq.split(";");
			
			for(int i=0; i < seqElts.length-1; i++){
				cleanedSeq = cleanedSeq.concat(seqElts[i]+";");
			}
			cleanedSeq = cleanedSeq.concat(seqElts[seqElts.length-1]);
			cleanedSequencesList.add(cleanedSeq);
		}
		return cleanedSequencesList;
	}
	
	public String saveMergedSequences(List <String> mergedSequencesList, String saveFilePath){
		Collections.sort(mergedSequencesList);
		try {
			PrintStream fGeneratedStampedClusters = new PrintStream(saveFilePath);
			System.setOut(fGeneratedStampedClusters);
			
			fGeneratedStampedClusters.println("Entete");
	        //Write stamped clusters to file
		      for(String scl : mergedSequencesList){
		    	 fGeneratedStampedClusters.println(scl);
				}
		      fGeneratedStampedClusters.close();
		   }
		      catch(IOException e1) {
		        System.out.println("Error during writing");
		   }
		return saveFilePath;
	}
}
