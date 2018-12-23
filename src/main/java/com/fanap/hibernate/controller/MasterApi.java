package com.fanap.hibernate.controller;

import com.fanap.hibernate.controller.Filter.JWTTokenNeeded;
import com.fanap.hibernate.data.modelVO.MasterVO;
import com.fanap.hibernate.data.repository.MasterCRUD;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.NoSuchElementException;

@Path("/master")
public class MasterApi {

    @POST
    @JWTTokenNeeded("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMaster(MasterVO masterVO) {

        if (masterVO == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        MasterVO master= MasterCRUD.saveMaster(masterVO);

        return Response.status(201).entity(master).build();
    }

    @GET
    @JWTTokenNeeded("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMasters() {
        return Response.status(200).entity(MasterCRUD.getAll()).build();
    }

    @GET
    @Path("/{masterId}")
    @JWTTokenNeeded({"admin","master"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMasterInformation(@PathParam("masterId") long masterId) {

        try{
            return Response.status(200).entity(MasterCRUD.getMaster(masterId)).build();
        }catch (NoSuchElementException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/{masterId}")
    @JWTTokenNeeded("admin")
    public Response deleteMaster(@PathParam("masterId") long masterId) {
        try{
            MasterCRUD.deleteMaster(masterId);
        }catch (NoSuchElementException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(204).build();
    }

    @PUT
    @Path("/{masterId}")
    @JWTTokenNeeded("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMaster(@PathParam("masterId") long masterId ,MasterVO masterVO) {

        if(masterVO == null)
            Response.status(Response.Status.BAD_REQUEST).build();
        try {
            return Response.status(200).entity(MasterCRUD.updateMaster(masterId ,masterVO)).build();
        }catch (NoSuchElementException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @GET
    @Path("/{masterId}/courses")
    @JWTTokenNeeded({"admin","master"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMasterCourses(@PathParam("masterId") long masterId){
        try{
            return Response.status(200).entity(MasterCRUD.getMasterCourses(masterId)).build();
        }catch (NoSuchElementException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{masterId}/students")
    @JWTTokenNeeded({"admin","master"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMasterStudents(@PathParam("masterId") long masterId){
        try{
            return Response.status(200).entity(MasterCRUD.getMasterStudents(masterId)).build();
        }catch (NoSuchElementException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/addScore/{score}/toStudentCourse/{studentCourseId}")
    @JWTTokenNeeded({"admin","master"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response addScore(@PathParam("studentCourseId") long studentCourseId, @PathParam("score") long score){
        try{
            return Response.status(200).entity(MasterCRUD.addScore(studentCourseId,score)).build();
        }catch (NoSuchElementException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
