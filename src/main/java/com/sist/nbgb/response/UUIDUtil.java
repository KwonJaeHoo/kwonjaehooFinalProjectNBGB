package com.sist.nbgb.response;

import java.util.UUID;

public class UUIDUtil 
{
	public static String uniqueValue()
	{
		return replace(UUID.randomUUID().toString(), "-", "");
	}
	
	public static String replace(String str, String oldPattern, String newPattern)
	{
		if (!isEmpty(str) && !isNull(oldPattern) && !isNull(newPattern))
		{
			StringBuilder sb = new StringBuilder();

			int pos = 0;
			int index = str.indexOf(oldPattern);
			int patLen = oldPattern.length();

			while (index >= 0)
			{
				sb.append(str.substring(pos, index));
				sb.append(newPattern);
				pos = index + patLen;
				index = str.indexOf(oldPattern, pos);
			}

			sb.append(str.substring(pos));

			return sb.toString();
		}
		else
		{
			return str;
		}
		
		
	}
	
	public static boolean isEmpty(String str)
	{
		if (nvl(str).length() > 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public static boolean isNull(String str)
	{
		return isNull((Object) str);
	}
	
	public static boolean isNull(Object obj)
	{
		if (obj != null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public static String nvl(String str)
	{
		return nvl(str, "");
	}
	
	public static String nvl(String str, String def)
	{
		if (str != null && str.length() > 0)
		{
			return str;
		}
		else
		{
			return def;
		}
	}
}
