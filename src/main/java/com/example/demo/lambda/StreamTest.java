package com.example.demo.lambda;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.*;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/5/25
 */
public class StreamTest {
    private static List<String> list = Stream.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10").collect(Collectors.toList());
    private static Set<String> set = Stream.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10").collect(Collectors.toSet());
    private static Map<String, String> map1 = list.stream().collect(Collectors.toMap(str -> str, str -> str + "_value1"));
    private static Map<String, String> map2 = set.stream().collect(Collectors.toMap(str -> str, str -> str + "_value2"));


    public static void main(String[] args) throws InterruptedException {
        /**
         * 及早求值函数，求总数
         */
        count();
        System.out.println("==========================================================================");
        /**
         * collect方法：由Stream里的值生成一个列表，是一个及早求值操作（.of()方法是惰性求值的方法）。list、set初始化既是
         */
        collect();
        System.out.println("==========================================================================");
        /**
         * 如果有一个函数可以将一种类型的值转换成另外一种类型，map操作就可以使用该函数，将一个流中的值转换成一个新的流
         * 扩展方法：
         * IntStream mapToInt(ToIntFunction<? super T> mapper);
         * LongStream mapToLong(ToLongFunction<? super T> mapper);
         * DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper);
         */
        map();
        System.out.println("==========================================================================");
        /**
         * 保留stream中的一些元素，过滤其他的
         */
        filter();
        System.out.println("==========================================================================");
        /**
         * flatMap方法可用Stream替换值，然后将多个Stream连接成一个Stream
         * 扩展方法：
         * IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper);
         * LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper);
         * DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);
         */
        flatMap();
        System.out.println("==========================================================================");
        /**
         * distinct及早返回函数，去掉重复对象
         */
        distinct();
        System.out.println("==========================================================================");
        /**
         * sorted排序
         * sorted(Comparator<? super T> comparator) 传入比较器
         */
        sorted();
        System.out.println("==========================================================================");
        /**
         * peek用于多次消费流，做一些事情不影响对流的处理，一般用于记录操作
         */
        peek();
        System.out.println("==========================================================================");
        /**
         * limit截断流 Stream<T> limit(long maxSize);
         */
        limit();
        System.out.println("==========================================================================");
        /**
         * skip跳过 Stream<T> skip(long n);
         * 可以理解成limit返回指定的数据，skip返回不包含指定的数据。
         */
        skip();
        System.out.println("==========================================================================");
        /**
         * forEach 略
         */
        forEach();
        System.out.println("==========================================================================");
        /**
         * forEachOrdered 并行时forEach()是无序的，forEachOrdered是有序的
         */
        forEachOrdered();
        System.out.println("==========================================================================");
        /**
         * max和min，注意这两个方法都是及早值操作，后面需要接一个get()方法获取结果对象（get可以取出Optional对象中的值）
         */
        max();
        min();
        System.out.println("==========================================================================");
        /**
         * reduce简约方法，reduce可以实现从一组值中生成一个值，其实count、max、min都是reduce操作。
         * 实际是reduce函数在stream中遍历一遍，最后函数结果即为最终值
         * T reduce(T identity, BinaryOperator<T> accumulator);identity累计结果值，accumulator是累加器
         */
        reduce();
        System.out.println("==========================================================================");
        /**
         * 及早返回函数match匹配，参数都是Predicate<? super T> predicate，返回boolean
         */
        anyMatch();
        allMatch();
        noneMatch();
        System.out.println("==========================================================================");
        /**
         * 返回一条记录或随机返回一条记录
         */
        findFirst();
        findAny();
        System.out.println("==========================================================================");
        /**
         * 用函数生成流
         * Stream.iterate(0, n -> n + 2).limit(10) .forEach(System.out::println);
         * Stream.generate(Math::random).limit(5) .forEach(System.out::println);
         */
        iterate();
        generate();
        System.out.println("==========================================================================");
        /**
         * IntStream：BaseStream子类，这里写了一些IntStream单独的方法：
         * OptionalDouble average()：计算平均数
         * IntSummaryStatistics summaryStatistics()：返回IntSummaryStatistics，包含count、sum、min、max
         * Stream<Integer> boxed()：装箱
         * IntStream range(int startInclusive, int endExclusive)：返回范围，不包含最后的结束节点
         * IntStream rangeClosed(int startInclusive, int endExclusive)：返回范围，包含最后的结束节点
         */
        intStream();
        System.out.println("==========================================================================");
        /**
         * LongStream：BaseStream子类，这里写了一些LongStream单独的方法：
         * OptionalDouble average()：计算平均数
         * LongSummaryStatistics summaryStatistics()：返回IntSummaryStatistics，包含count、sum、min、max
         * Stream<Long> boxed()：装箱
         * LongStream range(int startInclusive, int endExclusive)：返回范围，不包含最后的结束节点
         * LongStream rangeClosed(int startInclusive, int endExclusive)：返回范围，包含最后的结束节点
         */
        longStream();
        System.out.println("==========================================================================");
        /**
         * DoubleStream：BaseStream子类，这里写了一些DoubleStream单独的方法：
         * OptionalDouble average()：计算平均数
         * DoubleSummaryStatistics summaryStatistics()：返回IntSummaryStatistics，包含count、sum、min、max
         * Stream<Double> boxed()：装箱
         */
        doubleStream();
    }

