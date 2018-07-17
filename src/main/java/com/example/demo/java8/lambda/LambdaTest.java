package com.example.demo.java8.lambda;

import com.example.demo.java8.Person;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.*;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/13
 */
public class LambdaTest {

    private int outerNum;

    @FunctionalInterface
    public static interface Converter<F, T> {
        T convert(F from);
    }

    @FunctionalInterface
    interface Fun {
        void foo();
    }

    static class Something {
        String startsWith(String s) {
            return String.valueOf(s.charAt(0));
        }
    }

    interface PersonFactory<P extends Person> {
        P create(String firstName, String lastName);
    }

    public static void main(String[] args) throws Exception {
        //lambda排序
        test1();
        //lambda函数式编程
        test2();
        test3();
        new LambdaTest().testScopes();
        //BiConsumer Example
        test5();
    }

    private static void test5() {
        BiConsumer<String,Integer> printKeyAndValue
                = (key,value) -> System.out.println(key+"-"+value);
        printKeyAndValue.accept("One",1);
        printKeyAndValue.accept("Two",2);
        System.out.println("##################");
        //Java Hash-Map foreach supports BiConsumer
        HashMap<String, Integer> dummyValues = new HashMap<>();
        dummyValues.put("One", 1);
        dummyValues.put("Two", 2);
        dummyValues.put("Three", 3);
        dummyValues.forEach((key,value) -> System.out.println(key+"-"+value));
    }

    private static void test3() throws Exception {
        //Predicate
        Predicate<String> predicate = (s) -> s.length() > 0;
        predicate.test("foo");
        //negate意思是!
        predicate.negate().test("foo");
        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;
        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();
        //Functions
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        backToString.apply("123");
        // Suppliers
        Supplier<Person> personSupplier = Person::new;
        personSupplier.get();
        // Consumers
        Consumer<Person> greeter = (p) -> System.out.println("Hello, " + p.firstName);
        greeter.accept(new Person("Luke", "Skywalker"));
        // Comparators
        Comparator<Person> comparator = Comparator.comparing(p -> p.firstName);
        Person p1 = new Person("John", "Doe");
        Person p2 = new Person("Alice", "Wonderland");
        // > 0
        comparator.compare(p1, p2);
        // < 0 倒序
        comparator.reversed().compare(p1, p2);
        // Runnables
        Runnable runnable = () -> System.out.println(UUID.randomUUID());
        runnable.run();
        // Callables
        Callable<UUID> callable = UUID::randomUUID;
        callable.call();
    }

    private static void test1() {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        names.sort(Comparator.reverseOrder());
        System.out.println(names);
        List<String> names2 = Arrays.asList("peter", null, "anna", "mike", "xenia");
        names2.sort(Comparator.nullsLast(String::compareTo));
        System.out.println(names2);
        List<String> names3 = new ArrayList<>();
        Optional.of(names3).ifPresent(list -> list.sort(Comparator.naturalOrder()));
        System.out.println(names3);
    }

    private static void test2() {
        Converter<String, Integer> integerConverter = Integer::valueOf;
        Integer integer = integerConverter.convert("123");
        System.out.println(integer);
        PersonFactory<Person> personPersonFactory = Person::new;
        Person person = personPersonFactory.create("Peter", "Parker");
        System.out.println(person.toString());
    }

    private void testScopes() {
        int num = 1;
        LambdaTest.Converter<Integer, String> stringConverter =
                (from) -> String.valueOf(from + num);
        String convert = stringConverter.convert(2);
        System.out.println(convert);
        String[] array = new String[1];
        LambdaTest.Converter<Integer, String> stringConverter3 = (from) -> {
            array[0] = "Hi there";
            return String.valueOf(from);
        };
        stringConverter3.convert(23);
        System.out.println(array[0]);
    }

}
