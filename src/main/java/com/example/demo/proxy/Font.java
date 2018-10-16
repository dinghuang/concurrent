package com.example.demo.proxy;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/10/11
 */
public class Font {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .toString();
    }
}
