package com.zly.media.silk;

public class SilkEncoder
{
    
    public static final String TAG = "SILK";

    public static final int ERROR_BAD_VALUE = -2;
    public static final int ERROR_INVALID_OPERATION = -3;

    public static final String HEADER = "#!SILK_V3";

    public static final int MAX_INPUT_FRAMES = 5;
    public static final int FRAME_LENGTH_MS = 20;
    public static final int MAX_API_FS_KHZ = 48;

    public static final int SAMPLE_RATE_8KHz = 8000;
    public static final int SAMPLE_RATE_12KHz = 12000;
    public static final int SAMPLE_RATE_16KHz = 16000;
    public static final int SAMPLE_RATE_24KHz = 24000;
    public static final int SAMPLE_RATE_32KHz = 32000;
    public static final int SAMPLE_RATE_44_1KHz = 44100;
    public static final int SAMPLE_RATE_48KHz = 48000;
    
    
 
 
    
    static {
        System.loadLibrary("native-lib");
    }
    
    
    
    
    public native  static String nativeTransPCM2Silk(String inputPath, int sampleRate, String outputPath);
}
