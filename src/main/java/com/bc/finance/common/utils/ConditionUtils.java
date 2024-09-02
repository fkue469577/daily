package com.bc.finance.common.utils;

public class ConditionUtils {

    public static void trueRunnable(boolean b, Runnable runnable) {
        if(b) {
            runnable.run();
        }
    }

}
