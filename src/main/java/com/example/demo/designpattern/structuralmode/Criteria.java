package com.example.demo.designpattern.structuralmode;

import java.util.List;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public interface Criteria {

    public List<Person> meetCriteria(List<Person> persons);
}
