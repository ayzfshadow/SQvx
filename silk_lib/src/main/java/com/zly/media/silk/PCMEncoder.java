package com.zly.media.silk;

public class PCMEncoder
{
    
    static {
        System.loadLibrary("mpegrun");
        System.loadLibrary("avcodec");
        System.loadLibrary("fdk-aac");
        System.loadLibrary("avformat");
        System.loadLibrary("avutil");
        System.loadLibrary("avfilter");
        System.loadLibrary("swresample");
        System.loadLibrary("swscale");
    }
    
    
    
    
    public static String nativeTranstopcm(String inputPath, int sampleRate, String outputPath)
    {
        
        return "";
    }
    
   
    public static  native int ffmpegRun(String[] cmd);

    /**
     * 获取ffmpeg编译信息
     * @return
     */
    public static native String getFFmpegConfig();
}
