package DataHandling;

import java.awt.Shape;
import GUI.GUI;
import GUI.GuiSensorReadings;
import RobotConnections.SerialConn;

/**
 * 
 * Populating the 2D arrays is here. The arrays are the map.
 * 
 * @author Sean Murphy
 */
public class DistanceDataProcessing {

	private double cFS; 		// main sensor data var is used for drawing data to canvas

	public int compassReading = 0; // holds compass reading
	private double[] distArray; // raw Lidar data in inches
	public int[][] mapXY; 		// Main map array
	public int[][] obsXY; 		// observed array

	private int mapSize;
	private int sqSize; 		// set this to map square size ( mapGui class x or y /
								// window size (800) )
	private double scaling;

	// final SerialConn serialClass;

	private double sensorTemp;
	private int IndexX;
	private int IndexY;

	private double theta; 		// compass reading
	private double hyp;			// The hypotonuse - the line from robot to scanned object
	private double adj;		 	// The adjacent - Will hold the distance along X axis
								// that we need to increment
	private double opp; 		// The opposite - line going along Y axis, holding the
								// distance we need to increment

	private static double temp; // holds temp readings ready to be sent to text
								// fields within sensor read gui

	/**
	 * 
	 * @param sSize
	 * @param arrSize
	 * @param scale
	 */
	public DistanceDataProcessing(int sSize, int arrSize, double scale) {

		mapSize = arrSize;
		sqSize = sSize;
		scaling = scale;
		// serialClass = instance;
		mapXY = new int[mapSize][mapSize];
		obsXY = new int[mapSize][mapSize];
		distArray = new double[360];

	}

	/**
	 * 
	 * @param robot
	 */
	public void update(Shape robot) {

		mapXY = new int[mapSize][mapSize];
		obsXY = new int[mapSize][mapSize];

		distArray = GettersSetters.getDistArray();
		/*
		 * the line below used to simulate rotation for non-live testing or
		 * comment out both lines below and use int compassReading declaration as 0
		 */
		
		// compassReading = rotate( compassReading );
		
		/*
		 * The following line is no longer needed for mapping as the LiDARs natural 
		 * orientation is now used with the robot being fixed within the centre of 
		 * the map.
		 */
		// compassReading = Integer.parseInt( GettersSetters.getcompassAngle() );

		for (int i = 0; i < distArray.length; i++) {

			cFS = distArray[i];
			calcIndexPosMap(robot, cFS, i);
		}
		updateSensorReadGui();
	}

	/**
	 * 
	 * 
	 * @param temp
	 * @param sensor
	 * @param a
	 */
	public void calcIndexPosMap(Shape temp, double sensor, int a) {

		sensorTemp = sensor;

		theta = (compassReading - a) * (Math.PI / 180); // The rotation
		hyp = sensorTemp + ((temp.getBounds2D().getHeight() / 2) / sqSize);
		opp = hyp * (Math.cos(theta));
		adj = hyp * (Math.sin(theta));

		IndexX = (int) (((temp.getBounds2D().getCenterX()) / sqSize) + adj);
		IndexY = (int) (((temp.getBounds2D().getCenterY()) / sqSize) - opp);

		if (IndexX < mapSize && IndexY < mapSize && IndexX > 0 && IndexY > 0) {

			obsXY[IndexX][IndexY] = obsXY[IndexX][IndexY] = 1;

			if (hyp == cFS + (temp.getBounds2D().getHeight() / 2 / sqSize)
					&& cFS <= (236 * scaling)) {

				mapXY[IndexX][IndexY] = mapXY[IndexX][IndexY] = 1;

			} else {
				mapXY[IndexX][IndexY] = mapXY[IndexX][IndexY] = -1;
			}

			// System.out.println(mapXY [IndexX] [IndexY] ); // de-bugging
		}

		/*
		 * For loop below unused at the moment Its function is to fill the
		 * indexes from the object detected back to the robot. It is a recursive
		 * call to this method. Only needed for static map/occupancy grid
		 * not currently in use 29/05/2015
		 */
		/*
		 * for ( double i = 0; i < sensorTemp; ) {
		 * 
		 * sensorTemp = sensorTemp - 1; calcIndexPosMap(temp,sensorTemp,a); }
		 */
	}

