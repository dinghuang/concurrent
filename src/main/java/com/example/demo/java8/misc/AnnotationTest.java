package com.example.demo.java8.misc;

import java.lang.annotation.*;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/13
 */
public class AnnotationTest {

    @Target({ElementType.TYPE_PARAMETER,ElementType.TYPE_USE})
    @interface MyAnnotation{

    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Hints{
        Hint[] value();
    }

    @Repeatable(Hints.class)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Hint {
        String value();
    }

    @Hint("hint1")
    @Hint("hint2")
    class Person {

    }

    public static void main(String[] args) {
        Hint hint = Person.class.getAnnotation(Hint.class);
        // null
        System.out.println(hint);
        Hints hints1 = Person.class.getAnnotation(Hints.class);
        // 2
        System.out.println(hints1.value().length);
        Hint[] hints2 = Person.class.getAnnotationsByType(Hint.class);
        // 2
        System.out.println(hints2.length);

    }
}
