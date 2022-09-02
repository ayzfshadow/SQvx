package com.saki.service;

import android.util.Log;
import com.saki.tool.ByteReader;

public class Tlvparse
{

    ByteReader b;

    public class tlvdata
	{
        public int a;
        public byte[] b;
        public tlvdata()
		{

        }
    }

    public Tlvparse(byte[] bArr)
	{
        
        this.b = new ByteReader(bArr);
	}

    public tlvdata a()
	{
        if (this.b.hasMore())
        {
            tlvdata aVar = new tlvdata();
            aVar.a = (int) this.b.readInt();
            aVar.b = this.b.readBytes((int)b.readInt());
            return aVar;
        }
        return null;
    }
    
    public void destroy(){
        this.b.destroy();
    }
}
