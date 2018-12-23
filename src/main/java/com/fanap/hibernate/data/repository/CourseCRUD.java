package com.fanap.hibernate.data.repository;

import com.fanap.hibernate.data.HibernateUtils;
import com.fanap.hibernate.data.model.*;
import com.fanap.hibernate.data.modelVO.CourseVO;
import com.fanap.hibernate.data.modelVO.MasterVO;
import com.fanap.hibernate.data.modelVO.StudentCourseVO;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CourseCRUD {

    public static CourseVO saveCourse(CourseVO courseVO) {

        Course course = new Course(courseVO);

        HibernateUtils.save(course);

        CourseVO courseVO1 = new CourseVO(course);

        return courseVO1;
    }

    public static List<CourseVO> getAll() {

        List<Object> courses = HibernateUtils.select("Course",null,null,null);

        List<CourseVO> courseVOS = new ArrayList<>();

        courses.forEach(course -> {

            CourseVO courseVO = new CourseVO((Course)course);
            courseVOS.add(courseVO);

        });

        return courseVOS;
    }

    public static CourseVO getCourse(long courseId) {
        List<Object> courses = HibernateUtils.select("Course","id","=",new Long(courseId));

        if(!courses.isEmpty()) {
            CourseVO courseVO = new CourseVO((Course) courses.get(0));
            return courseVO;
        }

        throw new NoSuchElementException();
    }

    public static void deleteCourse(long courseId) {
        List<Object> courses = HibernateUtils.select("Course","id","=",new Long(courseId));

        if(!courses.isEmpty()) {
            HibernateUtils.delete((Course) courses.get(0));
            return;
        }

        throw new NoSuchElementException();
    }

    public static CourseVO updateCourse(long courseId, CourseVO newCourseVO) {
        List<Object> courses = HibernateUtils.select("Course","id","=",new Long(courseId));
        CourseVO courseVO = null;

        if(!courses.isEmpty()) {

            courseVO = new CourseVO((Course) courses.get(0));

            if(newCourseVO.getUnit()> 0)
                courseVO.setUnit(newCourseVO.getUnit());

            if(newCourseVO.getName()!=null)
                courseVO.setName(newCourseVO.getName());

            Course newCourse = new Course(courseVO);

            HibernateUtils.update(newCourse);

            return courseVO;
        }

        throw new NoSuchElementException();
    }

    public static List<StudentCourseVO> getCourseStudents(long courseId) {

        List<Object> studentCourses = HibernateUtils.select("StudentCourse","course_id","=",new Long(courseId));

        if(!studentCourses.isEmpty()) {

            List<StudentCourseVO> studentCourseVOS = new ArrayList<>();

            studentCourses.forEach(studentCourse -> {

                StudentCourseVO studentCourseVO = new StudentCourseVO((StudentCourse) studentCourse);
                studentCourseVOS.add(studentCourseVO);

            });

            return studentCourseVOS;
        }

        throw new NoSuchElementException();
    }

    public static List<MasterVO> getCourseMasters(long courseId) {

        List<Object> courses = HibernateUtils.select("Course","id","=",new Long(courseId));

        if(!courses.isEmpty()) {

            Course course = (Course) courses.get(0);

            List<Master> masters = course.getMasterList();

            List<MasterVO> masterVOS = new ArrayList<>();

            masters.forEach(master -> {
                MasterVO masterVO = new MasterVO((Master) master);
                masterVOS.add(masterVO);
            });
            return masterVOS;
        }

        throw new NoSuchElementException();
    }

    public static MasterVO addCourseMasters(long courseId, long masterId) {

        List<Object> masters = HibernateUtils.select("Master","id","=",new Long(masterId));
        List<Object> courses = HibernateUtils.select("Course","id","=",new Long(courseId));

        if(!masters.isEmpty() && !courses.isEmpty()){
            ((Master)masters.get(0)).getCourseList().add((Course) courses.get(0));
            ((Course)courses.get(0)).getMasterList().add((Master) masters.get(0));

            HibernateUtils.update(masters.get(0));
            HibernateUtils.update(courses.get(0));

            return new MasterVO((Master)masters.get(0));
        }

        throw new NoSuchElementException();

    }
}
