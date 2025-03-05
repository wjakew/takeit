/**
 * @author Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.takeit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jakubwawak.takeit.properties_engine.Properties;

/**
 * Main class for the Takeit application.
 */
@SpringBootApplication
public class TakeitApplication {

	public static final String version = "0.0.1";

	public static Properties properties;

	/**
	 * Main method for the Takeit application.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		showHeader();
		properties = new Properties("takeit.properties");
		if ( properties.fileExists ){
			properties.parsePropertiesFile();
			System.out.println("Properties file loaded. Starting the application...");
			SpringApplication.run(TakeitApplication.class, args);
		}else{
			properties.createPropertiesFile();
			System.out.println("Properties file created. Please edit it and run the application again.");
			System.exit(0);
		}
		
	}

	/**
	 * Shows the header of the application.
	 */
	static void showHeader(){
		String header = "takeit " + version + "\n";
		System.out.println(header);
	}

}
