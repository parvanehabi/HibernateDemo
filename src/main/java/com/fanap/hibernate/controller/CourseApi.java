package com.fanap.hibernate.controller;

import com.fanap.hibernate.controller.Filter.JWTTokenNeeded;
import com.fanap.hibernate.data.modelVO.CourseVO;
import com.fanap.hibernate.data.repository.CourseCRUD;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.NoSuchElementException;

@Path("/course")
public class CourseApi {

    @POST
    @JWTTokenNeeded("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCourse(CourseVO courseVO) {

        if (courseVO == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        CourseVO course = CourseCRUD.saveCourse(courseVO);

        return Response.status(201).entity(course).build();
    }

    @GET
    @JWTTokenNeeded("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourses() {
        return Response.status(200).entity(CourseCRUD.getAll()).build();
    }

    @GET
    @Path("/{courseId}")
    @JWTTokenNeeded("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourseInformation(@PathParam("courseId") long courseId) {
        try{
            return Response.status(200).entity(CourseCRUD.getCourse(courseId)).build();
        }catch (NoSuchElementException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/{courseId}")
    @JWTTokenNeeded("admin")
    public Response deleteCourse(@PathParam("courseId") long courseId) {
        try{
            CourseCRUD.deleteCourse(courseId);
        }catch (NoSuchElementException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(204).build();
    }

    @PUT
    @Path("/{courseId}")
    @JWTTokenNeeded("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCourse(@PathParam("courseId") long courseId ,CourseVO courseVO) {

        if(courseVO == null)
            Response.status(Response.Status.BAD_REQUEST).build();
        try {
            return Response.status(200).entity(CourseCRUD.updateCourse(courseId ,courseVO)).build();
        }catch (NoSuchElementException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @GET
    @Path("/{courseId}/students")
    @JWTTokenNeeded("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourseStudents(@PathParam("courseId") long courseId) {

        try {
            return Response.status(200).entity(CourseCRUD.getCourseStudents(courseId)).build();
        } catch (NoSuchElementException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{courseId}/masters")
    @JWTTokenNeeded("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourseMasters(@PathParam("courseId") long courseId) {
        return Response.status(200).entity(CourseCRUD.getCourseMasters(courseId)).build();
    }

    @POST
    @Path("/{courseId}/addMaster/{masterId}")
    @JWTTokenNeeded("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCourseMasters(@PathParam("courseId") long courseId, @PathParam("masterId") long masterId) {

        try{
            return Response.status(200).entity(CourseCRUD.addCourseMasters(courseId, masterId)).build();
        }catch (NoSuchElementException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
