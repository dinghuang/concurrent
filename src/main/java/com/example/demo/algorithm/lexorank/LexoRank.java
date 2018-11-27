package com.example.demo.algorithm.lexorank;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/11/27
 */
public class LexoRank implements Comparable<LexoRank> {
    public static final NumeralSystem NUMERAL_SYSTEM;
    private static final RankDecimal ZERO_DECIMAL;
    private static final RankDecimal ONE_DECIMAL;
    private static final RankDecimal EIGHT_DECIMAL;
    private static final RankDecimal MIN_DECIMAL;
    private static final RankDecimal MAX_DECIMAL;
    public static final RankDecimal MID_DECIMAL;
    private static final RankDecimal INITIAL_MIN_DECIMAL;
    private static final RankDecimal INITIAL_MAX_DECIMAL;
    private final String value;
    private RankBucket bucket;
    private RankDecimal decimal;

    private static final String BUCKET_ERROR = "error.rank.bucketNotEqual";
    private static final String NUMERAL_SYSTEM_ERROR = "error.rank.numeralSysNotEqual";
    private static final String RANK_ERROR = "error.rank.notBetweenRank";
    private static final String DISTANCE_ERROR = "error.rank.notSuitableDistance";

    private LexoRank(String value) {
        this.value = value;
    }

    private LexoRank(RankBucket bucket, RankDecimal decimal) {
        this.value = bucket.format() + "|" + formatDecimal(decimal);
        this.bucket = bucket;
        this.decimal = decimal;
    }

    public static LexoRank min() {
        return from(RankBucket.BUCKET_0, MIN_DECIMAL);
    }

    public static LexoRank max() {
        return max(RankBucket.BUCKET_0);
    }

    public static LexoRank max(RankBucket bucket) {
        return from(bucket, MAX_DECIMAL);
    }

    public static LexoRank initial(RankBucket bucket) {
        return bucket == RankBucket.BUCKET_0 ? from(bucket, INITIAL_MIN_DECIMAL) : from(bucket, INITIAL_MAX_DECIMAL);
    }

    public LexoRank genPrev() {
        this.fillDecimal();
        if (this.isMax()) {
            return new LexoRank(this.bucket, INITIAL_MAX_DECIMAL);
        } else {
            RankInteger floorInteger = this.decimal.floor();
            RankDecimal floorDecimal = RankDecimal.from(floorInteger);
            RankDecimal nextDecimal = floorDecimal.subtract(EIGHT_DECIMAL);
            if (nextDecimal.compareTo(MIN_DECIMAL) <= 0) {
                nextDecimal = between(MIN_DECIMAL, this.decimal);
            }

            return new LexoRank(this.bucket, nextDecimal);
        }
    }

    public LexoRank genNext() {
        this.fillDecimal();
        if (this.isMin()) {
            return new LexoRank(this.bucket, INITIAL_MIN_DECIMAL);
        } else {
            RankInteger ceilInteger = this.decimal.ceil();
            RankDecimal ceilDecimal = RankDecimal.from(ceilInteger);
            RankDecimal nextDecimal = ceilDecimal.add(EIGHT_DECIMAL);
            if (nextDecimal.compareTo(MAX_DECIMAL) >= 0) {
                nextDecimal = between(this.decimal, MAX_DECIMAL);
            }

            return new LexoRank(this.bucket, nextDecimal);
        }
    }

    public LexoRank between(LexoRank other) {
        return this.between(other, 0);
    }

    public LexoRank between(LexoRank other, int capacity) {
        this.fillDecimal();
        other.fillDecimal();
        if (!this.bucket.equals(other.bucket)) {
            throw new IllegalArgumentException(BUCKET_ERROR);
        } else {
            int cmp = this.decimal.compareTo(other.decimal);
            if (cmp > 0) {
                return new LexoRank(this.bucket, between(other.decimal, this.decimal, capacity));
            } else if (cmp == 0) {
                throw new IllegalArgumentException(RANK_ERROR);
            } else {
                return new LexoRank(this.bucket, between(this.decimal, other.decimal, capacity));
            }
        }
    }

