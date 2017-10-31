package com.effone.mobile.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by sumanth.peddinti on 10/6/2017.
 */

public class Validation {


    Pattern letter = Pattern.compile("[a-zA-z]");
    Pattern digit = Pattern.compile("[0-9]");
    Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
    Pattern eight = Pattern.compile (".{5,16}");
    private static final String DATE_PATTERN =
            "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)";

    public boolean isValidEmail(String mStrEmail) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(mStrEmail);
        return matcher.matches();
    }

    public boolean isValidPassword(String mStrPassword) {
        Matcher hasLetter = letter.matcher(mStrPassword);
        Matcher hasDigit = digit.matcher(mStrPassword);
        Matcher hasSpecial = special.matcher(mStrPassword);
        Matcher hasEight = eight.matcher(mStrPassword);
        return (hasLetter.find() || hasDigit.find() || hasSpecial.find())&&
                hasEight.matches();
    }

    public boolean isValidFirstName(String mStrName) {
        String mStrName_pattern = "[A-Za-z]{1,50}";
        Pattern pattern = Pattern.compile(mStrName_pattern);
        Matcher matcher = pattern.matcher(mStrName);
        return matcher.matches();
    }
    public boolean isValidDoctorName(String mStrName) {
        String mStrName_pattern = "[a-z0-9_-]{1,50}";
        Pattern pattern = Pattern.compile(mStrName_pattern);
        Matcher matcher = pattern.matcher(mStrName);
        return matcher.matches();
    }

    public boolean isValidPhone(String mStrPhone) {
        String mStrPhone_pattern = "[0-9]{10}";
        Pattern pattern = Pattern.compile(mStrPhone_pattern);
        Matcher matcher = pattern.matcher(mStrPhone);
        return matcher.matches();
    }

    public boolean isValidZip(String mStrZip) {
        if((mStrZip.equals("00000"))&&(mStrZip.equals(" "))){
            return true;
        }
        return false;
    }

    public boolean isValidCode(String mStrcode){
        String mStrName_pattern = "[0-9]{3,6}";
        Pattern pattern = Pattern.compile(mStrName_pattern);
        if(mStrcode.equals("")) {
            return true;
        }else {
            Matcher matcher = pattern.matcher(mStrcode);
            return matcher.matches();
        }
    }

    public boolean isValidAddress(String mStrAddress) {
        String mStrName_pattern = "[A-Za-z0-9# /-:]{0,255}";
        Pattern pattern = Pattern.compile(mStrName_pattern);
        Matcher matcher = pattern.matcher(mStrAddress);
        return matcher.matches();
    }



    public boolean isValidDate(final String date){

        String regex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }
}
