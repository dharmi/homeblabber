
package com.homeblabber;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.inject.Named;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lombok.extern.slf4j.Slf4j;

import com.homeblabber.helper.ClientHelper;
import com.sun.jersey.api.client.ClientResponse;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("/v1/homeblabber/user")
@Api(value = "/v1/homeblabber/user", description = "User Operations")
@Produces(MediaType.APPLICATION_JSON)
@Named
@Slf4j

public class UserResource {

	@POST
	@Path("/")
	@ApiOperation(value = "Create User", notes = "Creates an user")
	@ApiResponses(value = { @ApiResponse(code = 600, message = "Existing user"),
			@ApiResponse(code = 605, message = "Invalid userid"),
			@ApiResponse(code = 611, message = "Invalid password")})
	public Response createUser(
			@ApiParam(value = "User id", required = true) @FormParam("username") String username,
			@ApiParam(value = "User's Password", required = true) @FormParam("password") String password,
			@ApiParam(value = "User's First Name", required = false) @FormParam("firstname") String firstname,
			@ApiParam(value = "User's Last Name", required = false) @FormParam("lastname") String lastname,
			@ApiParam(value = "User's EmailID", required = false) @FormParam("emailId") String emailId,
			@ApiParam(value = "User's Address", required = false) @FormParam("address") String address,
			@ApiParam(value = "User's City", required = false) @FormParam("city") String city,
			@ApiParam(value = "User's State", required = false) @FormParam("state") String state
			) throws KeyManagementException, NoSuchAlgorithmException {

		String input = "{\"username\":\""+username+"\",\"password\":\""+password+"\",\"firstname\":\""+firstname+"\", \"lastname\":\""+lastname+"\", "
				+ "\"emailid\":\""+emailId+"\", \"address\":\""+address+"\", "
				+ "\"city\":\""+city+"\", \"state\":\""+state+"\", \"type\":\"user\"}";
		ClientResponse response = ClientHelper.postResponse("https://api.parse.com/1/classes/UserClass", input);

		if (response.getStatus() == 200 || response.getStatus() == 201) {
			String output = response.getEntity(String.class);
			return Response.ok(output).build();
		}
		return Response.status(response.getStatus()).build();
	}
	
	@GET
	@Path("/")
	@ApiOperation(value = "Get Users", notes = "get users")
	public Response getUsers(){

		ClientResponse response = ClientHelper.getResponse("https://api.parse.com/1/classes/UserClass");

		if (response.getStatus() == 200 || response.getStatus() == 201) {
			String output = response.getEntity(String.class);
			return Response.ok(output).build();
		}
		return Response.status(response.getStatus()).build();
	}
	
	@DELETE
	@Path("/{objectId}")
	@ApiOperation(value = "Delete User", notes = "get users")
	public Response deleteUser(
			@ApiParam(value = "ObjectId", required = true) @PathParam("objectId") String objectId){

		ClientResponse response = ClientHelper.deleteResponse("https://api.parse.com/1/classes/UserClass/"+objectId);

		if (response.getStatus() == 200 || response.getStatus() == 201) {
			String output = response.getEntity(String.class);
			return Response.ok(output).build();
		}
		return Response.status(response.getStatus()).build();
	}

	@PUT
	@Path("/{username}")
	@ApiOperation(value = "Update User", notes = "Update an user")
	@ApiResponses(value = { @ApiResponse(code = 600, message = "Existing user"),
			@ApiResponse(code = 605, message = "Invalid userid"),
			@ApiResponse(code = 611, message = "Invalid password")})
	public Response updateUser(
			@ApiParam(value = "User id", required = true) @PathParam("username") String username,
			@ApiParam(value = "User's EmailID", required = false) @FormParam("emailId") String emailId,
			@ApiParam(value = "User's Address", required = false) @FormParam("address") String address,
			@ApiParam(value = "User's City", required = false) @FormParam("city") String city,
			@ApiParam(value = "User's State", required = false) @FormParam("state") String state
			) {
		return Response.ok("{status:\"ok\"}", MediaType.APPLICATION_JSON).build();
	}

}
