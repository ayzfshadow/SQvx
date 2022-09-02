package com.saki.qq.datapacket.pack.send.data;

import android.util.Log;
import com.saki.loger.DebugLoger;
import com.saki.loger.ViewLoger;
import com.saki.qq.datapacket.pack.send.MessageSvc.PbSendMsg;
import com.saki.qq.userinfo.imgstore;
import java.util.Collections;

public abstract class MsgData implements Comparable<MsgData>
{
    private int a;

    private static class a implements com.saki.client.classd.a
    {
        imgstore a;
        private PbSendMsg b;
        private com.saki.client.PacketSender c;
        private int d;
        private int e;

        public a(com.saki.client.PacketSender classc, int i, int i2, PbSendMsg bVar, imgstore classc2)
        {
            this.a = classc2;
            this.b = bVar;
            this.c = classc;
            this.d = i2;
            this.e = i;
        }

        public void a(com.saki.client.classd classd)
        {
            DebugLoger.log(this, "上传图片");
            if (this.b.d == 2)
            {
                classd.a(this.b.c, this.a, this);
            }
        }

        public boolean a(imgstore classc)
        {
            if (classc != this.a)
            {
                return false;
            }
            this.b.addMsg(new ImgData(classc).a(this.d));
            DebugLoger.log(this, "上传完毕!" + this.b.e.size() + "|" + this.e);
            if (this.b.e.size() == this.e)
            {
                DebugLoger.log(this, "发送消息");
                Collections.sort(this.b.e);
                this.c.sendPacket(this.b.toByteArray());
            }
            return true;
        }
    }

    public static MsgData a(com.saki.client.PacketSender classc, com.saki.client.classd classd, int i, int i2, PbSendMsg bVar, String str, String str2)
    {
        char c = 65535;
        switch (str.hashCode())
        {
            case 3123:
                if (str.equals("at"))
                {
                    c = 3;
                    break;
                }
                break;
            case 104387:
                if (str.equals("img"))
                {
                    c = 4;
                    break;
                }
                break;
            case 108417:
                if (str.equals("msg"))
                {
                    c = 0;
                    break;
                }
                break;
            case 111344:
                if (str.equals("ptt"))
                {
                    c = 5;
                    break;
                }
                break;
            case 118807:
                if (str.equals("xml"))
                {
                    c = 1;
                    break;
                }
                break;
            case 3271912:
                if (str.equals("json"))
                {
                    c = 2;
                    break;
                }
                break;
            case 3357287:
                if (str.equals("mojo"/*mojo*/))
                {
                    c = 6;
                    break;
                }
                break;
        }
        switch (c)
        {
            case 0:
                return new com.saki.qq.datapacket.pack.send.data.TextData(str2).a(i2);
            case 1:
                return new XmlData(str2).a(i2);
            case 2:
                return new com.saki.qq.datapacket.pack.send.data.JsonData(str2).a(i2);
            case 3:
                String[] split = str2.split("@");
                if (split.length == 2)
                {
					if(Long.parseLong(split[0])==0){
						return new com.saki.qq.datapacket.pack.send.data.AtAllData("@" + split[1]).a(i2);
						
					}else{
						return new com.saki.qq.datapacket.pack.send.data.AtData("@" + split[1], Long.parseLong(split[0])).a(i2);

					}
                }
                break;
            case 4:
                imgstore a2 = str2.indexOf("http") == 0 ? com.saki.qq.userinfo.imgstore.b(str2) : com.saki.qq.userinfo.imgstore.a(str2);
                if (a2 != null)
                {
                    new a(classc, i, i2, bVar, a2).a(classd);
                    break;
                }
                break;
            case 5:
                Log.d("ptt", str2);
                if (str2 == null || str2.isEmpty())
                {
                    return null;
                }
                try
                {
                    com.saki.client.classf.voiceinfo aVar = new com.saki.client.classf.voiceinfo(str2, str2.substring(str2.lastIndexOf(".")), bVar.c);
                    classc.sendPacket(new com.saki.qq.datapacket.pack.send.PttStore.GroupPttUp(aVar.mark, bVar.c, (int) aVar.d, aVar.c, aVar.a, aVar.b, str2.substring(str2.lastIndexOf("."))).toByteArray());
                }
                catch (Exception e2)
                {
                    com.saki.loger.ViewLoger.erro(e2.getMessage());
                    e2.printStackTrace();
                    return null;
                }
                break;
            case 6:
                try
                {
                    return  new com.saki.qq.datapacket.pack.send.data.MojoData(str2).a(i2);
                }
                catch (java.lang.NumberFormatException e)
                {
                    ViewLoger.erro("高级词库发生错误: "+e.getMessage());
                    return null;
                }
                

        }
        return null;
    }

    public static void b(com.saki.client.PacketSender classc, com.saki.client.classd classd, PbSendMsg bVar, int i, int i2, String str)
    {
        bVar.addMsg(a(classc, classd, i, i2, bVar, str, ""));
        if (bVar.e.size() == i)
        {
            classc.sendPacket(bVar.toByteArray());
        }
    }


    public static void addPart(com.saki.client.PacketSender classc, com.saki.client.classd classd, PbSendMsg bVar, int i, int i2, String str, String str2)
    {
        bVar.addMsg(a(classc, classd, i, i2, bVar, str, str2));
        if (bVar.e.size() == i)
        {
            classc.sendPacket(bVar.toByteArray());
        }
    }

    /* renamed from: a */
    public int compareTo(MsgData classd)
    {
        return this.a - classd.a;
    }

    public MsgData a(int i)
    {
        this.a = i;
        return this;
    }

    public abstract byte[] getBytes();
}
