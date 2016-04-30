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
	public static String owlFileName=System.getProperty("catalina.base")  + "/webapps/OWLStore/data/Protege_Ontology.owl";
	public static String cypherFileName=System.getProperty("catalina.base")  + "/webapps/OWLStore/data/cypher.cql";
	
  public static void importOWLXML()
  {
	  try 
		{
		  File inputFile = new File(OWLParser.owlFileName);
		  SAXParserFactory factory = SAXParserFactory.newInstance();
		  SAXParser saxParser = factory.newSAXParser();
		  OWLHandler owlhandler = new OWLHandler();
		  owlhandler.setOutputFilePath(OWLParser.cypherFileName);
		  saxParser.parse(inputFile, owlhandler);
		} 
		catch (Exception e) 
		{
		  System.out.println(" Parsing Error : "+e.getMessage());
		}
  }
}
