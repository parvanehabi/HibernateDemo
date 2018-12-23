package com.fanap.hibernate.data.model;

import com.fanap.hibernate.data.modelVO.MasterVO;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;


@Entity
public class Master extends User{

  @ManyToMany(cascade= {CascadeType.MERGE,CascadeType.PERSIST})
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Course> courseList = new ArrayList<>();

  public Master() {
  }

  public Master(MasterVO masterVO) {
    setLastName(masterVO.getLastName());
    setFirstName(masterVO.getFirstName());
    setPassword(masterVO.getPassword());
    setUsername(masterVO.getUsername());
    setId(masterVO.getId());
    setCreated(masterVO.getCreated());
    setUpdated(masterVO.getUpdated());
  }

  public List<Course> getCourseList() {
    return courseList;
  }

  public void setCourseList(List<Course> courseList) {
    this.courseList = courseList;
  }
}
