package com.saki.service;

import com.saki.qq.datapacket.pack.send.StatSvc.SimpleGet;

public class HeartBeatThread implements Runnable {
    private com.saki.client.PacketSender a;
    
    public HeartBeatThread(com.saki.client.PacketSender classc) {
        this.a = classc;
    }

    public void run() {

                this.a.sendPacket(new SimpleGet(this.a.b()).toByteArray());
          
        }
    
}
