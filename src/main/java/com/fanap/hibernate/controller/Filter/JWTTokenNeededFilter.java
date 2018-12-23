package com.fanap.hibernate.controller.Filter;

import io.jsonwebtoken.Jwts;
import javax.annotation.Priority;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

import io.jsonwebtoken.Claims;



/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
@Provider
@JWTTokenNeeded("")
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        System.out.println(resourceInfo.getResourceMethod().getName());

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();

        Method method = resourceInfo.getResourceMethod();
        JWTTokenNeeded jwtTokenNeededAnnotation = method.getAnnotation(JWTTokenNeeded.class);
        Set<String> rolesSet = new HashSet<String>(Arrays.asList(jwtTokenNeededAnnotation.value()));

        try {
            Claims claims = Jwts.parser().setSigningKey("secret".getBytes("UTF-8"))
                    .parseClaimsJws(token).getBody();
            System.out.println("ID: " + claims.getId() + " Subject: "+claims.getSubject() );
            if(!rolesSet.contains(claims.getSubject()))
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            else {
                long id = Long.valueOf(claims.getId());
                if(claims.getSubject().equals("student") && requestContext.getUriInfo().getPathParameters().containsKey("studentId")){
                    long studentId = Long.valueOf(requestContext.getUriInfo().getPathParameters().get("studentId").get(0));
                    if(studentId!=id)
                        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                }else if(claims.getSubject().equals("master") && requestContext.getUriInfo().getPathParameters().containsKey("masterId")){
                    long masterId = Long.valueOf(requestContext.getUriInfo().getPathParameters().get("masterId").get(0));
                    if(masterId!=id)
                        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());

                    if(resourceInfo.getResourceMethod().getName() == "addScore"){

                    }

                }
            }

        } catch (Exception e) {

            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}