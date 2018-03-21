package tracesemanticdefiner;

import java.util.ArrayList;
import java.util.List;

public class VectorsAnalyzer {
	
	ToolsCoordinatesExtractor toolsCoordinates = new ToolsCoordinatesExtractor();
	String sequence;
	public static String trocar_up 			= "Trocar_move_up";
	public static String trocar_down 		= "Trocar_move_down";
	public static String trocar_right 		= "Trocar_move_right";
	public static String trocar_left		= "Trocar_move_left";
	public static String trocar_front 		= "Trocar_move_front";
	public static String trocar_back		= "Trocar_move_back";
	
	public static String trocar_lnback		= "Trocar_lean_back";
	public static String trocar_lnfront		= "Trocar_lean_front";
	public static String trocar_lnright		= "Trocar_lean_right";
	public static String trocar_lnleft		= "Trocar_lean_left";
	
	public static String trocar_t_unchanged = "Trocar_transverse_unchanged";
	public static String trocar_f_unchanged = "Trocar_frontal_unchanged";
	public static String trocar_s_unchanged = "Trocar_sagittal_unchanged";
	public static String trocar_unchanged 	= "Trocar_unchanged";
	
	public static String fluoroscope_f_right 	= "FluoroscopeFace_move_right";
	public static String fluoroscope_f_left 	= "FluoroscopeFace_move_left";
	public static String fluoroscope_f_up		= "FluoroscopeFace_move_up";
	public static String fluoroscope_f_down		= "FluoroscopeProfile_move_down";
	public static String fluoroscope_ft_unchanged	= "FluoroscopeFace_transverse_unchanged";
	public static String fluoroscope_fcc_unchanged	= "FluoroscopeFace_cephaliccaudal_unchanged";
	
	public static String fluoroscope_p_right 	= "FluoroscopeProfile_move_right";
	public static String fluoroscope_p_left 	= "FluoroscopeProfile_move_left";
	public static String fluoroscope_p_up		= "FluoroscopeProfile_move_down";
	public static String fluoroscope_p_down		= "FluoroscopeProfile_move_down";
	public static String fluoroscope_pap_unchanged	= "FluoroscopeProfile_anteroposterior_unchanged";
	public static String fluoroscope_pcc_unchanged	= "FluoroscopeProfile_cephaliccaudal_unchanged";
	//public static String fluoroscope_front	= "fluoroscope_move_front";
	//public static String fluoroscope_back	= "fluoroscope_move_back";
	
	public static String fluoroscope_f_lnright 	= "FluoroscopeFace_lean_right";
	public static String fluoroscope_f_lnleft 	= "FluoroscopeFace_lean_left";
	public static String fluoroscope_f_lnfront	= "FluoroscopeFace_lean_front";
	public static String fluoroscope_f_lnback	= "FluoroscopeFace_lean_back";

	
	public static String fluoroscope_p_lnright 	= "FluoroscopeProfile_lean_right";
	public static String fluoroscope_p_lnleft 	= "FluoroscopeProfile_lean_left";
	public static String fluoroscope_p_lnfront	= "FluoroscopeProfile_lean_front";
	public static String fluoroscope_p_lnback	= "FluoroscopeProfile_lean_back";
	
	static String tMark_left = "transverseMark_move_right";
	static String tMark_right = "transverseMark_move_left";
	static String tMark_lnleft = "transverseMark_oblique_leanLeft";
	static String tMark_lnright = "transverseMark_oblique_leanRight";
	
	static String rMark_up = "rightMark_move_right";
	static String rMark_down = "rightMark_move_left";
	static String rMark_lnleft = "rightMark_oblique_leanLeft";
	static String rMark_lnright = "rightMark_oblique_leanRight";
	
	static String lMark_up = "leftMark_move_right";
	static String lMark_down = "leftMark_move_left";
	static String lMark_lnleft = "leftMark_oblique_leanLeft";
	static String lMark_lnright = "leftMark_oblique_leanRight";
	
	public VectorsAnalyzer(){
		
	}	
	
