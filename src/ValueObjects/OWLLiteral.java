package ValueObjects;

public class OWLLiteral 
{
	String type;
	String datatypeIRI;
	String value;
	
	public OWLLiteral()
	{
		this.type = "Literal";
	}
	public void setDatatypeIRI(String datatypeIRI)
	{
		this.datatypeIRI = datatypeIRI;
	}
	public void setValue(String value)
	{
		this.value = value;
	}
	public String getValue()
	{
		return this.value;
	}
	public String getDatatypeIRI()
	{
		return this.datatypeIRI;
	}
}
