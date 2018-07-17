package com.example.demo.java8.misc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/13
 */
public class FileTest {

    public static void main(String[] args) throws IOException {
        //统计文件字符个数
        testLines();
        //查找文件
        testFind();
        //查找根目录下所有不为.开头的隐藏文件的文件夹和文件
        testList();
        //读取文件
        testReader();
        //写入文件
        testWriter();
        //读写文件
        testReadWriteLines();
    }

    private static void testReadWriteLines() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("res/nashorn1.js"));
        lines.add("print('foobar');");
        Files.write(Paths.get("res", "nashorn1-modified.js"), lines);
    }

    private static void testWriter() {
        Path path = Paths.get("res/output.js");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("print('Hello World');");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testReader() {
        Path path = Paths.get("res/nashorn1.js");
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            System.out.println(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testLines() {
        try (Stream<String> stream = Files.lines(Paths.get("res/nashorn1.js"))) {
            stream
                    .filter(line -> line.contains("print"))
                    .map(String::trim)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testList() {
        try (Stream<Path> stream = Files.list(Paths.get(""))) {
            String joined = stream
                    .map(String::valueOf)
                    .filter(path -> !path.startsWith("."))
                    .sorted()
                    .collect(Collectors.joining("; "));
            System.out.println("list(): " + joined);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testFind() {
        Path start = Paths.get("");
        int maxDepth = 5;
        try (Stream<Path> stream = Files.find(start, maxDepth, (path, attr) ->
                String.valueOf(path).endsWith(".js"))) {
            String joined = stream
                    .sorted()
                    .map(String::valueOf)
                    .collect(Collectors.joining("; "));
            System.out.println("find(): " + joined);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