	private List<Double> getCrossProduct(List<Double>coordinatesA, List<Double>coordinatesB){
		List <Double> crossProduct = new ArrayList<Double>();
		double crossProdX = 0;
		double crossProdY = 0;
		double crossProdZ = 0;
		double _x1 = 0;
		double _y1 = 0;
		double _z1 = 0;
		double _x2 = 0;
		double _y2 = 0;
		double _z2 = 0;
		
		_x1 = coordinatesA.get(0);
		_y1 = coordinatesA.get(1);
		_z1 = coordinatesA.get(2);
		
		_x2 = coordinatesB.get(0);
		_y2 = coordinatesB.get(1);
		_z2 = coordinatesB.get(2);
		
		crossProdX = _y1*_z2 - _z1*_y2;
		crossProdY = _z1*_x2 - _x1*_z2;
		crossProdZ = _x1*_y2 - _y1*_x2;
		
		crossProduct.add(crossProdX);
		crossProduct.add(crossProdY);
		crossProduct.add(crossProdZ);
		
		return crossProduct;
	}
	/*
	 * Plan transverse:
			coupe l'anatomie en largeur en une partie superieure et une partie inferieur (axe droite-gauche: x) 	
		Plan frontal:
			coupe l'anatomie en profondeur en une partie ventrale et une partie dorsale (axe dorsoventral: y)
		Plan sagittal:
	  		coupe l'anatomie en longueur une partie droite et une partie à gauche (axe anteroposterieur: z)
	 */
	
	/**
	 * @param currentTrocarPosition
	 * @param previousTrocarPosition
	 */
	  public List<String> defineTrocarMoves(List<Double> previousTrocarPosition, List<Double>currentTrocarPosition){
		  
		  List <String> trocarMovesSemantic = new ArrayList<String>();
		  
		  double endTransAxisMove = 0;
		  double endFrontAxisMove = 0;
		  double endSagitAxisMove = 0;
		  /*  double handleTransAxisMove = 0;
		  double handleFrontAxisMove = 0;
		  double handleSagitAxisMove = 0; 
		  
		  //Manche
		  handleTransAxisMove = currentTrocarPosition.get(0) - previousTrocarPosition.get(0);
		  handleFrontAxisMove = currentTrocarPosition.get(1) - previousTrocarPosition.get(1);
		  handleSagitAxisMove = currentTrocarPosition.get(2) - previousTrocarPosition.get(2);
		 */ 
		  //Calcul des mouvements sur les axes x, y et z
		  endTransAxisMove = currentTrocarPosition.get(3) - previousTrocarPosition.get(3);
		  endFrontAxisMove = currentTrocarPosition.get(4) - previousTrocarPosition.get(4);
		  endSagitAxisMove = currentTrocarPosition.get(5) - previousTrocarPosition.get(5);
		  
		  //if(endTransAxisMove == 0)trocarMovesSemantic.add(trocar_t_unchanged);
		  if (endTransAxisMove < 0) trocarMovesSemantic.add(trocar_front);
		  if (endTransAxisMove > 0)  trocarMovesSemantic.add(trocar_back);
		  
		  /*if(endFrontAxisMove == 0){
			  trocarMovesSemantic.add(trocar_f_unchanged);
		  }*/
		  if (endFrontAxisMove < 0 )  trocarMovesSemantic.add(trocar_up);
		  if (endFrontAxisMove > 0 ) trocarMovesSemantic.add(trocar_down);
		  
		  
		  if (endSagitAxisMove < 0 )  trocarMovesSemantic.add(trocar_right);
		  if (endSagitAxisMove > 0)  trocarMovesSemantic.add(trocar_left);
		  
		  return trocarMovesSemantic;
	  }
	  
