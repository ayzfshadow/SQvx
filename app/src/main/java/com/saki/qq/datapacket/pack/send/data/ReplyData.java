package com.saki.qq.datapacket.pack.send.data;

import com.saki.qq.datapacket.pack.ByteWriter;
import com.saki.qq.datapacket.pack.protobuff.ProtoBuff;

public class ReplyData extends MsgData
{
    private byte[] a;

    public ReplyData(String msg,long msgid, long msgindex, long msgtime)
    {
        this.a = new ByteWriter()
            .writePb(2, new ByteWriter()
                     .writePb(45,
                              new ByteWriter()
                              .writePb(1, msgindex)
                              .writePb(2, msgid)
                              .writePb(3, msgtime)
                              .writePb(4, 1)
                              .writePb(5, new ByteWriter()
                                       .writePb(1, ProtoBuff.writeLengthDelimit(1, msg.getBytes()))
                                       .getDataAndDestroy())
                              .writePb(6, 0)  
                              .getDataAndDestroy())
                     .getDataAndDestroy())
            .getDataAndDestroy();
    }

    public byte[] getBytes()
    {
        return this.a;
    }

}
