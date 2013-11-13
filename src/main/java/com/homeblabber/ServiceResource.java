
package com.homeblabber;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.inject.Named;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lombok.extern.slf4j.Slf4j;

import com.homeblabber.helper.ClientHelper;
import com.sun.jersey.api.client.ClientResponse;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/v1/homeblabber/service")
@Api(value = "/v1/homeblabber/service", description = "Service Request Operations")
@Produces(MediaType.APPLICATION_JSON)
@Named
@Slf4j

public class ServiceResource {
    
    @POST
    @Path("/{username}")
    @ApiOperation(value = "Create Service Request", notes = "Create Service Request")
    public Response createService(
    		@ApiParam(value = "Username", required = true) @PathParam("username") String username,
    		@ApiParam(value = "Title", required = true) @FormParam("title") String title,
    		@ApiParam(value = "Description", required = true) @FormParam("description") String description,
    		@ApiParam(value = "Comments", required = false) @FormParam("comments") String comments
    		) {
    	
    	int serviceRequestId = 45;
    	String input = "{\"serviceRequestId\":"+serviceRequestId+", \"username\":\""+username+"\",\"title\":\""+title+"\",\"description\":\""+description+"\", \"comments\":\""+comments+"\",\"isApproved\":false,\"rate\":0}";
    	System.out.println(input);
		ClientResponse response = ClientHelper.postResponse("https://api.parse.com/1/classes/ServiceRequestClass", input);

		if (response.getStatus() == 200 || response.getStatus() == 201) {
			String output = response.getEntity(String.class);
			return Response.ok(output).build();
		}
		return Response.status(response.getStatus()).build();
    }
    
    @PUT
    @Path("/{username}/{serviceRequestId}/{isApproved}")
    @ApiOperation(value = "Approve Or Reject the proposal", notes = "Approve or Reject the Proposal!")
    public Response UserApproveOrReject(
    		@ApiParam(value = "Username", required = true) @PathParam("username") String username,
    		@ApiParam(value = "Service Request ID", required = true) @PathParam("serviceRequestId") Integer serviceRequestId,
    		@ApiParam(value = "Approve or Reject", required = true) @PathParam("isApproved") Boolean isApproved
    		) {
    	
    	String input = "{\"username\":\""+username+"\",\"serviceRequestId\":"+serviceRequestId+",\"isApproved\":\""+isApproved+"\",\"rate\":\"0\"}";
		ClientResponse response = ClientHelper.putResponse("https://api.parse.com/1/classes/ServiceRequestClass", input);

		if (response.getStatus() == 200 || response.getStatus() == 201) {
			String output = response.getEntity(String.class);
			return Response.ok(output).build();
		}
		return Response.status(response.getStatus()).build();
    }
    
    @GET
	@ApiOperation(value = "Get all Service Requests", notes = "get all service requests")
	public Response getAllServiceRequests(){

		ClientResponse response = ClientHelper.getResponse("https://api.parse.com/1/classes/ServiceRequestClass");

		if (response.getStatus() == 200 || response.getStatus() == 201) {
			String output = response.getEntity(String.class);
			try {
				output = URLDecoder.decode(output, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return Response.ok(output).build();
		}
		return Response.status(response.getStatus()).build();
	}
    
    @POST
    @Path("/rate/{username}/{servicerequest}/{rate}")
    @ApiOperation(value = "Rate Service Provider", notes = "Rate Service Provider")
    public Response rateService(
    		@ApiParam(value = "Username", required = true) @PathParam("username") String username,
    		@ApiParam(value = "Service Request ID", required = true) @PathParam("serviceRequestId") Integer serviceRequestId,
    		@ApiParam(value = "rate", required = true) @PathParam("rate") Integer rate
    		) {
    	String input = "{\"username\":\""+username+"\",\"serviceRequestId\":"+serviceRequestId+",\"isApproved\":true,\"rate\":"+rate+"}";
    	ClientResponse response = ClientHelper.putResponse("https://api.parse.com/1/classes/ServiceRequestClass", input);

		if (response.getStatus() == 200 || response.getStatus() == 201) {
			String output = response.getEntity(String.class);
			return Response.ok(output).build();
		}
		return Response.status(response.getStatus()).build();
    }
    
    @DELETE
    @Path("/{serviceRequestId}")
    @ApiOperation(value = "delete Service Provider", notes = "delete Service Provider")
    public Response deleteRequest(
    		@ApiParam(value = "Service Request ID", required = true) @PathParam("serviceRequestId") Integer serviceRequestId
    		) {
    	ClientResponse response = ClientHelper.deleteResponse("https://api.parse.com/1/classes/ServiceRequestClass/"+serviceRequestId);

		if (response.getStatus() == 200 || response.getStatus() == 201) {
			String output = response.getEntity(String.class);
			return Response.ok(output).build();
		}
		return Response.status(response.getStatus()).build();
    }
}
