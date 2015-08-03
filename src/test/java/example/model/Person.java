package example.model;

import javax.annotation.Nonnull;

/**
 * 数据模型
 *
 * @author ：liuxing
 * @since ：2015-08-03 01:01
 */
public class Person {

    /**
     * 名字
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 年龄
     */
    private int age;
    /**
     * 工资
     */
    private int wage;

    public Person() {
    }

    public Person(@Nonnull String name) {
        this.name = name;
    }

    public Person(@Nonnull String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWage() {
        return wage;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }
}