    public static RankDecimal between(RankDecimal oLeft, RankDecimal oRight) {
        return between(oLeft, oRight, 0);
    }

    public static RankDecimal between(RankDecimal left, RankDecimal right, int spaceToRemain) {
        NumeralSystem system = left.getSystem();
        if (system != right.getSystem()) {
            throw new IllegalArgumentException(NUMERAL_SYSTEM_ERROR);
        } else {
            //将right与left相减
            RankDecimal space = right.subtract(left);
            int capacity = spaceToRemain + 2;
            RankDecimal spacing = findSpacing(space, capacity);
            RankDecimal floor = floorToSpacingDivisor(left, spacing);
            return roundToSpacing(left, floor, spacing);
        }
    }

    private static RankDecimal findSpacing(RankDecimal space, int capacity) {
        //capacity自然对数除space.getSystem().getBase()计算容量
        int capacityMagnitude = (int) Math.floor(Math.log((double) capacity) / Math.log((double) space.getSystem().getBase()));
        //int数组长度-”:“后字符长度 -1 - capacityMagnitude
        int spacingMagnitude = space.getOrderOfMagnitude() - capacityMagnitude;
        //
        RankDecimal lexoCapacity = RankDecimal.fromInt(capacity, space.getSystem());
        Iterator var5 = getSystemBaseDivisors(space.getSystem(), spacingMagnitude).iterator();

        RankDecimal spacingCandidate;
        do {
            if (!var5.hasNext()) {
                throw new IllegalArgumentException(DISTANCE_ERROR);
            }

            spacingCandidate = (RankDecimal) var5.next();
        } while (space.compareTo(spacingCandidate.multiply(lexoCapacity)) < 0);

        return spacingCandidate;
    }

    private static List<RankDecimal> getSystemBaseDivisors(NumeralSystem lexoNumeralSystem, int magnitude) {
        int fractionMagnitude = magnitude * -1;
        int adjacentFractionMagnitude = fractionMagnitude + 1;
        List<Object> list = ImmutableList.builder().addAll(NumeralSystemHelper.getBaseDivisors(lexoNumeralSystem, fractionMagnitude)).addAll(NumeralSystemHelper.getBaseDivisors(lexoNumeralSystem, adjacentFractionMagnitude)).build();
        List<RankDecimal> lexoDecimals = new ArrayList<>();
        list.forEach(object -> lexoDecimals.add((RankDecimal) (object)));
        return lexoDecimals;
    }

    private static RankDecimal floorToSpacingDivisor(RankDecimal number, RankDecimal spacing) {
        RankDecimal zero = RankDecimal.from(RankInteger.zero(number.getSystem()));
        if (zero.equals(number)) {
            return spacing;
        } else {
            RankInteger spacingsMag = spacing.getMag();
            int scaleDifference = number.getScale() + spacing.getOrderOfMagnitude();
            int spacingsMostSignificantDigit = spacingsMag.getMagSize() - 1;

            RankInteger floor;
            for (floor = number.getMag().shiftRight(scaleDifference).add(RankInteger.one(number.getSystem())); floor.getMag(0) % spacingsMag.getMag(spacingsMostSignificantDigit) != 0; ) {
                floor = floor.add(RankInteger.one(number.getSystem()));
            }
            return number.getScale() - scaleDifference > 0 ? RankDecimal.make(floor, number.getScale() - scaleDifference) : RankDecimal.make(floor.shiftLeft(scaleDifference), number.getScale());
        }
    }

    private static RankDecimal roundToSpacing(RankDecimal number, RankDecimal floor, RankDecimal spacing) {
        RankDecimal halfSpacing = spacing.multiply(RankDecimal.half(spacing.getSystem()));
        RankDecimal difference = floor.subtract(number);
        return difference.compareTo(halfSpacing) >= 0 ? floor : floor.add(spacing);
    }

