package com.fanap.hibernate.data.modelVO;

import com.fanap.hibernate.data.model.Course;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CourseVO {

    private long id;
    private Timestamp created;
    private Timestamp updated;

    private String name;

    private int unit;

    public CourseVO() {
    }

    public CourseVO(Course course) {
        id = course.getId();
        created = course.getCreated();
        updated = course.getUpdated();
        unit = course.getUnit();
        name = course.getName();
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
}

