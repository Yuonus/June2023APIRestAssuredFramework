package com.qa.gorest.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.qa.gorest.frameworkexception.APIFrameworkException;

public class ConfigurationManager {

	/* 	What are the two classes to read the properties file?
	 * Two classes are required to read the properties file: 1-> Properties file 2-> file input stream	
	 */
	private Properties prop;
	private FileInputStream ip;

	public Properties initProp() {
		prop = new Properties();

		// maven: cmd line argument:
		// mvn clean install -Denv="qa"
		// mvn clean install

		String envName = System.getProperty("env");
		
	/* 1: If you are not supplying any environment, meaning if it is null. then by default we want to run our test
	 * 		in QA environment.
	 * 2: If environment is given then
	 * Note: Why toLowerCase() method has been used after envName? Because, the user could pass the following 
	 * combinations [qa, Qa, QA, qA], that is Y we have converted it to lower case to prevent any possible issue.
	 * you can also use trim() method to trim extra space if the user unintentionally add it
	 */

		try {
			if (envName == null) {
				System.out.println("No environment is given, hence; running tests on QA environment");
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} else {
				System.out.println("Running tests on env: " + envName);

				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;

				default:
					System.out.println("Please pass the right env name..." + envName);
					throw new APIFrameworkException("WRONG ENV IS Given");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try

		{
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;

	}

}


		// This part of the code is commented, because we also covered the Prod above so, we dont need it anymore.
//		// this code was developed in the beginning of the framework
//		prop = new Properties();
//	try {
//		ip = new FileInputStream("./src/test/resources/config/config.properties ");
//			prop.load(ip);
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return prop;
//		}
//}

//////////////////////////////////


//package com.qa.gorest.configuration;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.Properties;
//
//import com.qa.gorest.frameworkexception.APIFrameworkException;
//
//public class ConfigurationManager {
//
//	private Properties prop;
//	private FileInputStream ip;
//
//	public Properties initProp() {
//		prop = new Properties();
//
//		// maven: cmd line argument:
//		// mvn clean install -Denv="qa"
//		// mvn clean install
//
//		String envName = System.getProperty("env");
//
//		try {
//			if (envName == null) {
//				System.out.println("no env is given...hence running tests on QA env... ");
//				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
//			} else {
//				System.out.println("Running tests on env: " + envName);
//
//				switch (envName.toLowerCase().trim()) {
//				case "qa":
//					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
//					break;
//				case "dev":
//					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
//					break;
//				case "stage":
//					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
//					break;
//				case "prod":
//					ip = new FileInputStream("./src/test/resources/config/config.properties");
//					break;
//
//				default:
//					System.out.println("Please pass the right env name..." + envName);
//					throw new APIFrameworkException("WRONG ENV IS Given");
//				}
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//		try
//
//		{
//			prop.load(ip);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return prop;
//
//	}
//
//}
//
//
//