	  public List<String> defineTrocarOrientation(List<Double> previousTrocarPosition, List<Double>currentTrocarPosition){
		  
		  List <Double> currentTrocarPositionCoordinates = new ArrayList<Double>();
		  List <Double> previousTrocarPositionCoordinates = new ArrayList<Double>();
		  List <Double> vectorsCrossProduct = new ArrayList<Double>();
		  List <String> trocarOrientationSemantic = new ArrayList<String>();
		  //List<List<Double>> trocarPositionsCoordinates = new ArrayList<List<Double>>();
		  double currentPosX = 0;
		  double currentPosY = 0;
		  double currentPosZ = 0;
		  double previousPosX = 0;
		  double previousPosY = 0;
		  double previousPosZ = 0;
		  
		//Calcul des vecteurs définis par le trocar dans les deux positions: courante et précédente
		  currentPosX = currentTrocarPosition.get(0) - currentTrocarPosition.get(3);
		  currentPosY = currentTrocarPosition.get(1) - currentTrocarPosition.get(4);
		  currentPosZ = currentTrocarPosition.get(2) - currentTrocarPosition.get(5);
		  currentTrocarPositionCoordinates.add(currentPosX);
		  currentTrocarPositionCoordinates.add(currentPosY);
		  currentTrocarPositionCoordinates.add(currentPosZ);
		  
		  previousPosX = previousTrocarPosition.get(0) - previousTrocarPosition.get(3);
		  previousPosY = previousTrocarPosition.get(1) - previousTrocarPosition.get(4);
		  previousPosZ = previousTrocarPosition.get(2) - previousTrocarPosition.get(5);
		  previousTrocarPositionCoordinates.add(previousPosX);
		  previousTrocarPositionCoordinates.add(previousPosY);
		  previousTrocarPositionCoordinates.add(previousPosZ);
		  
		  vectorsCrossProduct = getCrossProduct(previousTrocarPositionCoordinates, currentTrocarPositionCoordinates);
		  
		  /*if (vectorsCrossProduct.get(0) > vectorsCrossProduct.get(1)
				  && vectorsCrossProduct.get(0)> vectorsCrossProduct.get(2)){
			  if(vectorsCrossProduct.get(0) > 0) trocarOrientationSemantic.add(trocar_lnback);
			  //if (vectorsCrossProduct.get(0) == 0) trocarOrientationSemantic.add("NA_X");
			  if (vectorsCrossProduct.get(0) < 0) trocarOrientationSemantic.add(trocar_lnfront);
		  }
		  
		  if (vectorsCrossProduct.get(2) > vectorsCrossProduct.get(0)
				  && vectorsCrossProduct.get(2)> vectorsCrossProduct.get(1)){
			  if(vectorsCrossProduct.get(2) > 0) trocarOrientationSemantic.add(trocar_lnleft);
			  //if (vectorsCrossProduct.get(2) == 0) trocarOrientationSemantic.add("NA_Z");
			  if (vectorsCrossProduct.get(2) < 0) trocarOrientationSemantic.add(trocar_lnright);
		  }
		  
		 if (vectorsCrossProduct.get(1) > vectorsCrossProduct.get(0)
				  && vectorsCrossProduct.get(1)> vectorsCrossProduct.get(2)){
			  if(vectorsCrossProduct.get(1) > 0) trocarOrientationSemantic.add("oriented_upY");
			  if (vectorsCrossProduct.get(1) < 0) trocarOrientationSemantic.add("oriented_downY");
			  //if (vectorsCrossProduct.get(1) == 0) trocarOrientationSemantic.add("NA_Y");
		  }*/
		  
		  if (vectorsCrossProduct.get(0) > vectorsCrossProduct.get(2)){
			  if(vectorsCrossProduct.get(0) > 0) trocarOrientationSemantic.add(trocar_lnback);
			  if (vectorsCrossProduct.get(0) < 0) trocarOrientationSemantic.add(trocar_lnfront);
		  }
		  
		  if (vectorsCrossProduct.get(2) > vectorsCrossProduct.get(0)){
			  if(vectorsCrossProduct.get(2) > 0) trocarOrientationSemantic.add(trocar_lnleft);
			  if (vectorsCrossProduct.get(2) < 0) trocarOrientationSemantic.add(trocar_lnright);
		  }
		  
		 return trocarOrientationSemantic;
	  }
	  
