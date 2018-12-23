package com.fanap.hibernate.data.modelVO;

import com.fanap.hibernate.data.model.StudentCourse;

import java.sql.Timestamp;

public class StudentCourseVO {

    private long id;
    private Timestamp created;
    private Timestamp updated;

    private double score;

    private StudentVO studentVO;

    private CourseVO courseVO ;

    public StudentCourseVO() {
    }

    public StudentCourseVO(StudentCourse studentCourse) {
        id = studentCourse.getId();
        created = studentCourse.getCreated();
        updated = studentCourse.getUpdated();
        score = studentCourse.getScore();
        courseVO = new CourseVO(studentCourse.getCourse());
        studentVO = new StudentVO(studentCourse.getStudent());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public StudentVO getStudentVO() {
        return studentVO;
    }

    public void setStudentVO(StudentVO studentVO) {
        this.studentVO = studentVO;
    }

    public CourseVO getCourseVO() {
        return courseVO;
    }

    public void setCourseVO(CourseVO courseVO) {
        this.courseVO = courseVO;
    }
}
