#JAVA8实用范例

*写出更简洁优美的代码*

##简介
- 自java5以来最大的版本变动
- 很大程度增强java类库
- 主要目标
    - 更高的开发效率
    - 更高代码可用性
    - 更好的利用多核和多处理器系统


##Lambda表达式
- 函数式接口:只包含一个方法的接口
- 语法：`(参数) -> 表达式` 或者 `(参数) -> { 语句; }`
- 方法引用
 - 跟Lambda表达式一样，语法： `对象引用::方法名`


**示例1**
```java
new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("我是线程");
    }
}).start();

new Thread(() -> {
    System.out.println("我是Lambda创建的线程");
}).start();
```

**示例2**
接口的匿名实现类全部可以使用Lambda表达式声明（单个方法）
```java
public interface Radio {
    void play();
}

// java8以前的实现
Radio radio = new Radio() {
    @Override
    public void play() {
        System.out.println("播放广播");
    }
};

// java8的实现
Radio radio2 = () -> System.out.println("播放广播");
```

在Stream API中会有大量使用

---
##List、Map的新的迭代API
List
```java
// 模拟数据
List<Person> persons = Lists.newArrayList(new Person("张三"), new Person("李四"));

// 以前的方式
for (Person person : persons) {
    System.out.println(person.getName());
}

// 新的方式
persons.forEach(person -> System.out.println(person.getName()));
```

Map

```java
// 模拟数据
Map<String, Person> persons = ImmutableMap.of("张三", new Person("张三", 20), "李四", new Person("李四", 22));

// 以前的方式
for (String key : persons.keySet()) {
    System.out.println(key + ":" + persons.get(key).getAge());
}

// 新的方式
persons.forEach((key, person) -> System.out.println(key + ":" + person.getAge()));
```

Set
```java
// 模拟数据
Set<Person> persons = ImmutableSet.of(new Person("张三", 20), new Person("李四", 22));

// 以前的方式
for (Person person : persons) {
    System.out.println(person.getName());
}

// 新的方式
persons.forEach(person -> System.out.println(person.getName()));
```

点开源码看下，以List为例
```java
default void forEach(Consumer<? super T> action) {
    Objects.requireNonNull(action);
    for (T t : this) {
        action.accept(t);
    }
}
```
底层还是原先的迭代方式，但是语义上更好理解了。

感兴趣的可以打开源码，看看java.util.function包下面的这些函数接口，在很多地方都有使用。

---
##Stream API
流（Stream）仅仅代表着数据流，并没有数据结构，所以他遍历完一次之后便再也无法遍历（这点在编程时候需要注意，不像Collection，遍历多少次里面都还有数据），它的来源可以是Collection、array、io等等。

###中间与终点方法
流作用是提供了一种操作大数据接口，让数据操作更容易和更快。它具有过滤、映射以及减少遍历数等方法，这些方法分两种：中间方法和终端方法，“流”抽象天生就该是持续的，中间方法永远返回的是Stream，因此如果我们要获取最终结果的话，必须使用终点操作才能收集流产生的最终结果。区分这两个方法是看他的返回值，如果是Stream则是中间方法，否则是终点方法。有点类似sql语句的语义，自行脑补，哈哈。

以下列举几个常用的方法，更多使用方法请自行查阅API文档

####Filter
从集合总过滤数据
```java
List<Person> persons = Lists.newArrayList(new Person("张三", 20), new Person("赵六", 20), new Person("李四", 22), new Person("王五", 18));

// 过滤出年龄大于18岁的数据，转换List
List<Person> filteredPersons = persons.stream().filter(person -> person.getAge() > 18).collect(Collectors.toList());
System.out.println(JSON.toJSONString(filteredPersons));

// 过滤出年龄大于18岁的数据，转换Set
Set<Person> filteredPersonSet = persons.stream().filter(person -> person.getAge() > 18).collect(Collectors.toSet());
System.out.println(JSON.toJSONString(filteredPersonSet));
```

####Map
用来转换对象，比如把集合里面的某些属性组合成一个集合
```java
List<Person> persons = Lists.newArrayList(new Person("张三", 20), new Person("赵六", 20), new Person("李四", 22), new Person("王五", 18));

List<String> names = persons.stream().map(Person::getName).collect(Collectors.toList());
System.out.println(JSON.toJSONString(names));
```
####Count
count是一个流的终点方法，用来统计
```java
List<Person> persons = Lists.newArrayList(new Person("张三", 20), new Person("赵六", 20), new Person("李四", 22), new Person("王五", 18));

long count = persons.stream().map(Person::getName).count();
```

####Collect
collect方法也是一个流的终点方法，可收集最终的结果。

