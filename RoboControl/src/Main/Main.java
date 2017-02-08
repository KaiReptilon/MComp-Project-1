package Main;

import java.io.File;
import GUI.GUI;

/**
 * Main method which initiates the program.
 * 
 * @author Sean Murphy
 * 
 */
public class Main {

	public static void main(String[] args) {

		new GUI();

		// remove when compiling for Linux
		File dll = new File("rxtxSerial.dll");
		System.load(dll.getAbsolutePath());

		// add for Linux
		// File so = new File("librxtxSerial.so");
		// System.load(so.getAbsolutePath());
	}
}