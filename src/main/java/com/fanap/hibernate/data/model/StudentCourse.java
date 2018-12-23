package com.fanap.hibernate.data.model;

import com.fanap.hibernate.data.HibernateUtils;
import com.fanap.hibernate.data.modelVO.StudentCourseVO;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
public class StudentCourse extends BaseEntity{

@ManyToOne
@LazyCollection(LazyCollectionOption.FALSE)
  private Student student;

@ManyToOne
@LazyCollection(LazyCollectionOption.FALSE)
  private Course course;

  private double score;

  public StudentCourse() {
  }

  public StudentCourse(StudentCourseVO studentCourseVO) {
    setId(studentCourseVO.getId());
    setCreated(studentCourseVO.getCreated());
    setUpdated(studentCourseVO.getUpdated());
    score = studentCourseVO.getScore();
    List<Object> students = HibernateUtils.select("Student","id","=",new Long(studentCourseVO.getStudentVO().getId()));
    if(!students.isEmpty())
      student = (Student) students.get(0);
    List<Object> courses = HibernateUtils.select("Course","id","=",new Long(studentCourseVO.getCourseVO().getId()));
    if(!courses.isEmpty())
      course = (Course) courses.get(0);
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }
}
