
public class IP {
	
	

	private int[] bytes;
	private String[] binary;
	private IPClass addressClass;

	public IP(String ip, boolean decimal)
	{
		String[] parts = ip.split("[.]");
		bytes = new int[parts.length];
		binary = new String[parts.length];


		if (decimal)
		{
			for (int i = 0; i < parts.length; i++)
				bytes[i] = new Integer(parts[i]);
			toBinary();
		}
		else
		{
			for (int i = 0; i < parts.length; i++)
				binary[i] = parts[i];
			toDecimal();


		}
		
		addressClass = new IPClass(bytes[0]);

		
	}
	


	
	
	public IPClass getAddressClass()
	{
		return addressClass;
	}
	
	public void toBinary()
	{
		for (int i = 0; i < bytes.length; i++)
		{
			binary[i] = Integer.toBinaryString(bytes[i]);
			
			while (binary[i].length() < 8)
				binary[i] = "0" + binary[i];
		}
	}
	
	private void toDecimal() {
		
		for (int i = 0; i < binary.length; i++)
		{
			bytes[i] = Integer.parseInt(binary[i], 2);
			

		}
		
	}
	public String toString()
	{
		String str = "";
		
		for (int i = 0; i < bytes.length; i++)
			str += bytes[i] + ".";
		
		return str.substring(0, str.length() - 1);
	}
	
	public String toBinaryString()
	{
		String str = "";
		
		for (int i = 0; i < binary.length; i++)
			str += binary[i] + ".";
		
		return str.substring(0, str.length() - 1);
	}
}
