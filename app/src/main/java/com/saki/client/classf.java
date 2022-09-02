package com.saki.client;

import android.util.Log;
import com.saki.http.HTTP;
import com.saki.qq.datapacket.pack.send.PttStore;
import com.saki.qq.userinfo.User;
import com.saki.tool.HexTool;
import com.saki.util.FileUtil;
import com.zly.media.silk.PCMEncoder;
import com.zly.media.silk.SilkEncoder;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Random;

public class classf {
    static HashMap<Integer,voiceinfo> cache=new HashMap<Integer,voiceinfo>();

    public static class voiceinfo {
        public String a;
        public byte[] b;
        public String c;
        public long d;
        public byte[] e;
        public long f;
        public long mark;
        public String downloadvoice(String url,String sufix, boolean p1, int p2, int p3) throws Exception
        {
            if(sufix.equals("silk")){

            }else if(sufix.equals("amr")){

            }else{
                sufix="mp3";
            }
            Log.d("sufix",sufix);
            File file = new File("/sdcard/SQ/cache");
            if(!file.exists()){
                file.mkdir();
            }
            byte[] e =new HTTP().httpGet(url, true, 3000, 30000, new String[0]);
            String rand = getRandomString(6);
            String filepath = "/sdcard/SQ/cache/"+rand+"."+sufix;
            FileOutputStream in = new FileOutputStream(filepath);
            in.write(e);
            return filepath;
            
        }
        
        public void pcmToSilk(String inputPath,String outputPath){
            SilkEncoder.nativeTransPCM2Silk(inputPath,24000,outputPath);
        }
        
        public void audioToPcm(String inputPath,String outputPath){
            if(inputPath.contains(" ")||outputPath.contains(" ")){
                return;
            }
            PCMEncoder.ffmpegRun(("ffmpeg -i " +inputPath+  " -f s16le -ar 24000 -ac 1 -acodec pcm_s16le " +  outputPath+" -y").split("[ \\t]+"));
        }

        public static String getRandomString(int length)
        {
            String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            Random random=new Random();
            StringBuffer sb=new StringBuffer();
            for (int i=0;i < length;i++)
            {
                int number=random.nextInt(62);
                sb.append(str.charAt(number));
            }
            return sb.toString();
        }
        
        public voiceinfo(String str, String str2, long j) throws Exception {
            this.f = j;
            
                if (str.startsWith("http")) {
                   str= downloadvoice(str,str2, true, 3000, 30000);
                }
                
                if(User.enablesilkencode){
                    if(str.endsWith("mp3")){
                        audioToPcm(str,str.replaceAll("\\.mp3$",".pcm"));
                        pcmToSilk(str.replaceAll("\\.mp3$",".pcm"),str.replaceAll("\\.mp3$",".silk"));
                    }
                    this.e = FileUtil.c(new File(str.replaceAll("\\.mp3$",".silk")));
                }else{
                    this.e = FileUtil.c(new File(str));
                }
                Log.d("voicefile",str);
                this.d = (long) this.e.length;
                if(this.d==0){
                    throw new Exception("语音文件为空");
                
                }
                if(this.d>1048576){
                    throw new Exception("文件大1m的语音无法上传");
                }
                this.b = com.saki.tool.Code.md5(this.e);
			this.c = HexTool.Bytes2Hex(this.b, "");
                this.a = this.c + str2;
                this.mark=PttStore.id;
                PttStore.id+=1;
         
            classf.cache.put((int)mark,this);
        }
    }

    public static voiceinfo a(int key) {
        return cache.get(key);
    }

    public static boolean uploadvoice(voiceinfo aVar, byte[] bArr) {
        try {
            new HTTP().httpPost("http://grouptalk.c2c.qq.com?ver=4679&ukey=" + HexTool.Bytes2Hex(bArr, "") + "&filekey=" + aVar.c + "&filesize=" + aVar.d + "&range=0&bmd5=" + aVar.c + "&mType=pttGu&voice_codec=1", aVar.e, 3000, 30000, new String[0]);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void b(int key) {
        cache.remove(key);
       
    }
}
