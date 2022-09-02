package com.saki.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public abstract class SocketServer {
    private DataInputStream a;
    private OutputStream b;
    private Socket c;
    private String d;
    private int e;

    private class a extends Thread {
        private a() {
        }

        public void run() {
            while (true) {
                try {
                    SocketServer.this.onReceive(SocketServer.this.a());
                } catch (IOException e) {
                    SocketServer.this.a(e);
                    return;
                }
            }
        }
    }

    public SocketServer(String str, int i) {
        this.d = str;
        this.e = i;
    }

    public void a(IOException iOException) {
        e();
        iOException.printStackTrace();
    }

    public abstract void onReceive(byte[] bArr);

    public abstract byte[] a() throws IOException;

    /* access modifiers changed from: protected */
    public byte[] a(int i) throws IOException {
        byte[] bArr = new byte[i];
        this.a.readFully(bArr);
        return bArr;
    }

    public void send(byte[] bArr) throws IOException {
        this.b.write(bArr);
        this.b.flush();
    }

    public boolean b() {
        try {
            this.c = new Socket(this.d, this.e);
            this.a = new DataInputStream(this.c.getInputStream());
            new a().start();
            this.b = this.c.getOutputStream();
            return true;
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public byte c() throws IOException {
        return this.a.readByte();
    }

    /* access modifiers changed from: protected */
    public int d() throws IOException {
        return this.a.readInt();
    }

    public void e() {
        try {
            if (this.a != null) {
                this.a.close();
            }
            if (this.b != null) {
                this.b.close();
            }
            if (this.c != null) {
                this.c.close();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