	  public List<String> defineFluoroscopeFaceMoves(List<Double> previousFluoroscopeFacePosition, List<Double>currentFluoroscopeFacePosition){
		  List <String> fluoroscopeFaceMoveSemantic = new ArrayList<String>();
		  double displacementX 	= 0;
		  double displacementY 	= 0;
		  double displacementZ 	= 0;
		  
		  displacementX = currentFluoroscopeFacePosition.get(3) - previousFluoroscopeFacePosition.get(3);
		  displacementY = currentFluoroscopeFacePosition.get(4) - previousFluoroscopeFacePosition.get(4);
		  displacementZ = currentFluoroscopeFacePosition.get(5) - previousFluoroscopeFacePosition.get(5);
		  
		  //if(displacementX == 0) fluoroscopeFaceMoveSemantic.add(fluoroscope_ft_unchanged);
		  if(displacementX>0) fluoroscopeFaceMoveSemantic.add(fluoroscope_f_up);
		  if(displacementX<0) fluoroscopeFaceMoveSemantic.add(fluoroscope_f_down);

		  if(displacementY>0) fluoroscopeFaceMoveSemantic.add("depY_positif");
		  if(displacementY<0) fluoroscopeFaceMoveSemantic.add("depY_negatif");
		  //fluoroscopeFaceMoveSemantic.add("NA_Face");
		  
		  //if(displacementZ==0) fluoroscopeFaceMoveSemantic.add(fluoroscope_fcc_unchanged);
		  if(displacementZ>0) fluoroscopeFaceMoveSemantic.add(fluoroscope_f_left);
		  if (displacementZ<0) fluoroscopeFaceMoveSemantic.add(fluoroscope_f_right);
		  
		  return fluoroscopeFaceMoveSemantic;
	  }
	  public List<String> defineFluoroscopeProfileMoves(List<Double> previousFluoroscopeProfilePosition, List<Double>currentFluoroscopeProfilePosition){
		  List <String> fluoroscopeProfileMoveSemantic = new ArrayList<String>();
		  double displacementX 	= 0;
		  double displacementY 	= 0;
		  double displacementZ 	= 0;
		  
		  displacementX = currentFluoroscopeProfilePosition.get(3) - previousFluoroscopeProfilePosition.get(3);
		  displacementY = currentFluoroscopeProfilePosition.get(4) - previousFluoroscopeProfilePosition.get(4);
		  displacementZ = currentFluoroscopeProfilePosition.get(5) - previousFluoroscopeProfilePosition.get(5);
		  
		  //if (displacementX>0) fluoroscopeProfileMoveSemantic.add("depX_positif");
		  //if (displacementX<0) fluoroscopeProfileMoveSemantic.add("depX_negatif");
		  
		  //if(displacementY == 0) fluoroscopeProfileMoveSemantic.add(fluoroscope_pap_unchanged);
		  if (displacementY>0) fluoroscopeProfileMoveSemantic.add(fluoroscope_p_up);
		  if (displacementY<0) fluoroscopeProfileMoveSemantic.add(fluoroscope_p_down);
		  
		  //if(displacementZ==0) fluoroscopeProfileMoveSemantic.add(fluoroscope_pcc_unchanged);
		  if (displacementZ>0) fluoroscopeProfileMoveSemantic.add(fluoroscope_p_left);
		  if (displacementZ<0) fluoroscopeProfileMoveSemantic.add(fluoroscope_p_right);
		  
		  return fluoroscopeProfileMoveSemantic;
	  }
	  
	  
	  
	  
	  public List<String> defineFluoroscopeFaceOrientation(List<Double> previousFluoroscopeFacePosition, List<Double>currentFluoroscopeFacePosition){
		  List <Double> vectorsCrossProduct = new ArrayList<Double>();
		  List <String> fluoroscopeFaceOrientationSemantic = new ArrayList<String>();
		  List<Double> currentFluoroscopeFaceCoordinates = new ArrayList<Double>();
		  List<Double> previousFluoroscopeFaceCoordinates = new ArrayList<Double>();
		  
		  double currentFluoroscopeFaceX = currentFluoroscopeFacePosition.get(0);
		  double currentFluoroscopeFaceY = currentFluoroscopeFacePosition.get(1);
		  double currentFluoroscopeFaceZ = currentFluoroscopeFacePosition.get(2);
		  currentFluoroscopeFaceCoordinates.add(currentFluoroscopeFaceX);
		  currentFluoroscopeFaceCoordinates.add(currentFluoroscopeFaceY);
		  currentFluoroscopeFaceCoordinates.add(currentFluoroscopeFaceZ);
		  
		  double previousFluoroscopeFaceX = previousFluoroscopeFacePosition.get(0);
		  double previousFluoroscopeFaceY = previousFluoroscopeFacePosition.get(1);
		  double previousFluoroscopeFaceZ = previousFluoroscopeFacePosition.get(2);
		  previousFluoroscopeFaceCoordinates.add(previousFluoroscopeFaceX);
		  previousFluoroscopeFaceCoordinates.add(previousFluoroscopeFaceY);
		  previousFluoroscopeFaceCoordinates.add(previousFluoroscopeFaceZ);
		  
		 
		  vectorsCrossProduct = getCrossProduct(previousFluoroscopeFaceCoordinates, currentFluoroscopeFaceCoordinates);
		 // System.out.println(vectorsCrossProduct);
		  
		  if (vectorsCrossProduct.get(0) > vectorsCrossProduct.get(1)){
			  if (vectorsCrossProduct.get(0) > 0) fluoroscopeFaceOrientationSemantic.add(fluoroscope_f_lnback);
			  if (vectorsCrossProduct.get(0) < 0) fluoroscopeFaceOrientationSemantic.add(fluoroscope_f_lnfront);
		  }
		  
		  if (vectorsCrossProduct.get(2) > vectorsCrossProduct.get(0)){
			  if (vectorsCrossProduct.get(2) > 0) fluoroscopeFaceOrientationSemantic.add(fluoroscope_f_lnleft);
			  if (vectorsCrossProduct.get(2) < 0) fluoroscopeFaceOrientationSemantic.add(fluoroscope_f_lnright);
		  }
		  
		 /*if (vectorsCrossProduct.get(1) > vectorsCrossProduct.get(0)
				  && vectorsCrossProduct.get(1)> vectorsCrossProduct.get(2)){
			  if(vectorsCrossProduct.get(1) > 0) FluoroscopeFaceOrientationSemantic.add("oriented_upY");
			  if (vectorsCrossProduct.get(1) < 0) FluoroscopeFaceOrientationSemantic.add("oriented_downY");
			  if (vectorsCrossProduct.get(1) == 0) FluoroscopeFaceOrientationSemantic.add("NA_Y");
		  }*/
		  
		 return fluoroscopeFaceOrientationSemantic;  
	  }
	  
