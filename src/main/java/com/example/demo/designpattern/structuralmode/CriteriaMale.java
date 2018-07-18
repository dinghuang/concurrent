package com.example.demo.designpattern.structuralmode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/7/18
 */
public class CriteriaMale implements Criteria {

    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        return persons.stream().filter(person -> person.getGender()
                .equalsIgnoreCase("MALE")).collect(Collectors.toList());
    }
}
