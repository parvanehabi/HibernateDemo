package com.fanap.hibernate;

import javax.ws.rs.ApplicationPath;

import com.fanap.hibernate.controller.Filter.JWTTokenNeededFilter;
import org.glassfish.jersey.server.ResourceConfig;


@ApplicationPath("")
public class Application extends ResourceConfig {

  public Application(){
      packages("com.fanap.hibernate.controller");
      register(JWTTokenNeededFilter.class);
  }

  public static void main(String[] args) {
  }

}