	  public List<String> defineFluoroscopeProfileOrientation(List<Double> previousFluoroscopeProfilePosition, List<Double>currentFluoroscopeProfilePosition){
		  List <Double> vectorsCrossProduct = new ArrayList<Double>();
		  List <String> fluoroscopeProfileOrientationSemantic = new ArrayList<String>();
		  
		  List<Double> currentFluoroscopeFaceCoordinates = new ArrayList<Double>();
		  List<Double> previousFluoroscopeFaceCoordinates = new ArrayList<Double>();
		  
		  double currentFluoroscopeProfileX = currentFluoroscopeProfilePosition.get(0);
		  double currentFluoroscopeProfileY = currentFluoroscopeProfilePosition.get(1);
		  double currentFluoroscopeProfileZ = currentFluoroscopeProfilePosition.get(2);
		  currentFluoroscopeFaceCoordinates.add(currentFluoroscopeProfileX);
		  currentFluoroscopeFaceCoordinates.add(currentFluoroscopeProfileY);
		  currentFluoroscopeFaceCoordinates.add(currentFluoroscopeProfileZ);
		  
		  double previousFluoroscopeProfileX = previousFluoroscopeProfilePosition.get(0);
		  double previousFluoroscopeProfileY = previousFluoroscopeProfilePosition.get(1);
		  double previousFluoroscopeProfileZ = previousFluoroscopeProfilePosition.get(2);
		  previousFluoroscopeFaceCoordinates.add(previousFluoroscopeProfileX);
		  previousFluoroscopeFaceCoordinates.add(previousFluoroscopeProfileY);
		  previousFluoroscopeFaceCoordinates.add(previousFluoroscopeProfileZ);
		  
		  
		  vectorsCrossProduct = getCrossProduct(previousFluoroscopeProfilePosition, currentFluoroscopeProfilePosition);
		  
		  /*if (vectorsCrossProduct.get(0) > vectorsCrossProduct.get(1)){
			  if (vectorsCrossProduct.get(0) > 0) fluoroscopeProfileOrientationSemantic.add(fluoroscope_f_lnback);
			  if (vectorsCrossProduct.get(0) == 0) fluoroscopeProfileOrientationSemantic.add("NA_X");
			  if (vectorsCrossProduct.get(0) < 0) fluoroscopeProfileOrientationSemantic.add(fluoroscope_f_lnfront);
		  }*/
		  
		  if (vectorsCrossProduct.get(2) > vectorsCrossProduct.get(0)){
			  if (vectorsCrossProduct.get(2) > 0) fluoroscopeProfileOrientationSemantic.add(fluoroscope_p_lnleft);
			  if (vectorsCrossProduct.get(2) < 0) fluoroscopeProfileOrientationSemantic.add(fluoroscope_p_lnright);
		  }
		  
		 if (vectorsCrossProduct.get(1) > vectorsCrossProduct.get(0)
				  && vectorsCrossProduct.get(1)> vectorsCrossProduct.get(2)){
			  if(vectorsCrossProduct.get(1) > 0) fluoroscopeProfileOrientationSemantic.add(fluoroscope_p_lnback);
			  if (vectorsCrossProduct.get(1) < 0) fluoroscopeProfileOrientationSemantic.add(fluoroscope_p_lnfront);
		  }
		 return fluoroscopeProfileOrientationSemantic;  
	  }

