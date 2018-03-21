package itemcounter;
import java.io.*;
import java.util.*;

import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

public class XcelFileCreator {

public XcelFileCreator(){
	
}
	
	public void saveToXcelFile(List <String> patternsStatList, String xcelFileName){	  
		//Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		//Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Patterns elements count");
		
		//This data needs to be written (Object[])
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		
		String [] patternStatTokens;
		int nbItems = 0;
		int nbItemsets = 0;
		int nbDistinctItems = 0;
		int nbVerificationPerceptions = 0;
		int nbDecisionsPerceptions = 0;
		int nbSimStates = 0;
		int nbTotalPerceptions = 0;
		
		int nbTotalActions = 0;
		int nbTrocarActions = 0;
		int nbFluoroscopeActions = 0;
		int nbPreparationXraysActions = 0;
		int nbControlXraysActions = 0;
		int nbCutaneousMarksActions = 0;
		
		int nbTotalXraysActions = 0;
		int nbActionsPreparationPhase = 0;
		int nbActionsCutMarksPhase = 0;
		int nbActionsTrocarPhase = 0;
		
		data.put("1", new Object[] {"Items", "Itemsets", "Items distincts",
									"Total actions", "Actions Phase Preparation", "Actions Phase Reperes", "Actions Phase Trocart",
									"Total actions radios", "Actions radios prepa", "Actions radios controles",
									"Actions fluoroscope", "Actions repères cutanés", "Actions trocart",
									"Total perceptions", "Perceptions verification", "Perceptions decisions",
									"Etats simu"});
		
		int cellKey = 2;
		
		for(String simInf: patternsStatList){
			patternStatTokens = simInf.split(";");
			
			nbItems = Integer.parseInt(patternStatTokens[0]);
			nbItemsets = Integer.parseInt(patternStatTokens[1]);
			nbDistinctItems = Integer.parseInt(patternStatTokens[2]);
			
			nbTotalActions =  Integer.parseInt(patternStatTokens[3]);
			nbActionsPreparationPhase =  Integer.parseInt(patternStatTokens[4]);
			nbActionsCutMarksPhase =  Integer.parseInt(patternStatTokens[5]);
			nbActionsTrocarPhase =  Integer.parseInt(patternStatTokens[6]);
			
			nbTotalXraysActions = Integer.parseInt(patternStatTokens[7]);
			nbPreparationXraysActions = Integer.parseInt(patternStatTokens[8]);
			nbControlXraysActions = Integer.parseInt(patternStatTokens[9]);
			
			nbFluoroscopeActions  =  Integer.parseInt(patternStatTokens[10]);
			nbCutaneousMarksActions  =  Integer.parseInt(patternStatTokens[11]);
			nbTrocarActions = Integer.parseInt(patternStatTokens[12]);
			
			nbTotalPerceptions = Integer.parseInt(patternStatTokens[13]);
			nbVerificationPerceptions = Integer.parseInt(patternStatTokens[14]);
			nbDecisionsPerceptions = Integer.parseInt(patternStatTokens[15]);
			nbSimStates = Integer.parseInt(patternStatTokens[16]);
			
			for(int i =0; i< patternStatTokens.length; i++){
				data.put(String.valueOf(cellKey),
						new Object[]{
							nbItems, nbItemsets, nbDistinctItems,
							nbTotalActions, nbActionsPreparationPhase, nbActionsCutMarksPhase, nbActionsTrocarPhase,
							nbTotalXraysActions, nbPreparationXraysActions, nbControlXraysActions,
							nbFluoroscopeActions, nbCutaneousMarksActions, nbTrocarActions,
							nbTotalPerceptions, nbVerificationPerceptions, nbDecisionsPerceptions, nbSimStates});
			}
			cellKey++;
		}
		          
		//Iterate over data and write to sheet
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset){
			Row row = sheet.createRow(rownum++);
			Object [] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr){
		    	Cell cell = row.createCell(cellnum++);
		        if(obj instanceof String)
		        	cell.setCellValue((String)obj);
		        else if(obj instanceof Integer)
		        	cell.setCellValue((Integer)obj);
		        else if(obj instanceof Float)
		        	cell.setCellValue((Float)obj);
		        else if(obj instanceof Double)
		        	cell.setCellValue((Double)obj);
		   	}
		}
		try{
			//Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File(xcelFileName));
			workbook.write(out);
			out.close();
			System.out.println("Xcel file successfully written on disk");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}