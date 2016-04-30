package org.iiitb.owlStore;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ValueObjects.OWLClass;
import ValueObjects.OWLDataProperty;
import ValueObjects.OWLDatatype;
import ValueObjects.OWLLiteral;
import ValueObjects.OWLNamedIndividual;
import ValueObjects.OWLObjectProperty;

public class OWLHandler extends DefaultHandler 
{

	private boolean Declaration = false;
	private boolean SubClassOf = false;
	private boolean DisjointClasses = false;
	private boolean ClassAssertion = false;
	private boolean ObjectPropertyAssertion = false;
	private boolean DataPropertyAssertion = false;
	private boolean ObjectPropertyDomain = false;
	private boolean ObjectPropertyRange = false;
	private boolean DataPropertyDomain = false;
	private boolean DataPropertyRange = false;
	private boolean Class = false;
	private boolean ObjectProperty = false;
	private boolean DataProperty = false;
	private boolean NamedIndividual = false;
	private boolean Literal = false;
	private boolean Datatype = false;
	private boolean EquivalentClasses = false;
	private boolean SameIndividual = false;
	private boolean DifferentIndividuals = false;
	private boolean SubObjectPropertyOf = false;
	private boolean InverseObjectProperties = false;
	private Queue<Object> queue = new LinkedList<Object>();
	private OWLLiteral lit;
	private int mode = 2; // JSON : 1, CYPHER : 2
	private PrintWriter writer;


