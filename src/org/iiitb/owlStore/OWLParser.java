package org.iiitb.owlStore;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
	  
	  neodb.deleteAll();
	  
	  neodb.executeQueries();
	  System.out.println("Done...");
	   
	  
	} catch (Exception e) 
	{
		System.out.println(" NEO4J Error : "+e.getMessage());
	}
	
	/*try
	{
		HashMap<String,String> a = null;
		HashMap<String,String> b = null;
		Neo4JOperater neodb = new Neo4JOperater();  
		a = neodb.getAlldataPropertiesForGivenIRI("#Student1");
		b = neodb.getAlldataPropertiesForGivenIRI("#Std1");
		
		System.out.println(a.toString());
		System.out.println(b.toString());
		
		for (String key : a.keySet()) 
		{
			if(b.containsKey(key))
			{
				
			}
			else
			{
				b.put(key, a.get(key));
			}
		}
		for (String key : b.keySet()) 
		{
			if(a.containsKey(key))
			{
				
			}
			else
			{
				a.put(key, b.get(key));
			}
		}
		System.out.println(a.toString());
		System.out.println(b.toString());
	  
	} catch (Exception e) 
	{
		System.out.println(" NEO4J Error : "+e.getMessage());
		
	}*/
	
  }   
}
