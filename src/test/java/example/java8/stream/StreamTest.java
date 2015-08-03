package example.java8.stream;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import example.model.Person;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * StreamTest
 *
 * @author ：liuxing
 * @since ：2015-08-04 02:29
 */
public class StreamTest {

    @Test
    public void testStream() {
        List<Person> persons = Lists.newArrayList(new Person("张三"), new Person("李四"));
        persons.stream().forEach(System.out::print);
    }

    @Test
    public void testParallelStream() {
        List<Person> persons = Lists.newArrayList(new Person("张三"), new Person("李四"));
        persons.parallelStream().forEach(System.out::print);
    }

    @Test
    public void testFilter() {
        List<Person> persons = Lists.newArrayList(new Person("张三", 20), new Person("赵六", 20), new Person("李四", 22), new Person("王五", 18));

        // 过滤出年龄大于18岁的数据，转换List
        List<Person> filteredPersons = persons.stream().filter(person -> person.getAge() > 18).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(filteredPersons));

        //
        filteredPersons = persons.stream().filter(person -> person.getAge() > 18).collect(Collectors.toCollection(ArrayList::new));
        System.out.println(JSON.toJSONString(filteredPersons));

        // 过滤出年龄大于18岁的数据，转换Set
        Set<Person> filteredPersonSet = persons.stream().filter(person -> person.getAge() > 18).collect(Collectors.toSet());
        System.out.println(JSON.toJSONString(filteredPersonSet));
    }

    @Test
    public void testFind() {
        List<Person> persons = Lists.newArrayList(new Person("张三", 20), new Person("赵六", 20), new Person("李四", 22), new Person("王五", 18));

        // 查找一个年龄大于18岁的
        Optional<Person> optional = persons.stream().filter(person -> person.getAge() > 18).findFirst();
        if (optional.isPresent()) {
            System.out.println(JSON.toJSONString(optional.get()));
        }
    }

    @Test
    public void testMap() {
        List<Person> persons = Lists.newArrayList(new Person("张三", 20), new Person("赵六", 20), new Person("李四", 22), new Person("王五", 18));

        // 提取名字
        List<String> names = persons.stream().map(Person::getName).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(names));
    }

    @Test
    public void testCount() {
        List<Person> persons = Lists.newArrayList(new Person("张三", 20), new Person("赵六", 20), new Person("李四", 22), new Person("王五", 18));

        long count = persons.stream().map(Person::getName).count();

        System.out.println(count);
    }

    @Test
    public void testGroup() {
        List<Person> persons = Lists.newArrayList(new Person("张三", 20), new Person("赵六", 20), new Person("李四", 22), new Person("王五", 18));

        // 按照年龄分组
        Map<Integer, List<Person>> map = persons.stream().collect(Collectors.groupingBy(Person::getAge));
        System.out.println(JSON.toJSONString(map));
    }

}
