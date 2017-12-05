package com.vzw.booking.bg.connector.domain.exceptions;

public class NoResultsReturnedException extends Exception {

	ErrorEnum err;

	public NoResultsReturnedException(ErrorEnum err) {
		this.err = err;
	}

	private static final long serialVersionUID = -5406600931072794540L;

	/*
	 * Constructor of custom exception class here I am copying the message that
	 * we are passing while throwing the exception to a string and then
	 * displaying that string along with the message.
	 */
	public NoResultsReturnedException(String message) {
		super(message);
	}

	public String toString() {
		return ("No Rows Returned Exception Occurred: " + getMessage());
	}
}