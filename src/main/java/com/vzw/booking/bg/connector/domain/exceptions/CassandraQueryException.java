package com.vzw.booking.bg.connector.domain.exceptions;

public class CassandraQueryException extends Exception {

	ErrorEnum err;
	String message;

	public CassandraQueryException(ErrorEnum err, String message) {
		this.err = err;
		this.message = message;
	}

	private static final long serialVersionUID = 1096861666350727716L;

	/*
	 * Constructor of custom exception class here I am copying the message that
	 * we are passing while throwing the exception to a string and then
	 * displaying that string along with the message.
	 */
	public CassandraQueryException(String message, Throwable clause) {
		super(message, clause);
	}

	public String toString() {
		return ("Cassandra Database Exception Occurred: " + getMessage());
	}
}