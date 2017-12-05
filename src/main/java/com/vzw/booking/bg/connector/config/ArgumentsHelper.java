/**
 * 
 */
package com.vzw.booking.bg.connector.config;

/**
 * Class defining helper functions for application arguments management
 * @author torelfa
 *
 */
public class ArgumentsHelper {

	private static String cassandraPassoword;
	
	/**
	 * Constructor
	 */
	private ArgumentsHelper() {
		throw new IllegalStateException("ArgumentsHelper: Helper Class");
	}

	public static final void parseArguments(String[] args) {
    	for(String arg: args) {
    		if (arg.startsWith("-Dcassandra.password")) {
    			cassandraPassoword=arg.split("=")[1];
    			break;
    		}
    	}
	}
	
	public static String getCassandraPassword() {
		return cassandraPassoword;
	}
}
