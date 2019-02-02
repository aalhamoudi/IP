
public class SubnetMask {
	private int[] bytes;
	private String[] binary;
	private int subnetBits;

	private int numOfNetworks;
	private int numOfClientsPerNetwork;

	public SubnetMask(String str, IPClass ipClass)
	{
		subnetBits = 0;
		
		String[] parts = str.split("[.]");
		bytes = new int[parts.length];
		binary = new String[parts.length];

		
		for (int i = 0; i < parts.length; i++)
		{
			bytes[i] = new Short(parts[i]);
			
			subnetBits += Integer.bitCount(bytes[i]);
			

		}
			
		
		toBinary();
		
		numOfNetworks = (int) Math.pow(2, subnetBits - ipClass.getNetworkBits() - ipClass.getFixedBits().length());
		numOfClientsPerNetwork = (int) Math.pow(2, 32 - subnetBits) - 2;
		
	}
	


	public int getSubnetBits()
	{
		return subnetBits;
	}

	
	
	public int getNumOfNetworks()
	{
		return numOfNetworks;
	}
	
	public int getNumOfClientsPerNetwork()
	{
		return numOfClientsPerNetwork;
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
