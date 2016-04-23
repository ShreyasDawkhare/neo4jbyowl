package ValueObjects;

public class OWLObjectProperty 
{
	String type;
	String IRI;
	
	public OWLObjectProperty(String IRI)
	{
		this.type = "ObjectProperty";
		this.IRI = IRI;
	}
	public String getIRI()
	{
		return this.IRI;
	}
}
