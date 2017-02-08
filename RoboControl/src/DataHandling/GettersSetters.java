package DataHandling;
/**
 * Setters and getters for all data sent form robot
 * 
 * @author Sean Murphy
 * Student number: 11829078
 *
 */
public class GettersSetters {

	private static String nextMessageToRobot;

	// String to hold compass angle
	private static String compassAngle;

	// String to hold signal strength not currently in use 29/05/2015
	private static String signalStrength;

	// Strings to hold IR readings not currently in use 29/05/2015
	private static String centreIRSensor;
	private static String rightIRSensor;
	private static String leftIRSensor;

	// Strings to hold sonar readings not currently in use 29/05/2015
	private static String lFSonarInches;
	private static String centerSonarInches;
	private static String rightFSonarInches;
	private static String backSonarInches;
	
	// clickable map area calculations
	private static int TempHeading;
	private static String actRotation;
	
	// wheel sensor data from robot
	private static int wheelRotation;
	private static int wheelForward;
	private static int wheelReverse;
	private static int wheelRotate;
	
	// sonar sensor information not currently in use 29/05/2015
	private static String leftSideSonarInches;
	private static String reftSideSonarInches;
	
	//private static int noMotion;
	private static String currentBearing;
	
	private static double[] distArrayTmp;
	
	/**
	 * called from SerialConn/upDateUI()
	 * @param a
	 */
	public static void setDistArray(double[] a) {
		
		distArrayTmp = new double[360];
		distArrayTmp = a;
	}
	
	
	
	/****************************************
	 * 
	 *				 Setters
	 *
	 ***************************************/
	public static void setCurrentBearing(String cB){
		
		currentBearing = cB;
	}
	
	/**
	 * 
	 * @param csi
	 */
	public static void setcenterSonarInches( String csi ) {

		centerSonarInches = csi;
	}
	
	/**
	 * 
	 * @param fli
	 */
	public static void setFLSonarInches(String fli) {

		lFSonarInches = fli;
	}

	/**
	 * 
	 * @param cir
	 */
	public static void setcentreIRSensor( String cir ) {

		centreIRSensor = cir;
	}

	/**
	 * 
	 * @param bsi
	 */
	public static void setbackSonarInches( String bsi ) {

		backSonarInches = bsi;
	}

	/**
	 * 
	 * @param compassReading
	 */
	public static void setcompassAngle( String cA ) {

		compassAngle = cA;
	}

	/**
	 * 
	 * @param rFS
	 */
	public static void setFRSonarInches( String rFS ) {

		rightFSonarInches = rFS;
	}

	/**
	 * 
	 * @param wR
	 */
	public static void setWheelRotation( String wR ) {

		wheelRotation = Integer.parseInt(wR);
	}
	
	/**
	 * 
	 * @param wF
	 */
	public static void setWheelForward( String wF ) {

		wheelForward = Integer.parseInt(wF);
	}
	
	/**
	 * 
	 * @param wRev
	 */
	public static void setWheelReverse( String wRev ) {

		wheelReverse = Integer.parseInt(wRev);
	}
	
	/**
	 * 
	 * @param wRota
	 */
	public static void setRotating( String wRota ) {

		wheelRotate = Integer.parseInt(wRota);
	}
	
	/**
	 * 
	 * @param lSS
	 */
	public static void setLeftSideSonar( String lSS ) {

		leftSideSonarInches = lSS;
	}
	
	/**
	 * 
	 * @param rSS
	 */
	public static void setReftSideSonar(String rSS) {
		
		reftSideSonarInches = rSS;
	}
	
	/**
	 * 
	 * @param angle
	 */
	public static void setHeadingAngle(int angle) {
		
		TempHeading = angle;
	}
	
	/**
	 * 
	 * @param angle
	 */
	public static void setActRotation(String deg) {
		
		actRotation = deg;
	}
	
	
	
	
	/*****************************************
	 * 
	 *				 Getters
	 * 
	 ***************************************/
	
	
	/**
	 * 
	 * @return
	 */
	public static String getCurrentBearing(){
		
		return currentBearing;
	}
	
	/**
	 * 
	 * @return
	 */
	public static double[] getDistArray() {
		
		return distArrayTmp;
	}
	
	/**
	 * 
	 * @return compassAngle
	 */
	public static String getcompassAngle() {

		return compassAngle;
	}
	
	/**
	 * 
	 * @return
	 */
	public static int getHeadingAngle() {
		
		return TempHeading;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getActRotationAmount() {
		
		return actRotation;
	}

	/**
	 * 
	 * @return centerSonarInches
	 */
	public static String getCentSonarInches() {

		return centerSonarInches;
	}

	/**
	 * 
	 * @return centreIRSensor
	 */
	public static String getcentreIRSensor() {

		return centreIRSensor;
	}

	/**
	 * 
	 * @return backSonarInches
	 */
	public static String getbackSonarInches() {

		return backSonarInches;
	}

	/**
	 * 
	 * @return lFSonarInches
	 */
	public static String getFLSonarInches() {

		return lFSonarInches;
	}
	
	/**
	 * 
	 * @return rightFSonarInches
	 */
	public static String getFRSonarInches() {

		return rightFSonarInches;
	}
	
	/**
	 * 
	 * @return wheelRotation
	 */
	public static int getWheelRotation() {

		return wheelRotation;
	}
	
	/**
	 * 
	 * @return
	 */
	public static int getWheelForward() {

		return wheelForward;
	}
	
	/**
	 * 
	 * @return
	 */
	public static int getWheelReverse() {

		return wheelReverse;
	}
	
	/**
	 * 
	 * @return
	 */
	public static int getWheelRotating() {

		return wheelRotate;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getLeftSideSonar() {

		return leftSideSonarInches;
	}

	/**
	 * 
	 * @return
	 */
	public static String getReftSideSonar() {

		return reftSideSonarInches;
	}
}
