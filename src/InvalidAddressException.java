
@SuppressWarnings("serial")
public class InvalidAddressException extends Exception {

	String address;
	
	public InvalidAddressException(String adr)
	{
		address = adr;
	}
	
	public String toString()
	{
		return "Invalid Address: " + address;
	}
}
