package GUI;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import DataHandling.DistanceDataProcessing;
import DataHandling.GettersSetters;
import RobotConnections.Robot;
import RobotConnections.SerialConn;
import PathPlanning.FindPath;
import PathPlanning.FindPath.Node;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * Main MapVisulisaton class
 * 
 * @author Sean Murphy 
 * Student number: 11829078
 * 
 */
public class MapVisulisaton  {

	private JFrame mapFrame;

	DistanceDataProcessing distanceDataProcessing;
	FindPath PathIt;
	//SerialConn serialClass;

	/**
	 * 
	 * 
	 * @param instance
	 * @param squareSize
	 * @param arraySize
	 * @param robotW
	 * @param robotH
	 * @param scale
	 * @param roboPos
	 * @param res
	 * @param windowTitle
	 */
	public MapVisulisaton(int squareSize,int arraySize, int robotW,int robotH,double scale,int roboPos,int res,String windowTitle) {

		mapFrame = new JFrame( windowTitle );
		mapFrame.getContentPane().add( new Map(squareSize,arraySize,robotW,robotH,scale,roboPos) );
		mapFrame.setVisible( true );     									 // displays frame
		mapFrame.setSize( res+5, 6+res );									 // create frame slightly larger to account for window border
		mapFrame.getContentPane().setSize( res, res ); 						 // actual drawable canvas
		mapFrame.setResizable( false );
		mapFrame.setLocationRelativeTo( null );
		mapFrame.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );// allows the map to close separate from Main GUI
	}

	/**
	 * inner class
	 * @author Sean Murphy
	 * Student number: 11829078
	 *
	 */
	private class Map extends Canvas implements MouseListener {

		private static final long serialVersionUID = 1L;

		private int rowX;					// col
		private int colY;					// row
		private Graphics doubleGfxHolder;   // holder for double buffer
		private Image OffScreen;			// image object for double buffer
		private boolean st = false; 		// allows one time execution of an if statement
		private Robot robot;
		private Rectangle2D robotShape;

		private int wheelSegments = 0;

		private int[][] mapXY = new int[rowX][colY];
		private int[][] obsXY = new int[rowX][colY];

		private int sqSize;
		private int roboStartpos;
		
		// int xClick, yClick;
		
		private int forward; // forward motion
		private int reverse; // reverse motion

		/**
		 * Creates the actual map.
		 * 
		 * @param sSize
		 * @param arrSize
		 * @param roboW
		 * @param roboH
		 * @param scale
		 * @param roboPos
		 */
		public Map(int sSize,int arrSize,int roboW,int roboH,double scale,int roboPos) {	

			distanceDataProcessing = new DistanceDataProcessing(sSize,arrSize,scale);
			robot = new Robot(0, 0, roboW, roboH);
			robotShape = null;
			roboStartpos = roboPos;
			rowX = arrSize;
			colY = arrSize;
			sqSize = sSize;
			forward =  sqSize;
			reverse = -sqSize;
			//setBackground(Color.BLACK);
			addMouseListener(this);
		}

		/**
		 * Called by repaint()
		 */
		@Override
		public void update( Graphics g ) {
			paint(g);
		}

		/**
		 * Paint called from update
		 */
		@Override
		public void paint ( Graphics g ) {
			
			Dimension d = getSize();
			checkOffScreenImage();								// check if screen size has changed
			doubleGfxHolder =  OffScreen.getGraphics();
			doubleGfxHolder.setColor( getBackground() );
			doubleGfxHolder.fillRect( 0, 0, d.width, d.height );
			drawUpdateRTdata (  OffScreen.getGraphics() );		// get g context we create in drawupdateRTdata
			g.drawImage( OffScreen, 0, 0, null );			 	// first pass
		}

		/**
		 * Check if screen size has changed
		 * in preparation for double buffer
		 */
		private void checkOffScreenImage() {

			Dimension d = getSize();
			if ( OffScreen == null || OffScreen.getWidth( null ) != d.width
					|| OffScreen.getHeight( null ) != d.height ) {

				OffScreen = createImage( d.width,d.height );
			}
		}

		/**
		 * Draw the actual picture from buffer
		 * currently 50ms delay
		 * @param g
		 */
		public void drawUpdateRTdata ( Graphics g ) {

			final long startTime = System.currentTimeMillis();

			double width = getSize().width;
			double height = getSize().height;
			double htOfX = height / rowX;
			double wdOfX = width / colY;


			if ( st == false ) {

				int robotStartElavationInRows = roboStartpos; // Change this to move starting position of robot (up or down on map)
				int robotStartX = (int) ( ( ( colY*wdOfX )/2) - ( wdOfX * 2 ) ); // robots X start position on map (passed to g.fillRect)
				int robotStartY = (int) ( ( rowX*htOfX ) - ( htOfX * robotStartElavationInRows ) ); // robots Y start position on map (passed to g.fillRect)
				robot.setX( robotStartX - ( robot.getWidth()/2 ) );
				robot.setY( robotStartY - ( robot.getHeight()/2 ) );
				//System.out.println( robotStartX + " printed from MapVisulisation/drawUpdateRTdata ( Graphics g )" );
				//System.out.println( robotStartY + " printed from MapVisulisation/drawUpdateRTdata ( Graphics g )" );
				st = true;
				robotShape = new Rectangle2D.Double( robot.getX(), robot.getY() , robot.getWidth(), robot.getHeight() );
			}
			int compass = distanceDataProcessing.compassReading;
			double radians = compass*( Math.PI/180 );// Converts compass bearing to radians
			Graphics2D g2d = ( Graphics2D ) g;
			Rectangle2D actRobot = robotShape.getBounds2D();
			AffineTransform at = new AffineTransform();
			
			updateMapData(actRobot); // Collect new map data.
			/*for(int i = 0; i < mapXY.length; i++){
				for(int j = 0; j < mapXY[0].length; j++)
						System.out.print(mapXY[i][j] + " ");
				System.out.println();
			}*/
			// Main paint operation, this is the mostly costly operation within the entire program BIG O(n^2)
			for ( int x = 0; x < mapXY.length ; x++ ) {

				for ( int y = 0 ; y < mapXY.length; y++ ) {
					//if ( mapXY[x][y] == 0 ) { // never observed
					//	g.setColor(Color.BLUE);// graphics context colour
					//	g.fillRect( x * sqSize, y * sqSize , sqSize, sqSize );
					//}

					if ( mapXY[x][y] > 0 && obsXY[x][y] > 0 ) { // probably occupied
						g.setColor(Color.RED);// graphics context colour
						g.fillRect( x * sqSize, y * sqSize , sqSize*2, sqSize*2 );
					}
				
				//if ( mapXY[x][y] == 0 && obsXY[x][y] == 0 ) { // probably unoccupied
				//		g.setColor(Color.white);// graphics context colour
				//		g.fillRect( x * sqSize, y * sqSize , sqSize, sqSize );
				//	}
				}
			}
			g.setColor(Color.BLUE);

			Shape temp;
			at.rotate( radians, actRobot.getCenterX(), actRobot.getCenterY() );
			g2d.draw( temp = at.createTransformedShape( actRobot ) );
			g2d.setTransform(at);
			g2d.draw( robotShape );
			// next 2 lines paint orange rectangle to define front of robot
			g.setColor(Color.ORANGE);
			g2d.fillRect((int)robotShape.getX()+1,(int) robotShape.getY()-1,(int) robotShape.getWidth()-1, (int) ((int)robotShape.getHeight()-(robotShape.getHeight()-4)));
			
			// The ifs below are required if you wish the robot to move around the map
			// rather then the map move around the robot.
			/*
			if ( GettersSetters.getWheelForward() == 1 ) {

				straightMotion(radians,robotShape,forward);
			}
			if ( GettersSetters.getWheelReverse() == 1 ) {

				straightMotion(radians,robotShape,reverse);
			}
			 */
			
			/*for(Node n : finalPath){
				if(mapXY[n.x][n.y] == 1){
					PathPlanning.FindPath.updatedGoal();
					PathPlanning.FindPath.PathIt(mapXY, n.x, n.y);
				}
			}*/
			
			// can be used to measure time to complete map repaint
			//final long endTime = System.currentTimeMillis();
			//System.out.println("Total execution time: " + ( endTime - startTime ) );
			int robotStartElavationInRows = roboStartpos;
			int startX = (int) ( ( ( colY*wdOfX )/2) - ( wdOfX * 2 ) );
			int startY = (int) ( ( rowX*htOfX ) - ( htOfX * robotStartElavationInRows ) );
			PathPlanning.FindPath.PathIt(mapXY, startX, startY);
			repaint();
		}

		/**
		 * Allows the robot to move on the map
		 * called within the paint method above.
		 * This method is not currently used as the map rotates around the robot.
		 * @param compass
		 * @param actRobo
		 * @param motion
		 */
		public void straightMotion(double compass,Rectangle2D actRobo, int motion) {

			// System.out.println("Wheel segments are " + GettersSetters.getWheelRotation() );

			if( GettersSetters.getWheelRotation() != 0 ) {

				wheelSegments = GettersSetters.getWheelRotation() + wheelSegments;
			}

			if( wheelSegments >= 5 ) {

				double theta = compass;
				double hyp = motion;

				double opp = hyp * ( Math.cos( theta ) );
				double adj = hyp * ( Math.sin( theta ) );

				double x = actRobo.getX() + adj;
				double y = actRobo.getY() - opp;

				if (x > 0 && x < rowX*sqSize && y > 0 && y < colY*sqSize) {

					actRobo.setRect(x, y, actRobo.getWidth(), actRobo.getHeight());
				}
				wheelSegments = wheelSegments - 1;
				GettersSetters.setWheelRotation("0");
			}
		}
		
		
		/**
		 * 
		 * @param actRobo
		 */
		public void updateMapData(Rectangle2D actRobo) {
			
			distanceDataProcessing.update( actRobo );
			mapXY = distanceDataProcessing.getMapXY();
			obsXY = distanceDataProcessing.getObsXY();
		}

		/**
		 * Retrieve mouse click from canvas and store the x, y values
		 * calculate the angle from centre of robot to point clicked
		 * 
		 */
		@Override
		public void mouseClicked(MouseEvent e) {

			// get mouse click x,y
			int xClick, yClick;
			xClick = e.getX();
			yClick = e.getY();
			PathPlanning.FindPath.goals(xClick, yClick);
			// calculate angle from robot centre to point clicked
			float theta = (float) (Math.atan2( (yClick-robot.getY()), (xClick-robot.getX())));
			theta += Math.PI/2.0;
			double tempAngle = Math.toDegrees(theta);
			
			if( tempAngle < 0 ) { tempAngle += 359; }
			
			int angle = (int) Math.round(tempAngle);
			
			DataHandling.GettersSetters.setHeadingAngle(angle);
			String guiTxtAngle = Integer.toString(angle);
			
			//System.out.println(theta + " Printing from MapVisulisation/mouseClicked method");
			//System.out.println(angle + " Printing from MapVisulisation/mouseClicked method");

			// set the values to the gui
			GUI.xCoord.setText(String.valueOf(xClick));
			GUI.yCoord.setText(String.valueOf(yClick));
			GUI.computedAngle.setText(guiTxtAngle);
			
			DataHandling.DistanceDataProcessing.setHeading();
		}

		/**
		 * Unused
		 */
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		/**
		 * Unused
		 */
		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		/**
		 * Unused
		 */
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		/**
		 * Unused
		 */
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
}
