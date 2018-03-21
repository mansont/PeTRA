package postprocessing;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class PatternsAnalyzer {

	List <String> actionsList = new ArrayList<>();
	List <String> perceptionsList = new ArrayList<>();
	List <String> simulationStatesList = new ArrayList<>();
	
	boolean isAction = false;
	boolean isPerception = false;
	boolean isSimulationState = false;
	boolean isFlexibleConfiguration = false;
	
	public PatternsAnalyzer(){
		
	}
	
	public PatternsAnalyzer(boolean isAction, boolean isPerception, boolean isSimulationState, boolean isFlexibleConfiguration){
		this.isAction = isAction;
		this.isPerception = isPerception;
		this.isSimulationState = isSimulationState;
		this.isFlexibleConfiguration = isFlexibleConfiguration;
	}
	
	public List<Boolean> getPatternsConfiguration(){
		List<Boolean> patternConfiguration = new ArrayList<>();
		patternConfiguration.add(isAction);
		patternConfiguration.add(isPerception);
		patternConfiguration.add(isSimulationState);
	return patternConfiguration;
	}
	
	public void setActionsList (){
		//Ajouter codes actions simu à la liste
		for(int i = 100; i<=120; i++){
			actionsList.add(Integer.toString(i));
		}
		//Ajouter codes actions trocart à la liste
		for(int i = 200; i<=205; i++){
			actionsList.add(Integer.toString(i));
		}
		//Ajouter codes actions fluoroscope à la liste
		for(int i = 300; i<=309; i++){
			actionsList.add(Integer.toString(i));
		}
		//Ajouter codes actions repères cutanés à la liste
		for(int i = 400; i<=405; i++){
			actionsList.add(Integer.toString(i));
		}
	}
	
	public List<String> getActionsList(){
		return actionsList;
	}
	
	public void setPerceptionsList(){
		//Ajouter codes perceptions à la liste
		for(int i = 500; i<=531; i++){
			perceptionsList.add(Integer.toString(i));
		}
	}
	
	public List<String> getPerceptionsList(){
		return perceptionsList;
	}
	
	public void setSimulationState(){
		//Ajouter codes état du trocart
		for(int i = 206; i<=209; i++){
			simulationStatesList.add(Integer.toString(i));
		}
		//Ajouter codes état du fluoroscope
		for(int i = 310; i<=317; i++){
			simulationStatesList.add(Integer.toString(i));
		}
		//Ajouter codes état des repères cutanés
		for(int i = 406; i<=411; i++){
			simulationStatesList.add(Integer.toString(i));
		}
	}
	
	public List<String> getSimulationStates(){
		return simulationStatesList;
	}
	
	private List<String> splitSequence(String sequence){
		List<String> sequenceElementsList = new ArrayList<>();
		String [] sequenceElementsTable;
		//sequence.replaceAll("-1", "");
		//sequence.replaceAll("-2 ", "");
		//sequence.replaceAll("  ", " ");
		//sequence.trim();
		sequenceElementsTable = sequence.split(" ");
		
		for (int i=0; i <= sequenceElementsTable.length-1; i++){
			sequenceElementsList.add(sequenceElementsTable[i]);
		}
		return sequenceElementsList;
	}
	
	private List<String> splitSequence(String sequence, String separator){
		List<String> sequenceElementsList = new ArrayList<>();
		String [] sequenceElementsTable;
		sequenceElementsTable = sequence.split(separator);
		
		for (int i=0; i <= sequenceElementsTable.length-1; i++){
			sequenceElementsList.add(sequenceElementsTable[i]);
		}
		return sequenceElementsList;
	}
	
	//TODO utiliser contains
	private boolean checkAction (String sequence){
		boolean isAction = false;
		List <String> sequenceEltsList = new ArrayList<>();
		
		sequenceEltsList = splitSequence(sequence);
		
		for(String seqElts : sequenceEltsList){
			if(!seqElts.contains("#")){
				for(String action: actionsList){
					
					if(action.equals("-1")||action.equals("-2")||action.contains("<")) continue;
					
					if(seqElts.equals(action)){
						isAction = true;
						break;
					}
				}
			}
			else{
				break;
			}
		}
		
		return isAction;
	}
	
	//TODO Utiliser contains
	private boolean checkPerception (String sequence){
		boolean isPerception = false;
		List <String> sequenceEltsList = new ArrayList<>();
		
		sequenceEltsList = splitSequence(sequence);
		
		for(String seqElts : sequenceEltsList){
			if(!seqElts.contains("#")){
				for(String perception: perceptionsList){
					
					if(perception.equals("-1")||perception.equals("-2")||perception.contains("<")) continue;
					
					if(seqElts.equals(perception)){
						isPerception = true;
						break;
					}
				}
			}
			else{
				break;
			}
		}
		
		return isPerception;
	}
	
	//TODO Utiliser contains
	private boolean checkSimulationState (String sequence){
		boolean isSimulationState = false;
		List <String> sequenceEltsList = new ArrayList<>();
		
		sequenceEltsList = splitSequence(sequence);
		
		for(String seqElts : sequenceEltsList){
			if(!seqElts.contains("#")){
				for(String simulationState: simulationStatesList){
					
					if(simulationState.equals("-1")||simulationState.equals("-2")||simulationState.contains("<")) continue;
					
					if(seqElts.equals(simulationState)){
						isSimulationState = true;
						break;
					}
				}
			}
			else{
				break;
			}
		}
		
		return isSimulationState;
	}
	
	public List<Boolean> checkPattern(String sequence){
		List <Boolean> checkList = new ArrayList<>();
		boolean isAction = false;
		boolean isPerception = false;
		boolean isSimulationState = false;
		
		isAction = checkAction(sequence);
		isPerception = checkPerception(sequence);
		isSimulationState = checkSimulationState(sequence);
		
		checkList.add(isAction);
		checkList.add(isPerception);
		checkList.add(isSimulationState);
		
		return checkList;
	}
	
	private boolean checkPatternCorrespondance(List<Boolean> checkList, List<Boolean> patternsConfiguration){
		boolean isCompatible = false;
		
		//si le patron correspond exactement à la configuration demandée, il est retenu
		if(checkList.get(0).booleanValue() == patternsConfiguration.get(0).booleanValue()
				&& checkList.get(1).booleanValue() == patternsConfiguration.get(1).booleanValue()
				&& checkList.get(2).booleanValue() == patternsConfiguration.get(2).booleanValue())
			isCompatible = true;
		return isCompatible;
	}
	
	private boolean checkPatternCorrespondanceFlexible(List<Boolean> checkList, List<Boolean> patternsConfiguration){
		boolean isCompatible = false;
		
		//si le patron correspond à la configuration demandée...
		//Si l'un des éléments de la configuration est à false cela signifie qu'il n'est pas obligatoire
		if(
			((!patternsConfiguration.get(0).booleanValue() && (checkList.get(0).booleanValue() || !checkList.get(0).booleanValue()))
				|| (patternsConfiguration.get(0).booleanValue() && checkList.get(0).booleanValue()))
			&& ((!patternsConfiguration.get(1).booleanValue() && (checkList.get(1).booleanValue() || !checkList.get(1).booleanValue()))
				|| (patternsConfiguration.get(1).booleanValue() && checkList.get(1).booleanValue()))
			&& ((!patternsConfiguration.get(2).booleanValue() && (checkList.get(2).booleanValue() || !checkList.get(2).booleanValue()))
				|| (patternsConfiguration.get(2).booleanValue() && checkList.get(2).booleanValue()))
				)
			{
			isCompatible = true;
			}
		return isCompatible;
	}
	
	
	//TODO check rule correspondance non flexible
	
	
	public List<String> processPatternsSelection(List<String> patternsList){
		List <Boolean> checkList = new ArrayList<>();
		List <Boolean> patternsConfiguration = new ArrayList<>();
		List<String> selectedPatterns = new ArrayList<>();
		
		patternsConfiguration = getPatternsConfiguration();
		System.out.println("Configuration patterns: "+patternsConfiguration);
		
		for(String pattern: patternsList){
			System.out.println("Pattern en cours: "+pattern);
			checkList = checkPattern(pattern);
			System.out.println("Check du pattern en cours: " +checkList);
			if(isFlexibleConfiguration){
				if(checkPatternCorrespondanceFlexible(checkList, patternsConfiguration)==true) //verifier la correspondance du pattern avec la configuration demandée
					selectedPatterns.add(pattern);
			}
			else if(!isFlexibleConfiguration){
				if(checkPatternCorrespondance(checkList, patternsConfiguration)==true) //verifier la correspondance du pattern avec la configuration demandée
					selectedPatterns.add(pattern);
			}
			checkList.clear();
		}
		for(String sel: selectedPatterns){
			System.out.println("Patron sélectionné (methode process): " +sel);
		}
		
		return selectedPatterns;
	}
	
	
	public List<String> processRulesSelection(List<String> rulesList){
		List <Boolean> checkList = new ArrayList<>();
		List <Boolean> patternsConfiguration = new ArrayList<>();
		List<String> selectedRules = new ArrayList<>();
		String ruleLeftSide = "";
		String [] ruleSides;
		
		patternsConfiguration = getPatternsConfiguration();
		System.out.println("Configuration patterns: "+patternsConfiguration);
		
		for(String rule: rulesList){
			
			
			ruleSides = rule.split("==>");
			
			ruleLeftSide = ruleSides[0];
			System.out.println("Règle en cours: "+ruleLeftSide);
			ruleLeftSide = ruleLeftSide.replaceAll(",", " ");
			
			checkList = checkPattern(ruleLeftSide);
			System.out.println("Check du pattern en cours: " +checkList);
			if(isFlexibleConfiguration){
				if(checkPatternCorrespondanceFlexible(checkList, patternsConfiguration)==true) //verifier la correspondance du pattern avec la configuration demandée
					selectedRules.add(rule);
			}
			else if(!isFlexibleConfiguration){
				if(checkPatternCorrespondance(checkList, patternsConfiguration)==true) //verifier la correspondance du pattern avec la configuration demandée
					selectedRules.add(rule);
			}
			checkList.clear();
		}
		for(String sel: selectedRules){
			System.out.println("Règle sélectionnée (methode process): " +sel);
		}
		
		return selectedRules;
	}
	
	public void saveSelectedPatterns (List<String> selectedPatterns, String outPutFilePath){
		try {
			PrintStream fSelectedPatterns = new PrintStream(outPutFilePath);
			System.setOut(fSelectedPatterns);
			
		      for(String selectedpat : selectedPatterns){
		    	 fSelectedPatterns.println(selectedpat);
				}
		      fSelectedPatterns.close();
		   }
		      catch(IOException e1) {
		        System.out.println("Writing error");
		   }
	}
	
}
