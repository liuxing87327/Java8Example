package example.java8.each;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import example.model.Person;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * EachTest
 *
 * @author ：liuxing
 * @since ：2015-08-03 01:21
 */
public class ForEachTest {

    @Test
    public void testList() {
        // 模拟数据
        List<Person> persons = Lists.newArrayList(new Person("张三"), new Person("李四"));

        // 以前的方式
        for (Person person : persons) {
            System.out.println(person.getName());
        }

        // 新的方式
        persons.forEach(person -> System.out.println(person.getName()));
        persons.forEach(person -> {
            System.out.println(person.getName());
        });

        persons.forEach(new Consumer<Person>() {
            @Override
            public void accept(Person person) {
                System.out.println(person.getName());
            }
        });
    }

    @Test
    public void testMap() {
        // 模拟数据
        Map<String, Person> persons = ImmutableMap.of("张三", new Person("张三", 20), "李四", new Person("李四", 22));

        // 以前的方式
        for (String key : persons.keySet()) {
            System.out.println(key + ":" + persons.get(key).getAge());
        }

        // 新的方式
        persons.forEach((key, person) -> System.out.println(key + ":" + person.getAge()));
    }

    @Test
    public void testSet() {
        // 模拟数据
        Set<Person> persons = ImmutableSet.of(new Person("张三", 20), new Person("李四", 22));

        // 以前的方式
        for (Person person : persons) {
            System.out.println(person.getName());
        }

        // 新的方式
        persons.forEach(person -> System.out.println(person.getName()));
    }

}
