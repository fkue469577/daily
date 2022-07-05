package com.bc.finance.common.utils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class YearUtils {

    // 获取年度
    public static List<Integer> listAnnual() {
        int year = LocalDate.now().getYear();

        List<Integer> yearList = IntStream.range(-10, 20).mapToObj(e -> year + e).collect(Collectors.toList());

        return yearList;
    }

}
