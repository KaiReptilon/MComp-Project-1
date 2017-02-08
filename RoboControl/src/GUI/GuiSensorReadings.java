package GUI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;

/**
 * 
 * @author Sean Murphy
 * @param <Picture>
 *
 */
public class GuiSensorReadings extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public String portChoice;
	JLabel compassImg;

	final JFrame windowFrame;

	//Bearing data
	public static JTextField txtCompass;
	public static JTextField text0deg;
	public static JTextField text45deg;
	public static JTextField text90deg;
	public static JTextField text135deg;
	public static JTextField text225deg;
	public static JTextField text270deg;
	public static JTextField text180deg;
	public static JTextField text315deg;
	public static JTextField targetBearing;


	/**
	 * 
	 * 
	 */
	public GuiSensorReadings() {

		//serialClass = new SerialConn();
		//serial = false;
		//wifi = false;

		windowFrame = new JFrame("Sensor Output");
		windowFrame.setSize(450, 390); // Main window container size (in pixels) change this to resize whole GUI
		windowFrame.getContentPane().setLayout(null);
		windowFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		windowFrame.setResizable(false);
		
		setupBearingData();
	}

	/**
	 * 
	 * 
	 */
	public void showSensorWindow()
	{
		windowFrame.setVisible(true);
	}

	/**
	 * 
	 * 
	 */
	private void setupBearingData()
	{

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "LiDAR bearing readings in Inches", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 426, 342);
		windowFrame.getContentPane().add(panel);
		panel.setLayout(null);

		JPanel readingPanel = new JPanel();
		readingPanel.setBorder(new TitledBorder(null, "Readings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		readingPanel.setBounds(29, 30, 368, 301);
		panel.add(readingPanel);
		readingPanel.setLayout(null);

		JPanel panel0 = new JPanel();
		panel0.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "0 Deg", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel0.setBounds(143, 11, 69, 62);
		readingPanel.add(panel0);
		panel0.setLayout(null);

		text0deg = new JTextField();
		text0deg.setEditable(false);
		text0deg.setBounds(10, 31, 49, 20);
		panel0.add(text0deg);
		text0deg.setColumns(10);

		JPanel panel45 = new JPanel();
		panel45.setLayout(null);
		panel45.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "45 Deg", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel45.setBounds(222, 35, 69, 62);
		readingPanel.add(panel45);

		text45deg = new JTextField();
		text45deg.setEditable(false);
		text45deg.setColumns(10);
		text45deg.setBounds(10, 31, 51, 20);
		panel45.add(text45deg);

		JPanel panel90 = new JPanel();
		panel90.setLayout(null);
		panel90.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "90 Deg", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel90.setBounds(264, 108, 69, 62);
		readingPanel.add(panel90);

		text90deg = new JTextField();
		text90deg.setEditable(false);
		text90deg.setColumns(10);
		text90deg.setBounds(10, 31, 51, 20);
		panel90.add(text90deg);

		JPanel panel135 = new JPanel();
		panel135.setLayout(null);
		panel135.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "135 Deg", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel135.setBounds(234, 197, 69, 62);
		readingPanel.add(panel135);

		text135deg = new JTextField();
		text135deg.setEditable(false);
		text135deg.setColumns(10);
		text135deg.setBounds(10, 31, 49, 20);
		panel135.add(text135deg);

		JPanel panel225 = new JPanel();
		panel225.setLayout(null);
		panel225.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "225 Deg", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel225.setBounds(53, 197, 69, 62);
		readingPanel.add(panel225);

		text225deg = new JTextField();
		text225deg.setEditable(false);
		text225deg.setColumns(10);
		text225deg.setBounds(10, 31, 49, 20);
		panel225.add(text225deg);

		JPanel panel270 = new JPanel();
		panel270.setLayout(null);
		panel270.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "270 Deg", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel270.setBounds(35, 108, 69, 62);
		readingPanel.add(panel270);

		text270deg = new JTextField();
		text270deg.setEditable(false);
		text270deg.setColumns(10);
		text270deg.setBounds(10, 31, 51, 20);
		panel270.add(text270deg);

		JPanel panel180 = new JPanel();
		panel180.setLayout(null);
		panel180.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "180 Deg", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel180.setBounds(143, 228, 69, 62);
		readingPanel.add(panel180);

		text180deg = new JTextField();
		text180deg.setEditable(false);
		text180deg.setColumns(10);
		text180deg.setBounds(10, 31, 49, 20);
		panel180.add(text180deg);

		JPanel panel315 = new JPanel();
		panel315.setLayout(null);
		panel315.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "315 Deg", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel315.setBounds(64, 35, 69, 62);
		readingPanel.add(panel315);

		text315deg = new JTextField();
		text315deg.setEditable(false);
		text315deg.setColumns(10);
		text315deg.setBounds(10, 31, 51, 20);
		panel315.add(text315deg);
		
				txtCompass = new JTextField();
				txtCompass.setHorizontalAlignment(SwingConstants.CENTER);
				txtCompass.setBounds(129, 120, 94, 19);
				readingPanel.add(txtCompass);
				txtCompass.setEditable(false);
				txtCompass.setColumns(10);
				
						JLabel lblCompass = new JLabel("Compass Actual");
						lblCompass.setBounds(129, 108, 94, 12);
						readingPanel.add(lblCompass);
						lblCompass.setHorizontalAlignment(SwingConstants.CENTER);
						
						targetBearing = new JTextField();
						targetBearing.setEditable(false);
						targetBearing.setHorizontalAlignment(SwingConstants.CENTER);
						targetBearing.setBounds(129, 175, 94, 20);
						readingPanel.add(targetBearing);
						targetBearing.setColumns(10);
						
						JLabel lblTargetBearing = new JLabel("Target Bearing");
						lblTargetBearing.setHorizontalAlignment(SwingConstants.CENTER);
						lblTargetBearing.setBounds(129, 161, 94, 14);
						readingPanel.add(lblTargetBearing);
	}
}