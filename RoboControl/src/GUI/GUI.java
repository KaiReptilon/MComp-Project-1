package GUI;

import gnu.io.NoSuchPortException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import DataHandling.DistanceDataProcessing;
import RobotConnections.LidarServer;
import RobotConnections.SerialConn;

import java.awt.Color;

/**
 * 
 * Creates a GUI which allows the user to connect and control the robot.
 * 
 * @author Sean Murphy
 *
 */
public class GUI {

	LidarServer server;

	public String portChoice;
	boolean serial;
	boolean wifi;
	//Serial connectivity class
	final SerialConn serialClass;

	//Robot Serial Connection
	private JPanel pnlRobConSerial;
	private JLabel lblRobConSerialPort;
	private JComboBox<String> cmbPortList;
	private JButton btnSerialConnect;

	//Robot Control Panel
	private JPanel pnlRobotControl;
	private JButton btnRobControlAuto;
	private JButton btnRobControlFwdStep;
	private JButton btnRobControlManual;
	private JButton btnRobControlRight;
	private JButton btnRobControlReverseStep;
	private JButton btnRobControlLeft;
	private JButton btnRobControlStop;
	private JButton btnDesktopRealTimeMapLow;

	GuiSensorReadings sensorWindow;

	final JFrame windowFrame;
	final JButton parkButton;
	private JButton btnLaptopRealTimeMap;
	private JButton btnSensorReadingVIew;
	private JTextField txtPortNum;
	public static JTextField xCoord;
	public static JTextField yCoord;
	public static JTextField enterHeading;
	public static JTextField computedAngle;

	// DistanceDataProcessing filterClass = new DistanceDataProcessing(0, 0, 0);

	/**
	 * Constructor.
	 */
	public GUI() {


		serialClass = new SerialConn();

		serial = false;
		wifi = false;

		windowFrame = new JFrame("RoboController");
		parkButton = new JButton("Park");

		windowFrame.setSize(350, 730); // Main window container size (in pixels) change this to resize whole GUI
		windowFrame.getContentPane().setLayout(null);

		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFrame.setVisible(true);

		sensorWindow = new GuiSensorReadings();

		setupRobotConnectionPanel();				
		setupRobotControls();
		setupRealTimeMapButton();	
		setupActionListeners();
		windowFrame.setResizable(false);
	}

