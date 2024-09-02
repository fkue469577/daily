package com.bc.finance.common.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.formula.functions.T;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ace on 2017/9/10.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    public static String getObjectValue(Object obj){
        return obj==null?"":obj.toString();
    }

    //首字母变成大写
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    private static Pattern linePattern = Pattern.compile("_(\\w)");

    public static boolean isBlank(final Object ob) {
        String str = String.valueOf(ob);
        if(str.equals("null") || str.equals("") || str == null) {
            return true;
        }
        return false;
    }

    public static boolean isNotBlank(final Object ob) {
        return !isBlank(ob);
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isBlank(final CharSequence cs) {
        if(cs == null || cs.equals("null") || cs.equals("") || cs.toString().trim().equals("")) {
            return true;
        }
        return org.apache.commons.lang3.StringUtils.isBlank(cs);
    }

    public static boolean isAllBlank(CharSequence... css){
        if (ArrayUtils.isEmpty(css)) {
            return true;
        } else {
            CharSequence[] arr$ = css;
            int len$ = css.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                CharSequence cs = arr$[i$];
                if (isNotBlank(cs)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isIdCard(String idcard) {
        String regex = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(idcard);
        return m.matches();
    }

    public static boolean isTel(String tel) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(tel);
        return m.matches();
    }

    /** 下划线转驼峰 */
    public static String lineToHump(String str) {
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     *  驼峰转下划线
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static int parseInt(Object obj) {
        return Integer.parseInt(String.valueOf(obj));
    }
    public static int getAsInt(Map map, String key) {
        return parseInt(map.get(key));
    }
    public static long getAsLong(Map map, String key) {
        return Long.parseLong(String.valueOf(map.get(key)));
    }


    public static void notBlankRunnable(String ch, Runnable runnable) {
        if(isNotBlank(ch)) {
            runnable.run();
        }
    }

    public static <T> T notBlankSupplier(String ch, Supplier<T> supplier) {
        if(isNotBlank(ch)) {
            T t = supplier.get();
            return t;
        }
        return null;
    }

}
