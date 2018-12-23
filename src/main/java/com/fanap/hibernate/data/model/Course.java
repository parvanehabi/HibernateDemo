package com.fanap.hibernate.data.model;

import com.fanap.hibernate.data.modelVO.CourseVO;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;


@Entity
public class Course extends BaseEntity{

  private String name;

  private int unit;

  @OneToMany(cascade= CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<StudentCourse> studentCourses = new ArrayList<StudentCourse>();

  @ManyToMany(cascade= {CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "courseList")
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Master> masterList = new ArrayList<Master>(  );

  public Course() {
  }

  public Course(CourseVO courseVO) {
    name = courseVO.getName();
    unit = courseVO.getUnit();
    setId(courseVO.getId());
    setCreated(courseVO.getCreated());
    setUpdated(courseVO.getUpdated());
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getUnit() {
    return unit;
  }

  public void setUnit(int unit) {
    this.unit = unit;
  }

  public List<StudentCourse> getStudentCourses() {
    return studentCourses;
  }

  public void setStudentCourses(List<StudentCourse> studentCourses) {
    this.studentCourses = studentCourses;
  }

  public List<Master> getMasterList() {
    return masterList;
  }

  public void setMasterList(List<Master> masterList) {
    this.masterList = masterList;
  }
}