	/**
	 * Used for testing without robot Arduino required though with test program
	 * 
	 * @param A
	 * @return compass
	 * 
	 */
	public int rotate(int A) {

		if (compassReading == 359) {
			compassReading = 1;
		}
		compassReading = compassReading + 1;
		return compassReading;
	}

	/**
	 * returns map array
	 * 
	 * @return mapXY
	 */
	public int[][] getMapXY() {

		return mapXY;
	}

	/**
	 * returns observed array
	 * 
	 * @return obsXY
	 */
	public int[][] getObsXY() {

		return obsXY;
	}

	/**
	 * Filled as if the values are flipped along the y axis (left to right
	 * mirrored) Due to the rotation direction of the LiDAR
	 * 
	 */
	public void updateSensorReadGui() {

		temp = distArray[0];
		GuiSensorReadings.text0deg.setText(degToString(temp));

		temp = distArray[315];
		GuiSensorReadings.text45deg.setText(degToString(temp));

		temp = distArray[270];
		GuiSensorReadings.text90deg.setText(degToString(temp));

		temp = distArray[225];
		GuiSensorReadings.text135deg.setText(degToString(temp));

		temp = distArray[180];
		GuiSensorReadings.text180deg.setText(degToString(temp));

		temp = distArray[135];
		GuiSensorReadings.text225deg.setText(degToString(temp));

		temp = distArray[90];
		GuiSensorReadings.text270deg.setText(degToString(temp));

		temp = distArray[45];
		GuiSensorReadings.text315deg.setText(degToString(temp));
	}

	/**
	 * Called within method updateSensorReadGui() above
	 * @param deg
	 * @return
	 */
	public String degToString(double deg) {

		double incDouble = deg;
		String doubleConvert = String.valueOf(incDouble);
		return doubleConvert;
	}

	/**
	 * Sets heading after user mouse click on map
	 * and calculates rotation amount
	 */
	public static void setHeading() {
		
		int tempHeading = GettersSetters.getHeadingAngle();
		String actRotationAmount = Integer.toString(tempHeading);
		
		GUI.enterHeading.setText(actRotationAmount);
		GettersSetters.setActRotation(actRotationAmount);
		
		// System.out.println(actRotationAmount + " printed from DistanceDataProcessing/setHeadings()");
	}
	
	/**
	 * Sends rotation amount to robot
	 * called from GUI/JButton("Send Heading") line 330
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public static void sendHeading() throws Exception{
		

		String degreeToRotate = GettersSetters.getActRotationAmount();
		System.out.println(degreeToRotate + " printed from DistanceDataProcessing/sendHeadings()");
		SerialConn.sendCommand('R', degreeToRotate);
	}

	/**
	 * The following methods are applied to allow future development to be able
	 * to send commands to the robot from this class after using the map as a
	 * reference for its own location.
	 */

	/**
	 * Send move forward command to robot currently unused
	 * @throws Exception 
	 */
	public static void moveForward() throws Exception {

		SerialConn.sendCommand('^');
	}

	/**
	 * Send move reverse to robot currently unused
	 * @throws Exception 
	 */
	public static void moveBackwards() throws Exception {

		SerialConn.sendCommand('V');
	}

	/**
	 * Send command rotate left to robot currently unused.
	 * @throws Exception 
	 */
	public static void rotateLeft() throws Exception {

		SerialConn.sendCommand('<');
	}

	/**
	 * Send move rotate right to robot currently unused.
	 * @throws Exception 
	 */
	public static void rotateRight() throws Exception {

		SerialConn.sendCommand('>');
	}

	/**
	 * Send the stop command to the robot currently unused.
	 * @throws Exception 
	 */
	public static void stop() throws Exception {

		SerialConn.sendCommand('S');
	}
}