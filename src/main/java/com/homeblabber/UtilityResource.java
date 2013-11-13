
package com.homeblabber;

import javax.inject.Named;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lombok.extern.slf4j.Slf4j;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.homeblabber.helper.ClientHelper;
import com.sun.jersey.api.client.ClientResponse;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/v1/homeblabber/utils")
@Api(value = "/v1/homeblabber/utils", description = "Service Request Operations")
@Produces(MediaType.APPLICATION_JSON)
@Named
@Slf4j

public class UtilityResource {
    
    @GET
    @Path("/geocode")
    @ApiOperation(value = "get coordinates from address", notes = "get latitude and longitude information from address")
    public Response getCoordinates(
    		@ApiParam(value = "address", required = true) @QueryParam("address") String address,
    		@DefaultValue("true")  @ApiParam(value = "send true for complete info, else false for just coordinates", required = false) @QueryParam("allInfo") boolean allInfo
    		){
    	
    	String uri = "http://maps.googleapis.com/maps/api/geocode/json?address="+address+"&sensor=false";
    	log.info(uri);
		ClientResponse response = ClientHelper.getGeoResponse(uri);

		if (response.getStatus() == 200 || response.getStatus() == 201) {
			String output = response.getEntity(String.class);
			
			if(!allInfo){
				JSONObject obj = (JSONObject) JSONValue.parse(output);
				JSONArray jsonArray = (JSONArray) obj.get("results");
				obj = (JSONObject) jsonArray.get(0);
				obj = (JSONObject) obj.get("geometry");
				return Response.ok(obj.get("location")).build();
				
			}
			
			return Response.ok(output).build();
		}
		return Response.status(response.getStatus()).build();
    }
    
    /**
     * TODO: TBD
     * @return
     */
    @GET
    @Path("/geofence")
    @ApiOperation(value = "get coordinates from address", notes = "TBD")
    public Response setGeoFence(){
		return Response.ok().build();
    }
    
}
