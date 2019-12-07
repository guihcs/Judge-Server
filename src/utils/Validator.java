package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static Pattern notNumberPattern = Pattern.compile("\\D+");

    public static boolean onlyNumber(String text){
        Matcher matcher = notNumberPattern.matcher(text);
        return !matcher.find();
    }

}
