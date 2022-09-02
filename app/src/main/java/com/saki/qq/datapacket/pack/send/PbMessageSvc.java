package com.saki.qq.datapacket.pack.send;

import com.saki.qq.datapacket.pack.ByteWriter;
import com.saki.qq.datapacket.pack.protobuff.ProtoBuff;

public class PbMessageSvc
{
    public static class PbMsgWithDraw extends T0B01
    {
        private long group;

        private long id;

        private long index;
      
        public PbMsgWithDraw(long group,long id, long index) {
            super("PbMessageSvc.PbMsgWithDraw");

            
            //PbMessageSvc.PbMsgWithDraw
            //com.saki.loger.ViewLoger.info(str);
            this.group=group;
            this.id=id;
            this.index=index;
           
        }

        /* access modifiers changed from: protected */
        public byte[] getContent() {
            ByteWriter classa = new ByteWriter();
            classa.writePb(2,new ByteWriter()
                                  .writePb(1,1)
                                  .writePb(2,0)
                                  .writePb(3,this.group)
                                  .writePb(4,new ByteWriter()
                                           .writePb(1,this.index)
                                           .writePb(2,this.id)
                                           .getDataAndDestroy())
                                  .writePb(5,ProtoBuff.writeVarint(
                                               1,0))
                                  .getDataAndDestroy());
            return classa.getData();
        }
    }
}
