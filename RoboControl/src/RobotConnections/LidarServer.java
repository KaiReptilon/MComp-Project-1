package RobotConnections;

import java.io.IOException;
import java.net.Socket;
import DataHandling.GettersSetters;

/**
 * Wi-Fi connection class creates a socket and connects to the LiDAR Wi-Fly unit.
 * 
 * This class also contains of course synchronisation and data collection.
 * I also deal with measurement data here, that is I sort through each packet and extract
 * the measurement data if the data has no invalid flag set (the MSB of byte 1).
 * 
 * @author Sean Murphy
 *
 */
public class LidarServer {

	private Socket socketOne;

	private static double[] distInMM360;
	private static int[] onePacket;
	private static int[] bufferArray;

	private static final int PACKET_HEADER = 0xFA;
	private static final int FIRST_PACKET = 0xA0;
	private static int SYNC_STATUS = 0;

	private int readIn;
	private int line;
	private int indexTracker = 0;

	/**
	 * 
	 * 
	 * 
	 * @param port
	 * @throws IOException
	 */
	public LidarServer(int port) throws IOException {

		distInMM360 = new double[360];
		onePacket = new int[22];
		bufferArray = new int[1980];

		System.out.println("waiting for connection");

		socketOne =  new Socket("192.168.1.120" ,port);

		System.out.println("accepted");

		startSocketOpen();
	}

	/**
	 * Create new thread and start comms with LiDAR
	 * 
	 */
	public void startSocketOpen() {

		Thread thread = new Thread(new Runnable() {

			public void run() {

				while (true) {
					try {

						while (socketOne.getInputStream().available() > 0) {

							getSync();

						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		});
		thread.start();
	}

	/**
	 * 
	 * 
	 * @param a
	 */
	private void upDateUI( double[] a ) {

		GettersSetters.setDistArray(a);
		indexTracker = 0;
		//System.out.println("upDate");
	}


	/**
	 * Provides sync with LiDAR's first packet of the next
	 * rotation from button connection press.
	 */
	public void getSync() {

		try {
			while (true) {

				readIn = socketOne.getInputStream().read();
				line = (readIn & 0xff);

				switch (SYNC_STATUS) {

				case 0:

					if (line == PACKET_HEADER) {
						// System.out.println(line);
						bufferArray[0] = line;
						SYNC_STATUS = 1;
					}
					break;

				case 1:
					if (line == FIRST_PACKET) {

						bufferArray[1] = line;

						for (int i = 2; i < 1980; i++) {

							readIn = socketOne.getInputStream().read();
							line = (readIn & 0xff);
							bufferArray[i] = line;
						}
						collectPacket(bufferArray);
						SYNC_STATUS = 0;
						distInMM360 = null;
					}
					break;
				}
			}
		} catch (Exception e) {

			System.err.println(e.toString());
		}
	}

	/**
	 * 
	 * 
	 * @param buffer
	 */
	public void collectPacket(int[] buffer) {
		int counter = 0;
		distInMM360 = new double[360];
		// Big O n^2
		for (int i = 0; i < 90; i++) {

			for (int x = 0; x < 22; x++) {
				//System.out.print(buffer[counter] + "\t"); // debugging
				onePacket[x] = buffer[counter];
				counter++;
			}
			//System.out.println(); // debugging
			extractDistData(onePacket);
		}
		upDateUI(distInMM360);
	}

	/**
	 * Decodes the data for each packet collecting the measurement data and
	 * filling the distInMM360 array in reverse.
	 * I'll accept all data except when flag data error occurs
	 * 
	 * @param a
	 */
	public void extractDistData(int[] a) {

		int byte0 = 0;
		int byte1 = 0;
		int result = 0;
		int realNum = 0;
		double inches = 0.0;

		if(a[5] <= 127) {
			byte0 = a[4]; // (bit positions 7-0)
			byte1 = a[5]; // (bit positions 15-8)
			result = (byte0 & 0xff) | ( (byte1 & 0xff) << 8 );
			realNum = (result << (32 - 14)) >> (32 - 14);// collect only the first 14 bits 13-0
			inches = realNum * 0.039370;
			distInMM360[indexTracker] = Math.round(inches);
			indexTracker++;
		}else {
			distInMM360[indexTracker] = 0;
			indexTracker++;
		}

		if(a[9] <= 127) {
			byte0 = a[8];
			byte1 = a[9];
			result = (byte0 & 0xff) | ( (byte1 & 0xff) << 8 );
			realNum = (result << (32 - 14)) >> (32 - 14);
			inches = realNum * 0.039370;
			distInMM360[indexTracker] = Math.round(inches);
			indexTracker++;

		}else {
			distInMM360[indexTracker] = 0;
			indexTracker++;
		}

		if(a[13] <= 127) {
			byte0 = a[12];
			byte1 = a[13];
			result = (byte0 & 0xff) | ( (byte1 & 0xff) << 8 );
			realNum = (result << (32 - 14)) >> (32 - 14);
			inches = realNum * 0.039370;
			distInMM360[indexTracker] = Math.round(inches );
			indexTracker++;
		}else {
			distInMM360[indexTracker] = 0;
			indexTracker++;
		}

		if(a[17] <= 127) {
			byte0 = a[16];
			byte1 = a[17];
			result = (byte0 & 0xff) | ( (byte1 & 0xff) << 8 );
			realNum = (result << (32 - 14)) >> (32 - 14);
			inches = realNum * 0.039370;
			distInMM360[indexTracker] = Math.round(inches );
			indexTracker++;
		}else {
			distInMM360[indexTracker] = 0;
			indexTracker++;
		}
		// System.out.println(indexTracker + "Printed from LidarSever/extractDistData method"); //de-bugging
	}
}
