package com.fanap.hibernate.data.repository;

import com.fanap.hibernate.data.HibernateUtils;
import com.fanap.hibernate.data.model.*;
import com.fanap.hibernate.data.model.Master;
import com.fanap.hibernate.data.modelVO.CourseVO;
import com.fanap.hibernate.data.modelVO.MasterVO;
import com.fanap.hibernate.data.modelVO.StudentCourseVO;

import java.util.*;

public class MasterCRUD {

    public static MasterVO saveMaster(MasterVO masterVO) {

        Master master = new Master(masterVO);

        HibernateUtils.save(master);

        MasterVO masterVO1 = new MasterVO(master);

        return masterVO1;
    }

    public static List<MasterVO> getAll() {

        List<Object> masters = HibernateUtils.select("Master",null,null,null);

        List<MasterVO> masterVOS = new ArrayList<>();

        masters.forEach(master -> {

            MasterVO masterVO = new MasterVO((Master)master);
            masterVOS.add(masterVO);

        });

        return masterVOS;
    }

    public static MasterVO getMaster(long masterId) {
        List<Object> masters = HibernateUtils.select("Master", "id", "=", new Long(masterId));

        if (!masters.isEmpty()) {
            MasterVO masterVO = new MasterVO((Master) masters.get(0));
            return masterVO;
        }

        throw new NoSuchElementException();
    }

    public static void deleteMaster(long masterId) {
        List<Object> masters = HibernateUtils.select("Master","id","=",new Long(masterId));

        if(!masters.isEmpty()) {
            HibernateUtils.delete((Master) masters.get(0));
            return;
        }

        throw new NoSuchElementException();
    }

    public static MasterVO updateMaster(long masterId, MasterVO newMasterVO) {
        List<Object> masters = HibernateUtils.select("Master","id","=",new Long(masterId));
        MasterVO masterVO = null;

        if(!masters.isEmpty()) {

            masterVO = new MasterVO((Master) masters.get(0));

            if (newMasterVO.getFirstName() != null)
                masterVO.setFirstName(newMasterVO.getFirstName());

            if (newMasterVO.getLastName() != null)
                masterVO.setLastName(newMasterVO.getLastName());

            if (newMasterVO.getPassword() != null)
                masterVO.setPassword(newMasterVO.getPassword());

            if (newMasterVO.getUsername() != null)
                masterVO.setUsername(newMasterVO.getUsername());

            Master newMaster = new Master(masterVO);
            HibernateUtils.update(newMaster);
            return masterVO;

        }

        throw new NoSuchElementException();
    }

    public static List<CourseVO> getMasterCourses(long masterId) {

        List<Object> masters = HibernateUtils.select("Master","id","=",new Long(masterId));

        if(!masters.isEmpty()){

            List<Course> courses = ((Master)masters.get(0)).getCourseList();

            List<CourseVO> courseVOS = new ArrayList<>();

            courses.forEach(course -> {
                courseVOS.add(new CourseVO(course));
            });

            return courseVOS;

        }
        throw new NoSuchElementException();
    }

    public static List<StudentCourseVO> getMasterStudents(long masterId) {

        List<Object> masters = HibernateUtils.select("Master","id","=",new Long(masterId));

        if(!masters.isEmpty()) {

            List<StudentCourse> studentCourses  = new ArrayList<>();

            List<StudentCourseVO> studentCourseVOS = new ArrayList<>();

            Master master = (Master) masters.get(0);

            master.getCourseList().forEach(course -> {
                studentCourses.addAll(course.getStudentCourses());
            });

            studentCourses.forEach(studentCourse -> {
                studentCourseVOS.add(new StudentCourseVO((StudentCourse) studentCourse));
            });
            return studentCourseVOS;
        }
        throw new NoSuchElementException();
    }

    public static StudentCourseVO addScore(long studentCourseId, double score){

        List<Object> studentCourses = HibernateUtils.select("StudentCourse","id","=",new Long(studentCourseId));

        if(!studentCourses.isEmpty()){

            StudentCourse studentCourse = (StudentCourse) studentCourses.get(0);
            studentCourse.setScore(score);
            HibernateUtils.update(studentCourse);
            return new StudentCourseVO(studentCourse);

        }

        throw new NoSuchElementException();
    }
}
