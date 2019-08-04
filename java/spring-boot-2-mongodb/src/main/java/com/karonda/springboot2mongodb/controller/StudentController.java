package com.karonda.springboot2mongodb.controller;

import com.google.common.collect.Maps;
import com.karonda.springboot2mongodb.config.MajorConfig;
import com.karonda.springboot2mongodb.entity.Course;
import com.karonda.springboot2mongodb.entity.Student;
import com.karonda.springboot2mongodb.util.ReflectUtil;
import com.mongodb.BasicDBObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/student")
@Api(tags = "学生接口")
public class StudentController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value="根据专业获取列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "major", value = "专业", paramType = "query", required = true),
    })
    public Object list(String major){

        MajorConfig majorConfig = getMajorConfig(major);

        Query query = new Query(Criteria.where("major").is(major));
        List<Student> studentList = mongoTemplate.find(query, Student.class);

        List<Object> result = new ArrayList<>();

        for(Student student:studentList){

            Map<String,Object> properties = Maps.newHashMap();
            for(String name : majorConfig.getCourseList()){
                properties.put(name,null);
                for(Course course : student.getCourseList()){
                    if(name.equals(course.getName())){
                        properties.put(name,course.getScore());
                        break;
                    }
                }
            }

            result.add(ReflectUtil.getTarget(student,properties));
        }

        return result;
    }

    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    @ApiOperation(value="添加课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "major", value = "专业", paramType = "query", required = true),
            @ApiImplicitParam(name = "name", value = "课程名称", paramType = "query", required = true),
            @ApiImplicitParam(name = "updateDatabase", value = "是否更新数据库", paramType = "query", required = true),
    })
    public Object add(String major, String name, Boolean updateDatabase){

        MajorConfig majorConfig = getMajorConfig(major);

        List<String> courseList = majorConfig.getCourseList();

        if(!courseList.contains(name))
        {
            courseList.add(name);
            majorConfig.setCourseList(courseList);
        }

        if(updateDatabase){

            Random random = new Random();

            Course course = new Course();
            course.setName(name);
            course.setScore(random.nextFloat() * 100);

            Update update = new Update();
            update.addToSet("courseList", course);
            Query query = Query.query(Criteria.where("major").is(majorConfig.getName()));
            mongoTemplate.updateMulti(query, update, Student.class);

        }

        return majorConfig;
    }

    @RequestMapping(value = "/del", method = RequestMethod.PUT)
    @ApiOperation(value="删除课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "major", value = "专业", paramType = "query", required = true),
            @ApiImplicitParam(name = "name", value = "课程名称", paramType = "query", required = true),
            @ApiImplicitParam(name = "updateDatabase", value = "是否更新数据库", paramType = "query", required = true),
    })
    public Object del(String major, String name, Boolean updateDatabase){

        MajorConfig majorConfig = getMajorConfig(major);

        List<String> courseList = majorConfig.getCourseList();

        if(courseList.contains(name)){
            courseList.remove(name);
            majorConfig.setCourseList(courseList);
        }


        if(updateDatabase){
            Update update = new Update();
            update.pull("courseList", new BasicDBObject("name", name));
            Query query = Query.query(Criteria.where("major").is(majorConfig.getName()));
            mongoTemplate.updateMulti(query,update,Student.class);
        }

        return majorConfig;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ApiOperation(value="修改某学生的某课程分数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", paramType = "query", required = true),
            @ApiImplicitParam(name = "courseName", value = "课程", paramType = "query", required = true),
            @ApiImplicitParam(name = "score", value = "分数", paramType = "query", required = true),
    })
    public Object update(String name, String courseName, Float score){

        Query query = Query.query(Criteria.where("name").is(name).and("courseList.name").is(courseName));

        Update update = new Update();
        update.set("courseList.$.score", score);

        mongoTemplate.updateFirst(query, update, Student.class);

        return null;
    }

    @RequestMapping(value = "/avg", method = RequestMethod.GET)
    @ApiOperation(value="根据专业计算各科平均分")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "major", value = "专业", paramType = "query", required = true),
    })
    public Object avg(String major){

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("courseList"),
                Aggregation.match(Criteria.where("major").is(major)),
                Aggregation.group("courseList.name").avg("courseList.score").as("avg")
        ); // avg 可以替换成 sum, max, min 分别求各科总分、最高分、最低分

        AggregationResults<BasicDBObject> aggregationResults = mongoTemplate.aggregate(aggregation, Student.class, BasicDBObject.class);

        List<BasicDBObject> result = new ArrayList<>();
        for(Iterator<BasicDBObject> iterator = aggregationResults.iterator(); iterator.hasNext();){
            result.add(iterator.next());
        }

        return result;
    }

    @RequestMapping(value = "/sum", method = RequestMethod.GET)
    @ApiOperation(value="个人总分")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", paramType = "query", required = true),
    })
    public Object sum(String name){

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("courseList"),
                Aggregation.match(Criteria.where("name").is(name)),
                Aggregation.group("name").sum("courseList.score").as("sum")
        );

        AggregationResults<BasicDBObject> aggregationResults = mongoTemplate.aggregate(aggregation, Student.class, BasicDBObject.class);

        List<BasicDBObject> result = new ArrayList<>();
        for(Iterator<BasicDBObject> iterator = aggregationResults.iterator(); iterator.hasNext();){
            result.add(iterator.next());
        }

        return result;
    }


    private MajorConfig getMajorConfig(String major){
        MajorConfig majorConfig = MajorConfig.getComputer();
        if("数学".equals(major)){
            majorConfig = MajorConfig.getMath();
        }

        return majorConfig;
    }

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    @ApiOperation(value="初始化测试数据")
    public void init(){

        MajorConfig majorComputer = MajorConfig.getComputer();
        MajorConfig majorMath = MajorConfig.getMath();

        Random random = new Random();

        LocalDateTime[] localDateTimeList = new LocalDateTime[]{
          LocalDateTime.of(2018, 1, 20, 8, 0),
                LocalDateTime.of(2019, 10, 1, 7, 10, 50)
        };

        String[] computerStudentList = new String[]{"张三", "李四"};
        String[] mathStudentList = new String[]{"王五", "赵六"};



        for(int i = 0;i<localDateTimeList.length; i++){

            Student student = null;
            List<Course> courseList = null;

            student = new Student();
            student.setName(computerStudentList[i]);
            student.setMajor(majorComputer.getName());
            student.setCreateTime(localDateTimeList[i]);

            courseList = new ArrayList<>();
            for(String name : majorComputer.getCourseList()){

                Course course = new Course();
                course.setName(name);
                course.setScore(random.nextFloat() * 100);
                courseList.add(course);
            }
            student.setCourseList(courseList);

            mongoTemplate.save(student);

            student = new Student();
            student.setName(mathStudentList[i]);
            student.setMajor(majorMath.getName());
            student.setCreateTime(localDateTimeList[i]);

            courseList = new ArrayList<>();
            for(String name : majorMath.getCourseList()){

                Course course = new Course();
                course.setName(name);
                course.setScore(random.nextFloat() * 100);
                courseList.add(course);
            }
            student.setCourseList(courseList);

            mongoTemplate.save(student);
        }

    }
}
