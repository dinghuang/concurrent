package com.example.demo.java8.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

/**
 * A {@link Future} that may be explicitly completed (setting its
 * value and status), and may be used as a {@link CompletionStage},
 * supporting dependent functions and actions that trigger upon its
 * completion.
 *
 * @author dinghuang123@gmail.com
 * @since 2018/6/11
 */
public class CompletableFutureTest {
    public static void main(String[] args) {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("dinghuang");
        future.thenAccept(System.out::println)
                .thenAccept(v -> System.out.println("done"));
    }
}
