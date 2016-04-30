package org.iiitb.owlStore;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.multipart.FormDataParam;

@Path("/UserService")
public class UserService {

   @GET
   @Path("/helloworld")
   @Produces(MediaType.TEXT_PLAIN)
   public String getUsers(){
	   String workingDir = System.getProperty("catalina.base");
		//System.out.println("Current working directory : " + workingDir);
      return workingDir;
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
				response = neodb.executeMatchQueryForAPI(query);
				System.out.println("response : " + response);
			}
		  
		} catch (Exception e) 
		{
			System.out.println(" NEO4J Error : "+e.getMessage());
			response = " NEO4J Error : "+e.toString();
		}
	   return response;
   }
   
   @POST
   @Path("/uploadowlxml")
   @Consumes(MediaType.MULTIPART_FORM_DATA)
   public String uploadOWLXML(@FormDataParam("file") InputStream fileInputStream) {
	   String response="";
       try
       {
    	   String workingDir = System.getProperty("catalina.base");
    	   OutputStream fileOutputStream = new FileOutputStream(workingDir + "/webapps/OWLStore/data/Protege_Ontology.owl");
    	   int read = 0;
	   	   byte[] bytes = new byte[1024];
	
	   	   while ((read = fileInputStream.read(bytes)) != -1) 
	   	   {
	   		   fileOutputStream.write(bytes, 0, read);
	   	   }
	   	   OWLParser.importOWLXML();
    	   response = "Got it!!";
       }catch(Exception e){
    	   response = "Error : "+e.toString();
       }
       return response;
   }
   
}