    private static void count() {
        System.out.println("count_" + list.stream().filter(str -> Integer.valueOf(str) > 5).count());
        System.out.println("count_" + set.stream().filter(str -> str.contains("1")).count());
    }

    private static void collect() {
        System.out.println("collect_" + list.stream().count());
        System.out.println("collect_" + set.stream().count());
        System.out.println("collect_" + map1.size());
        System.out.println("collect_" + map2.size());
        List<String> list2 = Stream.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10").filter(str -> Integer.valueOf(str) > 5).collect(Collectors.toList());
        Set<String> set2 = Stream.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10").filter(str -> Integer.valueOf(str) > 4).collect(Collectors.toSet());
        System.out.println("collect_" + list2.stream().count());
        System.out.println("collect_" + set2.stream().count());
    }

    private static void map() {
        List<String> list = Stream.of("a", "b", "c").map(str -> str.toUpperCase()).collect(Collectors.toList());
        System.out.println("map_" + String.join(",", list));
        /** IntStream mapToInt(ToIntFunction<? super T> mapper); **/
        List<Integer> listInteger = Stream.of(1, 2, 3).map(i -> i + 5).collect(Collectors.toList());
        System.out.println("mapToInt_" + listInteger.get(0) + "," + listInteger.get(1) + "," + listInteger.get(2));

        /** LongStream mapToLong(ToLongFunction<? super T> mapper);**/
        List<Long> listLong = Stream.of(111, 211, 311).map(i -> i + 1L).collect(Collectors.toList());
        System.out.println("mapToInt_" + listLong.get(0) + "," + listLong.get(1) + " " + listLong.get(2));

        /** DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper);**/
        List<Double> listDouble = Stream.of(1, 2, 3).map(i -> i + 0.1).collect(Collectors.toList());
        System.out.println("mapToInt_" + listDouble.get(0) + "," + listDouble.get(1) + " " + listDouble.get(2));
    }

    private static void filter() {
        Stream stream = list.stream().filter(str -> Integer.valueOf(str) > 5);
        System.out.println(stream.filter(str -> {
            System.out.println("filter_" + str);
            return true;
        }).count());
    }

