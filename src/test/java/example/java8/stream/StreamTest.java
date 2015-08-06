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

    /**
     * 测试顺序流
     */
    @Test
    public void testStream() {
        List<Person> persons = Lists.newArrayList(new Person("张三"), new Person("李四"));
        persons.stream().forEach(System.out::print);
    }

    /**
     * 测试并行流
     */
    @Test
    public void testParallelStream() {
        List<Person> persons = Lists.newArrayList(new Person("张三"), new Person("李四"));
        persons.parallelStream().forEach(System.out::print);
    }

    /**
     * 测试筛选
     */
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

    /**
     * 测试查找
     */
    @Test
    public void testFind() {
        List<Person> persons = Lists.newArrayList(new Person("张三", 20), new Person("赵六", 20), new Person("李四", 22), new Person("王五", 18));

        // 查找一个年龄大于18岁的
        Optional<Person> optional = persons.stream().filter(person -> person.getAge() > 18).findFirst();
        if (optional.isPresent()) {
            System.out.println(JSON.toJSONString(optional.get()));
        }
    }

    /**
     * 测试提取
     */
    @Test
    public void testMap() {
        List<Person> persons = Lists.newArrayList(new Person("张三", 20), new Person("赵六", 20), new Person("李四", 22), new Person("王五", 18));

        // 提取名字
        List<String> names = persons.stream().map(Person::getName).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(names));
    }

    /**
     * 测试统计
     */
    @Test
    public void testCount() {
        List<Person> persons = Lists.newArrayList(new Person("张三", 20), new Person("赵六", 20), new Person("李四", 22), new Person("王五", 18));

        long count = persons.stream().map(Person::getName).count();

        System.out.println(count);
    }

    /**
     * 测试分组
     */
    @Test
    public void testGroup() {
        List<Person> persons = Lists.newArrayList(new Person("张三", 20, "男"), new Person("赵六", 20, "男"), new Person("李四", 22, "男"), new Person("王五", 18, "女"));

        // 按照年龄分组
        Map<Integer, List<Person>> groupMap = persons.stream().collect(Collectors.groupingBy(Person::getAge));
        System.out.println(JSON.toJSONString(groupMap));

        // 按年龄统计名字
        Map<Integer, List<String>> groupName = persons.stream().collect(Collectors.groupingBy(Person::getAge, Collectors.mapping(Person::getName, Collectors.toList())));
        System.out.println(JSON.toJSONString(groupName));

        // 按性别求年龄总和
        Map<String, Integer> groupAgeCount = persons.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.reducing(0, Person::getAge, Integer::sum)));
        System.out.println(JSON.toJSONString(groupAgeCount));

        // 统计各年龄的数量
        Map<Integer, Integer> groupCount = persons.stream().collect(Collectors.groupingBy(Person::getAge, Collectors.summingInt(p -> 1)));
        System.out.println(JSON.toJSONString(groupCount));
    }

    /**
     * 测试管道特性
     */
    @Test
    public void testPipelining() {
        List<Person> persons = Lists.newArrayList(new Person("张三", 20, "男"), new Person("赵六", 20, "男"), new Person("李四", 22, "男"), new Person("王五", 18, "女"));

        persons.stream()
                .filter(p -> {
                    System.out.println("filter=>" + p);
                    return true;
                })
                .map(p -> {
                    System.out.println("map=>" + p.getName());
                    return p.getName();
                })
                .forEach(p -> System.out.println("结果=>" + p));
    }

}
