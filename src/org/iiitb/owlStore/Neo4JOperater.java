package org.iiitb.owlStore;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.shell.util.json.JSONArray;
import org.neo4j.shell.util.json.JSONException;
import org.neo4j.shell.util.json.JSONObject;

public class Neo4JOperater 
{
	private String DB_PATH;
	private String NEO4J_Properties_Path;
	private String inputFilePath;
	private GraphDatabaseService db;
	
	public Neo4JOperater()
	{
		this.setDB_PATH("/home/shreyas/neo4j-community-2.3.2/data/graph.db");
	    this.setNEO4J_Properties_Path("/home/shreyas/neo4j-community-2.3.2/conf/neo4j.properties");
	}
	public void setDB_PATH(String DB_PATH)
	{
		this.DB_PATH = DB_PATH;
	}
	public void setNEO4J_Properties_Path(String NEO4J_Properties_Path)
	{
		this.NEO4J_Properties_Path = NEO4J_Properties_Path;
	}
	public void setInputFilePath(String inputFilePath)
	{
		this.inputFilePath = inputFilePath;
	}
	public void executeQueries()
	{
		createConnection();
		try (BufferedReader br = new BufferedReader(new FileReader(this.inputFilePath))) 
		{
		    String line;
		    while ((line = br.readLine()) != null) 
		    {
		    	execute(line);
		    }
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		shutdownConnection();
	}
	private void shutdownConnection() 
	{
		db.shutdown();
	}
	private void createConnection() 
	{
		try
		{
			db = new GraphDatabaseFactory()
				    .newEmbeddedDatabaseBuilder(new File(this.DB_PATH))
				    .loadPropertiesFromFile(this.NEO4J_Properties_Path)
				    .newGraphDatabase();
		} catch (org.neo4j.kernel.lifecycle.LifecycleException e)
		{
			System.out.println(e.getMessage());
			System.exit(0);
		} catch (org.neo4j.kernel.StoreLockException e)
		{
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}
	private void execute(String line) 
	{
		Transaction tx1 = db.beginTx();
	    try
	    {
	    	db.execute(line);
	    	System.out.println("Executing : "+ line);
	        tx1.success();
	        
	    } finally {
	        tx1.close();
	    }
	}
	public void deleteAll()
	{
		createConnection();
		execute("MATCH (n)	OPTIONAL MATCH (n)-[r]-() DELETE n,r");
		shutdownConnection();
	}
	public String executeMatchQueryForAPI(String query)
	{
		String result="";
		createConnection();
		result = "";
		Transaction tx1 = db.beginTx();
		int rowid = 1;
	    try
	    {
	    	String tableheader = "<thead><tr><th>#</th>";
	    	String tablebody = "<tbody>";
	    	Result execResult = db.execute(query);
	    	while ( execResult.hasNext() )
	        {
	    		tablebody +=  "<tr><th scope=\"row\">"+Integer.toString(rowid)+"</th> ";
	            Map<String,Object> row = execResult.next();
	            for ( Entry<String,Object> column : row.entrySet() )
	            {
	            	if(rowid == 1) // add header also
	            	{
	            		tableheader += "<th>" + column.getKey() + "</th>";
	            	}
	            	tablebody += "<td>" + column.getValue() + "</td>";
	            }
	            if(rowid == 1)
	            {
	            	tableheader += "</tr></thead>";
	            }
	            tablebody +=  "</tr>";
	            rowid++;
	        }
	    	tablebody +=  "</tbody>";
	    	System.out.println("Executing : "+ query);
	    	
	        tx1.success();
	        
	        result = tableheader + tablebody;
	        
	    } finally {
	        tx1.close();
	    }
		shutdownConnection();
		return result;
	}
	public ArrayList<String> getAllObjectProperties()
	{
		String result="";
		ArrayList<String> lst;
		String query = "MATCH (x:ObjectProperty) return x.IRI";
		
		createConnection();
		
		Transaction tx1 = db.beginTx();
	    try
	    {
	    	lst = new ArrayList<String>();
	    	Result execResult = db.execute(query);
	    	while ( execResult.hasNext() )
	        {
	    		Map<String,Object> row = execResult.next();
	            for ( Entry<String,Object> column : row.entrySet() )
	            {
	            	lst.add((column.getValue().toString()).substring(1));
	            }
	        }
	    	
	        tx1.success();
	        
	    } finally {
	        tx1.close();
	    }
		shutdownConnection();
		
		return lst;
	}
	public HashMap<String,String> getAlldataPropertiesForGivenIRI(String IRI)
	{
		String query = "MATCH(a) where a.IRI=\""+IRI+"\" return a";
		HashMap<String,String> dataPropertyPairs= new HashMap<String, String>();
		
		createConnection();
		
		Transaction tx1 = db.beginTx();
	    try
	    {
	    	Result execResult = db.execute(query);
	    	while ( execResult.hasNext() )
	        {
	    		Map<String,Object> row = execResult.next();
	            for ( Entry<String,Object> column : row.entrySet() )
	            {
	            	JSONObject val = new JSONObject(column.getValue());
	            	JSONObject allProperties = new JSONObject();
	            	allProperties = val.getJSONObject("allProperties");
	            	
	            	Iterator<String> iterator  = allProperties.keys();
	            	while(iterator.hasNext())
	            	{
	            		String key = iterator.next();
	            		dataPropertyPairs.put(key, allProperties.getString(key));
	            		
	            	}
	            	
	            }
	        }
	    	
	        tx1.success();
	        
	    } catch (JSONException e) {
			e.printStackTrace();
		} finally {
	        tx1.close();
	    }
		shutdownConnection();

		return dataPropertyPairs;
	}
	public ArrayList<ArrayList<String>> getAllSameIndividualPairs()
	{
		String query = "MATCH (a)-[r1:IsSameIndividualAs]->(b) return a.IRI,b.IRI";
		ArrayList<ArrayList<String>> sameIndividualPairs= new ArrayList<ArrayList<String>>();
		ArrayList<String> a = null;
		createConnection();
		String first="",second="";
		
		Transaction tx1 = db.beginTx();
	    try
	    {
	    	Result execResult = db.execute(query);
	    	while ( execResult.hasNext() )
	        {
	    		Map<String,Object> row = execResult.next();
	    		
	    		a = new ArrayList<String>();
	            for ( Entry<String,Object> column : row.entrySet() )
	            {
	            	if(column.getKey().toString().equals("a.IRI"))
	            	{
	            		first = column.getValue().toString();
	            	}
	            	if(column.getKey().toString().equals("b.IRI"))
	            	{
	            		second = column.getValue().toString();
	            	}
	            }
	            a.add(first);
	            a.add(second);
	            sameIndividualPairs.add(a);
	        }
	    	
	        tx1.success();
	        
	    } finally {
	        tx1.close();
	    }
		
		shutdownConnection();
		return sameIndividualPairs;
	}
	public ArrayList<ArrayList<String>> getAllInverseObjectProperties() 
	{
		String query = "MATCH (a)-[r1:IsInverseObjectPropertyOf]->(b) return a.IRI,b.IRI";
		ArrayList<ArrayList<String>> inverseObjectPropertiesPairs= new ArrayList<ArrayList<String>>();
		ArrayList<String> a = null;
		createConnection();
		String first="",second="";
		
		Transaction tx1 = db.beginTx();
	    try
	    {
	    	Result execResult = db.execute(query);
	    	while ( execResult.hasNext() )
	        {
	    		Map<String,Object> row = execResult.next();
	    		
	    		a = new ArrayList<String>();
	            for ( Entry<String,Object> column : row.entrySet() )
	            {
	            	if(column.getKey().toString().equals("a.IRI"))
	            	{
	            		first = column.getValue().toString().substring(1);
	            	}
	            	if(column.getKey().toString().equals("b.IRI"))
	            	{
	            		second = column.getValue().toString().substring(1);
	            	}
	            }
	            a.add(first);
	            a.add(second);
	            inverseObjectPropertiesPairs.add(a);
	        }
	    	
	        tx1.success();
	        
	    } finally {
	        tx1.close();
	    }
		
		shutdownConnection();
		
		return inverseObjectPropertiesPairs;
	
	}
	public ArrayList<ArrayList<String>> getAllSubObjectProperties() 
	{
		String query = "MATCH (a)-[r1:IsSubObjectPropertyOf]->(b) return a.IRI,b.IRI";
		ArrayList<ArrayList<String>> subObjectPropertiesPairs= new ArrayList<ArrayList<String>>();
		ArrayList<String> a = null;
		createConnection();
		String first="",second="";
		
		Transaction tx1 = db.beginTx();
	    try
	    {
	    	Result execResult = db.execute(query);
	    	while ( execResult.hasNext() )
	        {
	    		Map<String,Object> row = execResult.next();
	    		
	    		a = new ArrayList<String>();
	            for ( Entry<String,Object> column : row.entrySet() )
	            {
	            	if(column.getKey().toString().equals("a.IRI"))
	            	{
	            		first = column.getValue().toString().substring(1);
	            	}
	            	if(column.getKey().toString().equals("b.IRI"))
	            	{
	            		second = column.getValue().toString().substring(1);
	            	}
	            }
	            a.add(first);
	            a.add(second);
	            subObjectPropertiesPairs.add(a);
	        }
	    	
	        tx1.success();
	        
	    } finally {
	        tx1.close();
	    }
		
		shutdownConnection();
		
		return subObjectPropertiesPairs;

	}
	public void executeMatchQuery(String query)
	{
		createConnection();
		Transaction tx1 = db.beginTx();
	    try
	    {
	    	db.execute(query);
	    	System.out.println("Executing : "+ query);
	        tx1.success();
	        
	    } finally {
	        tx1.close();
	    }
		shutdownConnection();
	}
}
