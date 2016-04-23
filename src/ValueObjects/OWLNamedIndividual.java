package ValueObjects;

public class OWLNamedIndividual 
{
	String type;
	String IRI;
	
	public OWLNamedIndividual(String IRI)
	{
		this.type = "NamedIndividual";
		this.IRI = IRI;
	}
	public String getIRI()
	{
		return this.IRI;
	}
}
