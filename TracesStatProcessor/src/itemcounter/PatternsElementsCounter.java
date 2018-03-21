package itemcounter;

import java.util.ArrayList;
import java.util.List;

public class PatternsElementsCounter {

	public PatternsElementsCounter(){
		//setActionsList();
		setPreparationXraysActionsList();
		setControlXraysActionsList();
		setCutaneousMarksActionsList();
		setFluoroscopeActionsList();
		setTrocarActionsList();
		setDecisionPerceptionsList();
		setValidationPerceptionsList();
		setSimulationState();
	}
	
	//List <String> actionsList = new ArrayList<>();
	
	List <String> trocarActionsList = new ArrayList<>();
	List <String> fluoroscopeActionsList = new ArrayList<>();
	List <String> preparationXraysActionsList = new ArrayList<>();
	List <String> controlXraysActionsList = new ArrayList<>();
	List <String> cutaneousMarkActionsList = new ArrayList<>();
	
	List <String> validationPerceptionsList = new ArrayList<>();
	List <String> decisionPerceptionsList = new ArrayList<>();
	List <String> simulationStatesList = new ArrayList<>();
	
	/*public void setActionsList (){
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
	}*/
	
	public void setPreparationXraysActionsList (){
		for(int i = 100; i<=106; i+=2){
			preparationXraysActionsList.add(Integer.toString(i));
		}
	}
	
	public void setControlXraysActionsList (){
		for(int i = 101; i<=107; i+=2){
			controlXraysActionsList.add(Integer.toString(i));
		}
	}
	
	public void setCutaneousMarksActionsList (){
		for(int i = 108; i<=113; i++){
			cutaneousMarkActionsList.add(Integer.toString(i));
		}
		for(int i = 400; i<=405; i++){
			cutaneousMarkActionsList.add(Integer.toString(i));
		}
	}
	
	public void setTrocarActionsList (){
		for(int i = 114; i<=120; i++){
			trocarActionsList.add(Integer.toString(i));
		}
		for(int i = 200; i<=205; i++){
			trocarActionsList.add(Integer.toString(i));
		}
	}
	public void setFluoroscopeActionsList (){
		//Ajouter codes actions simu à la liste
		for(int i = 300; i<=309; i++){
			fluoroscopeActionsList.add(Integer.toString(i));
		}
	}
	
