package com.karonda.springboot2mongodb.config;

import java.util.ArrayList;
import java.util.List;

public class MajorConfig {

    private static MajorConfig computer;
    private static MajorConfig math;

    static {
        computer = new MajorConfig();
        computer.setName("计算机");

        List<String> courseList = new ArrayList<>();
        courseList.add("高等数学");
        courseList.add("数据结构");
        computer.setCourseList(courseList);

        math = new MajorConfig();
        math.setName("数学");
        courseList = new ArrayList<>();
        courseList.add("高等数学");
        math.setCourseList(courseList);


    }

    private String name;
    private List<String> courseList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<String> courseList) {
        this.courseList = courseList;
    }

    public static MajorConfig getComputer() {
        return computer;
    }

    public static MajorConfig getMath() {
        return math;
    }

    public static void setComputer(MajorConfig computer) {
        MajorConfig.computer = computer;
    }

    public static void setMath(MajorConfig math) {
        MajorConfig.math = math;
    }
}
