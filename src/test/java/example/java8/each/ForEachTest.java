package example.java8.each;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import example.model.Person;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * EachTest
 *
 * @author ：liuxing
 * @since ：2015-08-03 01:21
 */
public class ForEachTest {

    @Test
    public void testList() {
        List<Person> persons = Lists.newArrayList(new Person("张三"), new Person("李四"));
        persons.forEach(person -> System.out.println(person.getName()));
    }

    @Test
    public void testMap() {
        Map<String, Person> persons = ImmutableMap.of("张三", new Person("张三", 20), "李四", new Person("李四", 22));

        persons.forEach((key, person) -> System.out.println(key + ":" + person.getAge()));
    }

    @Test
    public void testSet() {
        Set<Person> persons = ImmutableSet.of(new Person("张三", 20), new Person("李四", 22));

        persons.forEach(person -> System.out.println(person.getName()));
    }

}
