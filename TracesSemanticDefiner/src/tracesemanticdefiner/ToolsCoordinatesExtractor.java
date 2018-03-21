package tracesemanticdefiner;

import java.util.ArrayList;
import java.util.List;

public class ToolsCoordinatesExtractor {
	private String [] sequenceTokens;
	
	private int indexTimecode 				= 0;
	private int indexActionName 			= 1;
	private int indexFluoroscopeFaceCoord_start 	= 8;
	private int indexFluoroscopeFaceCoord_end 		= 13;
	private int indexFluoroscopeProfileCoord_start 	= 14;
	private int indexFluoroscopeProfileCoord_end 	= 19;
	private int indexTrocarCoord_start 		= 20;
	private int indexTrocarCoord_end 		= 25;
	private int indexSpeedCoord_start 		= 30;
	private int indexSpeedCoord_end 		= 32;
	private int indexForceCoord_start 		= 33;
	private int indexForceCoord_end 		= 35;
	private int indexMarksCoord_start 		= 36;
	private int indexMarksCoord_end 		= 53;
	//private int indexAOI 					= 54;
	private int indexAOI_start				= 54;
	
	private List<Double> trocar_params = new ArrayList<Double>();
	private List<Double> speed_params = new ArrayList<Double>();
	private List<Double> force_params = new ArrayList<Double>();
	private List<Double> fluoroscopeFace_params = new ArrayList<Double>();
	private List<Double> fluoroscopeProfile_params = new ArrayList<Double>();
	private List<Double> marks_params = new ArrayList<Double>();
	
	private String timecode;
	private String actionName;
	private String fixations;
	
	public ToolsCoordinatesExtractor() {
		// TODO Auto-generated constructor stub
	}
	
	public List <List<Double>> extractSequenceTokens(String sequence){
		List <List<Double>> sequenceElements = new ArrayList<List<Double>>();
		
		if (!sequence.isEmpty()) this.sequenceTokens = sequence.split(";");
		
		if(sequenceTokens.length>0){
			setTimecode();
			setActionName();
			
			
			this.fluoroscopeFace_params = extractFluoroscopeFaceCoordinates();
				sequenceElements.add(fluoroscopeFace_params);
			this.fluoroscopeProfile_params = extractFluoroscopeProfileCoordinates();
				sequenceElements.add(fluoroscopeProfile_params);
			this.trocar_params = extractTrocarCoordinates();
				sequenceElements.add(trocar_params);
			this.speed_params = extractSpeedCoordinates();
				sequenceElements.add(speed_params);
			this.force_params = extractForceCoordinates();
				sequenceElements.add(force_params);
			this.marks_params = extractCutaneousMarksCoordinates();
				sequenceElements.add(marks_params);
			
			setFixations();
		}
		return sequenceElements;
	}
	
	public void setTimecode (){
		timecode = this.sequenceTokens[indexTimecode];
	}
	public String getTimecode (){
		return timecode;
	}
	
	public void setActionName (){
		actionName = this.sequenceTokens[indexActionName];
	}
	public String getActionName (){
		return actionName;
	}
	
	public void setFixations(){
		fixations = extractFixations();
	}
	public String getFixations (){
		return fixations;
	}
	
	private List<Double> extractTrocarCoordinates(){
		List <Double> trocar_params = new ArrayList<Double>();
		for(int iTokens = indexTrocarCoord_start; iTokens<= indexTrocarCoord_end; iTokens++){
			trocar_params.add(Double.parseDouble(this.sequenceTokens[iTokens]));
		}
	return trocar_params;
	}
	
	private List <Double> extractSpeedCoordinates(){
		List <Double> speed_params = new ArrayList<Double>();
		for(int iTokens = indexSpeedCoord_start; iTokens<= indexSpeedCoord_end; iTokens++){
			speed_params.add(Double.parseDouble(this.sequenceTokens[iTokens]));
		}
		return speed_params;
	}
	
	private List<Double> extractForceCoordinates(){
		List <Double> force_params = new ArrayList<Double>();
		for(int iTokens = indexForceCoord_start; iTokens<= indexForceCoord_end; iTokens++){
			force_params.add(Double.parseDouble(this.sequenceTokens[iTokens]));
		}
	return force_params;
	}
	
	private List<Double> extractFluoroscopeFaceCoordinates(){
		List<Double> fluoroscopeFace_params = new ArrayList<Double>();
		for(int iTokens = indexFluoroscopeFaceCoord_start; iTokens<= indexFluoroscopeFaceCoord_end; iTokens++){
			fluoroscopeFace_params.add(Double.parseDouble(this.sequenceTokens[iTokens]));
		}
		//System.out.println(fluoroscopeFace_params);
	return fluoroscopeFace_params;
	}
	private List<Double> extractFluoroscopeProfileCoordinates(){
		List<Double> fluoroscopeProfile_params = new ArrayList<Double>();
		for(int iTokens = indexFluoroscopeProfileCoord_start; iTokens<= indexFluoroscopeProfileCoord_end; iTokens++){
			fluoroscopeProfile_params.add(Double.parseDouble(this.sequenceTokens[iTokens]));
		}
	return fluoroscopeProfile_params;
	}
	private List<Double> extractCutaneousMarksCoordinates(){
		List <Double> marksTRL_params = new ArrayList<Double>();
		for(int iTokens = indexMarksCoord_start; iTokens <= indexMarksCoord_end; iTokens++){
			marksTRL_params.add(Double.parseDouble(sequenceTokens[iTokens]));
		}
	return marksTRL_params;
	}
	
	private String extractFixations(){
		String fixations = "";
		if(indexAOI_start < sequenceTokens.length-1){
			for(int iTokens = indexAOI_start; iTokens < sequenceTokens.length-1; iTokens++){
				fixations = fixations.concat(sequenceTokens[iTokens]+";");
			}
			fixations = fixations.concat(sequenceTokens[sequenceTokens.length-1]);
		}
	return fixations;
	}
	
}
