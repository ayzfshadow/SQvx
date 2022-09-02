package com.saki.qq.datapacket.pack.send;

import com.saki.loger.LogcatLoger;
import com.saki.qq.datapacket.pack.ByteWriter;
import com.saki.qq.userinfo.User;
import com.saki.tool.HexTool;
import com.saki.tool.TeaCryptor;

public abstract class SendPack {
    private byte cmdtype = 10;
    private byte type = 1;
    private byte[] token0044;
    protected int seq = -1;
    private long uin = User.uin;
    ByteWriter pack = new ByteWriter();
    protected String command;
    protected byte[] msgcookie = {113, 8, 62, -19};
    private long appsubid = -1;
    private boolean dud = true;
    private byte[] token004c;
    protected String imei;
    private byte[] token0014;
    private String verssion;
    private byte[] key;
    private byte[] publickey;
    private byte[] cmd;
    private int seqid = -1;

    public SendPack(String str) {
        System.out.println("send: "+str);
        this.command = str;
    }

    private void packCMD(byte b2, byte b3) {
        this.pack.writeByte((byte)0);
        this.pack.writeByte((byte)0);
        this.pack.writeByte((byte)0);
        this.pack.writeByte( b2);
        this.pack.writeByte( b3);
    }
	
	protected void setcmdtype(int p0)
	{
		this.cmdtype=(byte)p0;
	}
	
    private void packAppSub(long j2, boolean z) {
        if (j2 != -1) {
            this.pack.writeInt(j2);
            if (z) {
                this.pack.writeInt(j2);
            } else {
                this.pack.writeByte((byte)-1);
            }
            this.pack.writeBytes((byte)1, (byte)0, (byte)0, (byte)0);
            this.pack.writeInt(0);
            this.pack.writeInt(0);
        }
    }

    private void packITV(String str, byte[] bArr, String str2) {
        if (str != null && str2 != null) {
            packimei(str);
            packtoken0014(bArr);
            packversion(str2);
        }
    }

    private void packToken44(byte[] bArr, int i2) {
       // Log.d("seq",i2+"");
        if (bArr != null) {
            this.pack.writeInt(bArr.length + 4);
            this.pack.writeBytes(bArr);
        } else if (i2 != -1) {
            this.pack.writeInt(i2);
        } else {
            this.pack.writeInt(4);
        }
    }

    private void encodeContent(byte[] bArr, byte[] bArr2) {
		//Log.d(HexTool.a(bArr2),HexTool.a(bArr));
		TeaCryptor cry= new TeaCryptor();
        this.pack.writeBytes(cry.encrypt(bArr, bArr2));
        cry.destroy();
        this.pack.rewriteBytes(com.saki.tool.HexTool.int2bytes(this.pack.length() + 4));
        
    }

    private void packRequestionId(int i2) {
        if (i2 != -1) {
            LogcatLoger.d("seq",i2+"");
            this.pack.writeInt(i2);
        }
    }

    private void packCommand(String str) {
        this.pack.writeInt(str.length() + 4);
        this.pack.writeBytes(str);
    }

    private void packimei(String str) {
        if (str != null) {
            this.pack.writeInt(str.length() + 4);
            this.pack.writeBytes(str);
        }
    }

    private void packMsgCookie(byte[] bArr) {
        if (bArr != null) {
            this.pack.writeInt(bArr.length + 4);
            this.pack.writeBytes(bArr);
            return;
        }
        this.pack.writeInt(4);
    }

    private void packUin(String str) {
        this.pack.writeBytes((byte)0);
        if (str == null) {
            this.pack.writeByte((byte)0);
            this.pack.writeInt(1328);
            return;
        }
        this.pack.writeInt(str.length() + 4);
        this.pack.writeBytes(str);
    }

    private void packtoken0014(byte[] bArr) {
        if (bArr != null) {
            this.pack.writeInt(bArr.length + 4);
            this.pack.writeBytes(bArr);
            return;
        }
        this.pack.writeInt(4);
    }