    private static RankDecimal mid(RankDecimal left, RankDecimal right) {
        RankDecimal sum = left.add(right);
        RankDecimal mid = sum.multiply(RankDecimal.half(left.getSystem()));
        int scale = Math.max(left.getScale(), right.getScale());
        if (mid.getScale() > scale) {
            RankDecimal roundDown = mid.setScale(scale, false);
            if (roundDown.compareTo(left) > 0) {
                return roundDown;
            }

            RankDecimal roundUp = mid.setScale(scale, true);
            if (roundUp.compareTo(right) < 0) {
                return roundUp;
            }
        }

        return mid;
    }

    private void fillDecimal() {
        if (this.decimal == null) {
            String[] parts = this.value.split("\\|");
            this.bucket = RankBucket.from(parts[0]);
            this.decimal = RankDecimal.parse(parts[1], NUMERAL_SYSTEM);
        }

    }

    public RankBucket getBucket() {
        this.fillDecimal();
        return this.bucket;
    }

    public RankDecimal getDecimal() {
        this.fillDecimal();
        return this.decimal;
    }

    public LexoRank inNextBucket() {
        this.fillDecimal();
        return from(this.bucket.next(), this.decimal);
    }

    public LexoRank inPrevBucket() {
        this.fillDecimal();
        return from(this.bucket.prev(), this.decimal);
    }

    public boolean isMin() {
        this.fillDecimal();
        return this.decimal.equals(MIN_DECIMAL);
    }

    public boolean isMax() {
        this.fillDecimal();
        return this.decimal.equals(MAX_DECIMAL);
    }

    public String format() {
        return this.value;
    }

    public static String formatDecimal(RankDecimal decimal) {
        String formatVal = decimal.format();
        StringBuilder val = new StringBuilder(formatVal);
        int partialIndex = formatVal.indexOf(NUMERAL_SYSTEM.getRadixPointChar());
        char zero = NUMERAL_SYSTEM.toChar(0);
        if (partialIndex < 0) {
            partialIndex = formatVal.length();
            val.append(NUMERAL_SYSTEM.getRadixPointChar());
        }

        while (partialIndex < 6) {
            val.insert(0, zero);
            ++partialIndex;
        }

        while (val.charAt(val.length() - 1) == zero) {
            val.setLength(val.length() - 1);
        }

        return val.toString();
    }

    public static LexoRank parse(String str) {
        return new LexoRank(str);
    }

    public static LexoRank from(RankBucket bucket, RankDecimal decimal) {
        if (decimal.getSystem() != NUMERAL_SYSTEM) {
            throw new IllegalArgumentException(NUMERAL_SYSTEM_ERROR);
        } else {
            return new LexoRank(bucket, decimal);
        }
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof LexoRank && (this == o || this.value.equals(((LexoRank) o).value));
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public int compareTo(LexoRank o) {
        return this.value.compareTo(o.value);
    }

    static {
        NUMERAL_SYSTEM = NumeralSystem.BASE_36;
        ZERO_DECIMAL = RankDecimal.parse("0", NUMERAL_SYSTEM);
        ONE_DECIMAL = RankDecimal.parse("1", NUMERAL_SYSTEM);
        EIGHT_DECIMAL = RankDecimal.parse("8", NUMERAL_SYSTEM);
        MIN_DECIMAL = ZERO_DECIMAL;
        MAX_DECIMAL = RankDecimal.parse("1000000", NUMERAL_SYSTEM).subtract(ONE_DECIMAL);
        MID_DECIMAL = mid(MIN_DECIMAL, MAX_DECIMAL);
        INITIAL_MIN_DECIMAL = RankDecimal.parse("100000", NUMERAL_SYSTEM);
        INITIAL_MAX_DECIMAL = RankDecimal.parse(NUMERAL_SYSTEM.toChar(NUMERAL_SYSTEM.getBase() - 2) + "00000", NUMERAL_SYSTEM);
    }
}

