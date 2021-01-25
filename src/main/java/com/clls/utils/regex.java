package com.clls.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regex {
    //判断字符串是否全是数字
    public static boolean isNumeric(String str) {
        //Pattern pattern = Pattern.compile("^-?[0-9]+"); //这个也行
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");//这个也行
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }
    public static boolean isBlankspace(String str){
        //String pattern = "\\w+ \\w+ \\w+ \\w+";
        Pattern pattern=Pattern.compile("\\w+ \\w+ \\w+ \\w+");
        Matcher isBlankspace= pattern.matcher(str);
        return isBlankspace.matches();

    }

    public static void main(String[] args) {
        System.out.println(isNumeric("88"));
        System.out.println(isBlankspace("s s s s "));
    }
}
