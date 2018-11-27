package com.example.demo.algorithm.lexorank;


import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/11/27
 */
public class RankDecimal implements Comparable<RankDecimal> {
    private final RankInteger mag;
    private final int sig;

    private static final String RADIX_POINT_ERROR = "error.rank.moreThanOne";

    private RankDecimal(RankInteger mag, int sig) {
        this.mag = mag;
        this.sig = sig;
    }

    public static RankDecimal half(NumeralSystem sys) {
        int mid = sys.getBase() / 2;
        return make(RankInteger.make(sys, 1, new int[]{mid}), 1);
    }

    public static RankDecimal parse(String str, NumeralSystem system) {
        int partialIndex = str.indexOf(system.getRadixPointChar());
        if (str.lastIndexOf(system.getRadixPointChar()) != partialIndex) {
            throw new IllegalArgumentException(RADIX_POINT_ERROR);
        } else if (partialIndex < 0) {
            return make(RankInteger.parse(str, system), 0);
        } else {
            String intStr = str.substring(0, partialIndex) + str.substring(partialIndex + 1);
            return make(RankInteger.parse(intStr, system), str.length() - 1 - partialIndex);
        }
    }

    public static RankDecimal from(RankInteger integer) {
        return make(integer, 0);
    }

    public static RankDecimal make(RankInteger integer, int sig) {
        if (integer.isZero()) {
            return new RankDecimal(integer, 0);
        } else {
            int zeroCount = 0;

            for (int i = 0; i < sig && integer.getMag(i) == 0; ++i) {
                ++zeroCount;
            }

            RankInteger newInteger = integer.shiftRight(zeroCount);
            int newSig = sig - zeroCount;
            return new RankDecimal(newInteger, newSig);
        }
    }

    public static RankDecimal fromInt(int value, NumeralSystem system) {
        char decimalPoint = NumeralSystem.BASE_10.getRadixPointChar();
        char targetSystemPoint = system.getRadixPointChar();
        String lexoString = Integer.toString(value, system.getBase()).replace(decimalPoint, targetSystemPoint);
        return parse(lexoString, system);
    }

    public NumeralSystem getSystem() {
        return this.mag.getSystem();
    }

    public int getOrderOfMagnitude() {
        return this.mag.getMagSize() - this.sig - 1;
    }

    public RankDecimal add(RankDecimal other) {
        RankInteger tmag = this.mag;
        int tsig = this.sig;
        RankInteger omag = other.mag;

        int osig;
        for (osig = other.sig; tsig < osig; ++tsig) {
            tmag = tmag.shiftLeft();
        }

        while (tsig > osig) {
            omag = omag.shiftLeft();
            ++osig;
        }

        return make(tmag.add(omag), tsig);
    }

    public RankDecimal subtract(RankDecimal other) {
        RankInteger thisMag = this.mag;
        int thisSig = this.sig;
        RankInteger otherMag = other.mag;

        int otherSig;
        for (otherSig = other.sig; thisSig < otherSig; ++thisSig) {
            thisMag = thisMag.shiftLeft();
        }

        while (thisSig > otherSig) {
            otherMag = otherMag.shiftLeft();
            ++otherSig;
        }

        return make(thisMag.subtract(otherMag), thisSig);
    }

    public RankDecimal multiply(RankDecimal other) {
        return make(this.mag.multiply(other.mag), this.sig + other.sig);
    }

    public RankInteger floor() {
        return this.mag.shiftRight(this.sig);
    }

    public RankInteger ceil() {
        if (this.isExact()) {
            return this.mag;
        } else {
            RankInteger floor = this.floor();
            return floor.add(RankInteger.one(floor.getSystem()));
        }
    }

    public boolean isExact() {
        if (this.sig == 0) {
            return true;
        } else {
            for (int i = 0; i < this.sig; ++i) {
                if (this.mag.getMag(i) != 0) {
                    return false;
                }
            }

            return true;
        }
    }

    public RankInteger getMag() {
        return this.mag;
    }

    public int getScale() {
        return this.sig;
    }

    public RankDecimal setScale(int nsig) {
        return this.setScale(nsig, false);
    }

    public RankDecimal setScale(int nsig, boolean ceiling) {
        if (nsig >= this.sig) {
            return this;
        } else {
            if (nsig < 0) {
                nsig = 0;
            }

            int diff = this.sig - nsig;
            RankInteger nmag = this.mag.shiftRight(diff);
            if (ceiling) {
                nmag = nmag.add(RankInteger.one(nmag.getSystem()));
            }

            return make(nmag, nsig);
        }
    }

    /**
     * @param o o
     * @return int
     */
    @Override
    public int compareTo(RankDecimal o) {
        RankInteger tMag = this.mag;
        RankInteger oMag = o.mag;
        if (this.sig > o.sig) {
            oMag = oMag.shiftLeft(this.sig - o.sig);
        } else if (this.sig < o.sig) {
            tMag = tMag.shiftLeft(o.sig - this.sig);
        }

        return tMag.compareTo(oMag);
    }

    public String format() {
        String intStr = this.mag.format();
        if (this.sig == 0) {
            return intStr;
        } else {
            StringBuilder sb = new StringBuilder(intStr);
            char head = sb.charAt(0);
            boolean specialHead = head == this.mag.getSystem().getPositiveChar() || head == this.mag.getSystem().getNegativeChar();
            if (specialHead) {
                sb.deleteCharAt(0);
            }

            while (sb.length() < this.sig + 1) {
                sb.insert(0, this.mag.getSystem().toChar(0));
            }

            sb.insert(sb.length() - this.sig, this.mag.getSystem().getRadixPointChar());
            if (sb.length() - this.sig == 0) {
                sb.insert(0, this.mag.getSystem().toChar(0));
            }

            if (specialHead) {
                sb.insert(0, head);
            }

            return sb.toString();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RankDecimal)) {
            return false;
        } else {
            RankDecimal o = (RankDecimal) obj;
            return this.mag.equals(o.mag) && this.sig == o.sig;
        }
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    @Override
    public String toString() {
        return this.format();
    }
}

