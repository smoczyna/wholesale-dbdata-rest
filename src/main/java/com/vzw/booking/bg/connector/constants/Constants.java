/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.connector.constants;

/**
 *
 * @author smorcja
 */
public class Constants {
     
    public static final String BOOK_DATE_FILENAME = "bookdate.csv";
    public static final String FINANCIAL_EVENT_OFFSET_FILENAME = "offset.csv";
    public static final String ALT_BOOKING_FILENAME = "altbook.csv";
    public static final String BILLED_BOOKING_FILENAME = "billed.csv";    
    public static final String UNBILLED_BOOKING_FILENAME = "unbilled.csv";
    public static final String ADMIN_FEES_FILENAME = "adminfees.csv";
    
    public static final String SUBLEDGER_SUMMARY_FILENAME_PATTERN = "subledger_summary";
    public static final String SUBLEDGER_SUMMARY_FILENAME = "subledger_summary.csv";
    public static final String WHOLESALE_REPORT_FILENAME_PATTERN = "wholesale_report";
    public static final String WHOLESALE_REPORT_FILENAME = "wholesale_report.csv";
    
    public static final String FILE_SPLIT_MESSAGE = "File %s has been splitted into %d chunks";
    public static final String DEFAULT_CSV_FIELDS_DELIMITER = ",";
    public static final Integer DEFAULT_MAX_SKIPPED_RECORDS = 100;
    public static final Integer DEFAULT_NUMBER_OF_CHUNKS = 1;
    
    public static final String DEBIT_CODE = "DR";
    public static final String CREDIT_CODE = "CR";
    
    public static final String RECORD_COUNT = "count";
    public static final String ZERO_CHARGES = "zero";
    public static final String GAPS ="gap";
    public static final String DATA_ERRORS = "error";
    public static final String BYPASS = "bypass";
    public static final String SUBLEDGER = "subledger";
    public static final String WHOLESALES_REPORT = "report";
    
    public static final String JOB_STARTED = "Starting booking files processing job";
    public static final String JOB_FINISHED = "Stopping booking files processing job";
    public static final String JOB_STARTED_MESSAGE = "Wholesale booking processing started at: %S";
    public static final String JOB_FINISHED_MESSAGE = "Wholesale booking processing ended at: %s";
    public static final String JOB_PROCESSIG_TIME_MESSAGE = "Overall processing time: %d seconds.";
    public static final String JOB_EXCEPTIONS_ENCOUNTERED = "All encountered exceptions:";
    public static final String EXCEPTION_MESSAGE = "exception : %s";
    public static final String FILE_ARCHIVED_MESSAGE = "%s file archived successfully";
    
    public static final String CHECK_IF_FILES_EXIST = "Checking if file exists...";
    public static final String SOURCE_LOCATION_MISSING_MESSAGE = "Source location missing !!!";
    public static final String FILES_NOT_FOUND_MESSAGE = "Source file(s) do not exist";
    public static final String FILES_NOT_FOUND_JOB_ABORTED = "One or more required files not found, job aborted.";
    
    public static final String FEC_NOT_FOUND_MESSAGE = "Original Fianancial Event Catergory not found, looking for default one...";
    public static final String DEFAULT_FEC_OBTAINED = "Default Fianancial Event Catergory obtained instead, everythig's cool";
    public static final String DEFAULT_FEC_NOT_FOUND = "Default Fianancial Event Catergory not found either !!!";
    public static final String WHOLESALE_PRICE_NOT_FOUND = "Original Wholesale Price not found, looking for default one...";
    public static final String DEFAULT_WP_OBTAINED = "Default Wholesale Price obtained instead, everything's cool";
    public static final String DEFAULT_WP_NOT_FOUND = "Default Wholesale Price not found either !!!";
    
    public static final String BOOKING_BYPASS_DETECTED = "Booking bypass detected, record skipped for sub ledger file ...";
    public static final String ZERO_CHARGES_DETECTED = "Zero charges detected, skipping...";
    public static final String ZERO_CHARGES_SKIP = "Record skipped due to zero charges !!!";
    public static final String GAP_DETECTED = "Valid Toll Charges skipped from processing !!!";
    public static final String INVALID_INPUT = "Invalid input record encountered !!!";
    public static final String OFFSET_NOT_FOUND = "Offset fin cat value not found !!!";
    public static final String ZERO_SUBLEDGER_AMOUNT = "Zero amounts in sub ledger record found !!!";
    public static final String FAILED_TO_CREATE_OFFSET = "Failed to create offset booking !!!";
    
    public static final String PROCESSING_RECORD = "Processing record: %s";
    public static final String READER_EXCEPTION = "Reader exception encountered: %s";
    public static final String WRITER_EXCEPTION = "Writer exception encountered: %s";
    public static final String JOB_EXECUTION_FINISHED = "Step completed, read count: %d, write count: %d";
    public static final String WHOLESALE_REPORT_NO = "Number of wholesale report records created: %d";
    public static final String SUBLEDGER_REPORD_NO = "Number of sub ledger records created: %d";
    public static final String ZERO_CHARGE_NO = "Number of zero charges: %d";
    public static final String CODE_GAPS_NO = "Number of gaps: %d";
    public static final String DATA_ERRORS_NO = "Number of data errors: %d";
    public static final String BYPASS_NO = "Number of bypasses: %d";
    
    public static final String MAX_ALLOWED_EXCEPTION = "Maximum allowed exceptions reached, terminating...";
    public static final String FILE_MISSING_MESSAGE = "File missing, terminating...";
    public static final String PARSING_ERROR = "Parsing error of input line: %s";
    public static final String NULL_ENCOUNTERED = "NULL encountered but the value was expected - skipping record ...";
    public static final String RECORD_SKIP_DETECTED = "Record skipped due to processing errors !!!";
    public static final String DB_CALL_FAILURE = "%s Cassandra table call error %s";
    public static final String WRITERS_CONFIG_FATAL_ERROR = "FATAL ERROR: Failed to load writers configuration properties";
    public static final String CONTENTS_TOO_LONG = "Contents to long to fit defined output field : %d > %d";
}
