package com.fanap.hibernate.controller;


import com.fanap.hibernate.controller.Filter.JWTTokenNeeded;
import com.fanap.hibernate.data.modelVO.StudentVO;
import com.fanap.hibernate.data.repository.StudentCRUD;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.NoSuchElementException;


@Path("/student")
public class StudentApi {

    final static String Image_Folder = "images";

    @POST
    @JWTTokenNeeded("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(StudentVO studentVO) {

        if (studentVO == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        StudentVO student= StudentCRUD.saveStudent(studentVO);

        return Response.status(201).entity(student).build();
    }

    @GET
    @JWTTokenNeeded("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudents() {
        return Response.status(200).entity(StudentCRUD.getAll()).build();
    }

    @GET
    @Path("/{studentId}")
    @JWTTokenNeeded({"admin","student"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentInformation(@PathParam("studentId") long studentId) {

        try {
            return Response.status(200).entity(StudentCRUD.getStudent(studentId)).build();
        }catch (NoSuchElementException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/{studentId}")
    @JWTTokenNeeded("admin")
    public Response deleteStudent(@PathParam("studentId") long studentId) {
        try {
            StudentCRUD.deleteStudent(studentId);
        }catch (NoSuchElementException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(204).build();
    }

    @PUT
    @Path("/{studentId}")
    @JWTTokenNeeded("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(@PathParam("studentId") long studentId ,StudentVO studentVO) {

        if(studentVO == null)
            Response.status(Response.Status.BAD_REQUEST).build();
        try {
            return Response.status(200).entity(StudentCRUD.updateStudent(studentId ,studentVO)).build();
        }catch (NoSuchElementException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @POST
    @Path("/{studentId}/addImage")
    @JWTTokenNeeded("admin")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.APPLICATION_JSON)
    public Response addImage(@FormDataParam("file") InputStream fileInputStream,
                             @FormDataParam("file") FormDataContentDisposition fileMetaData, @PathParam("studentId") long studentId) {


        String uploadedFileLocation = Image_Folder + "/" + fileMetaData.getFileName();

        File dir = new File(Image_Folder);
        if(!dir.exists())
            if(!dir.mkdir())
                return Response.status(500).entity("server can't make a directory to save image").build();
        try {
            StudentCRUD.writeToFile(fileInputStream, uploadedFileLocation);
        }catch (IOException e){
            return Response.status(500).entity("can't save image").build();
        }

        StudentVO studentVO = new StudentVO();
        studentVO.setImage(fileMetaData.getFileName());

        StudentVO studentVO1 = StudentCRUD.updateStudent(studentId,studentVO);

        return Response.status(204).build();
    }


    @GET
    @Path("/{studentId}/image")
    @JWTTokenNeeded({"admin","student"})
    @Produces("image/png")
    public Response getFullImage(@PathParam("studentId") long studentId) {
        StudentVO studentVO = StudentCRUD.getStudent(studentId);

        try {
            File file = new File(Image_Folder + "/" + studentVO.getImage());
            return Response.ok((Object) file).header("Content-Disposition", "attachment; filename=image_from_server.png").build();
        }catch (NullPointerException e){
            return Response.status(404).entity("can't find file on server").build();
        }
    }

    @GET
    @Path("/{studentId}/courses")
    @JWTTokenNeeded({"admin","student"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentCourses(@PathParam("studentId") long studentId){
        try{
            return Response.status(200).entity(StudentCRUD.getStudentCourses(studentId)).build();
        }catch (NoSuchElementException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/{studentId}/addCourse/{courseId}")
    @JWTTokenNeeded("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudentCourse(@PathParam("studentId") long studentId, @PathParam("courseId") long courseId){
        try{
            return Response.status(200).entity(StudentCRUD.addStudentCourse(studentId, courseId)).build();
        }catch (NoSuchElementException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{studentId}/masters")
    @JWTTokenNeeded({"admin","student"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentMasters(@PathParam("studentId") long studentId){
        try{
            return Response.status(200).entity(StudentCRUD.getStudentMasters(studentId)).build();
        }catch (NoSuchElementException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
