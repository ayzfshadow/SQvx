package com.saki.client;

import com.saki.qq.userinfo.imgstore;
import com.saki.qq.datapacket.pack.send.ImgStore.UP;
import java.io.*;

public class IMGUploader extends com.saki.client.SocketServer {
    imgstore a;
  
    a c;

    public interface a {
        void a(imgstore classc);
    }

    public IMGUploader(imgstore classc,a aVar) {
        super("htdata.qq.com", 8080);
        this.a = classc;
        
        this.c = aVar;
        b();
    }

    public void onReceive(byte[] bArr) {
        this.c.a(this.a);
        e();
    }

    public byte[] a() throws IOException {
        byte c2;
        do {
            c2 = c();
            if (c2 == -1) {
                return null;
            }
        } while (c2 != 40);
        int d = d();
        d();
        return a(d);
    }

    public void send(byte[] bArr) throws IOException{
        super.send(new UP(this.a.a, bArr).a());
    }
}
