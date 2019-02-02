import java.util.regex.Pattern;

public class IPAddress {

	public class IP {
	public class IPClass
	{
		char ipClass;
		int networkBits;


		int hostBits;
		String fixedBits;
		
		public IPClass(int firstByte)
		{
			if (1 < firstByte && firstByte < 126 )
			{
				ipClass = 'A';
				networkBits = 7;
				hostBits = 24;
				fixedBits = "0";
			}
			else if (firstByte >= 128 && firstByte < 191 )
			{
				ipClass = 'B';
				networkBits = 14;
				hostBits = 16;
				fixedBits = "10";
			}
			else if (firstByte >= 192 && firstByte < 223 )
			{
				ipClass = 'C';
				networkBits = 21;
				hostBits = 8;
				fixedBits = "110";
			}
			else if (firstByte >= 224 && firstByte < 239 )
			{
				ipClass = 'D';
				networkBits = 20;
				hostBits = 0;
				fixedBits = "1110";
			}
			else if (firstByte >= 240 && firstByte < 255 )
			{
				ipClass = 'E';
				networkBits = 19;
				hostBits = 0;
				fixedBits = "11110";
			}

		}
		
		public int getNetworkBits() {
			return networkBits;
		}
		
		public int getHostBits() {
			return hostBits;
		}
		
		public String getFixedBits()
		{
			return fixedBits;
		}
		public String toString()
		{
			return new StringBuilder().append(ipClass).toString();
		}
		

		
	}
	
	

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

	public class SubnetMask {
	private int[] bytes;
	private String[] binary;
	private int subnetBits;

	private int numOfNetworks;
	private int numOfClientsPerNetwork;

	public SubnetMask(String str, IP.IPClass ipClass)
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

	private IP ip;
	private SubnetMask mask;
	private String cidrNotation;
	private IP subnet, broadcast, host;
	private IP firstAddress, secondAddress;
	private static String defaultSubnet = "255.255.255.0";
	
	public static void main(String[] args) throws InvalidAddressException
	{
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
		
		IPAddress adr = new IPAddress(ip, mask);
		
		System.out.println(adr);
	}
	
	public IPAddress(String ip) throws InvalidAddressException
	{
		this(ip, defaultSubnet);
	}
	
	public IPAddress(String ip, String mask) throws InvalidAddressException
	{
		String pattern  = "\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}";
		if (!Pattern.matches(pattern, ip))
			throw new InvalidAddressException(ip);
		if (!Pattern.matches(pattern, mask))
			throw new InvalidAddressException(mask);

		this.ip = new IP(ip, true);
		this.mask = new SubnetMask(mask, this.ip.getAddressClass());
		cidrNotation = ip + "/" + (this.mask).getSubnetBits();
		determineSubnetAndBroadcast();
	
		
		String adr = subnet.toString().substring(0, subnet.toString().length() - 1) + "1";
		firstAddress = new IP(adr, true);
		
		adr = broadcast.toString().substring(0, broadcast.toString().length() - 1) + "4";
		secondAddress = new IP(adr, true);
	}
	

	
	

	

	
	public void determineSubnetAndBroadcast()
	{
		String strIP = ip.toBinaryString();
		String strMask = mask.toBinaryString();
		String broadcastMask = "";
		String subnetAddress = "";
		String broadcastAddress = "";
		String hostAddress = "";
		
		for (int i = 0; i< strMask.length(); i++)
		{
			if (strMask.charAt(i) == '.')
				broadcastMask += '.';
			else
				broadcastMask += (Integer.parseInt(new StringBuilder().append((strMask.charAt(i))).toString())) == 0? 1 : 0;
		}
		

		
		for (int i = 0; i < strIP.length(); i++)
		{
			if (strIP.charAt(i) == '.')
			{
				subnetAddress += '.';
				broadcastAddress += '.';
				hostAddress += '.';
			}
			else
			{
				int ip = Integer.parseInt(new StringBuilder().append(strIP.charAt(i)).toString());
				int mask = Integer.parseInt(new StringBuilder().append(strMask.charAt(i)).toString());
				int broadcast = Integer.parseInt(new StringBuilder().append(broadcastMask.charAt(i)).toString());
				
				subnetAddress += ip & mask;
				broadcastAddress += ip | broadcast;
				hostAddress += ip & broadcast; 
			}
			
		}

		subnet = new IP(subnetAddress, false);
		broadcast = new IP(broadcastAddress, false);
		host = new IP(hostAddress, false);
		
	}
	
	
	public String toString()
	{

		return "IP Address (Dotted Decimal Notation): " + ip +
				"\nIP Address (Binary Noation): " + ip.toBinaryString() +
				
				"\n\nSubnet Mask (Dotted Decimal Notation): "+ mask + 
				"\nSubnet Mask (Binary Noation): " + mask.toBinaryString() +
				
				"\n\nSubnet (Network) Address: " + subnet +
				"\nBroadcast Address: " + broadcast +
				"\nHost Address: " + host +
				"\nIP Address Range: " + firstAddress + "-" + secondAddress +
				
				"\n\nAddress Class: " + ip.getAddressClass() +
				"\nCIDR Notation: " + cidrNotation +
				
				"\n\nNumber of Networks: " + String.format("%1$,d", mask.getNumOfNetworks()) +
				"\nNumber of Clients Per Network: " + String.format("%1$,d", mask.getNumOfClientsPerNetwork()) +
				
				"\n\nFixed Bits: " + ip.getAddressClass().getFixedBits() +
				"\nNumber of Network Bits: " + ip.getAddressClass().getNetworkBits() +
				"\nNumber of Host Bits: " + ip.getAddressClass().getHostBits();
	}
}
