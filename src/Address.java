import java.util.regex.*;

public class Address {
	private IP ip;
	private SubnetMask mask;
	private String cidrNotation;
	private IP subnet, broadcast, host;
	private IP firstAddress, secondAddress;
	private static String defaultSubnet = "255.255.255.0";
	
	public Address(String ip) throws InvalidAddressException
	{
		this(ip, defaultSubnet);
	}
	
	public Address(String ip, String mask) throws InvalidAddressException
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
