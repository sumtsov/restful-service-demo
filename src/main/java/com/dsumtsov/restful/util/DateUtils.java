package com.dsumtsov.restful.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public final static String BIRTH_DATE_PATTERN = "yyyy-MM-dd";

    private DateUtils() {}

    public static String formatBirthday(Date date) {
        return new SimpleDateFormat(BIRTH_DATE_PATTERN).format(date);
    }

    public static Date formatBirthday(String date) {
        try {
            return new SimpleDateFormat(BIRTH_DATE_PATTERN).parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
