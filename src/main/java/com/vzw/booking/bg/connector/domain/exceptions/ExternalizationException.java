/**
 * 
 */
package com.vzw.booking.bg.connector.domain.exceptions;

/**
 * Exception raised during data parsing
 * @author torelfa
 *
 */
public class ExternalizationException extends Exception {

	/**
	 * Exception Serial Version ID
	 */
	private static final long serialVersionUID = -925631112845713384L;

	/**
	 * Constructor
	 * @param arg0
	 */
	public ExternalizationException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param arg0
	 */
	public ExternalizationException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param arg0
	 * @param arg1
	 */
	public ExternalizationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}
