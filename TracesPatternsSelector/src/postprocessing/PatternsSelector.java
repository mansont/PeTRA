package postprocessing;

import java.util.ArrayList;
import java.util.List;

public class PatternsSelector {

	//Every sequence must contain at least an action, an action control and a simulation state
	public static PatternsAnalyzer pa = new PatternsAnalyzer(true, true, false, true);
	static PatternsExtractor pe = new PatternsExtractor();
	
	static Boolean isAction = pa.getPatternsConfiguration().get(0); 
	static Boolean isPerception = pa.getPatternsConfiguration().get(1);
	static Boolean isSimulationState = pa.getPatternsConfiguration().get(2);
	
	static List<String> selectedPatternsList = new ArrayList<>();
	static List<String> selectedRulesList = new ArrayList<>();
	//static List<Boolean> patternCheckList = new ArrayList<>();
	static List<String> patternsList = new ArrayList<>();
	static List<String> rulesList = new ArrayList<>();
	
	static List<String> cleanedPatternsList = new ArrayList<>();
	
	static String algo = "CMRules_";
	
	//static String patternsType = "frequent patterns";
	static String config = "_0.1_0.7_L2R1";
	static String tracesFormat = "IF_";
	static String sessionID = "2325";
	static String inputFilePath = "C:\\TeleosTraces\\PatternedTraces\\SequentialRules\\CMRules\\"+algo+tracesFormat+sessionID+"_Patterns"+config+".txt";
	//static String inputFilePath = "C:\\TeleosTraces\\PatternedTraces\\SequentialPatterns\\FournierVigier\\"+algo+tracesFormat+sessionID+"_Patterns.txt";
	static String outputFilePath = "C:\\TeleosTraces\\PatternedTraces\\PostProcessedPatterns\\"+algo+tracesFormat+"Flex_"+sessionID+"_SelectedPatterns"+config+".txt";
	
	public static void main(String[] args) {
		pa.setActionsList();
		pa.setPerceptionsList();
		pa.setSimulationState();
		
		if(!isAction && !isPerception && !isSimulationState){ //si la configuration ne demande la sélection d'aucun type de patrons
			System.out.println("Mauvaise configuation: au moins un paramètre doit etre choisi.");
		}
		else{
			//patternsList = pe.extractPatterns(inputFilePath);
			//selectedPatternsList = pa.processPatternsSelection(patternsList);
			
			rulesList = pe.extractPatterns(inputFilePath);
			selectedRulesList = pa.processRulesSelection(rulesList);
			
			for(String sel: selectedRulesList){
				System.out.println("Règle sélectionnée: "+ sel);
			}
			System.out.println("Nombre de correspondances trouvées: "+ selectedRulesList.size());
			pa.saveSelectedPatterns(selectedRulesList, outputFilePath);
		}
	}
}
