package com.bc.finance.common.utils;

import com.bc.finance.common.model.KVModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EnumUtils {

    public static String getTipsByCode(Class clz, int code) {
        try {
            Method getName = clz.getMethod("getTips");
            Method getCode = clz.getMethod("getCode");

            for (Object con: clz.getEnumConstants()) {
                if(IntegerUtils.parseInt(getCode.invoke(con)) == code) {
                    return String.valueOf(getName.invoke(con));
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getNameByCode(Class clz, int code) {
        try {
            Method getName = clz.getMethod("getName");
            Method getCode = clz.getMethod("getCode");

            for (Object con: clz.getEnumConstants()) {
                if(IntegerUtils.parseInt(getCode.invoke(con)) == code) {
                    return String.valueOf(getName.invoke(con));
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer getCodeByName(Class clz, String name) {
        try {
            Method getName = clz.getMethod("getName");
            Method getCode = clz.getMethod("getCode");

            for (Object con: clz.getEnumConstants()) {
                if(name.equals(String.valueOf(getName.invoke(con)))) {
                    return IntegerUtils.parseInt(getCode.invoke(con));
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer getScoreByCode(Class clz, int code) {
        try {
            Method getScore = clz.getMethod("getScore");
            Method getCode = clz.getMethod("getCode");

            for (Object con: clz.getEnumConstants()) {
                Object val = getCode.invoke(con);
                if(val!=null && code == Integer.parseInt(String.valueOf(val))) {
                    return IntegerUtils.parseInt(getScore.invoke(con));
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer getCodeByWord(Class clz, String word) {
        try {
            Method getWord = clz.getMethod("getWord");
            Method getCode = clz.getMethod("getCode");

            for (Object con: clz.getEnumConstants()) {
                if(word.equals(String.valueOf(getWord.invoke(con)))) {
                    return IntegerUtils.parseInt(getCode.invoke(con));
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWordByCode(Class clz, Integer code) {
        if(code==null) return null;

        try {
            Method getWord = clz.getMethod("getWord");
            Method getCode = clz.getMethod("getCode");

            for (Object con: clz.getEnumConstants()) {
                if(code == Integer.parseInt(String.valueOf(getCode.invoke(con)))) {
                    return String.valueOf(getWord.invoke(con));
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getNameByWord(Class clz, String word) {
        try {
            Method getWord = clz.getMethod("getWord");
            Method getName = clz.getMethod("getName");

            for (Object con: clz.getEnumConstants()) {
                if(word.equals(String.valueOf(getWord.invoke(con)))) {
                    return String.valueOf(getName.invoke(con));
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map toMapWordName(Class clz) {
        Map map = new HashMap();
        try {
            Method getWord = clz.getMethod("getWord");
            Method getName = clz.getMethod("getName");
            for (Object con: clz.getEnumConstants()) {
                map.put(getWord.invoke(con), getName.invoke(con));
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static List<KVModel> getList(Class clz) {
        List list = new ArrayList();
        try {
            Method getName = clz.getMethod("getName");
            Method getCode = clz.getMethod("getCode");
            for (Object e : clz.getEnumConstants()) {
                int code = IntegerUtils.parseInt(getCode.invoke(e));
                String name = String.valueOf(getName.invoke(e));
                list.add(new KVModel(String.valueOf(code), name));
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取下一个code, 如果是最后一个, 则返回 -1
     * @param clz
     * @param code
     * @return
     */
    public static int getNextCode(Class clz, int code) {
        List list = new ArrayList();
        try {
            Method getCode = clz.getMethod("getCode");

            Object[] constants = clz.getEnumConstants();
            for (int i = 0; i < constants.length-1; i++) {
                if(IntegerUtils.parseInt(getCode.invoke(constants[i])) == code) {
                    return IntegerUtils.parseInt(getCode.invoke(constants[i+1]));
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static Map<String, String> toMap(Class clz) {
        List<KVModel> list = getList(clz);
        Map<String, String> map = list.stream().collect(Collectors.toMap(KVModel::getCode, KVModel::getName));
        return map;
    }
}
