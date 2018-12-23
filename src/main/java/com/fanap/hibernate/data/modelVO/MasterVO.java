package com.fanap.hibernate.data.modelVO;

import com.fanap.hibernate.data.model.Master;

import java.sql.Timestamp;

public class MasterVO {

    private long id;
    private Timestamp created;
    private Timestamp updated;

    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public MasterVO() {
    }

    public MasterVO(Master master) {
        id = master.getId();
        created = master.getCreated();
        updated = master.getUpdated();
        username = master.getUsername();
        password = master.getPassword();
        firstName = master.getFirstName();
        lastName = master.getLastName();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}