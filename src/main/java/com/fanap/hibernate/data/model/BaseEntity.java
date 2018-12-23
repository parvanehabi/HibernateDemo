package com.fanap.hibernate.data.model;

import java.sql.Timestamp;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public class BaseEntity {

  @Id
  @GeneratedValue
  private long id;

  @CreationTimestamp
  @Column(updatable = false)
  private Timestamp created;

  @UpdateTimestamp
  private Timestamp updated;

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
}