    private void packversion(String str) {
        if (str != null) {
            this.pack.writeShort(str.length() + 2);
            this.pack.writeBytes(str);
        }
    }

    public void a(byte b2) {
        this.cmdtype = b2;
    }

    public void setseqid(int i2) {
        this.seqid = i2;
    }
	
	protected void setType(int p0)
	{
		this.type=(byte) p0;
	}

    public void setappid(long j2) {
        this.appsubid = j2;
    }

    public void packContent(long j2, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        if (bArr == null || bArr2 == null || bArr3 == null) {
            this.pack.writeInt(bArr4.length + 4);
           
            this.pack.writeBytes(bArr4);
            return;
        }
        ByteWriter classa = new ByteWriter();
        classa.writeBytes((byte)31,(byte) 65);
        classa.writeBytes(bArr);
        classa.writeBytes((byte)0, (byte)1);
        classa.writeInt(j2);
        classa.writeBytes((byte)3, (byte)7,(byte) 0,(byte) 0,(byte) 0,(byte) 0,(byte) 2,(byte) 0,(byte) 0,(byte) 0,(byte) 0,(byte) 0,(byte) 0,(byte) 0,(byte) 0,(byte) 1,(byte) 1);
        classa.writeBytes(bArr2);
        classa.writeBytes((byte)1, (byte)2);
        classa.writeShort(bArr3.length);
        classa.writeBytes(bArr3);
        classa.writeBytes(bArr4);
        classa.rewriteBytes(com.saki.tool.HexTool.a((short) (classa.getData().length + 4)));
        classa.rewriteBytes(new byte[]{2});
        classa.rewriteBytes(com.saki.tool.HexTool.int2bytes(classa.getData().length + 5));
        classa.writeBytes((byte)3);
        this.pack.writeBytes(classa.getDataAndDestroy());
    }

    public void setImei(String str) {
        this.imei = str;
    }

    /* access modifiers changed from: protected */
    public void packToken4c(byte[] bArr) {
        if (bArr != null) {
            this.pack.writeInt(bArr.length + 4);
            this.pack.writeBytes(bArr);
            return;
        }
        this.pack.writeInt(4);
    }

    /* access modifiers changed from: protected */
    public abstract byte[] getContent();

    /* access modifiers changed from: protected */
    public void packInfo() {
        packRequestionId(this.seqid);
        packAppSub(this.appsubid, this.dud);
        packToken4c(this.token004c);
        packCommand(this.command);
        packMsgCookie(this.msgcookie);
        packITV(this.imei, this.token0014, this.verssion);
        this.pack.writeInt(4);
        this.pack.rewriteBytes(com.saki.tool.HexTool.int2bytes(this.pack.length() + 4));
        packContent();
    }

    public void setSeq(int i2) {
        this.seq = i2;
    }

    public void setVersion(String str) {
        this.verssion = str;
    }

    public void setToken0044(byte[] bArr) {
        this.token0044 = bArr;
    }

    public void setToken004c(byte[] bArr) {
        this.token004c = bArr;
    }

    public byte[] toByteArray() {
        if (this.pack.isEmpty()) {
            packInfo();
            packHead();
        }
        return this.pack.getDataAndDestroy();
    }

    /* access modifiers changed from: protected */
    public abstract byte[] d();

    /* access modifiers changed from: protected */
    public void packContent() {
        packContent(this.uin, this.cmd, this.key, this.publickey, getContent());
    }

    /* access modifiers changed from: protected */
    public void packHead() {
        byte[] b2 = this.pack.getDataAndDestroy();
        this.pack.reCreate();
        packCMD(this.cmdtype, this.type);
        packToken44(this.token0044, this.seq);
        packUin(this.uin == -1 ? null : String.valueOf(this.uin));
        encodeContent(b2, d());
    }
}