	  public List<String> defineTransverseMarkMoves(List<Double> currentTranverseMarkPosition, List<Double>previousTranverseMarkPosition){
		  List <Double> currentTMarkCoordinates = new ArrayList<Double>();
		  List <Double> previousTMarkCoordinates = new ArrayList<Double>();
		  List <Double> vectorsCrossProduct = new ArrayList<Double>();
		  List <String> transverseMarkSemantic = new ArrayList<String>();
		  
		  double currentTmarkX = 0;
		  double currentTmarkY = 0;
		  double currentTmarkZ = 0;
		  double previousTmarkX = 0;
		  double previousTmarkY = 0;
		  double previousTmarkZ = 0;
		  
		//Calcul des vecteurs définis par les repères cutanées dans les deux positions: courante et précédente
		  currentTmarkX = currentTranverseMarkPosition.get(0) - currentTranverseMarkPosition.get(3);
		  currentTmarkY = currentTranverseMarkPosition.get(1) - currentTranverseMarkPosition.get(4);
		  currentTmarkZ = currentTranverseMarkPosition.get(2) - currentTranverseMarkPosition.get(5);
		  currentTMarkCoordinates.add(currentTmarkX);
		  currentTMarkCoordinates.add(currentTmarkY);
		  currentTMarkCoordinates.add(currentTmarkZ);
		  previousTmarkX = previousTranverseMarkPosition.get(0) - previousTranverseMarkPosition.get(3);
		  previousTmarkY = previousTranverseMarkPosition.get(1) - previousTranverseMarkPosition.get(4);
		  previousTmarkZ = previousTranverseMarkPosition.get(2) - previousTranverseMarkPosition.get(5);
		  previousTMarkCoordinates.add(previousTmarkX);
		  previousTMarkCoordinates.add(previousTmarkY);
		  previousTMarkCoordinates.add(previousTmarkZ);
		  
		  if(currentTmarkZ - previousTmarkZ < 0) transverseMarkSemantic.add(tMark_right);
		  if(currentTmarkZ - previousTmarkZ > 0) transverseMarkSemantic.add(tMark_left);
		  
		  vectorsCrossProduct = getCrossProduct(previousTMarkCoordinates, currentTMarkCoordinates);
		  
		  if (vectorsCrossProduct.get(2) > vectorsCrossProduct.get(0)){
			  if(vectorsCrossProduct.get(2) > 0) transverseMarkSemantic.add(tMark_lnleft);
			  if (vectorsCrossProduct.get(2) < 0) transverseMarkSemantic.add(tMark_lnright);
		  }
		 return transverseMarkSemantic;
	  }
	  
