package com.saki.http;

import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTP {
    private CookieStore cookies = new CookieStore();
    private String b = "UTF-8";
    private int c = 0;

	//public static String host ="106.12.4.207:9999";

    
    
    public byte[] a(String str, String str2, byte[] bArr, int i, int i2, boolean z, String... strArr) throws IOException {
        OutputStream outputStream;
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str2).openConnection();
        httpURLConnection.setRequestMethod(str);
        if (bArr != null) {
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
        }
        httpURLConnection.addRequestProperty("Cookie", this.cookies.toString());
        httpURLConnection.addRequestProperty("Accept-Charset", this.b);
        httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        for (String str3 : strArr) {
            if (str3.contains(":")) {
                int indexOf = str3.indexOf(":");
                httpURLConnection.addRequestProperty(str3.substring(0, indexOf), str3.substring(indexOf + 1, str3.length()));
            }
        }
        if (i > 0) {
            httpURLConnection.setConnectTimeout(30000);
        }
        if (i2 > 0) {
            httpURLConnection.setReadTimeout(30000);
        }
        httpURLConnection.connect();
        if (bArr != null) {
            OutputStream outputStream2 = httpURLConnection.getOutputStream();
            outputStream2.write(bArr);
            outputStream2.flush();
            outputStream = outputStream2;
        } else {
            outputStream = null;
        }
        this.c = httpURLConnection.getResponseCode();
        if (httpURLConnection.getHeaderFields().get("Set-Cookie") != null) {
            for (String a2 :  httpURLConnection.getHeaderFields().get("Set-Cookie")) {
                this.cookies.a(a2);
            }
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream inputStream = httpURLConnection.getInputStream();
        byte[] bArr2 = new byte[4096];
        while (true) {
            int read = inputStream.read(bArr2);
            if (read == -1) {
                break;
            }
            byteArrayOutputStream.write(bArr2, 0, read);
        }
        if (outputStream != null) {
            outputStream.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        httpURLConnection.disconnect();
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] a(String str, String str2, byte[] bArr, String... strArr) {
        try {
            return a(str, str2, bArr, -1, -1, true, strArr);
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public byte[] httpGet(String url, boolean z, int i, int i2, String... strArr) throws Exception {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setRequestProperty("Connection", "close");
        httpURLConnection.addRequestProperty("Cookie", this.cookies.toString());
        httpURLConnection.addRequestProperty("Accept-Charset", this.b);
        httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        httpURLConnection.setInstanceFollowRedirects(z);
        for (String str2 : strArr) {
            if (str2.contains(":")) {
                int indexOf = str2.indexOf(":");
                httpURLConnection.addRequestProperty(str2.substring(0, indexOf), str2.substring(indexOf + 1, str2.length()));
            }
        }
        httpURLConnection.setConnectTimeout(30000);
        httpURLConnection.setReadTimeout(30000);
        httpURLConnection.connect();
        this.c = httpURLConnection.getResponseCode();
        if (httpURLConnection.getHeaderFields().get("Set-Cookie") != null) {
            for (String a2 : httpURLConnection.getHeaderFields().get("Set-Cookie")) {
                this.cookies.a(a2);
            }
        }
        InputStream inputStream = httpURLConnection.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int read = inputStream.read();
            if (read == -1) {
                break;
            }
            byteArrayOutputStream.write(read);
        }
        if (inputStream != null) {
            inputStream.close();
        }
        httpURLConnection.disconnect();
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] httpPost(String url, byte[] postData, int i, int i2, String... strArr) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.addRequestProperty("Cookie", this.cookies.toString());
        httpURLConnection.addRequestProperty("Accept-Charset", this.b);
        httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        for (String str2 : strArr) {
            if (str2.contains(":")) {
                int indexOf = str2.indexOf(":");
                httpURLConnection.addRequestProperty(str2.substring(0, indexOf), str2.substring(indexOf + 1, str2.length()));
            }
        }
        httpURLConnection.setConnectTimeout(30000);
        httpURLConnection.setReadTimeout(30000);
        httpURLConnection.connect();
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(postData);
        outputStream.flush();
        this.c = httpURLConnection.getResponseCode();
        if (httpURLConnection.getHeaderFields().get("Set-Cookie") != null) {
            for (String a2 : httpURLConnection.getHeaderFields().get("Set-Cookie")) {
                this.cookies.a(a2);
            }
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream inputStream = httpURLConnection.getInputStream();
        while (true) {
            int read = inputStream.read();
            if (read == -1) {
                break;
            }
            byteArrayOutputStream.write(read);
        }
        if (outputStream != null) {
            outputStream.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        httpURLConnection.disconnect();
        return byteArrayOutputStream.toByteArray();
    }
}

