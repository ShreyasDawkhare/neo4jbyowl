package ValueObjects;

public class OWLDatatype 
{
	String type;
	String abbreviatedIRI;
	
	public OWLDatatype(String abbreviatedIRI)
	{
		this.type = "Datatype";
		this.abbreviatedIRI = abbreviatedIRI;
	}
	public String getabbreviatedIRI()
	{
		return this.abbreviatedIRI;
	}
}
