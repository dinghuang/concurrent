package com.example.demo.algorithm.lexorank;

import java.util.Objects;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/11/27
 */
public enum RankBucket {
    /**
     * 第一个桶
     */
    BUCKET_0("0"),
    /**
     * 第一个桶
     */
    BUCKET_1("1"),
    /**
     * 第一个桶
     */
    BUCKET_2("2");

    private static final String BUCKET_ERROR = "error.rank.unknownBucket";
    private static final String RANK_ERROR = "error.rank.illegalRankValue";

    private final RankInteger value;

    RankBucket(String val) {
        this.value = RankInteger.parse(val, LexoRank.NUMERAL_SYSTEM);
    }

    public static RankBucket resolve(int bucketId) {
        RankBucket[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            RankBucket bucket = var1[var3];
            if (Objects.equals(bucket.value, String.valueOf(bucketId))) {
                return bucket;
            }
        }

        throw new IllegalArgumentException(BUCKET_ERROR);
    }

    public String format() {
        return this.value.format();
    }

    public RankBucket next() {
        switch (this.ordinal()) {
            case 1:
                return BUCKET_1;
            case 2:
                return BUCKET_2;
            case 3:
                return BUCKET_0;
            default:
                throw new IllegalArgumentException(BUCKET_ERROR);
        }
    }

    public RankBucket prev() {
        switch (this.ordinal()) {
            case 1:
                return BUCKET_2;
            case 2:
                return BUCKET_0;
            case 3:
                return BUCKET_1;
            default:
                throw new IllegalArgumentException(BUCKET_ERROR);
        }
    }

    public static RankBucket fromRank(String rank) {
        String bucket = rank.substring(0, rank.indexOf('|'));
        return from(bucket);
    }

    public static RankBucket from(String str) {
        RankInteger val = RankInteger.parse(str, LexoRank.NUMERAL_SYSTEM);
        RankBucket[] var2 = values();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            RankBucket bucket = var2[var4];
            if (bucket.value.equals(val)) {
                return bucket;
            }
        }

        throw new IllegalArgumentException(BUCKET_ERROR);
    }

    public static RankBucket max() {
        RankBucket[] values = values();
        return values[values.length - 1];
    }

    public Integer intValue() {
        switch (this.ordinal()) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            default:
                throw new IllegalArgumentException(RANK_ERROR);
        }
    }
}