	public void setOutputFilePath(String cypherFileName) 
	{
		try 
		{
			writer = new PrintWriter(cypherFileName, "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void startElement(String uri,String localName, String qName, Attributes attributes) throws SAXException 
	{
		switch(qName)
		{
			case "Ontology": 
							//writer.println("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r");
							break;
			case "Declaration": Declaration = true; break;
			case "SubClassOf": 
							SubClassOf = true; 
							queue.clear();
							break;
			case "EquivalentClasses" : 
							EquivalentClasses = true; 
							queue.clear();
							break;
			case "DisjointClasses": 
							DisjointClasses = true; 
							queue.clear();
							break;
			case "ClassAssertion": ClassAssertion = true; break;
			case "SameIndividual": 
							SameIndividual = true; 
							queue.clear();
							break;
			case "DifferentIndividuals": 
							DifferentIndividuals = true; 
							queue.clear();
							break;
			case "SubObjectPropertyOf" : 
							SubObjectPropertyOf = true; 
							queue.clear();
							break;
			case "ObjectPropertyAssertion": 
							ObjectPropertyAssertion = true; 
							queue.clear();
							break;
			case "DataPropertyAssertion": 
							DataPropertyAssertion = true; 
							queue.clear();
							break;
			case "ObjectPropertyDomain": 
							ObjectPropertyDomain = true; 
							queue.clear();
							break;
			case "ObjectPropertyRange": 
							ObjectPropertyRange = true; 
							queue.clear();
							break;
			case "DataPropertyDomain": 
							DataPropertyDomain = true; 
							queue.clear();
							break;
			case "DataPropertyRange": 
							DataPropertyRange = true; 
							queue.clear();
							break;
			case "Class": 
							Class = true;
							if(Declaration)
							{
								if(mode == 1)
								{
									System.out.println("{ type : \"Class\", IRI : \""+attributes.getValue("IRI")+"\" },");
								}
								else  if(mode == 2)
								{
									writer.println("MERGE (node:Class {IRI : '"+attributes.getValue("IRI")+"', type : 'Class'})");
								}
							}
							if(SubClassOf)
							{
								queue.add(new OWLClass(attributes.getValue("IRI")));
							}
							if(DisjointClasses)
							{
								queue.add(new OWLClass(attributes.getValue("IRI")));
							}
							if(ClassAssertion)
							{
								queue.add(new OWLClass(attributes.getValue("IRI")));
							}
							if(ObjectPropertyDomain)
							{
								queue.add(new OWLClass(attributes.getValue("IRI")));
							}
							if(ObjectPropertyRange)
							{
								queue.add(new OWLClass(attributes.getValue("IRI")));
							}
							if(DataPropertyDomain)
							{
								queue.add(new OWLClass(attributes.getValue("IRI")));
							}
							if(EquivalentClasses)
							{
								queue.add(new OWLClass(attributes.getValue("IRI")));
							}
							break;
			case "ObjectProperty": 
							ObjectProperty = true;
							if(Declaration)
							{
								if(mode == 1)
								{
									System.out.println("{ type : \"ObjectProperty\", IRI : \""+attributes.getValue("IRI")+"\" },");
								}
								else  if(mode == 2)
								{
									writer.println("MERGE (node:ObjectProperty {IRI : '"+attributes.getValue("IRI")+"', type : 'ObjectProperty'})");
									
								}
							}
							if(ObjectPropertyAssertion)
							{
								queue.add(new OWLObjectProperty(attributes.getValue("IRI")));
							}
							if(ObjectPropertyDomain)
							{
								queue.add(new OWLObjectProperty(attributes.getValue("IRI")));
							}
							if(ObjectPropertyRange)
							{
								queue.add(new OWLObjectProperty(attributes.getValue("IRI")));
							}
							if(SubObjectPropertyOf)
							{
								queue.add(new OWLObjectProperty(attributes.getValue("IRI")));
							}
							if(InverseObjectProperties)
							{
								queue.add(new OWLObjectProperty(attributes.getValue("IRI")));
							}
							break;
			case "DataProperty": 
							DataProperty = true;
							if(Declaration)
							{
								if(mode == 1)
								{
									System.out.println("{ type : \"DataProperty\", IRI : \""+attributes.getValue("IRI")+"\" },");
								}
								else  if(mode == 2)
								{
									writer.println("MERGE (node:DataProperty {IRI : '"+attributes.getValue("IRI")+"', type : 'DataProperty'})");
									
								}
							}
							if(DataPropertyAssertion)
							{
								queue.add(new OWLDataProperty(attributes.getValue("IRI")));
							}
							if(DataPropertyDomain)
							{
								queue.add(new OWLDataProperty(attributes.getValue("IRI")));
							}
							if(DataPropertyRange)
							{
								queue.add(new OWLDataProperty(attributes.getValue("IRI")));
							}
							break;
			case "NamedIndividual": 
							NamedIndividual = true;
							if(Declaration)
							{
								if(mode == 1)
								{
									System.out.println("{ type : \"NamedIndividual\", IRI : \""+attributes.getValue("IRI")+"\" },");
								}
								else  if(mode == 2)
								{
									writer.println("MERGE (node:NamedIndividual {IRI : '"+attributes.getValue("IRI")+"', type : 'NamedIndividual'})");
									
								}
							}
							if(ClassAssertion)
							{
								queue.add(new OWLNamedIndividual(attributes.getValue("IRI")));
							}
							if(ObjectPropertyAssertion)
							{
								queue.add(new OWLNamedIndividual(attributes.getValue("IRI")));
							}
							if(DataPropertyAssertion)
							{
								queue.add(new OWLNamedIndividual(attributes.getValue("IRI")));
							}
							if(SameIndividual)
							{
								queue.add(new OWLNamedIndividual(attributes.getValue("IRI")));
							}
							if(DifferentIndividuals)
							{
								queue.add(new OWLNamedIndividual(attributes.getValue("IRI")));
							}
							break;
			case "Literal": 
							Literal = true; 
							if(DataPropertyAssertion)
							{
								lit = new OWLLiteral();
								lit.setDatatypeIRI(attributes.getValue("datatypeIRI"));
							}
							break;
			case "Datatype": 
							Datatype = true; 
							if(DataPropertyRange)
							{
								queue.add(new OWLDatatype(attributes.getValue("abbreviatedIRI")));
							}
							break;
			case "InverseObjectProperties": 
							InverseObjectProperties = true; 
							queue.clear();
							break;
		}
	}
	
	@Override
    public void endElement(String uri,String localName, String qName) throws SAXException 
	{
		Object subject;
		Object predicate;
		Object object;
		
		switch(qName)
		{
			case "Declaration": Declaration = false; break;
			case "SubClassOf": 
							SubClassOf = false; 
							subject = queue.remove();
							object = queue.remove();
							queue.clear();
							if(mode == 1)
							{
								System.out.println("{ subject : \""+((OWLClass)subject).getIRI()+"\", predicate : \"SubClassOf\", predicatetype : \"SubClassOf\", object : \""+((OWLClass)object).getIRI()+"\" },");
							}
							else  if(mode == 2)
							{
								writer.println("MATCH (subject:Class), (object:Class) " +
													" WHERE subject.IRI='"+((OWLClass)subject).getIRI()+"' and object.IRI='"+((OWLClass)object).getIRI()+"'" +
													" MERGE (subject)-[:SubClassOf {predicatetype : 'SubClassOf', predicatename:'SubClassOf'}]->(object)");
								
							}
							break;
			case "EquivalentClasses" : 
							EquivalentClasses = false; 
							subject = queue.remove();
							object = queue.remove();
							queue.clear();
							if(mode == 1)
							{
								System.out.println("{ subject : \""+((OWLClass)subject).getIRI()+"\", predicate : \"IsEquivalentTo\", predicatetype : \"EquivalentClasses\", object : \""+((OWLClass)object).getIRI()+"\" },");
							}
							else  if(mode == 2)
							{
								writer.println("MATCH (subject:Class), (object:Class) " +
													" WHERE subject.IRI='"+((OWLClass)subject).getIRI()+"' and object.IRI='"+((OWLClass)object).getIRI()+"'" +
													" MERGE (subject)-[:IsEquivalentTo {predicatetype : 'EquivalentClasses'}]->(object)");
								writer.println("MATCH (subject:Class), (object:Class) " +
										" WHERE subject.IRI='"+((OWLClass)object).getIRI()+"' and object.IRI='"+((OWLClass)subject).getIRI()+"'" +
										" MERGE (subject)-[:IsEquivalentTo {predicatetype : 'EquivalentClasses',predicatename:'IsEquivalentTo'}]->(object)");
								
								
							}
							break;
			case "SameIndividual": 
							SameIndividual = false; 
							subject = queue.remove();
							object = queue.remove();
							queue.clear();
							if(mode == 1)
							{
								System.out.println("{ subject : \""+((OWLNamedIndividual)subject).getIRI()+"\", predicate : \"IsSameIndividualAs\", predicatetype : \"SameIndividual\", object : \""+((OWLNamedIndividual)object).getIRI()+"\" },");
								System.out.println("{ subject : \""+((OWLNamedIndividual)object).getIRI()+"\", predicate : \"IsSameIndividualAs\", predicatetype : \"SameIndividual\", object : \""+((OWLNamedIndividual)subject).getIRI()+"\" },");
							}
							else  if(mode == 2)
							{
								writer.println("MATCH (subject:NamedIndividual), (object:NamedIndividual) " +
													" WHERE subject.IRI='"+((OWLNamedIndividual)subject).getIRI()+"' and object.IRI='"+((OWLNamedIndividual)object).getIRI()+"'" +
													" MERGE (subject)-[:IsSameIndividualAs {predicatetype : 'SameIndividual',predicatename:'IsSameIndividualAs'}]->(object)");
								writer.println("MATCH (subject:NamedIndividual), (object:NamedIndividual) " +
													" WHERE subject.IRI='"+((OWLNamedIndividual)object).getIRI()+"' and object.IRI='"+((OWLNamedIndividual)subject).getIRI()+"'" +
													" MERGE (subject)-[:IsSameIndividualAs {predicatetype : 'SameIndividual',predicatename:'IsSameIndividualAs'}]->(object)");
								
							}
							break;
			case "DifferentIndividuals": 
							DifferentIndividuals = false; 
							subject = queue.remove();
							object = queue.remove();
							queue.clear();
							if(mode == 1)
							{
								System.out.println("{ subject : \""+((OWLNamedIndividual)subject).getIRI()+"\", predicate : \"IsDifferentIndividualFrom\", predicatetype : \"DifferentIndividuals\", object : \""+((OWLNamedIndividual)object).getIRI()+"\" },");
								System.out.println("{ subject : \""+((OWLNamedIndividual)object).getIRI()+"\", predicate : \"IsDifferentIndividualFrom\", predicatetype : \"DifferentIndividuals\", object : \""+((OWLNamedIndividual)subject).getIRI()+"\" },");
							}
							else  if(mode == 2)
							{
								writer.println("MATCH (subject:NamedIndividual), (object:NamedIndividual) " +
													" WHERE subject.IRI='"+((OWLNamedIndividual)subject).getIRI()+"' and object.IRI='"+((OWLNamedIndividual)object).getIRI()+"'" +
													" MERGE (subject)-[:IsDifferentIndividualFrom {predicatetype : 'DifferentIndividuals',predicatename:'IsDifferentIndividualFrom'}]->(object)");
								writer.println("MATCH (subject:NamedIndividual), (object:NamedIndividual) " +
													" WHERE subject.IRI='"+((OWLNamedIndividual)object).getIRI()+"' and object.IRI='"+((OWLNamedIndividual)subject).getIRI()+"'" +
													" MERGE (subject)-[:IsDifferentIndividualFrom {predicatetype : 'DifferentIndividuals',predicatename:'IsDifferentIndividualFrom'}]->(object)");
								
							}
							break;
			case "SubObjectPropertyOf" : 
							SubObjectPropertyOf = false; 
							subject = queue.remove();
							object = queue.remove();
							queue.clear();
							if(mode == 1)
							{
								System.out.println("{ subject : \""+((OWLObjectProperty)subject).getIRI()+"\", predicate : \"IsSubObjectPropertyOf\", predicatetype : \"SubObjectPropertyOf\", object : \""+((OWLObjectProperty)object).getIRI()+"\" },");
							}
							else  if(mode == 2)
							{
								writer.println("MATCH (subject:ObjectProperty), (object:ObjectProperty) " +
													" WHERE subject.IRI='"+((OWLObjectProperty)subject).getIRI()+"' and object.IRI='"+((OWLObjectProperty)object).getIRI()+"'" +
													" MERGE (subject)-[:IsSubObjectPropertyOf {predicatetype : 'SubObjectPropertyOf',predicatename:'IsSubObjectPropertyOf'}]->(object)");
								
							};
			case "DisjointClasses": 
							DisjointClasses = false;
							for (Object c1 : queue) 
							{
								for (Object c2 : queue) 
								{
							        if(c1 != c2)
							        {
							        	if(mode == 1)
										{
											System.out.println("{ subject : \""+((OWLClass)c1).getIRI()+"\", predicate : \"IsDisjointWith\", predicatetype : \"DisjointClasses\", object : \""+((OWLClass)c2).getIRI()+"\" },");
										}
										else  if(mode == 2)
										{
											writer.println("MATCH (subject {IRI:'"+((OWLClass)c1).getIRI()+"'}), (object {IRI:'"+((OWLClass)c2).getIRI()+"'}) " +
																" MERGE (subject)-[:IsDisjointWith {predicatetype : 'DisjointClasses',predicatename:'IsDisjointWith'}]->(object)");
											
										}
							        }
								}
							}
							queue.clear();
							break;
			case "ClassAssertion": 
							ClassAssertion = false;
							object= queue.remove();
							subject = queue.remove();
							queue.clear();
							if(mode == 1)
							{
								System.out.println("{ subject : \""+((OWLNamedIndividual)subject).getIRI()+"\", predicate : \"IsA\", predicatetype : \"ClassAssertion\", object : \""+((OWLClass)object).getIRI()+"\" },");
							}
							else  if(mode == 2)
							{
								writer.println("MATCH (subject {IRI:'"+((OWLNamedIndividual)subject).getIRI()+"'}), (object {IRI:'"+((OWLClass)object).getIRI()+"'}) " +
													" MERGE (subject)-[:IsA {predicatetype : 'ClassAssertion',predicatename:'IsA'}]->(object)");
								
								
							}
							break;
			case "ObjectPropertyAssertion": 
							ObjectPropertyAssertion = false;
							predicate= queue.remove();
							subject = queue.remove();
							object= queue.remove();
							queue.clear();
							if(mode == 1)
							{
								System.out.println("{ subject : \""+((OWLNamedIndividual)subject).getIRI()+"\", predicate : \""+((OWLObjectProperty)predicate).getIRI()+"\", predicatetype : \"ObjectPropertyAssertion\", object : \""+((OWLNamedIndividual)object).getIRI()+"\" },");
							}
							else  if(mode == 2)
							{
								writer.println("MATCH (subject {IRI:'"+((OWLNamedIndividual)subject).getIRI()+"'}), (object {IRI:'"+((OWLNamedIndividual)object).getIRI()+"'}) " +
													" MERGE (subject)-[:"+(((OWLObjectProperty)predicate).getIRI()).substring(1)+" {predicatetype : 'ObjectPropertyAssertion',predicatename: '"+(((OWLObjectProperty)predicate).getIRI()).substring(1)+"'}]->(object)");
								
							}
							break;
			case "DataPropertyAssertion": 
							DataPropertyAssertion = false; 
							predicate= queue.remove();
							subject = queue.remove();
							object= queue.remove();
							queue.clear();
							if(mode == 1)
							{
								System.out.println("{ subject : \""+((OWLNamedIndividual)subject).getIRI()+"\", predicate : \""+((OWLDataProperty)predicate).getIRI()+"\", predicatetype : \"DataPropertyAssertion\", object : \""+((OWLLiteral)object).getValue()+"\" },");
							}
							else  if(mode == 2)
							{
								writer.println("MATCH (node {IRI:'"+((OWLNamedIndividual)subject).getIRI()+"'}) " +
													" SET node."+(((OWLDataProperty)predicate).getIRI()).substring(1)+" = '"+((OWLLiteral)object).getValue()+"', " +
													" node.predicatetype = 'DataPropertyAssertion'" );
								
							}
							break;
			case "ObjectPropertyDomain": 
							ObjectPropertyDomain = false;
							subject = queue.remove();
							object= queue.remove();
							queue.clear();
							if(mode == 1)
							{
								System.out.println("{ subject : \""+((OWLObjectProperty)subject).getIRI()+"\", predicate : \"HasDomainOf\", predicatetype : \"ObjectPropertyDomain\", object : \""+((OWLClass)object).getIRI()+"\" },");
							}
							else  if(mode == 2)
							{
								writer.println("MATCH (subject {IRI:'"+((OWLObjectProperty)subject).getIRI()+"'}), (object {IRI:'"+((OWLClass)object).getIRI()+"'}) " +
													" MERGE (subject)-[:HasDomainOf {predicatetype : 'ObjectPropertyDomain',predicatename:'HasDomainOf'}]->(object)");
								
							}
							break;
			case "ObjectPropertyRange": 
							ObjectPropertyRange = false; 
							subject = queue.remove();
							object= queue.remove();
							queue.clear();
							if(mode == 1)
							{
								System.out.println("{ subject : \""+((OWLObjectProperty)subject).getIRI()+"\", predicate : \"HasRangeOf\", predicatetype : \"ObjectPropertyRange\", object : \""+((OWLClass)object).getIRI()+"\" },");
							}
							else  if(mode == 2)
							{
								writer.println("MATCH (subject {IRI:'"+((OWLObjectProperty)subject).getIRI()+"'}), (object {IRI:'"+((OWLClass)object).getIRI()+"'}) " +
													" MERGE (subject)-[:HasRangeOf {predicatetype : 'ObjectPropertyRange',predicatename : 'HasRangeOf'}]->(object)");
								
							}
							break;
			case "DataPropertyDomain": 
							DataPropertyDomain = false; 
							subject = queue.remove();
							object= queue.remove();
							queue.clear();
							if(mode == 1)
							{
								System.out.println("{ subject : \""+((OWLDataProperty)subject).getIRI()+"\", predicate : \"HasDomainOf\", predicatetype : \"DataPropertyDomain\", object : \""+((OWLClass)object).getIRI()+"\" },");
							}
							else  if(mode == 2)
							{
								writer.println("MATCH (subject {IRI:'"+((OWLDataProperty)subject).getIRI()+"'}), (object {IRI:'"+((OWLClass)object).getIRI()+"'}) " +
													" MERGE (subject)-[:HasDomainOf {predicatetype : 'DataPropertyDomain',predicatename:'HasDomainOf'}]->(object)");
								
							}
							break;
			case "DataPropertyRange": 
							DataPropertyRange = false; 
							subject = queue.remove();
							object= queue.remove();
							queue.clear();
							if(mode == 1)
							{
								System.out.println("{ subject : \""+((OWLDataProperty)subject).getIRI()+"\", predicate : \"HasRangeOf\", predicatetype : \"DataPropertyRange\", object : \""+((OWLDatatype)object).getabbreviatedIRI()+"\" },");
							}
							else  if(mode == 2)
							{
								writer.println("MATCH (subject {IRI:'"+((OWLDataProperty)subject).getIRI()+"'}), (object {IRI:'"+((OWLDatatype)object).getabbreviatedIRI()+"'}) " +
													" MERGE (subject)-[:HasRangeOf {predicatetype : 'DataPropertyRange',predicatename:'HasRangeOf'}]->(object)");
								
							}
							break;
			case "InverseObjectProperties" : 
							InverseObjectProperties = false; 
							subject = queue.remove();
							object = queue.remove();
							queue.clear();
							if(mode == 1)
							{
								System.out.println("{ subject : \""+((OWLObjectProperty)subject).getIRI()+"\", predicate : \"IsInverseObjectPropertyOf\", predicatetype : \"InverseObjectProperties\", object : \""+((OWLObjectProperty)object).getIRI()+"\" },");
							}
							else  if(mode == 2)
							{
								writer.println("MATCH (subject:ObjectProperty), (object:ObjectProperty) " +
													" WHERE subject.IRI='"+((OWLObjectProperty)subject).getIRI()+"' and object.IRI='"+((OWLObjectProperty)object).getIRI()+"'" +
													" MERGE (subject)-[:IsInverseObjectPropertyOf {predicatetype : 'InverseObjectProperties',predicatename:'IsInverseObjectPropertyOf'}]->(object)");
								writer.println("MATCH (subject:ObjectProperty), (object:ObjectProperty) " +
													" WHERE subject.IRI='"+((OWLObjectProperty)object).getIRI()+"' and object.IRI='"+((OWLObjectProperty)subject).getIRI()+"'" +
													" MERGE (subject)-[:IsInverseObjectPropertyOf {predicatetype : 'InverseObjectProperties',predicatename:'IsInverseObjectPropertyOf'}]->(object)");
								
							};
			case "Class": Class = false; break;
			case "ObjectProperty": ObjectProperty = false; break;
			case "DataProperty": DataProperty = false; break;
			case "NamedIndividual": NamedIndividual = false; break;
			case "Literal": Literal = false; break;
			case "Datatype": Datatype = false; break;
			case "Ontology": 
							writer.close();
							fireAllQueries();
							startInferencing();							
							break;
		}
		inferenceRules();
    }
	
	private void startInferencing() 
	{
		setOutputFilePath(OWLParser.cypherFileName);
		inferenceRules();
		generateSameIndividualInference();
		generateInverseObjectPropertyInference();
		generateSubObjectPropertyInference();
		writer.close();
		fireAllQueries();
	}

	private void fireAllQueries() 
	{
		try
		{
			  Neo4JOperater neodb = new Neo4JOperater();
			  neodb.setInputFilePath(OWLParser.cypherFileName);
			  neodb.executeQueries();
			  System.out.println("Done...");
			} catch (Exception e) 
			{
				System.out.println(" NEO4J Error : "+e.getMessage());
			}
	}

	@Override
	public void characters(char ch[],int start, int length) throws SAXException 
	{
		if(Literal)
		{
			lit.setValue(new String(ch, start, length));
			queue.add(lit);
		}
	}

	public void inferenceRules()
	{
		writer.println("MATCH (a)-[r1:IsA]->(b),(b)-[r2:SubClassOf]->(c) MERGE (a)-[r3:IsA]->(c)");
		writer.println("MATCH (a)-[r1:IsDisjointWith]->(b),(b)-[r2:IsEquivalentTo]->(c) MERGE (a)-[r3:IsDisjointWith]->(c)");
		writer.println("MATCH (a)-[r1:IsEquivalentTo]->(b),(b)-[r2:IsEquivalentTo]->(c) MERGE (a)-[r3:IsEquivalentTo]->(c)");
		writer.println("MATCH (a)-[r1:IsA]->(b),(b)-[r2:IsEquivalentTo]->(c) MERGE (a)-[r3:IsA]->(c)");
		writer.println("MATCH (a)-[r1:SubClassOf]->(b),(b)-[r2:IsEquivalentTo]->(c) MERGE (a)-[r3:SubClassOf]->(c)");
		writer.println("MATCH (a)-[r1:HasDomainOf]->(b),(b)-[r2:IsEquivalentTo]->(c) MERGE (a)-[r3:HasDomainOf]->(c)");
		writer.println("MATCH (a)-[r1:HasRangeOf]->(b),(b)-[r2:IsEquivalentTo]->(c) MERGE (a)-[r3:HasRangeOf]->(c)");
		writer.println("MATCH (a)-[r1:IsDisjointWith]->(b),(c)-[r2:IsA]->(a) MERGE (c)-[r3:IsNotA]->(b)");
	}
	public void generateSameIndividualInference()
	{
		ArrayList<String> lst;
		writer.println("MATCH (a)-[r1:IsSameIndividualAs]->(b),(a)-[r2:IsA]->(c) MERGE (b)-[r3:IsA]->(c)");
		writer.println("MATCH (a)-[r1:IsSameIndividualAs]->(b),(a)-[r2:IsNotA]->(c) MERGE (b)-[r3:IsNotA]->(c)");
		Neo4JOperater neodb = new Neo4JOperater();  
		lst = neodb.getAllObjectProperties();
		if(lst != null)
		{
			for (String str : lst) 
			{
				writer.println("MATCH (a)-[r1:IsSameIndividualAs]->(b),(a)-[r2:"+str+"]->(c) MERGE (b)-[r3:"+str+"]->(c)");
				writer.println("MATCH (a)-[r1:IsSameIndividualAs]->(b),(c)-[r2:"+str+"]->(a) MERGE (c)-[r3:"+str+"]->(b)");
			}
		}
		ArrayList<ArrayList<String>> sameIndividualPairs =  neodb.getAllSameIndividualPairs();
		
		for (ArrayList<String> spair : sameIndividualPairs) 
		{
			HashMap<String,String> a = null;
			HashMap<String,String> b = null; 
			a = neodb.getAlldataPropertiesForGivenIRI(spair.get(0));
			b = neodb.getAlldataPropertiesForGivenIRI(spair.get(1));

			for (String key : a.keySet()) 
			{
				if(b.containsKey(key))
				{
					
				}
				else
				{
					b.put(key, a.get(key));
					writer.println("MATCH (a)-[r1:IsSameIndividualAs]->(b) WHERE a.IRI=\""+spair.get(0)+"\" and b.IRI=\""+spair.get(1)+"\" SET b."+key+" = \""+a.get(key)+"\"");
				}
			}
		}
		
	}
	public void generateInverseObjectPropertyInference()
	{
		ArrayList<ArrayList<String>> inverseObjectPropertiesPairs;
		Neo4JOperater neodb = new Neo4JOperater();  
		inverseObjectPropertiesPairs = neodb.getAllInverseObjectProperties();
		for (ArrayList<String> spair : inverseObjectPropertiesPairs) 
		{
			writer.println("MATCH (a)-[r1:"+spair.get(0)+"]->(b) MERGE (b)-[r2:"+spair.get(1)+"]->(a)");
		}
	}
	public void generateSubObjectPropertyInference()
	{
		ArrayList<ArrayList<String>> subObjectPropertiesPairs;
		Neo4JOperater neodb = new Neo4JOperater();  
		subObjectPropertiesPairs = neodb.getAllSubObjectProperties();
		for (ArrayList<String> spair : subObjectPropertiesPairs) 
		{
			writer.println("MATCH (a)-[r1:"+spair.get(0)+"]->(b) MERGE (a)-[r2:"+spair.get(1)+"]->(b)");
		}
	}
}
