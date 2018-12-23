package com.fanap.hibernate.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Phone extends BaseEntity{

  @Column(unique = true)
  private String number;

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }
}
