package com.lch.lottery.filter;

import android.content.Context;

import com.lch.lottery.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lchli on 2016/3/26.
 */
public final class FilterUtils {


    public static List<String> getDanmaNumbers() {
        List<String> numbers = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            numbers.add(String.valueOf(i));
        }
        return numbers;
    }

    public static String formatInputNumber(String numbers) {
        if (numbers == null) {
            return null;
        }
        numbers = numbers.substring(1, numbers.length() - 1);
        numbers = numbers.replaceAll(",", "");
        return numbers;
    }

    public static List<String> getBaseFilterNumbers(Context context) {
        String base = context.getString(R.string.filter_base_numbers);
        String[] baseArray = base.split("\\s+");
        List<String> baseList = new ArrayList<>();
        for (String e : baseArray) {
            baseList.add(e);
        }
        return baseList;
    }

    public static List<String> getErmaNumbers() {
        List<String> ermas = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                String e = i < j ? i + "" + j : j + "" + i;
                if (!ermas.contains(e)) {
                    ermas.add(e);
                }

            }
        }
        return ermas;
    }
}
