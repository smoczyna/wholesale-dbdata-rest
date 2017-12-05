/**
 * 
 */
package com.vzw.booking.bg.connector.cache;

/**
 * @author torelfa
 *
 */
public class CacheException extends Exception {

	/**
	 * Exception Serial Version ID
	 */
	private static final long serialVersionUID = -6831889958550485009L;

	/**
	 * Constructor
	 * @param arg0
	 */
	public CacheException(String arg0) {
		super(arg0);
	}

	/**
	 * Constructor
	 * @param arg0
	 */
	public CacheException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * Constructor
	 * @param arg0
	 * @param arg1
	 */
	public CacheException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructor
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public CacheException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
