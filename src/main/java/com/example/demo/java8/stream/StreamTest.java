package com.example.demo.java8.stream;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/9/13
 */
public class StreamTest {

    public static final int MAX = 1000000;

    public static void main(String[] args) {
//        test1();
//        //sequential
//        test2();
//        //并发流sequential
//        test3();
//        //函数式管道
//        test4();
//        test5();
        //建造者模式使用
//        test6();
        //flatMap置换流
//        test7();
//        test8();
        //Collectors
//        test9();
        //reduce
//        test10();
        //并发测试
//        test11();
        //随机数
        test12();

    }

    private static void test12() {
        SecureRandom secureRandom = new SecureRandom(new byte[]{1, 3, 3, 7});
        int[] randoms = IntStream.generate(secureRandom::nextInt)
                .filter(n -> n > 0)
                .limit(10)
                .toArray();
        System.out.println(Arrays.toString(randoms));


        int[] nums = IntStream.iterate(1, n -> n * 2)
                .limit(11)
                .toArray();
        System.out.println(Arrays.toString(nums));
    }

    private static void test11() {
        List<String> strings = Arrays.asList("a1", "a2", "b1", "c2", "c1");
        strings
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter:  %s [%s]\n", s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map:     %s [%s]\n", s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .sorted((s1, s2) -> {
                    System.out.format("sort:    %s <> %s [%s]\n", s1, s2, Thread.currentThread().getName());
                    return s1.compareTo(s2);
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n", s, Thread.currentThread().getName()));
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println(commonPool.getParallelism());
    }

    private static void test10() {
        List<Person> persons =
                Arrays.asList(
                        new Person("Max", 18),
                        new Person("Peter", 23),
                        new Person("Pamela", 23),
                        new Person("David", 12));
        persons
                .stream()
                .reduce((p1, p2) -> p1.age > p2.age ? p1 : p2)
                .ifPresent(System.out::println);
        Person result =
                persons
                        .stream()
                        .reduce(new Person("", 0), (p1, p2) -> {
                            p1.age += p2.age;
                            p1.name += p2.name;
                            return p1;
                        });
        System.out.format("name=%s; age=%s", result.name, result.age);

        Integer ageSum = persons
                .stream()
                .reduce(0, (sum, p) -> sum += p.age, (sum1, sum2) -> sum1 + sum2);

        System.out.println(ageSum);

        Integer ageSum2 = persons
                .stream()
                .reduce(0,
                        (sum, p) -> {
                            System.out.format("accumulator: sum=%s; person=%s\n", sum, p);
                            return sum += p.age;
                        },
                        (sum1, sum2) -> {
                            System.out.format("combiner: sum1=%s; sum2=%s\n", sum1, sum2);
                            return sum1 + sum2;
                        });

        System.out.println(ageSum2);


    }

    private static void test9() {
        List<Person> persons =
                Arrays.asList(
                        new Person("Max", 18),
                        new Person("Peter", 23),
                        new Person("Pamela", 23),
                        new Person("David", 12));
        Map<Integer, List<Person>> personsByAge = persons.stream()
                .collect(Collectors.groupingBy(p -> p.age));
        Double averageAge = persons
                .stream()
                .collect(Collectors.averagingInt(p -> p.age));
        System.out.println(averageAge);
        IntSummaryStatistics ageSummary = persons.stream()
                .collect(Collectors.summarizingInt(p -> p.age));
        System.out.println(ageSummary);
        String names = persons
                .stream()
                .filter(p -> p.age >= 18)
                .map(p -> p.name)
                .collect(Collectors.joining(" and ", "In Germany ", " are of legal age."));
        System.out.println(names);
        //键值相同的用;合成一个
        Map<Integer, String> map = persons
                .stream()
                .collect(Collectors.toMap(
                        p -> p.age,
                        p -> p.name,
                        (name1, name2) -> name1 + ";" + name2));
        System.out.println(map);

        Collector<Person, StringJoiner, String> personNameCollector1 =
                Collector.of(
                        () -> {
                            System.out.println("supplier");
                            return new StringJoiner(" | ");
                        },
                        (j, p) -> {
                            System.out.format("accumulator: p=%s; j=%s\n", p, j);
                            j.add(p.name.toUpperCase());
                        },
                        (j1, j2) -> {
                            System.out.println("merge");
                            return j1.merge(j2);
                        },
                        j -> {
                            System.out.println("finisher");
                            return j.toString();
                        });

        String names2 = persons
                .stream()
                .collect(personNameCollector1);

        System.out.println(names2);
    }

    static class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static void test8() {
        Arrays.asList("a1", "a2", "a3")
                .stream()
                .findFirst()
                .ifPresent(System.out::println);

        Stream.of("a1", "a2", "a3")
                .map(s -> s.substring(1))
                .mapToInt(Integer::parseInt)
                .max()
                .ifPresent(System.out::println);
        IntStream.range(1, 4)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);

        Arrays.stream(new int[]{1, 2, 3})
                .map(n -> 2 * n + 1)
                .average()
                .ifPresent(System.out::println);

        Stream.of(1.0, 2.0, 3.0)
                .mapToInt(Double::intValue)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);

        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .stream()
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);
    }

    static class Foo {
        String name;
        List<Bar> bars = new ArrayList<>();

        Foo(String name) {
            this.name = name;
        }
    }

    static class Bar {
        String name;

        Bar(String name) {
            this.name = name;
        }
    }

    private static void test7() {
        IntStream.range(1, 4).mapToObj(num -> new Foo("Foo" + num))
                .peek(f -> IntStream.range(1, 4).mapToObj(num -> new Bar("Bar" + num + "<-" + f.name))
                        .forEach(f.bars::add)).flatMap(f -> f.bars.stream()).forEach(b -> System.out.println(b.name));
    }

    private static void test6() {
        Stream.of(new BigDecimal("1.2"), new BigDecimal("3.7"))
                .mapToDouble(BigDecimal::doubleValue)
                .average().ifPresent(System.out::println);
        IntStream.builder().add(1).add(3).add(5).add(7).add(11).build().average().ifPresent(System.out::println);
    }

    private static void test5() {
        List<String> strings = Arrays.asList("d2", "a2", "b1", "b3", "c");
        Supplier<Stream<String>> streamSupplier = () -> strings.stream().filter(s -> s.startsWith("a"));
        boolean a = streamSupplier.get().anyMatch(s -> true);
        boolean b = streamSupplier.get().noneMatch(s -> true);
        System.out.println(a);
        System.out.println(b);
        Stream<String> stream = strings
                .stream()
                .filter(s -> s.startsWith("a"));

        a = stream.anyMatch(s -> true);
        //此时流关闭，会抛出异常
//        b = stream.noneMatch(s -> true);
        strings.stream()
                .sorted((s1, s2) -> {
                    System.out.printf("sort:    %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .filter(s -> {
                    System.out.println("filter:  " + s);
                    return s.toLowerCase().startsWith("a");
                })
                .map(s -> {
                    System.out.println("map:     " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));

    }

    private static void test4() {
        IntStream.range(0, 10)
                .filter(i -> i % 2 == 1)
                .forEach(System.out::println);
        OptionalInt reduced1 = IntStream.range(0, 10).reduce((a, b) -> a + b);
        System.out.println(reduced1.getAsInt());
        int reduced2 =
                IntStream.range(0, 10)
                        .reduce(7, (a, b) -> a + b);
        System.out.println(reduced2);
    }

    private static void test3() {
        List<String> values = new ArrayList<>(MAX);
        for (int i = 0; i < MAX; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        // sequential

        long t0 = System.nanoTime();

        long count = values.parallelStream().sorted().count();
        System.out.println(count);

        long t1 = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("parallel sort took: %d ms", millis));
    }

    private static void test2() {
        List<String> values = new ArrayList<>(MAX);
        for (int i = 0; i < MAX; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }
        long t0 = System.nanoTime();
        long count = values.stream().sorted().count();
        System.out.println(count);
        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms", millis));
    }

    private static void test1() {
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");
        //filtering
        stringCollection
                .stream()
                .filter(s -> s.startsWith("a"))
                .forEach(System.out::println);
        //sorting
        stringCollection
                .stream()
                .sorted()
                .filter(s -> s.startsWith("a"))
                .forEach(System.out::println);
        //mapping
        stringCollection.stream().map(String::toUpperCase)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(System.out::println);
        //matching
        boolean anyStartWithA = stringCollection
                .stream().anyMatch(s -> s.startsWith("a"));
        System.out.println(anyStartWithA);
        boolean allStartsWithA = stringCollection
                .stream()
                .allMatch(s -> s.startsWith("a"));
        System.out.println(allStartsWithA);
        boolean noneStartsWithZ = stringCollection
                .stream()
                .noneMatch(s -> s.startsWith("z"));
        System.out.println(noneStartsWithZ);
        //counting
        long startsWithB = stringCollection.stream()
                .filter(s -> s.startsWith("b")).count();
        System.out.println(startsWithB);
        //reducing
        Optional<String> reduced = stringCollection.stream()
                .sorted().reduce((s1, s2) -> s1 + "#" + s2);
        reduced.ifPresent(System.out::println);


    }

}
