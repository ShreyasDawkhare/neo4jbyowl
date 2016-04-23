package ValueObjects;

public class OWLClass 
{
	String type;
	String IRI;
	
	public OWLClass(String IRI)
	{
		this.type = "Class";
		this.IRI = IRI;
	}
	public String getIRI()
	{
		return this.IRI;
	}
}
