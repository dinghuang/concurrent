package com.example.demo.algorithm;

/**
 * 推特雪花算法
 * <p>
 * 分布式系统中，有一些需要使用全局唯一ID的场景，这种时候为了防止ID冲突可以使用36位的UUID，
 * 但是UUID有一些缺点，首先他相对比较长，另外UUID一般是无序的。
 * 有些时候我们希望能使用一种简单一些的ID，并且希望ID能够按照时间有序生成。
 * <p>
 * 而twitter的snowflake解决了这种需求，最初Twitter把存储系统从MySQL迁移到Cassandra，
 * 因为Cassandra没有顺序ID生成机制，所以开发了这样一套全局唯一ID生成服务。
 * <p>
 * snowflake的结构如下(每部分用-分开):
 * <p>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 * <p>
 * 第一位为未使用，接下来的41位为毫秒级时间(41位的长度可以使用69年)，
 * 然后是5位datacenterId和5位workerId(10位的长度最多支持部署1024个节点） ，
 * 最后12位是毫秒内的计数（12位的计数顺序号支持每个节点每毫秒产生4096个ID序号）
 * <p>
 * 一共加起来刚好64位，为一个Long型。(转换成字符串长度为18)
 * <p>
 * snowflake生成的ID整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞（由datacenter和workerId作区分），
 * 并且效率较高。据说：snowflake每秒能够产生26万个ID。
 *
 * @author dinghuang123@gmail.com
 * @since 2018/9/11
 */
public class SnowFlake {

    /**
     * 其实时间戳   2017-01-01 00:00:00
     */
    private static final long TWEPOCH = 1483200000000L;

    /**
     * 10bit（位）的工作机器id  中IP标识所占的位数 8bit（位）
     */
    private static final long IP_ID_BITS = 8L;

    /**
     * IP标识最大值 255  即2的8次方减一。
     */
    private static final long IP_ID_MAX = ~(-1L << IP_ID_BITS);

    /**
     * 10bit（位）的工作机器id  中数字标识id所占的位数 2bit（位）
     */
    private static final long DATA_CENTER_ID_BITS = 2L;

    /**
     * 数字标识id最大值 3  即2的2次方减一。
     */
    private static final long DATA_CENTER_ID_MAX = ~(-1L << DATA_CENTER_ID_BITS);

    /**
     * 序列在id中占的位数 12bit
     */
    private static final long SEQ_BITS = 12L;

    /**
     * 序列最大值 4095 即2的12次方减一。
     */
    private static final long SEQ_MAX = ~(-1L << SEQ_BITS);

    /**
     * 64位的数字：首位0  随后41位表示时间戳 随后10位工作机器id（8位IP标识 + 2位数字标识） 最后12位序列号
     */
    private static final long DATA_CENTER_ID_LEFT_SHIFT = SEQ_BITS;
    private static final long IP_ID_LEFT_SHIFT = SEQ_BITS + DATA_CENTER_ID_BITS;
    private static final long timeLeftShift = SEQ_BITS + DATA_CENTER_ID_BITS + IP_ID_LEFT_SHIFT;

    /**
     * IP标识(0~255)
     */
    private long ipId;

    /**
     * 数据中心ID(0~3)
     */
    private long dataCenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long seq = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTime = -1L;

    public SnowFlake(long ipId, long dataCenterId) {
        if (ipId < 0 || ipId > IP_ID_MAX) {
            System.out.println(" ---------- ipId不在正常范围内(0~" + IP_ID_MAX + ") " + ipId);
            System.exit(0);
        }

        if (dataCenterId < 0 || dataCenterId > DATA_CENTER_ID_MAX) {
            System.out.println(" ---------- dataCenterId不在正常范围内(0~" + DATA_CENTER_ID_MAX + ") " + dataCenterId);
            System.exit(0);
        }

        this.ipId = ipId;
        this.dataCenterId = dataCenterId;
    }

    public synchronized long nextId() {
        long nowTime = System.currentTimeMillis();

        if (nowTime < lastTime) {
            System.out.println(" ---------- 当前时间前于上次操作时间，当前时间有误: " + nowTime);
            System.exit(0);
        }

        if (nowTime == lastTime) {
            seq = (seq + 1) & SEQ_MAX;
            if (seq == 0) {
                nowTime = getNextTimeStamp();
            }
        } else {
            seq = 0L;
        }

        lastTime = nowTime;

        return ((nowTime - TWEPOCH) << timeLeftShift)
                | (ipId << IP_ID_LEFT_SHIFT)
                | (dataCenterId << DATA_CENTER_ID_LEFT_SHIFT)
                | seq;
    }

    private long getNextTimeStamp() {
        long nowTime;
        do {
            nowTime = System.currentTimeMillis();
        } while (nowTime <= lastTime);
        return nowTime;
    }

    public static class ThreadSnowFlake extends Thread {

        SnowFlake snowFlake;

        int cnt = 0;

        public ThreadSnowFlake(SnowFlake snowFlake) {
            this.snowFlake = snowFlake;
        }

        @Override
        public void run() {
            System.out.println();
            if (snowFlake != null) {
                while (cnt < 10) {
                    System.out.println(Thread.currentThread().getId() + " : " + snowFlake.nextId());
                    cnt++;
                }
            }
        }
    }

    public static void main(String[] args) {

        SnowFlake snowFlake = new SnowFlake(196, 2);

        ThreadSnowFlake t1 = new ThreadSnowFlake(snowFlake);
        ThreadSnowFlake t2 = new ThreadSnowFlake(snowFlake);
        ThreadSnowFlake t3 = new ThreadSnowFlake(snowFlake);

        t1.start();
        t2.start();
        t3.start();
    }

}
