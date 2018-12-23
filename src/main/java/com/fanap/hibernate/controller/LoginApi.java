package com.fanap.hibernate.controller;

import com.fanap.hibernate.data.HibernateUtils;
import com.fanap.hibernate.data.model.Admin;
import com.fanap.hibernate.data.model.Master;
import com.fanap.hibernate.data.model.Student;
import com.fanap.hibernate.data.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;


@Path("/users")
@Transactional
public class LoginApi {

    @POST
    @Path("/login")
    @Consumes(APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("username") String username, @FormParam("password") String password) {

        try {

            User user = authenticate(username, password);
            String role = "";
            if(user instanceof Admin){
                role = "admin";
            }else if(user instanceof Student){
                role = "student";
            }else if(user instanceof Master){
                role = "master";
            }

            String token = issueToken(user.getId(), role);

            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();

        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }

    private String issueToken(long id, String role) throws UnsupportedEncodingException {

        String jwt = Jwts.builder()
                .setId(Long.toString(id))
                .setExpiration(addHoursToJavaUtilDate(new Date(),5))
                .setSubject(role)
                .signWith(
                        SignatureAlgorithm.HS256,
                        "secret".getBytes("UTF-8"))
                .compact();
        return jwt;

    }

    private User authenticate(String username, String password) throws NoSuchElementException {

        List<Object> admins = HibernateUtils.select("Admin", "username", "=",username);
        if(admins.size()!=0 && ((Admin)admins.get(0)).getPassword().equals(password)) return (Admin)admins.get(0);

        List<Object> students = HibernateUtils.select("Student", "username", "=",username);
        if(students.size()!=0 && ((Student)students.get(0)).getPassword().equals(password)) return (Student)students.get(0);

        List<Object> masters = HibernateUtils.select("Master", "username", "=",username);
        if(masters.size()!=0 && ((Master)masters.get(0)).getPassword().equals(password)) return (Master)masters.get(0);

        throw new NoSuchElementException();
    }

    public Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
}