    private static void flatMap() {
        List<Integer> together = Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4))
                .flatMap(num -> num.stream())
                .collect(Collectors.toList());
        System.out.println("flatMap_" + together.stream().count());

        /** IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper); **/
        int[] intArray = {1, 2, 3, 4, 5, 6};
        IntStream i = Stream.of(intArray).flatMapToInt(num -> Arrays.stream(num));
        System.out.println("flatMapToInt_" + i.count());

        /** LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper); **/
        long[] longArray = {1, 2, 3, 4};
        LongStream l = Stream.of(longArray).flatMapToLong(num -> Arrays.stream(num));
        System.out.println("flatMapToLong_" + l.count());

        /** DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper); **/
        double[] doubleArray = {1.0, 2.1, 3.2, 4.3, 5.4};
        DoubleStream d = Stream.of(doubleArray).flatMapToDouble(num -> Arrays.stream(num));
        System.out.println("flatMapToLong_" + d.count());

    }

    private static void distinct() {
        List<String> list = Stream.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "1", "5", "10").collect(Collectors.toList());
        System.out.println("distinct_" + list.stream().distinct().toArray().length);
    }

    private static void sorted() {
        List<String> list = Stream.of("1", "22", "333", "4444", "55555", "66", "77", "88", "99", "1000").collect(Collectors.toList());
        System.out.println("sorted_" + String.join(",", list.stream().sorted().collect(Collectors.toList())));
        System.out.println("sorted Comparator_" + String.join(",", list.stream().sorted(Comparator.comparing(str -> str.length())).collect(Collectors.toList())));
    }

    private static void peek() {
        System.out.println("peek start");
        list.stream().map(str -> str + "1").peek(System.out::println).collect(Collectors.toList());
        System.out.println("peek end");
    }

    private static void limit() {
        System.out.println("limit start");
        list.stream().limit(5).peek(System.out::println).collect(Collectors.toList());
        System.out.println("limit end");
    }

    private static void skip() {
        System.out.println("skip start");
        list.stream().skip(5).map(str -> str + "1").peek(System.out::println).collect(Collectors.toList());
        System.out.println("skip end");
    }

    private static void forEach() {
        System.out.println("forEach start");
        list.stream().forEach(System.out::println);
        System.out.println("forEach end");
    }

    private static void forEachOrdered() {
        System.out.println("forEachOrdered start");
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        list.stream().parallel().forEach(x -> System.out.println("forEach并行无序：" + x));
        list.stream().parallel().forEachOrdered(x -> System.out.println("forEachOrdered并行有序：" + x));
        System.out.println("forEachOrdered start");
    }

    private static void max() {
        UserTest userTest = Stream
                .of(new UserTest("A", 1), new UserTest("B", 2))
                .max(Comparator.comparing(user -> user.getAge()))
                .get();
        System.out.println(userTest.toString());
    }

    private static void min() {
        UserTest userTest = Stream
                .of(new UserTest("A", 1), new UserTest("B", 2))
                .min(Comparator.comparing(user -> user.getAge()))
                .get();
        System.out.println(userTest.toString());
    }

    private static void reduce() {
        int sum = Stream.of(1, 2, 3).reduce(0, (a, b) -> a + b);
        System.out.println(sum);
        int subtraction = Stream.of(10, 5, 3).reduce(100, (a, b) -> a - b);
        System.out.println(subtraction);
        int multiplication = Stream.of(1, 2, 3).reduce(1, (a, b) -> a * b);
        System.out.println(multiplication);
        int division = Stream.of(8, 4, 2).reduce(256, (a, b) -> a / b);
        System.out.println(division);

        BinaryOperator<Integer> accumlator = (a, b) -> a + b;
        int count = accumlator
                .apply(accumlator
                                .apply(accumlator
                                                .apply(accumlator
                                                                .apply(-1, 0)
                                                        , 1)
                                        , 2)
                        , 3);
        System.out.println(count);

        count = 0;
        for (Integer a : Arrays.asList(-1, 0, 1, 2, 3)) {
            count = count + a;
        }
        System.out.println(count);
    }

    public static class UserTest {
        private String name;
        private Integer age;

        public UserTest(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public UserTest() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "name：" + name + "，age：" + age;
        }
    }


    public static void anyMatch() {
        List<String> list = Stream.of("1", "2", "13", "14", "25", "36", "47").collect(Collectors.toList());
        System.out.println("anyMatch ：" + list.stream().anyMatch(str -> str.contains("1")));
        System.out.println("anyMatch ：" + list.stream().anyMatch(str -> str.contains("11")));
    }

    public static void allMatch() {
        List<String> list = Stream.of("1", "2", "13", "14", "25", "36", "47").collect(Collectors.toList());
        System.out.println("allMatch ：" + list.stream().allMatch(str -> Integer.valueOf(str) > 0));
        System.out.println("allMatch ：" + list.stream().allMatch(str -> Integer.valueOf(str) > 1));
    }

    public static void noneMatch() {
        List<String> list = Stream.of("1", "2", "13", "14", "25", "36", "47").collect(Collectors.toList());
        System.out.println("noneMatch ：" + list.stream().noneMatch(str -> Integer.valueOf(str) > 100));
        System.out.println("noneMatch ：" + list.stream().noneMatch(str -> Integer.valueOf(str) > 10));
    }

    public static void findFirst() {
        List<String> list = Stream.of("1", "2", "13", "14", "25", "36", "47").collect(Collectors.toList());
        System.out.println("findFirst ：" + list.stream().findFirst().get().toString());
    }

    public static void findAny() throws InterruptedException {
        List<String> list = Stream.of("1", "2", "13", "14", "25", "36", "47", "3", "4", "5", "6", "7", "8").collect(Collectors.toList());
        System.out.println("findAny ：" + list.stream().parallel().findAny().get().toString());
        Thread.sleep(50L);
        System.out.println("findAny ：" + list.stream().parallel().findAny().get().toString());
        Thread.sleep(50L);
        System.out.println("findAny ：" + list.stream().parallel().findAny().get().toString());
        Thread.sleep(50L);
        System.out.println("findAny ：" + list.stream().parallel().findAny().get().toString());
        Thread.sleep(50L);
        System.out.println("findAny ：" + list.stream().parallel().findAny().get().toString());
        Thread.sleep(50L);
        System.out.println("findAny ：" + list.stream().parallel().findAny().get().toString());
        Thread.sleep(50L);
        System.out.println("findAny ：" + list.stream().parallel().findAny().get().toString());
    }

    public static void iterate() {
        System.out.println("iterate start");
        Stream.iterate(0, n -> n + 2).limit(5).forEach(System.out::println);
        System.out.println("iterate end");
    }

    public static void generate() {
        System.out.println("generate start");
        Stream.generate(Math::random).limit(5).forEach(System.out::println);
        System.out.println("generate start");
    }

    /**
     * IntStream：BaseStream子类，这里写了一些IntStream单独的方法：
     * OptionalDouble average()：计算平均数
     * IntSummaryStatistics summaryStatistics()：返回IntSummaryStatistics，包含count、sum、min、max
     * Stream<Integer> boxed()：装箱
     * IntStream range(int startInclusive, int endExclusive)：返回范围，不包含最后的结束节点
     * IntStream rangeClosed(int startInclusive, int endExclusive)：返回范围，包含最后的结束节点
     */
    private static void intStream() {
        System.out.println("intStream start");

        System.out.println("iterate：");
        System.out.println(IntStream.iterate(0, n -> n + 1).limit(5).average());

        System.out.println("summaryStatistics：");
        System.out.println("average：" + IntStream.iterate(0, n -> n + 1).limit(5).summaryStatistics().getAverage()
                + "，count：" + IntStream.iterate(0, n -> n + 1).limit(5).summaryStatistics().getCount()
                + "，max：" + IntStream.iterate(0, n -> n + 1).limit(5).summaryStatistics().getMax()
                + "，min：" + IntStream.iterate(0, n -> n + 1).limit(5).summaryStatistics().getMin()
                + "，sum：" + IntStream.iterate(0, n -> n + 1).limit(5).summaryStatistics().getSum()
        );

        System.out.println("boxed：");
        IntStream.iterate(0, n -> n + 1).limit(5).boxed().collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("range：");
        IntStream.range(1, 3).forEach(System.out::println);

        System.out.println("rangeClosed：");
        IntStream.rangeClosed(1, 3).forEach(System.out::println);
    }

    /**
     * LongStream：BaseStream子类，这里写了一些LongStream单独的方法：
     * OptionalDouble average()：计算平均数
     * LongSummaryStatistics summaryStatistics()：返回IntSummaryStatistics，包含count、sum、min、max
     * Stream<Long> boxed()：装箱
     * LongStream range(int startInclusive, int endExclusive)：返回范围，不包含最后的结束节点
     * LongStream rangeClosed(int startInclusive, int endExclusive)：返回范围，包含最后的结束节点
     */
    private static void longStream() {
        System.out.println("longStream start");

        System.out.println("iterate：");
        System.out.println(LongStream.iterate(0, n -> n + 1).limit(5).average());

        System.out.println("summaryStatistics：");
        System.out.println("average：" + LongStream.iterate(0, n -> n + 1).limit(5).summaryStatistics().getAverage()
                + "，count：" + LongStream.iterate(0, n -> n + 1).limit(5).summaryStatistics().getCount()
                + "，max：" + LongStream.iterate(0, n -> n + 1).limit(5).summaryStatistics().getMax()
                + "，min：" + LongStream.iterate(0, n -> n + 1).limit(5).summaryStatistics().getMin()
                + "，sum：" + LongStream.iterate(0, n -> n + 1).limit(5).summaryStatistics().getSum()
        );

        System.out.println("boxed：");
        LongStream.iterate(0, n -> n + 1).limit(5).boxed().collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("range：");
        LongStream.range(1, 3).forEach(System.out::println);

        System.out.println("rangeClosed：");
        LongStream.rangeClosed(1, 3).forEach(System.out::println);
    }

    /**
     * DoubleStream：BaseStream子类，这里写了一些DoubleStream单独的方法：
     * OptionalDouble average()：计算平均数
     * DoubleSummaryStatistics summaryStatistics()：返回IntSummaryStatistics，包含count、sum、min、max
     * Stream<Double> boxed()：装箱
     */
    private static void doubleStream() {
        System.out.println("doubleStream start");

        System.out.println("iterate：");
        System.out.println(DoubleStream.iterate(0, n -> n + 1).limit(5).average());

        System.out.println("summaryStatistics：");
        System.out.println("average：" + DoubleStream.iterate(0, n -> n + 1).limit(5).summaryStatistics().getAverage()
                + "，count：" + DoubleStream.iterate(0, n -> n + 1).limit(5).summaryStatistics().getCount()
                + "，max：" + DoubleStream.iterate(0, n -> n + 1).limit(5).summaryStatistics().getMax()
                + "，min：" + DoubleStream.iterate(0, n -> n + 1).limit(5).summaryStatistics().getMin()
                + "，sum：" + DoubleStream.iterate(0, n -> n + 1).limit(5).summaryStatistics().getSum()
        );

        System.out.println("boxed：");
        DoubleStream.iterate(0, n -> n + 1).limit(5).boxed().collect(Collectors.toList()).forEach(System.out::println);
    }
}