```java
List<Person> persons = Lists.newArrayList(new Person("张三", 20), new Person("赵六", 20), new Person("李四", 22), new Person("王五", 18));

// 过滤出年龄大于18岁的数据，转换List
List<Person> filteredPersons = persons.stream().filter(person -> person.getAge() > 18).collect(Collectors.toList());
System.out.println(JSON.toJSONString(filteredPersons));

// 过滤出年龄大于18岁的数据，转换Set
Set<Person> filteredPersonSet = persons.stream().filter(person -> person.getAge() > 18).collect(Collectors.toSet());
        System.out.println(JSON.toJSONString(filteredPersonSet));
```
或者使用特定的实现类收集结果
```java
List<Person> filteredPersons = persons.stream().filter(person -> person.getAge() > 18).collect(Collectors.toCollection(ArrayList::new));
System.out.println(JSON.toJSONString(filteredPersons));
```

或者对结果分组
```java
List<Person> persons = Lists.newArrayList(new Person("张三", 20), new Person("赵六", 20), new Person("李四", 22), new Person("王五", 18));

// 按照年龄分组
Map<Integer, List<Person>> map = persons.stream().collect(Collectors.groupingBy(Person::getAge));
System.out.println(JSON.toJSONString(map));
```

####Find
find结合filter使用也是很常见的
```java
List<Person> persons = Lists.newArrayList(new Person("张三", 20), new Person("赵六", 20), new Person("李四", 22), new Person("王五", 18));

// 查找一个年龄大于18岁的
Optional<Person> optional = persons.stream().filter(person -> person.getAge() > 18).findFirst();
if (optional.isPresent()) {
    System.out.println(JSON.toJSONString(optional.get()));
}
```

###顺序流与并行流
每个Stream都有两种模式：顺序执行和并行执行。
顺序流
```java
List<Person> persons = Lists.newArrayList(new Person("张三"), new Person("李四"));
persons.stream().forEach(System.out::print);
```

并行流
```java
List<Person> persons = Lists.newArrayList(new Person("张三"), new Person("李四"));
persons.parallelStream().forEach(System.out::print);
```
并行流的原理是将数据拆分成多个段，然后并行执行，然后将结果合并到一起返回。

以前我们需要自己实现并行，现在使用java8就是so easy了！

注意点：
小数据量时候没必要使用并行流，比如几条数据。
建议在数据库批量操作、HTTP批量请求API时候使用并行操作。

案例：
并行修改300w数据，把数据库拖到高负载了- -!
调用百度地图API，给一些带地址的数据查询坐标，节省70%的时间

更多场景请结合业务组合使用

---
##Base64
Java一直缺少BASE64编码 API，以至于通常在项目开发中会选用第三方的API实现。但是，Java 8实现了BASE64编解码API，它包含到java.util包。

java.util.Base64工具类提供了一套静态方法获取下面三种BASE64编解码器

- Basic编码
- URL编码
- MIME编码

Basic编码是标准的BASE64编码，用于处理常规的需求：输出的内容不添加换行符，而且输出的内容由字母加数字组成。

基本用法
```java
// 编码
Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8))

// 解码
new String(Base64.getDecoder().decode(text), StandardCharsets.UTF_8)
```

编码URL
默认的编码可能会出现“/”，这个在URL里面会有特殊语义，所以使用URL编码器

```java
Base64.getUrlEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8))
```

网上搞到的性能测试

![Base64性能测试](http://static.oschina.net/uploads/space/2014/0522/142224_Ho8k_1028150.png)

参考：http://my.oschina.net/benhaile/blog/267738

---
##JVM消除永久代
Java8彻底删除了永久代，取而代之的是“元空间”

- 它是本地堆内存中的一部分
- 它可以通过-XX:MetaspaceSize和-XX:MaxMetaspaceSize来进行调整
- 当到达XX:MetaspaceSize所指定的阈值后会开始进行清理该区域
- 如果本地空间的内存用尽了会收到java.lang.OutOfMemoryError: Metadata space的错误信息。
- 和持久代相关的JVM参数-XX:PermSize及-XX:MaxPermSize将会被忽略掉。

G1垃圾回收器优化

http://ifeve.com/java-garbage-first/

---
##时间API改进
鉴于大家对joda-time使用的比较熟悉了，新的时间API自行查阅

http://ifeve.com/20-examples-of-date-and-time-api-from-java8/

##并发的增强
http://ifeve.com/java-se-8-concurrent-tool-enhance/

##参考资料
Java8特性
http://ifeve.com/java-8-features-tutorial/

Java 8学习资料汇总
http://ifeve.com/java8-learning-resources/

Stream语法详解
http://ifeve.com/stream/

http://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/