package com.fanap.hibernate.data.model;

import javax.persistence.Entity;

@Entity
public class Admin  extends User{

    private String Email;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}


