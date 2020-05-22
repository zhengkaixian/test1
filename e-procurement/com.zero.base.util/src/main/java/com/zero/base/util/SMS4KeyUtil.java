package com.zero.base.util;

public class SMS4KeyUtil {
	public static  byte[]  string2Bytes(String str)
	{
		if(!StringUtil.isEmpty(str)){
			byte[] dbyte = new byte[str.length() / 2];
		    for ( int i = 0; i < str.length() / 2; i++)
		    {
		    	String temp = str.substring(i * 2, i * 2+2);
		        dbyte[i] = formatByte(temp);
		        if (dbyte[i] == -1)
		        {
		            dbyte[0] = -1;
		            return dbyte;
		        }
		    }
		    return dbyte;
		}else{
			return null;
		}
	}
	 private static byte  formatByte(String keyStr)
	    {
		   
		 byte result = 0 ;
		 if(!StringUtil.isEmpty(keyStr)){
		 	int val1 = (int)keyStr.charAt(0);
		 	if (val1 >= 48 && val1 <= 57)
		        val1 -= 48;
		        else if (val1 >= 65 && val1 <= 70)
		        val1 -= 55;
		        else if (val1 >= 97 && val1 <= 102)
		        val1 -= 87;
		        else
		        {
		            return -1;
		        }
	        int val2 = (int)keyStr.charAt(1);
	        if (val2 >= 48 && val2 <= 57)
		        val2 -= 48;
		        else if (val2 >= 65 && val2 <= 70)
		        val2 -= 55;
		        else if (val2 >= 97 && val2 <= 102)
		        val2 -= 87;
		        else
		        {
		            return -1;
		        }
	         result = (byte)(val1 * 16 + val2);
		 }
	        return result;
	    }
	
}