	/**
	 * 
	 */
	public void setupActionListeners() {
		// Real Time Map Button Action Listener
		btnDesktopRealTimeMapLow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				new MapVisulisaton(4,200,20,24,10.16,100,800,"800x800 Real Time map ratio: 4x4"
						+ " pixels to 1 array index mapping (160,000 indexes)");
			}
		});

		// Stop Button Override Action Listener
		btnRobControlStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					SerialConn.sendCommand('S');
				} catch (IOException e1) {

					System.out.println("Caught S GUI.java");
				}
			}
		});

		// Robot Control Manual Buttons
		// Forward Button Action Listener
		btnRobControlFwdStep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					SerialConn.sendCommand('^');
				} catch (IOException e1) {

					System.out.println("Caught ^ GUI.java");
				}
			}
		});

		// Left Button Action Listener
		btnRobControlLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					SerialConn.sendCommand('<');
				} catch (IOException e1) {

					System.out.println("Caught < GUI.java");
				}
			}
		});

		// Reverse Button Action Listener
		btnRobControlReverseStep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					SerialConn.sendCommand('V');
				} catch (IOException e1) {

					System.out.println("Caught V GUI.java");
				}
			}
		});

		// Right Button Action Listener
		btnRobControlRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					SerialConn.sendCommand('>');
				} catch (IOException e1) {

					System.out.println("Caught > GUI.java");
				}
			}
		});

		// Robot Control Buttons
		// Manual Control Button Action Listener
		btnRobControlManual.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					SerialConn.sendCommand('M');
				} catch (IOException e1) {

					System.out.println("Caught M GUI.java");
				}
			}
		});

		// Robot Autopilot Button Action Listener
		btnRobControlAuto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					SerialConn.sendCommand('A');

				} catch (IOException e1) {

					System.out.println("Caught A GUI.java");
				}
			}
		});

		// Robot Control Serial Connect Button Action Listener
		btnSerialConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				serialClass.connectButton();
			}
		});

		// Sensor reading view action listener
		btnSensorReadingVIew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				sensorWindow.showSensorWindow();
			}
		});


		// Combo box event listener
		cmbPortList.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				portChoice = (String) cmbPortList.getSelectedItem();//Get string from combo box selection
				try {		

					serialClass.setChosenPort(portChoice);//Set it to chosen port
				}
				catch (NoSuchPortException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 
	 */
	private void setupRealTimeMapButton() {

		JPanel pnlRobWiFiConn = new JPanel();
		pnlRobWiFiConn.setBorder(new TitledBorder(null, "Robot WiFi Connection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlRobWiFiConn.setBounds(10, 11, 324, 63);
		windowFrame.getContentPane().add(pnlRobWiFiConn);
		pnlRobWiFiConn.setLayout(null);

		JButton btnWiFiConn = new JButton("Connect");
		btnWiFiConn.setBackground(UIManager.getColor("ToggleButton.highlight"));
		btnWiFiConn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				Thread serverThread = new Thread(new Runnable() {
					int i = Integer.parseInt(txtPortNum.getText());
					public void run() {     	
						try {

							server = new LidarServer(i);
						} catch (IOException e1) {

							e1.printStackTrace();
						}
					};
				});
				serverThread.start();
			}
		});

		btnWiFiConn.setBounds(195, 23, 97, 29);
		pnlRobWiFiConn.add(btnWiFiConn);

		txtPortNum = new JTextField();
		txtPortNum.setHorizontalAlignment(SwingConstants.CENTER);
		txtPortNum.setText("2000");
		txtPortNum.setBounds(129, 23, 60, 29);
		pnlRobWiFiConn.add(txtPortNum);
		txtPortNum.setColumns(10);

		JLabel lblPortNum = new JLabel("Port No");
		lblPortNum.setHorizontalAlignment(SwingConstants.CENTER);
		lblPortNum.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPortNum.setBounds(59, 23, 60, 29);
		pnlRobWiFiConn.add(lblPortNum);

		JPanel panViewSelect = new JPanel();
		panViewSelect.setBorder(new TitledBorder(null, "Robot View Selections", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panViewSelect.setBounds(10, 329, 324, 226);
		windowFrame.getContentPane().add(panViewSelect);
		panViewSelect.setLayout(null);

		// Sensor reading view button
		btnSensorReadingVIew = new JButton("Sensor Reading View");
		btnSensorReadingVIew.setBackground(UIManager.getColor("ToggleButton.highlight"));
		btnSensorReadingVIew.setBounds(26, 195, 275, 23);
		panViewSelect.add(btnSensorReadingVIew);

		JPanel mapSelectPanel = new JPanel();
		mapSelectPanel.setBorder(new TitledBorder(null, "Desktop View 800x800", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mapSelectPanel.setBounds(26, 24, 275, 86);
		panViewSelect.add(mapSelectPanel);
		mapSelectPanel.setLayout(null);

		JButton btnDesktopRealTimeMapHigh = new JButton("High res map");
		btnDesktopRealTimeMapHigh.setBackground(UIManager.getColor("ToggleButton.highlight"));
		btnDesktopRealTimeMapHigh.setBounds(10, 21, 116, 23);
		mapSelectPanel.add(btnDesktopRealTimeMapHigh);

		JButton btnDesktopRealTimeMapMedium = new JButton("Med res map");
		btnDesktopRealTimeMapMedium.setBackground(UIManager.getColor("ToggleButton.highlight"));
		btnDesktopRealTimeMapMedium.setBounds(149, 21, 116, 23);
		mapSelectPanel.add(btnDesktopRealTimeMapMedium);
		// Real Time Map Button
		btnDesktopRealTimeMapLow = new JButton("Low res map");
		btnDesktopRealTimeMapLow.setBackground(UIManager.getColor("ToggleButton.highlight"));
		btnDesktopRealTimeMapLow.setBounds(74, 55, 130, 23);
		mapSelectPanel.add(btnDesktopRealTimeMapLow);

		JPanel laptopMapPanel = new JPanel();
		laptopMapPanel.setBorder(new TitledBorder(null, "Laptop View 600x600", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		laptopMapPanel.setBounds(26, 121, 275, 63);
		panViewSelect.add(laptopMapPanel);
		laptopMapPanel.setLayout(null);

		//*********************Start map size selection buttons***********************//

		btnLaptopRealTimeMap = new JButton("Laptop Map");
		btnLaptopRealTimeMap.setBackground(UIManager.getColor("ToggleButton.highlight"));
		btnLaptopRealTimeMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				new MapVisulisaton(2,400,10,12,5.08,200,600,"600x600 Real Time map ratio: 2x2"
						+ " pixel to 1 array index mapping (180,000 indexes)");
			}
		});
		btnLaptopRealTimeMap.setBounds(75, 29, 130, 23);
		laptopMapPanel.add(btnLaptopRealTimeMap);

		JPanel pnlBearing = new JPanel();
		pnlBearing.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Robot Bearing Information", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlBearing.setBounds(10, 566, 324, 125);
		windowFrame.getContentPane().add(pnlBearing);
		pnlBearing.setLayout(null);

		JButton btnSetHeading = new JButton("Send Rotation");
		btnSetHeading.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				try {
					DistanceDataProcessing.sendHeading();
				} catch (Exception e) {

					System.out.println("Caught send rotation button press exception");
				}
			}
		});
		btnSetHeading.setBounds(10, 22, 121, 20);
		pnlBearing.add(btnSetHeading);

		xCoord = new JTextField();
		xCoord.setHorizontalAlignment(SwingConstants.CENTER);
		xCoord.setEditable(false);
		xCoord.setBounds(244, 22, 70, 20);
		pnlBearing.add(xCoord);
		xCoord.setColumns(10);

		yCoord = new JTextField();
		yCoord.setHorizontalAlignment(SwingConstants.CENTER);
		yCoord.setEditable(false);
		yCoord.setBounds(244, 53, 70, 20);
		pnlBearing.add(yCoord);
		yCoord.setColumns(10);

		JLabel lblXcoord = new JLabel("Current X Coord");
		lblXcoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblXcoord.setBounds(130, 25, 104, 14);
		pnlBearing.add(lblXcoord);

		JLabel lblYcoord = new JLabel("Current Y Coord");
		lblYcoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblYcoord.setBounds(130, 56, 104, 14);
		pnlBearing.add(lblYcoord);

		// text field for entering bearing will not accept more than 3 characters 
		// and will only accept integers or backspace delete.
		enterHeading = new JTextField();
		enterHeading.setEditable(false);
		//enterHeading.addKeyListener(new KeyAdapter() {
		//	@Override
		//	public void keyTyped(KeyEvent arg0) {
		//		char c = arg0.getKeyChar();
		//		if(!(Character.isDigit(c) || c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE) {
		//			arg0.consume();
		//		}
		//	}
		//});
		enterHeading.setHorizontalAlignment(SwingConstants.CENTER);
		enterHeading.setBounds(30, 53, 86, 20);
		//enterHeading.setDocument(new JTextFieldLimit(3));
		pnlBearing.add(enterHeading);
		enterHeading.setColumns(10);

		computedAngle = new JTextField();
		computedAngle.setHorizontalAlignment(SwingConstants.CENTER);
		computedAngle.setEditable(false);
		computedAngle.setBounds(244, 84, 70, 20);
		pnlBearing.add(computedAngle);
		computedAngle.setColumns(10);

		JLabel lblcomputedAngle = new JLabel("Computed Angle");
		lblcomputedAngle.setHorizontalAlignment(SwingConstants.CENTER);
		lblcomputedAngle.setBounds(130, 84, 104, 20);
		pnlBearing.add(lblcomputedAngle);


		// end text field for entering bearing


		btnDesktopRealTimeMapMedium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				new MapVisulisaton(2,400,10,12,5.08,200,800,"800x800 Real Time map ratio: 2x2"
						+ " pixel to 1 array index mapping (320,000 indexes)");
			}
		});

		btnDesktopRealTimeMapHigh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				new MapVisulisaton(1,800,10,12,2.54,400,800,"800x800 Real Time map ratio: 1 pixel"
						+ " to 1 array index mapping (640,000 indexes)");
			}
		});

		//*******************End map size selection buttons*************************//
	}

	/**
	 * 
	 */
	private void setupRobotControls() {
		// Robot Control panel
		pnlRobotControl = new JPanel();
		pnlRobotControl.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Robot Manual Control", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlRobotControl.setBounds(10, 181, 324, 137);
		windowFrame.getContentPane().add(pnlRobotControl);
		pnlRobotControl.setLayout(null);

		// Robot Control Autopilot Button
		btnRobControlAuto = new JButton("Auto");
		btnRobControlAuto.setBounds(27, 22, 76, 23);
		pnlRobotControl.add(btnRobControlAuto);

		// Robot Control Forward Step Button
		btnRobControlFwdStep = new JButton("^");
		btnRobControlFwdStep.setBackground(UIManager.getColor("ToggleButton.highlight"));
		btnRobControlFwdStep.setBounds(123, 21, 76, 24);
		pnlRobotControl.add(btnRobControlFwdStep);

		// Robot Control Manual Pilot Button
		btnRobControlManual = new JButton("Manual");
		btnRobControlManual.setBounds(219, 21, 76, 24);
		pnlRobotControl.add(btnRobControlManual);

		// Robot Control Right Button
		btnRobControlRight = new JButton(">");
		btnRobControlRight.setBackground(UIManager.getColor("ToggleButton.highlight"));
		btnRobControlRight.setBounds(219, 56, 76, 24);
		pnlRobotControl.add(btnRobControlRight);

		// Robot Control Reverse Step Button
		btnRobControlReverseStep = new JButton("V");
		btnRobControlReverseStep.setBackground(UIManager.getColor("ToggleButton.highlight"));
		btnRobControlReverseStep.setBounds(123, 56, 76, 24);
		pnlRobotControl.add(btnRobControlReverseStep);

		// Robot Control Left Button
		btnRobControlLeft = new JButton("<");
		btnRobControlLeft.setBackground(UIManager.getColor("ToggleButton.highlight"));
		btnRobControlLeft.setBounds(27, 56, 76, 24);
		pnlRobotControl.add(btnRobControlLeft);

		// Robot Control Stop Override Button		
		btnRobControlStop = new JButton("Stop");
		btnRobControlStop.setBackground(UIManager.getColor("ToggleButton.highlight"));
		btnRobControlStop.setBounds(37, 91, 248, 24);
		pnlRobotControl.add(btnRobControlStop);
	}

	/**
	 * 
	 */
	private void setupRobotConnectionPanel() {		
		// Robot Connect Serial panel
		pnlRobConSerial = new JPanel();
		pnlRobConSerial.setBounds(10, 85, 324, 85);
		windowFrame.getContentPane().add(pnlRobConSerial);
		pnlRobConSerial.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Robot Serial Connection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlRobConSerial.setLayout(null);

		// Robot Connection Serial Port list label
		lblRobConSerialPort = new JLabel("Serial Port");
		lblRobConSerialPort.setHorizontalAlignment(SwingConstants.CENTER);
		lblRobConSerialPort.setBounds(10, 40, 76, 29);
		pnlRobConSerial.add(lblRobConSerialPort);		

		// Robot Connection Serial Port List combo box		
		cmbPortList = new JComboBox<String>();
		cmbPortList.setBounds(96, 40, 92, 29);
		pnlRobConSerial.add(cmbPortList);		

		// Robot Connection Serial connect button
		btnSerialConnect = new JButton("Connect");
		btnSerialConnect.setBackground(UIManager.getColor("ToggleButton.highlight"));
		btnSerialConnect.setBounds(196, 40, 97, 29);
		pnlRobConSerial.add(btnSerialConnect);

		// Populate combo box
		for(int i=0; i < serialClass.getCommPorts().size()  ; i++) {

			cmbPortList.addItem(serialClass.getCommPorts().get(i));
		}		
	}
}