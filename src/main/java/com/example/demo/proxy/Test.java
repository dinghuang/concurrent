package com.example.demo.proxy;

/**
 * 比较之下，Java API提供的动态代理需要面向接口，产生代理对象，因此真实主题实现类必须实现了接口才可以。而CGLIB不需要面向接口，可以代理简单类，
 * 但由于动态代理对象是继承真实主题实现类的，因此要求真实主题实现类不能是final的。
 *
 * @author dinghuang123@gmail.com
 * @since 2018/10/11
 */
public class Test {

    public static void main(String[] args) {
//        //静态代理
//        FontProvider fontProvider = ProviderFactory.getFontProvider();
//        Font font = fontProvider.getFont("微软雅黑");
//        System.out.println(font.toString());
//        //静态代理增加缓存功能
//        Font font2 = fontProvider.getFont("微软雅黑");
//        System.out.println(font2.toString());
        //java API实现的动态代理
//        JavaDynProxy proxy = new JavaDynProxy();
//        Hello hello = (Hello)proxy.getProxyInstance(new HelloImpl());
//        String s = hello.sayHello("Leon");
//        System.out.println(s);
//
//        UserDao userDao = (UserDao) proxy.getProxyInstance(new UserDaoImpl());
//        userDao.login("Leon", "1234");
//        System.out.println(userDao.getClass().getName());
        //采用cglib实现
//        CglibProxy proxy = new CglibProxy();
//        Hello hello = (Hello) proxy.getProxyInstance(new HelloImpl());
//        System.out.println(hello.sayHello("Leon"));
//        UserDaoImpl userDao = (UserDaoImpl) proxy.getProxyInstance(new UserDaoImpl());
//        userDao.login("Leon", "1234");
//        //看动态代理实例的父类
//        System.out.println(userDao.getClass().getSuperclass());
        //异步同步回调
        People people = new People();
        CallbackTest callback = msg -> System.out.println("打印机告诉我的消息是 ---> " + msg);
        System.out.println("需要打印的内容是 ---> " + "打印一份简历");
//        people.goToPrintSyn(callback, "打印一份简历");
        people.goToPrintASyn(callback, "打印一份简历");
        System.out.println("我在等待 打印机 给我反馈");
    }
}
