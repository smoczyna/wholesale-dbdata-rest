package com.vzw.booking.bg.connector.domain.exceptions;

public enum ErrorEnum {
	
	SINGLE_ROW(0, "Query returned single row."), 
	NO_ROWS(1, "Query returned no rows."), 
	MULTIPLE_ROWS(2, "Query returned multiple rows."), 
	DATABASE_ERROR(3, "A database error occured.");

	private final int code;
	private final String description;

	private ErrorEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return code + ": " + description;
	}
}