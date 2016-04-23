package ValueObjects;

public class OWLDataProperty 
{
	String type;
	String IRI;
	
	public OWLDataProperty(String IRI)
	{
		this.type = "DataProperty";
		this.IRI = IRI;
	}
	public String getIRI()
	{
		return this.IRI;
	}
}
