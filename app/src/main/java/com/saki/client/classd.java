package com.saki.client;

import com.saki.loger.DebugLoger;
import com.saki.qq.userinfo.imgstore;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import android.util.Log;
import com.saki.tool.HexTool;

public class classd implements com.saki.client.IMGUploader.a {
    com.saki.client.PacketSender a;
    public static HashMap<Integer,imgstore> cache=new HashMap<Integer,imgstore>();
   
    public interface a {
        boolean a(imgstore classc);
    }

    public classd(com.saki.client.PacketSender classc) {
        this.a = classc;
    }

    public imgstore getrecord(int seq) {
        
     return cache.get(seq);
    }

    public void a(long j,imgstore classc, a aVar) {
        classc.v=aVar;
        this.a.sendPacket(new com.saki.qq.datapacket.pack.send.ImgStore.GroupPicUp(classc.mark,j, classc).toByteArray());
    }

    public void a(imgstore classc) {
         classc.v.a(classc);
    }

    public void ProcessPicup(int seq,com.saki.qq.datapacket.unpack.ReImgStore.GroupPicUp aVar) {
        imgstore a2 = getrecord(seq);
        a2.g = aVar.id;
        if (aVar.unknowData == null) {
            DebugLoger.log(this, "该图片无需上传");
            a(a2);
            return;
        }
        
        DebugLoger.log(this, "正在上传...");
        try {
            new IMGUploader(a2, this).send(aVar.unknowData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
