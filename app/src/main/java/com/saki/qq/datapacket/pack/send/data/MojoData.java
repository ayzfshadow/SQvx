package com.saki.qq.datapacket.pack.send.data;

import com.saki.qq.datapacket.pack.protobuff.ProtoBuff;

public class MojoData extends MsgData 
{

    private byte[] a;
    
    public MojoData(String id) throws java.lang.NumberFormatException{
        this.a = ProtoBuff.writeVarint(1, Long.parseLong(id));
        this.a = ProtoBuff.writeLengthDelimit(2, this.a);
        this.a = ProtoBuff.writeLengthDelimit(2, this.a);
    }

    @Override
    public byte[] getBytes()
    {
        return this.a;
    }
}
