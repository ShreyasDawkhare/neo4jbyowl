package org.iiitb.owlStore;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/UserService")
public class UserService {

   @GET
   @Path("/helloworld")
   @Produces(MediaType.TEXT_PLAIN)
   public String getUsers(){
      return "Hello World!!!";
   }	
	
   @POST
   @Path("/executematchquery")
   @Consumes(MediaType.TEXT_PLAIN)
   public String executeMatchQuery(String query) 
   {
	   String response="";
		try
		{
			if(query == null)
			{
				response = "Query is null";
			}
			else
			{
				Neo4JOperater neodb = new Neo4JOperater();  
				response = neodb.executeMatchQuery(query);
				System.out.println("response : " + response);
			}
		  
		} catch (Exception e) 
		{
			System.out.println(" NEO4J Error : "+e.getMessage());
			response = " NEO4J Error : "+e.toString();
		}
	   return response;
   }
}