package com.example.demo.java8.misc;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 用于无麻烦地使用抛出检查异常的lambda表达式的实用程序。
 *
 * @author dinghuang123@gmail.com
 * @since 2018/7/13
 */
public class CheckedFunctionTest {

    @FunctionalInterface
    public interface CheckedConsumer<T> {
        void accept(T input) throws Exception;
    }

    @FunctionalInterface
    public interface CheckedPredicate<T> {
        boolean test(T input) throws Exception;
    }

    @FunctionalInterface
    public interface CheckedFunction<F, T> {
        T apply(F input) throws Exception;
    }

    /**
     * 返回一个函数，该函数将可能检查的异常作为运行时异常重新抛出。
     *
     * @param function function
     * @param <F>      <F>
     * @param <T>      <T>
     * @return function.apply(input)
     */
    public static <F, T> Function<F, T> function(CheckedFunction<F, T> function) {
        return input -> {
            try {
                return function.apply(input);
            } catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new RuntimeException(e);
            }
        };
    }

    public static <T> Predicate<T> predicate(CheckedPredicate<T> predicate) {
        return input -> {
            try {
                return predicate.test(input);
            }
            catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new RuntimeException(e);
            }
        };
    }

    public static <T> Consumer<T> consumer(CheckedConsumer<T> consumer) {
        return input -> {
            try {
                consumer.accept(input);
            }
            catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new RuntimeException(e);
            }
        };
    }
}
