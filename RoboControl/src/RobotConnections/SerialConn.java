package RobotConnections;

import gnu.io.*; // RXTX

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Enumeration;

import DataHandling.GettersSetters;
import GUI.GUI;
import GUI.GuiSensorReadings;

/**
 * Serial connection class. Uses RXTXcomm.jar
 * 
 * @author Modified by and part authored Sean Murphy
 * 
 */
public class SerialConn implements SerialPortEventListener {

	private String chosenPort;
	private boolean foundPorts;
	private static SerialPort serialPort;
	private static BufferedReader input;
	private static OutputStream output;
	private static PrintStream p;
	private static final int TIME_OUT = 2000;
	private static final int DATA_RATE = 9600;

	public ArrayList<String> availablePorts = new ArrayList<String>();

	private CommPortIdentifier serialPortId;
	//static CommPortIdentifier sSerialPortId;

	private Enumeration<?> enumComm;
	private String nextMessageToRobot;

	// String to hold compass angle
	private String compassAngle;

	//String to hold signal strength
	private String signalStrength;

	// Strings to hold IR readings
	private String centreIRSensor;
	private String rightIRSensor;
	private String leftIRSensor;

	// Strings to hold sonar readings
	private String leftFrontSonarInches;
	private String centerSonarInches;
	private String rightFrontSonarInches;
	private String backSonarInches;

	private String wheelRotation;
	private String wheelForward;
	private String wheelReverse;

	private String currentBearing;


	/**
	 * Creates the serial port connection.
	 */
	public void connectButton() {

		try {

			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) serialPortId.open(this.getClass()
					.getName(), TIME_OUT);
			System.out.println("my name is " + serialPort.getName());

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(
					serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			//p = new PrintStream(output);

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);

		} catch (Exception e) {

			// need to unswallow this
		}
	}

	/**
	 * Collects the ports from available ports.
	 * 
	 * @return
	 */
	public ArrayList<String> setCommPorts() {

		enumComm = CommPortIdentifier.getPortIdentifiers();

		while (enumComm.hasMoreElements()) {

			serialPortId = (CommPortIdentifier) enumComm.nextElement();

			if (serialPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

				availablePorts.add(serialPortId.getName());
			}
		}
		return availablePorts;
	}

	/**
	 * Collects the string from the robot and
	 * sets the results.
	 * 
	 * @param message
	 */
	private void upDateUIserialConn( String incData ) {

		// This splits the param around commas, storing each substring as
		// objects in an array

		String[] data = incData.split(",");
		// Switch on first index of array
		switch ( data[0] ) {

		case "S":

			GettersSetters.setcompassAngle( compassAngle = data[1] );         // compass angle
			signalStrength = data[2];
			//GettersSetters.setcentreIRSensor( centreIRSensor = data[7] );
			//rightIRSensor = data[8];
			//leftIRSensor = data[9];
			GettersSetters.setWheelRotation( wheelRotation = data[3] );      // wheel photo sensor
			GettersSetters.setWheelForward( wheelForward = data[4] );        // motion flags from robot
			GettersSetters.setWheelReverse( wheelReverse = data[5] );        // motion flags from robot
			GettersSetters.setCurrentBearing( currentBearing = data[6] );    // the heading the robot has rotated to

			/*
			compassAngle 	 	  = data[1]; // compass angle
			signalStrength 		  = data[2]; // signal strength
			//	centreIRSensor 		  = data[7]; // centre IR
			//	rightIRSensor 		  = data[8]; // right IR
			//	leftIRSensor		  = data[9]; // left IR
			wheelRotation		  = data[3]; // wheel photo sensor
			wheelForward		  = data[4]; // motion flags from robot
			wheelReverse 		  = data[5]; // motion flags from robot
			currentBearing 		  = data[6]; 
			 */
		}

		// Bearing panel
		GuiSensorReadings.txtCompass.setText(compassAngle);
		GuiSensorReadings.targetBearing.setText(currentBearing);

		// System.out.println("upDate Serial printed from SerialConn/upDateUIserialConn method"); // de-bugging
	}

	/**
	 * 
	 * @param inch
	 * @return
	 */
	public String stringInchToStringCm(String inch) {

		return String.valueOf((Double.parseDouble(inch)*2.54));
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getCommPorts() {

		if (!foundPorts)
			setCommPorts();
		foundPorts = true;
		return availablePorts;
	}

	/**
	 * 
	 * @param thePort
	 * @throws NoSuchPortException
	 */
	public void setChosenPort(String thePort) throws NoSuchPortException {

		chosenPort = thePort;
		serialPortId = CommPortIdentifier.getPortIdentifier(chosenPort);
	}

	/**
	 * Read the serial port look for 0xFA followed by 0xAO if these are found 
	 * then the first packet of a rotation is found. Proceed to fill the array.
	 */
	@Override
	public synchronized void serialEvent(final SerialPortEvent oEvent) {

		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {

			try {

				String line = null;
				if ( input.ready() ) {

					line = input.readLine();
					// System.out.println(line + " Printed from SerialConn/serialEvent method");// de-bugging
					upDateUIserialConn(line);// comment this out to simply use LiDAR alone
				}
			} catch (Exception e) {

				System.out.println("here");
				//System.err.println( e.toString() );
			}
		}
	}

	/**
	 * 
	 * @param command
	 * @return
	 * @throws IOException
	 */
	public static boolean sendCommand(char command) throws IOException {

		return sendCommand(command, "");
	}

	/**
	 * 
	 * @param command
	 * @param args
	 * @return
	 * @throws IOException
	 */
	public static boolean sendCommand(char command, String args) throws IOException  {

		output.write( command );
		output.write( args.getBytes("US-ASCII") );
		if (args.length() > 0) output.write('\0');
		output.flush();
		
		//p.print(command);
		//serialPort.getOutputStream().write(command);
		//serialPort.getOutputStream().write(args.getBytes("US-ASCII"));
		//if (args.length() > 0) serialPort.getOutputStream().write('\0');
		//serialPort.getOutputStream().flush();
		//p.flush();
		return true;
	}
}