	public void setValidationPerceptionsList(){
		for(int i = 514; i<=530; i++){
			validationPerceptionsList.add(Integer.toString(i));
		}
	}
	public void setDecisionPerceptionsList(){
		//Ajouter codes perceptions à la liste
		for(int i = 500; i<=513; i++){
			decisionPerceptionsList.add(Integer.toString(i));
		}
		decisionPerceptionsList.add("531");
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
	
	public List<String> setPatternsStatTable(List<String> patternsList){
		List<String> patternsStatTable = new ArrayList<>();
		int nbItems = 0;
		int nbItemsets = 0;
		int nbDistinctItems = 0;
		
		int nbTotalActions = 0;
		int nbActionsPreparationPhase = 0;
		int nbActionsCutMarksPhase = 0;
		int nbActionsTrocarPhase = 0;
		
		int nbTotalXraysActions = 0;
		int nbPreparationXraysActions = 0;
		int nbControlXraysActions = 0;
		
		int nbFluoroscopeActions = 0;
		int nbCutaneousMarksActions = 0;
		int nbTrocarActions = 0;
		
		int nbTotalPerceptions = 0;
		int nbVPerceptions = 0;
		int nbDPerceptions = 0;
		
		int nbSimStates = 0;
		
		/*
		 * "Items", "Itemsets", "Items distincts",
			"Total actions", "Actions Phase Preparation", "Actions Phase Reperes", "Actions Phase Trocart",
			"Total actions radios", "Actions radios prepa", "Actions radios controles",
			"Actions fluoroscope", "Actions repères cutanés", "Actions trocart",
			"Total perceptions", "Perceptions verification", "Perceptions decisions",
			"Etats simu"
		 */
		
		String [] patternTokens;
		String patternStat = "";
		
		for(String pattern: patternsList){
			patternTokens = pattern.split(" ");
			nbItems = countItems(patternTokens);
			nbItemsets = countItemsets(patternTokens);
			nbDistinctItems = countDistinctItems(patternTokens);
			nbPreparationXraysActions = countPreparationXrays(patternTokens);
			nbControlXraysActions = countControlXrays(patternTokens);
			nbFluoroscopeActions = countFluororoscopeActions(patternTokens);
			nbCutaneousMarksActions = countCutaneousMarksActions(patternTokens);
			nbTrocarActions = countTrocarActions(patternTokens);
			nbVPerceptions = countValidationPerceptions(patternTokens);
			nbDPerceptions = countDecisionPerceptions(patternTokens);
			nbSimStates = countSimulationStates(patternTokens);
			
			nbTotalPerceptions = nbVPerceptions + nbDPerceptions;
			nbTotalXraysActions = nbPreparationXraysActions + nbControlXraysActions;
			nbActionsPreparationPhase = nbFluoroscopeActions + nbPreparationXraysActions;
			nbActionsCutMarksPhase = nbCutaneousMarksActions + nbControlXraysActions;
			nbActionsTrocarPhase = nbTrocarActions + nbControlXraysActions;
			nbTotalActions = nbTotalXraysActions+nbFluoroscopeActions+nbCutaneousMarksActions+nbTrocarActions;
			
			patternStat =	nbItems+";"+nbItemsets+";"+nbDistinctItems+";"
							+nbTotalActions+";"+nbActionsPreparationPhase+";"+nbActionsCutMarksPhase+";"+nbActionsTrocarPhase+";"
							+nbTotalXraysActions+";"+nbPreparationXraysActions+";"+nbControlXraysActions+";"
							+nbFluoroscopeActions+";"+nbCutaneousMarksActions+";"+nbTrocarActions+";"
							+nbTotalPerceptions+";"+nbVPerceptions+";"+nbDPerceptions+";"+nbSimStates;
			patternsStatTable.add(patternStat);
		}
		return patternsStatTable;
	}
	
	/*private boolean isAction(String token){
		boolean actionFlag = false;
		for(String action: actionsList){
			if(action.equals(token))
				actionFlag = true;
		}
		return actionFlag;
	}*/
	
	private boolean isPreparationXray(String token){
		boolean pXrayFlag = false;
		for(String action: preparationXraysActionsList){
			if(action.equals(token))
				pXrayFlag = true;
		}
		return pXrayFlag;
	}
	
	private boolean isControlXray(String token){
		boolean cXrayFlag = false;
		for(String action: controlXraysActionsList){
			if(action.equals(token))
				cXrayFlag = true;
		}
		return cXrayFlag;
	}
	
	private boolean isFluoroscopeAction(String token){
		boolean fluorFlag = false;
		for(String action: fluoroscopeActionsList){
			if(action.equals(token))
				fluorFlag = true;
		}
		return fluorFlag;
	}
	
	private boolean isCutaneousMarkAction(String token){
		boolean markFlag = false;
		for(String action: cutaneousMarkActionsList){
			if(action.equals(token))
				markFlag = true;
		}
		return markFlag;
	}
	
	private boolean isTrocarAction(String token){
		boolean trocarFlag = false;
		for(String action: trocarActionsList){
			if(action.equals(token))
				trocarFlag = true;
		}
		return trocarFlag;
	}
	
	private boolean isValidationPerception(String token){
		boolean vPerceptionFlag = false;
		for(String vPerc: validationPerceptionsList){
			if(vPerc.equals(token))
				vPerceptionFlag = true;
		}
		return vPerceptionFlag;
	}
	
	private boolean isDecisionPerception(String token){
		boolean dPerceptionFlag = false;
		for(String dPerc: decisionPerceptionsList){
			if(dPerc.equals(token))
				dPerceptionFlag = true;
		}
		return dPerceptionFlag;
	}
	
	private boolean isSimulationState(String token){
		boolean simStateFlag = false;
		for(String simState: simulationStatesList){
			if(simState.equals(token))
				simStateFlag = true;
		}
		return simStateFlag;
	}
	
	private int countItemsets(String [] patternTokens){
		int itemsetsNb = 0;
		for(int i =0; i<= patternTokens.length-1; i++){
			if(patternTokens[i].equals("-1"))
				itemsetsNb++;
		}
		return itemsetsNb;
	}
	
	private int countItems(String [] patternTokens){
		int itemsNb = 0;
		for(int i=0; i<= patternTokens.length-2; i++){
			if(patternTokens[i].contains("#") || patternTokens[i].isEmpty())
				break;
			else
				if(!patternTokens[i].contains("<") && !patternTokens[i].equals("-1") && !patternTokens[i].equals("-2"))
					itemsNb++;
		}
		return itemsNb;
	}
	
	private int countDistinctItems(String [] patternTokens){
		int distinctItemsNb = 0;
		boolean dbItemFlag = false;
		List <String> distinctItemsList = new ArrayList<>();
		for(int i= 0; i<= patternTokens.length-1; i++){
			if(patternTokens[i].contains("#") || patternTokens[i].isEmpty())
				break;
			else if(!patternTokens[i].contains("<") && !patternTokens[i].equals("-1") && !patternTokens[i].equals("-2")){
				for(String dItem: distinctItemsList){
					if(patternTokens[i].equals(dItem))
						dbItemFlag = true;
				}
				if(!dbItemFlag)
					distinctItemsList.add(patternTokens[i]);
			}
			dbItemFlag = false;
		}
		distinctItemsNb = distinctItemsList.size();
		
		return distinctItemsNb;
	}
	
	private int countValidationPerceptions(String [] patternTokens){
		int vPerceptionsNb = 0;
		for(int i=0; i<=patternTokens.length-1; i++){
			if(!patternTokens[i].contains("<") || !patternTokens[i].equals("-1") || !patternTokens[i].equals("-2")){
				if(isValidationPerception(patternTokens[i]))
					vPerceptionsNb++;
			}
		}
		return vPerceptionsNb;
	}
	
	private int countDecisionPerceptions(String [] patternTokens){
		int dPerceptionsNb = 0;
		for(int i=0; i<=patternTokens.length-1; i++){
			if(!patternTokens[i].contains("<") || !patternTokens[i].equals("-1") || !patternTokens[i].equals("-2")){
				if(isDecisionPerception(patternTokens[i]))
					dPerceptionsNb++;
			}
		}	
		return dPerceptionsNb;
	}
	
	/*private int countActions(String [] patternTokens){
		int actionsNb = 0;
		for(int i=0; i<=patternTokens.length-1; i++){
			if(!patternTokens[i].contains("<") || !patternTokens[i].equals("-1") || !patternTokens[i].equals("-2")){
				if(isAction(patternTokens[i])) actionsNb++;
			}
		}
		return actionsNb;
	}*/
	
	private int countFluororoscopeActions(String [] patternTokens){
		int actionsNb = 0;
		for(int i=0; i<=patternTokens.length-1; i++){
			if(!patternTokens[i].contains("<") || !patternTokens[i].equals("-1") || !patternTokens[i].equals("-2")){
				if(isFluoroscopeAction(patternTokens[i])) actionsNb++;
			}
		}
		return actionsNb;
	}
	
	private int countCutaneousMarksActions(String [] patternTokens){
		int actionsNb = 0;
		for(int i=0; i<=patternTokens.length-1; i++){
			if(!patternTokens[i].contains("<") || !patternTokens[i].equals("-1") || !patternTokens[i].equals("-2")){
				if(isCutaneousMarkAction(patternTokens[i])) actionsNb++;
			}
		}
		return actionsNb;
	}
	
	private int countTrocarActions(String [] patternTokens){
		int actionsNb = 0;
		for(int i=0; i<=patternTokens.length-1; i++){
			if(!patternTokens[i].contains("<") || !patternTokens[i].equals("-1") || !patternTokens[i].equals("-2")){
				if(isTrocarAction(patternTokens[i])) actionsNb++;
			}
		}
		return actionsNb;
	}
	
	private int countPreparationXrays(String [] patternTokens){
		int xRaysNb = 0;
		for(int i=0; i<=patternTokens.length-1; i++){
			if(!patternTokens[i].contains("<") || !patternTokens[i].equals("-1") || !patternTokens[i].equals("-2")){
				if(isPreparationXray(patternTokens[i])) xRaysNb++;
			}
		}
		return xRaysNb;
	}
	
	private int countControlXrays(String [] patternTokens){
		int xRaysNb = 0;
		for(int i=0; i<=patternTokens.length-1; i++){
			if(!patternTokens[i].contains("<") || !patternTokens[i].equals("-1") || !patternTokens[i].equals("-2")){
				if(isControlXray(patternTokens[i])) xRaysNb++;
			}
		}
		return xRaysNb;
	}
	
	private int countSimulationStates(String [] patternTokens){
		int simStatesNb = 0;
		for(int i=0; i<=patternTokens.length-1; i++){
			if(!patternTokens[i].contains("<") || !patternTokens[i].equals("-1") || !patternTokens[i].equals("-2")){
			if(isSimulationState(patternTokens[i])) simStatesNb++;
			}
		}
		return simStatesNb;
	}
	
}
