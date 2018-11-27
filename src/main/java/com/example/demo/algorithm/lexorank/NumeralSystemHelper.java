package com.example.demo.algorithm.lexorank;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/11/27
 */
public class NumeralSystemHelper {
    private static final List<RankInteger> BASE_36_DIVIDERS;
    private static final String NUMERAL_SYSTEM_ERROR = "error.rank.unsupportedNumeralSystem";

    private NumeralSystemHelper() {
    }

    public static List<RankDecimal> getBaseDivisors(NumeralSystem lexoNumeralSystem, int fractionMagnitude) {
        int base = lexoNumeralSystem.getBase();
        if (base == NumeralSystem.BASE_36.getBase()) {
            return fractionMagnitude < 0 ? BASE_36_DIVIDERS.stream().map(lexoInteger ->
                    RankDecimal.make(lexoInteger.shiftLeft(fractionMagnitude * -1), 0)
            ).collect(Collectors.toList()) : BASE_36_DIVIDERS.stream().map(lexoInteger ->
                    RankDecimal.make(lexoInteger, fractionMagnitude)
            ).collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException(NUMERAL_SYSTEM_ERROR);
        }
    }

    static {
        List<Object> list = ImmutableList.builder().add(RankInteger.make(NumeralSystem.BASE_36, 1, new int[]{18})).add(RankInteger.make(NumeralSystem.BASE_36, 1, new int[]{12})).add(RankInteger.make(NumeralSystem.BASE_36, 1, new int[]{9})).add(RankInteger.make(NumeralSystem.BASE_36, 1, new int[]{6})).add(RankInteger.make(NumeralSystem.BASE_36, 1, new int[]{4})).add(RankInteger.make(NumeralSystem.BASE_36, 1, new int[]{3})).add(RankInteger.make(NumeralSystem.BASE_36, 1, new int[]{2})).add(RankInteger.make(NumeralSystem.BASE_36, 1, new int[]{1})).build();
        List<RankInteger> lexoIntegers = new ArrayList<>();
        list.forEach(object -> lexoIntegers.add((RankInteger) (object)));
        BASE_36_DIVIDERS = lexoIntegers;
    }
}
