package com.example.demo.proxy;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/10/11
 */
public class FontProviderFromDisk implements FontProvider {
    @Override
    public Font getFont(String name) {
        Font font = new Font();
        font.setName("getFromDisk" + name);
        return font;
    }
}
