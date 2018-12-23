package com.fanap.hibernate.data.repository;

import com.fanap.hibernate.data.HibernateUtils;
import com.fanap.hibernate.data.model.*;
import com.fanap.hibernate.data.modelVO.MasterVO;
import com.fanap.hibernate.data.modelVO.StudentCourseVO;
import com.fanap.hibernate.data.modelVO.StudentVO;

import java.io.*;
import java.util.*;


public class StudentCRUD {

    public static StudentVO saveStudent(StudentVO studentVO) {

        Student student = new Student(studentVO);

        HibernateUtils.save(student);

        StudentVO studentVO1 = new StudentVO(student);

        return studentVO1;
    }

    public static List<StudentVO> getAll() {

        List<Object> students = HibernateUtils.select("Student",null,null,null);

        List<StudentVO> studentVOS = new ArrayList<>();

        students.forEach(std -> {

            StudentVO studentVO = new StudentVO((Student)std);
            studentVOS.add(studentVO);

        });

        return studentVOS;
    }

    public static StudentVO getStudent(long studentId) {
        List<Object> students = HibernateUtils.select("Student","id","=",new Long(studentId));

        if(!students.isEmpty()) {
            StudentVO studentVO = new StudentVO((Student) students.get(0));
            return studentVO;
        }

        throw new NoSuchElementException();
    }

    public static void deleteStudent(long studentId) {
        List<Object> students = HibernateUtils.select("Student","id","=",new Long(studentId));

        if(!students.isEmpty()) {
            HibernateUtils.delete((Student) students.get(0));
            return;
        }

        throw new NoSuchElementException();
    }

    public static StudentVO updateStudent(long studentId, StudentVO newStudentVO) {
        List<Object> students = HibernateUtils.select("Student","id","=",new Long(studentId));
        StudentVO studentVO = null;

        if(!students.isEmpty()) {

            studentVO = new StudentVO((Student) students.get(0));

            if(newStudentVO.getPhones()!=null && newStudentVO.getPhones().size()>0)
                studentVO.setPhones(newStudentVO.getPhones());

            if(newStudentVO.getFirstName()!=null)
                studentVO.setFirstName(newStudentVO.getFirstName());

            if(newStudentVO.getLastName()!=null)
                studentVO.setLastName(newStudentVO.getLastName());

            if(newStudentVO.getPassword()!=null)
                studentVO.setPassword(newStudentVO.getPassword());

            if(newStudentVO.getUsername()!=null)
                studentVO.setUsername(newStudentVO.getUsername());

            if(newStudentVO.getImage()!=null)
                studentVO.setImage(newStudentVO.getImage());

            Student newStudent = new Student(studentVO);
            newStudent.setPhoneList(((Student) students.get(0)).getPhoneList());
            HibernateUtils.update(newStudent);
            return studentVO;

        }

        throw new NoSuchElementException();
    }

    public static List<StudentCourseVO> getStudentCourses(long studentId) {

        List<Object> studentCourses = HibernateUtils.select("StudentCourse","student_id","=",new Long(studentId));

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

    public static StudentCourseVO addStudentCourse(long studentId ,long courseId) {

        List<Object> students = HibernateUtils.select("Student","id","=",new Long(studentId));
        List<Object> courses = HibernateUtils.select("Course","id","=",new Long(courseId));

        if(!students.isEmpty() && !courses.isEmpty()) {
            Student student = (Student) students.get(0);
            Course course = (Course) courses.get(0);

            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setStudent(student);
            studentCourse.setCourse(course);
            studentCourse.setScore(0);

            HibernateUtils.save(studentCourse);

            course.getStudentCourses().add(studentCourse);
            student.getCourseList().add(studentCourse);

            HibernateUtils.update(student);
            HibernateUtils.update(course);

            StudentCourseVO studentCourseVO = new StudentCourseVO(studentCourse);

            return studentCourseVO;
        }

        throw new NoSuchElementException();
    }

    public static List<MasterVO> getStudentMasters(long studentId) {

        List<Object> studentCourses = HibernateUtils.select("StudentCourse","student_id","=",new Long(studentId));

        if(!studentCourses.isEmpty()) {

            Set<Master> masters = new HashSet<>();

            studentCourses.forEach(studentCourse -> {

                masters.addAll(((StudentCourse)studentCourse).getCourse().getMasterList());

            });

            List<MasterVO> mastersList = new ArrayList<MasterVO>();

            masters.forEach(master -> {
                mastersList.add(new MasterVO(master));
            });

            return mastersList;
        }

        throw new NoSuchElementException();
    }

    public static void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) throws IOException {

        try {

            int read = 0;
            byte[] bytes = new byte[1024];

            OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();

        } catch (IOException e) {
            throw e;
        }

    }
}
