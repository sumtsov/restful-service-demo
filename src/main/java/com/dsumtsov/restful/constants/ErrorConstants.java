package com.dsumtsov.restful.constants;

public class ErrorConstants {
    public static final String AUTHOR_NOT_FOUND_BY_ID_ERR = "Author not found by id '%s'";
    public static final String AUTHOR_EMAIL_ALREADY_EXISTS_ERR = "Author with email '%s' already exists";
    public static final String AUTHOR_BOOK_ALREADY_EXISTS_ERR = "Author '%s' already has a book with title '%s'";
    public static final String BOOK_NOT_FOUND_BY_ID_ERR = "Book not found by id '%s'";
    public static final String VALIDATION_FAILED_ERR = "Validation Failed";

    private ErrorConstants() {}
}
