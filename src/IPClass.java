	class IPClass
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
	