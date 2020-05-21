package com.my.parking.myparking.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author siddharthdwivedi
 */
public class Validator {

    public static final String dateFormat = "dd/MM/yyyy";

    public static Boolean isStringEmpty(String str) {
        return str.isEmpty();
    }

    public static Boolean isStringContainsSpecialChar(String str) {
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static Boolean isStringContainsSpace(String str) {
        return !isStringEmpty(str) && (str.split(" ").length > 0);
    }

    public static Boolean isUserNameValid(String str) {
        return !isStringContainsSpace(str) && !isStringContainsSpecialChar(str);
    }

    public static Boolean isDateOfBirthValid(String str) {
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(str);
        } catch (ParseException e) {
            return false;
        }
        return true;

    }
}