	  public List<String> defineRightMarkMoves(List<Double> currentRightMarkPosition, List<Double>previousRightMarkPosition){
		  List <Double> currentRMarkCoordinates = new ArrayList<Double>();
		  List <Double> previousRMarkCoordinates = new ArrayList<Double>();
		  List <String> rightMarkSemantic = new ArrayList<String>();
		  List <Double> vectorsCrossProduct = new ArrayList<Double>();
		  
		  double currentRmarkX = 0;
		  double currentRmarkY = 0;
		  double currentRmarkZ = 0;
		  
		  double previousRmarkX = 0;
		  double previousRmarkY = 0;
		  double previousRmarkZ = 0;
		  currentRmarkX = currentRightMarkPosition.get(6) - currentRightMarkPosition.get(9);
		  currentRmarkY = currentRightMarkPosition.get(7) - currentRightMarkPosition.get(10);
		  currentRmarkZ = currentRightMarkPosition.get(8) - currentRightMarkPosition.get(11);
		  currentRMarkCoordinates.add(currentRmarkX);
		  currentRMarkCoordinates.add(currentRmarkY);
		  currentRMarkCoordinates.add(currentRmarkZ);
		  previousRmarkX = previousRightMarkPosition.get(6) - previousRightMarkPosition.get(9);
		  previousRmarkY = previousRightMarkPosition.get(7) - previousRightMarkPosition.get(10);
		  previousRmarkZ = previousRightMarkPosition.get(8) - previousRightMarkPosition.get(11);
		  previousRMarkCoordinates.add(previousRmarkX);
		  previousRMarkCoordinates.add(previousRmarkY);
		  previousRMarkCoordinates.add(previousRmarkZ);
		  
		  if(currentRmarkX - previousRmarkX < 0) rightMarkSemantic.add(rMark_down);
		  if(currentRmarkX - previousRmarkX > 0) rightMarkSemantic.add(rMark_up);
		  
		  vectorsCrossProduct = getCrossProduct(previousRMarkCoordinates, currentRMarkCoordinates);
		  
		  if (vectorsCrossProduct.get(2) > vectorsCrossProduct.get(0)){
			  if(vectorsCrossProduct.get(2) > 0) rightMarkSemantic.add(rMark_lnleft);
			  if (vectorsCrossProduct.get(2) < 0) rightMarkSemantic.add(rMark_lnright);
		  }
		  
		  return rightMarkSemantic;
	  }
	  
