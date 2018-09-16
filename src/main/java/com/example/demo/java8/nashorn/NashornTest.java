package com.example.demo.java8.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 使用nashorn从java调用javascript函数。
 *
 * @author dinghuang123@gmail.com
 * @since 2018/9/13
 */
public class NashornTest {

    public static void main(String[] args) throws ScriptException, NoSuchMethodException {
        //执行js函数
//        test1();
        //js调用java函数
//        test2();
        //直接运行js函数
//        test3();
//        test4();
//        test5();
//        test6();
//        test7();
//        test8();
//        test9();
//        test10();
        test11();

    }

    private static void test11() throws ScriptException {
        NashornScriptEngine engine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");

        engine.eval("var obj = { foo: 23 };");

        ScriptContext defaultContext = engine.getContext();
        Bindings defaultBindings = defaultContext.getBindings(ScriptContext.ENGINE_SCOPE);

        SimpleScriptContext context1 = new SimpleScriptContext();
        context1.setBindings(defaultBindings, ScriptContext.ENGINE_SCOPE);

        SimpleScriptContext context2 = new SimpleScriptContext();
        context2.getBindings(ScriptContext.ENGINE_SCOPE).put("obj", defaultBindings.get("obj"));

        engine.eval("obj.foo = 44;", context1);
        engine.eval("print(obj.foo);", context1);
        engine.eval("print(obj.foo);", context2);
    }

    private static void test10() throws ScriptException, NoSuchMethodException {
        NashornScriptEngine engine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval("load('res/nashorn10.js')");

        long t0 = System.nanoTime();

        for (int i = 0; i < 100000; i++) {
            engine.invokeFunction("testPerf");
        }

        long took = System.nanoTime() - t0;
        System.out.format("Elapsed time: %d ms", TimeUnit.NANOSECONDS.toMillis(took));
    }

    private static void test9() throws ScriptException, NoSuchMethodException {
        NashornScriptEngine engine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval("load('res/nashorn9.js')");

        long t0 = System.nanoTime();

        double result = 0;
        for (int i = 0; i < 1000; i++) {
            double num = (double) engine.invokeFunction("testPerf");
            result += num;
        }

        System.out.println(result > 0);

        long took = System.nanoTime() - t0;
        System.out.format("Elapsed time: %d ms", TimeUnit.NANOSECONDS.toMillis(took));
    }


    private static void test8() throws ScriptException, NoSuchMethodException {
        NashornScriptEngine engine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval("load('res/nashorn8.js')");
        // [object global]
        engine.invokeFunction("evaluate1");
        // [object Object]
        engine.invokeFunction("evaluate2");
        // Foobar
        engine.invokeFunction("evaluate3", "Foobar");
        // [object global] <- ???????
        engine.invokeFunction("evaluate3", new com.example.demo.java8.Person("John", "Doe"));
    }

    private static void test7() throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval("function foo(predicate, obj) { return !!(eval(predicate)); };");

        Invocable invocable = (Invocable) engine;

        Person person = new Person();
        person.setName("Hans");

        String predicate = "obj.getLengthOfName() >= 4";
        Object result = invocable.invokeFunction("foo", predicate, person);
        System.out.println(result);
    }

    private static void test6() throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval("load('res/nashorn6.js')");

        Invocable invocable = (Invocable) engine;

        Product product = new Product();
        product.setName("Rubber");
        product.setPrice(1.99);
        product.setStock(1337);

        ScriptObjectMirror result = (ScriptObjectMirror)
                invocable.invokeFunction("calculate", product);
        System.out.println(result.get("name") + ": " + result.get("valueOfGoods"));
    }

    public static void getProduct(ScriptObjectMirror result) {
        System.out.println(result.get("name") + ": " + result.get("valueOfGoods"));
    }

    private static void test1() throws FileNotFoundException, ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(new FileReader("res/nashorn1.js"));
        Invocable invocable = (Invocable) engine;
        Object result = invocable.invokeFunction("fun1", "Peter Parker");
        System.out.println(result);
        System.out.println(result.getClass());
        invocable.invokeFunction("fun2", new Date());
        invocable.invokeFunction("fun2", LocalDateTime.now());
        invocable.invokeFunction("fun2", new Person());
    }

    private static void test2() throws FileNotFoundException, ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(new FileReader("res/nashorn2.js"));
    }

    private static void test3() throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval("load('res/nashorn3.js')");
    }

    private static void test4() throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval("loadWithNewGlobal('res/nashorn4.js')");
    }

    private static void test5() throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval("load('res/nashorn5.js')");
        Invocable invocable = (Invocable) engine;
        Product product = new Product();
        product.setName("Rubber");
        product.setPrice(1.99);
        product.setStock(1037);
        Object result = invocable.invokeFunction("getValueOfGoods", product);
        System.out.println(result);
    }

    public static String fun(String name) {
        System.out.format("Hi there from Java, %s", name);
        return "greetings from java";
    }

    public static void fun2(Object object) {
        System.out.println(object.getClass());
    }

    public static void fun3(ScriptObjectMirror mirror) {
        System.out.println(mirror.getClassName() + ": " + Arrays.toString(mirror.getOwnKeys(true)));
    }

    public static void fun4(ScriptObjectMirror person) {
        System.out.println("Full Name is: " + person.callMember("getFullName"));
    }

    public static class Person {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getLengthOfName() {
            return name.length();
        }
    }

}
