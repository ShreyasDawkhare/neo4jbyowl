package org.iiitb.owlStore;
import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class OWLParser 
{
  public static void main(String[] args)
  {

	String owlFileName="data/Protege_Ontology.owl";
	String cypherFileName="data/cypher.cql";
	try 
	{
	  File inputFile = new File(owlFileName);
	  SAXParserFactory factory = SAXParserFactory.newInstance();
	  SAXParser saxParser = factory.newSAXParser();
	  OWLHandler owlhandler = new OWLHandler();
	  owlhandler.setOutputFilePath(cypherFileName);
	  saxParser.parse(inputFile, owlhandler);
	} 
	catch (Exception e) 
	{
	  System.out.println(" Parsing Error : "+e.getMessage());
	}
	try
	{
	  Neo4JOperater neodb = new Neo4JOperater();
	  neodb.setInputFilePath(cypherFileName);
	  
	  //neodb.deleteAll();
	  
	  neodb.executeQueries();
	  System.out.println("Done...");
	  
	  
	} catch (Exception e) 
	{
		System.out.println(" NEO4J Error : "+e.getMessage());
	}
	/*String query="MATCH (s)-[r1:IsA]->(cs),(o)-[r2:IsA]->(co),(s)-[r3:Studies]->(o) Where (1=1) AND (cs.type=\"Class\" and (cs.IRI=\"#Human\")) AND (co.type=\"Class\" and (co.IRI=\"#Module\")) Return s.FirstName,o.IRI";
	String response="";
	Neo4JOperater neodb = new Neo4JOperater();
	try
	{
		  
		response = neodb.executeMatchQuery(query);
		System.out.println("response : " + response);
	  
	} catch (Exception e) 
	{
		System.out.println(" NEO4J Error : "+e.getMessage());
		response = " NEO4J Error : "+e.toString();
	}*/
	
  }   
}
