package itemcounter;

import java.util.ArrayList;
import java.util.List;

public class TracesStatProcessor {

	public TracesStatProcessor(){
		
	}
	
	public static PatternsExtractor patEX = new PatternsExtractor();
	public static PatternsElementsCounter patEC = new PatternsElementsCounter();
	public static XcelFileCreator excelFC = new XcelFileCreator();
	
	public static List<String> patternsList = new ArrayList<>();
	public static List<String> patternsStatTableList = new ArrayList<>();
	
	public static String inputTracesType = "_AnnotatedTraces";
	public static String sessionID = "2327";
	
	public static String inputFilepath = "C:\\TeleosTraces\\EncodedTraces\\TemporalItemsetFormat\\Encoded_TemporalItemsetFormat_"+sessionID+inputTracesType+".txt";
	public static String outputFilepath = "C:\\TeleosTraces\\PatternedTraces\\StatRepository\\Stat_"+sessionID+inputTracesType+".xlsx";
	//public static String inputFilepath = "C:\\TeleosTraces\\PatternedTraces\\PostProcessedPatterns\\FV_TIF_Flex_"+sessionID+"_SelectedPatterns.txt";
	//public static String outputFilepath = "C:\\TeleosTraces\\PatternedTraces\\StatRepository\\Stat_"+sessionID+"_FlexSelectedSequentialPatterns.xlsx";
	
	public static void main(String[] args) {
		//int i = 0;
		patternsList = patEX.extractPatterns(inputFilepath);
		patternsStatTableList = patEC.setPatternsStatTable(patternsList);
		excelFC.saveToXcelFile(patternsStatTableList, outputFilepath);
		
		/*for(String stat: patternsStatTableList){
			System.out.println(i+" - "+stat);
			i++;
		}*/
	}

}
