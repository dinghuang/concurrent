package com.example.demo.proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/10/11
 */
public class CachedFontProvider implements FontProvider {

    private FontProvider fontProvider;

    private Map<String, Font> cached = new HashMap<>();

    public CachedFontProvider(FontProvider fontProvider) {
        this.fontProvider = fontProvider;
    }

    @Override
    public Font getFont(String name) {
        Font font = cached.get(name);
        if (font == null) {
            font = fontProvider.getFont(name);
            cached.put(name, font);
        }
        return font;
    }
}
