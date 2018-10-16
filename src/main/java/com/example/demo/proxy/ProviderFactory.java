package com.example.demo.proxy;

import java.lang.reflect.Proxy;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/10/11
 */
public abstract class ProviderFactory {
    public static FontProvider getFontProvider() {
        //静态代理
//        return new CachedFontProvider(new FontProviderFromDisk());
        //动态代理
        Class<FontProvider> targetClass = FontProvider.class;
        return (FontProvider) Proxy.newProxyInstance(targetClass.getClassLoader(),
                new Class[] { targetClass },
                new CachedProviderHandler(new FontProviderFromDisk()));
    }
}