package com.saki.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Code {
    public static final byte[] a = new byte[16];

    public static long randomLong()
    {
        return (long) (1000000000 + Math.random() * (1500000000 - 1000000000 + 1));
	}

    public static byte[] md5(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            return instance.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    
	public static  long getGroupUinByGroupCode(long code)
	{
		Object localObject2 = Long.toString(code);
		int i = Integer.parseInt(((String)localObject2).substring(0, ((String)localObject2).length() - 6));
		Object localObject1 = "";
		if ((i >= 1) && (i <= 10))
		{
			localObject2 = ((String)localObject2).substring(((String)localObject2).length() - 6, ((String)localObject2).length());
			localObject1 = new StringBuilder();
			((StringBuilder)localObject1).append(i + 202);
			((StringBuilder)localObject1).append((String)localObject2);
			localObject1 = ((StringBuilder)localObject1).toString();
		}
		else if ((i >= 11) && (i <= 19))
		{
			localObject1 = ((String)localObject2).substring(((String)localObject2).length() - 6, ((String)localObject2).length());
			localObject2 = new StringBuilder();
			((StringBuilder)localObject2).append(i + 469);
			((StringBuilder)localObject2).append((String)localObject1);
			localObject1 = ((StringBuilder)localObject2).toString();
		}
		else if ((i >= 20) && (i <= 66))
		{
			localObject1 = new StringBuilder();
			((StringBuilder)localObject1).append(i);
			((StringBuilder)localObject1).append("");
			i = Integer.parseInt(((StringBuilder)localObject1).toString().substring(0, 1));
			localObject2 = ((String)localObject2).substring(((String)localObject2).length() - 7, ((String)localObject2).length());
			localObject1 = new StringBuilder();
			((StringBuilder)localObject1).append(i + 208);
			((StringBuilder)localObject1).append((String)localObject2);
			localObject1 = ((StringBuilder)localObject1).toString();
		}
		else if ((i >= 67) && (i <= 156))
		{
			localObject2 = ((String)localObject2).substring(((String)localObject2).length() - 6, ((String)localObject2).length());
			localObject1 = new StringBuilder();
			((StringBuilder)localObject1).append(i + 1943);
			((StringBuilder)localObject1).append((String)localObject2);
			localObject1 = ((StringBuilder)localObject1).toString();
		}
		else if ((i >= 157) && (i <= 209))
		{
			localObject1 = new StringBuilder();
			((StringBuilder)localObject1).append(i);
			((StringBuilder)localObject1).append("");
			i = Integer.parseInt(((StringBuilder)localObject1).toString().substring(0, 2));
			localObject2 = ((String)localObject2).substring(((String)localObject2).length() - 7, ((String)localObject2).length());
			localObject1 = new StringBuilder();
			((StringBuilder)localObject1).append(i + 199);
			((StringBuilder)localObject1).append((String)localObject2);
			localObject1 = ((StringBuilder)localObject1).toString();
		}
		else if ((i >= 210) && (i <= 309))
		{
			localObject1 = new StringBuilder();
			((StringBuilder)localObject1).append(i);
			((StringBuilder)localObject1).append("");
			i = Integer.parseInt(((StringBuilder)localObject1).toString().substring(0, 2));
			localObject1 = ((String)localObject2).substring(((String)localObject2).length() - 7, ((String)localObject2).length());
			localObject2 = new StringBuilder();
			((StringBuilder)localObject2).append(i + 389);
			((StringBuilder)localObject2).append((String)localObject1);
			localObject1 = ((StringBuilder)localObject2).toString();
		}
		else if ((i >= 310) && (i <= 335))
		{
			localObject1 = new StringBuilder();
			((StringBuilder)localObject1).append(i);
			((StringBuilder)localObject1).append("");
			i = Integer.parseInt(((StringBuilder)localObject1).toString().substring(0, 2));
			localObject1 = ((String)localObject2).substring(((String)localObject2).length() - 7, ((String)localObject2).length());
			localObject2 = new StringBuilder();
			((StringBuilder)localObject2).append(i + 349);
			((StringBuilder)localObject2).append((String)localObject1);
			localObject1 = ((StringBuilder)localObject2).toString();
		}
		else if ((i >= 336) && (i <= 386))
		{
			localObject1 = new StringBuilder();
			((StringBuilder)localObject1).append(i);
			((StringBuilder)localObject1).append("");
			i = Integer.parseInt(((StringBuilder)localObject1).toString().substring(0, 3));
			localObject2 = ((String)localObject2).substring(((String)localObject2).length() - 6, ((String)localObject2).length());
			localObject1 = new StringBuilder();
			((StringBuilder)localObject1).append(i + 2265);
			((StringBuilder)localObject1).append((String)localObject2);
			localObject1 = ((StringBuilder)localObject1).toString();
		}
		else if ((i >= 387) && (i <= 499))
		{
			localObject1 = new StringBuilder();
			((StringBuilder)localObject1).append(i);
			((StringBuilder)localObject1).append("");
			i = Integer.parseInt(((StringBuilder)localObject1).toString().substring(0, 2));
			localObject2 = ((String)localObject2).substring(((String)localObject2).length() - 7, ((String)localObject2).length());
			localObject1 = new StringBuilder();
			((StringBuilder)localObject1).append(i + 349);
			((StringBuilder)localObject1).append((String)localObject2);
			localObject1 = ((StringBuilder)localObject1).toString();
		}
		else if (i >= 500)
		{
			return code;
		}
		return Long.parseLong((String)localObject1);
	}

	public static long getGroupCodeByGroupUin(long uin)
	{
		String l = Long.toString(uin);
		String str = "";
		if (Integer.parseInt(l.substring(0, 3)) >= 500)
		{
			return uin;
		}
		uin = Integer.parseInt(l.substring(0, l.length() - 6));
		String substring;
		StringBuilder r7;
		StringBuilder stringBuilder;
		if (uin >= 203 && uin <= 212)
		{
			substring = l.substring(l.length() - 6, l.length());
			stringBuilder = new StringBuilder();
			stringBuilder.append(uin - 202);
			stringBuilder.append(substring);
			str = stringBuilder.toString();
		}
		else if (uin >= 480 && uin <= 488)
		{
			substring = l.substring(l.length() - 6, l.length());
			stringBuilder = new StringBuilder();
			stringBuilder.append(uin - 469);
			stringBuilder.append(substring);
			str = stringBuilder.toString();
		}
		else if (uin >= 2100 && uin <= 2146)
		{
			r7 = new StringBuilder();
			r7.append(uin);
			r7.append("");
			uin = Integer.parseInt(r7.toString().substring(0, 3));
			substring = l.substring(l.length() - 7, l.length());
			stringBuilder = new StringBuilder();
			stringBuilder.append(uin - 208);
			stringBuilder.append(substring);
			str = stringBuilder.toString();
		}
		else if (uin >= 2010 && uin <= 2099)
		{
			substring = l.substring(l.length() - 6, l.length());
			stringBuilder = new StringBuilder();
			stringBuilder.append(uin - 1943);
			stringBuilder.append(substring);
			str = stringBuilder.toString();
		}
		else if (uin >= 2147 && uin <= 2199)
		{
			r7 = new StringBuilder();
			r7.append(uin);
			r7.append("");
			uin = Integer.parseInt(r7.toString().substring(0, 3));
			substring = l.substring(l.length() - 7, l.length());
			stringBuilder = new StringBuilder();
			stringBuilder.append(uin - 199);
			stringBuilder.append(substring);
			str = stringBuilder.toString();
		}
		else if (uin >= 2601 && uin <= 2651)
		{
			r7 = new StringBuilder();
			r7.append(uin);
			r7.append("");
			uin = Integer.parseInt(r7.toString().substring(0, 4));
			substring = l.substring(l.length() - 6, l.length());
			stringBuilder = new StringBuilder();
			stringBuilder.append(uin - 2265);
			stringBuilder.append(substring);
			str = stringBuilder.toString();
		}
		else if (uin >= 4100 && uin <= 4199)
		{
			r7 = new StringBuilder();
			r7.append(uin);
			r7.append("");
			uin = Integer.parseInt(r7.toString().substring(0, 3));
			substring = l.substring(l.length() - 7, l.length());
			stringBuilder = new StringBuilder();
			stringBuilder.append(uin - 389);
			stringBuilder.append(substring);
			str = stringBuilder.toString();
		}
		else if (uin >= 3800 && uin <= 3825)
		{
			r7 = new StringBuilder();
			r7.append(uin);
			r7.append("");
			uin = Integer.parseInt(r7.toString().substring(0, 3));
			substring = l.substring(l.length() - 7, l.length());
			stringBuilder = new StringBuilder();
			stringBuilder.append(uin - 349);
			stringBuilder.append(substring);
			str = stringBuilder.toString();
		}
		else if (uin >= 3877 && uin <= 3989)
		{
			r7 = new StringBuilder();
			r7.append(uin);
			r7.append("");
			uin = Integer.parseInt(r7.toString().substring(0, 3));
			substring = l.substring(l.length() - 7, l.length());
			stringBuilder = new StringBuilder();
			stringBuilder.append(uin - 349);
			stringBuilder.append(substring);
			str = stringBuilder.toString();
		}
		return Long.parseLong(str);
	}
    
}

