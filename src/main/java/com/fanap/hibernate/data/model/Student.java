package com.fanap.hibernate.data.model;

import com.fanap.hibernate.data.modelVO.StudentVO;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Student extends User{

  private String image;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Phone> phoneList = new ArrayList<Phone>();

  @OneToMany(cascade= CascadeType.ALL, orphanRemoval = true, mappedBy = "student")
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<StudentCourse> courseList = new ArrayList<>();

  public Student() {
  }

  public Student(StudentVO studentVO) {
      studentVO.getPhones().forEach(phone ->{
        Phone newPhone = new Phone();
        newPhone.setNumber(phone);
        phoneList.add(newPhone);
      });
      try {
          BeanUtils.copyProperties(this, studentVO);
      } catch (IllegalAccessException | InvocationTargetException e) {
          e.printStackTrace();
      }
  }

  public List<Phone> getPhoneList() {
    return phoneList;
  }

  public void setPhoneList(List<Phone> phoneList) {
    this.phoneList = phoneList;
  }

  public List<StudentCourse> getCourseList() {
    return courseList;
  }

  public void setCourseList(List<StudentCourse> courseList) {
    this.courseList = courseList;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