	  public List<String> defineLeftMarkMoves(List<Double> currentLeftMarkPosition, List<Double>previousLeftMarkPosition){
		  List <Double> currentLMarkCoordinates = new ArrayList<Double>();
		  List <Double> previousLMarkCoordinates = new ArrayList<Double>();
		  List <String> leftMarkSemantic = new ArrayList<String>();
		  List <Double> vectorsCrossProduct = new ArrayList<Double>();
		  
		  double currentLmarkX = 0;
		  double currentLmarkY = 0;
		  double currentLmarkZ = 0;
		  double previousLmarkX = 0;
		  double previousLmarkY = 0;
		  double previousLmarkZ = 0;
		  
		  currentLmarkX = currentLeftMarkPosition.get(12) - currentLeftMarkPosition.get(15);
		  currentLmarkY = currentLeftMarkPosition.get(13) - currentLeftMarkPosition.get(16);
		  currentLmarkZ = currentLeftMarkPosition.get(14) - currentLeftMarkPosition.get(17);
		  currentLMarkCoordinates.add(currentLmarkX);
		  currentLMarkCoordinates.add(currentLmarkY);
		  currentLMarkCoordinates.add(currentLmarkZ);
		  previousLmarkX = previousLeftMarkPosition.get(12) - previousLeftMarkPosition.get(15);
		  previousLmarkY = previousLeftMarkPosition.get(13) - previousLeftMarkPosition.get(16);
		  previousLmarkZ = previousLeftMarkPosition.get(14) - previousLeftMarkPosition.get(17);
		  previousLMarkCoordinates.add(previousLmarkX);
		  previousLMarkCoordinates.add(previousLmarkY);
		  previousLMarkCoordinates.add(previousLmarkZ);
		  
		  if(currentLmarkX - previousLmarkX < 0) leftMarkSemantic.add(lMark_down);
		  if(currentLmarkX - previousLmarkX > 0) leftMarkSemantic.add(lMark_up);
		  
		  vectorsCrossProduct = getCrossProduct(previousLMarkCoordinates, currentLMarkCoordinates);
		  
		  if (vectorsCrossProduct.get(2) > vectorsCrossProduct.get(0)){
			  if(vectorsCrossProduct.get(2) > 0) leftMarkSemantic.add(lMark_lnleft);
			  if (vectorsCrossProduct.get(2) < 0) leftMarkSemantic.add(lMark_lnright);
		  }
		  
		  return leftMarkSemantic;
	  }
	  
	  public String defineFixationSemantic(String fixationsSequence){
		  String fixationSemantic = "";
		  String currentFixation = "";
		  String [] currentFixationElts;
		  String [] fixationsTable;
		  int duration = 0;
		  String aoipoi = "";
		  String simFixations = "";
		  String [] aoipoiElts;
		 
		  String fixationCategory = "";
		  
		  if(!fixationsSequence.isEmpty()){
		  fixationsTable = fixationsSequence.split(";");
		  
		  for(int i=0; i<=fixationsTable.length-1; i++){
			  currentFixation = fixationsTable[i];
			  
			  if(currentFixation.contains("(")){
				currentFixation = currentFixation.replace("(", "");
				currentFixation = currentFixation.replace(")", "");
				  
				currentFixationElts = currentFixation.split(",");
				aoipoi = currentFixationElts[0];
				aoipoiElts = aoipoi.split(" ");
				duration = Integer.parseInt(currentFixationElts[1]);
				  
				if(duration<200)
					fixationCategory = "_short";
				else if (duration >200 && duration <=1000)
					fixationCategory = "";
				else if (duration > 1000 && duration <= 3000)
					fixationCategory = "_long";
				else if (duration> 3000)
					fixationCategory = "_verylong";
				  
				  simFixations = "(";
				  for(int j=0; j<aoipoiElts.length-1; j++){
					  simFixations = simFixations.concat(aoipoiElts[j]+fixationCategory+" ");
				  }
				  simFixations = simFixations.concat(aoipoiElts[aoipoiElts.length-1]+fixationCategory+");");
				  fixationSemantic = fixationSemantic.concat(simFixations);
			  }
			  else{
				  System.out.println(currentFixation);
				  currentFixationElts = currentFixation.split(",");
				  
				  aoipoi = currentFixationElts[0];
				  duration = Integer.parseInt(currentFixationElts[1]);
				  
				  if(duration<200)
					  fixationCategory = "_short";
				  else if (duration >200 && duration <=1000)
					  fixationCategory = "";
				  else if (duration > 1000 && duration <= 3000)
					  fixationCategory = "_long";
				  else if (duration> 3000)
					  fixationCategory = "_verylong";
				  
				  aoipoi = aoipoi.concat(fixationCategory);
				  fixationSemantic = fixationSemantic.concat(aoipoi+";");
				  aoipoi = "";
			  }
		  }
		  }
		  
		  return fixationSemantic;
	  }
}








