import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) throws InvalidAddressException {
		String ip, mask;
		
		if (args.length < 2)
		{
			ip = "192.168.1.1";
			mask = "255.255.255.0";	
		}
		
		else
		{
			ip = args[0];
			mask = args[1];
		}
		
		Address adr = new Address(ip, mask);
		
		System.out.println(adr);

		

		
		
	}

}
