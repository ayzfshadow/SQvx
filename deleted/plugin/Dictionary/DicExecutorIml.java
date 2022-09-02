package com.saki.plugin.Dictionary;

import com.saki.http.HTTP;
import com.saki.loger.ViewLoger;
import com.saki.service.NewService;
import com.setqq.script.Msg;
import java.util.ArrayList;
import java.util.HashMap;
import com.saki.loger.DebugLoger;

public class DicExecutorIml extends DicExecutor
{

    

	

    

    private HTTP c;

    public DicExecutorIml(NewService newService, Msg msg, String str, String str2, long[] jArr) {
        super(newService, msg, str, str2, jArr);
    }

	
	
    /* access modifiers changed from: protected */
    public String a(String str, String str2) {
        if (this.c == null) {
            this.c = new HTTP();
        }
        try {
            if (str.equals("GET")) {
                return new String(this.c.httpGet(str2, true, 10000, 10000, new String[0]));
            }
            String substring = str2.substring(str2.indexOf("?") + 1);
            return new String(this.c.httpPost(str2.substring(0, str2.indexOf("?")), substring.getBytes(), 3000, 10000, new String[0]));
        } catch (Exception e) {
            return "";
        }
    }

    /* access modifiers changed from: protected */
    public void kick(long j, long j2) {
        this.b.a(j, j2);
    }
    
    @Override
    public void groupshutup(long j, boolean j2)
    {
        this.b.a(j,j2);
    }
    

    /* access modifiers changed from: protected */
    public void membershutup(long j, long j2, int i) {
        this.b.a(j, j2, i);
    }

    /* access modifiers changed from: protected */
    public void a(long j, long j2, String str) {
        this.b.a(j, j2, str);
    }
    
    @Override
    public void withDraw(long groupid, long messageCurrency, long messageInside)
    {
        this.b.withDraw(groupid,messageCurrency,messageInside);
    }
    
    @Override
    public void exit(long groupid)
    {
        this.b.exit(groupid);
    }
    
	
	@Override
	public void passreq(long j)
	{
		this.b.passreq(j);
	}

	@Override
	public void unpassreq(long j)
	{
		this.b.unpassreq(j);
	}


	

    /* access modifiers changed from: protected */
    public void a(String str, long j, long j2, String str2) {
       // ViewLoger.info(str+" "+j+" "+j2+" "+str2);
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(str);
        hashMap.put(str, arrayList);
        hashMap.put("index", arrayList2);
        arrayList.add(str2);
        this.b.a(-1, j, j2, hashMap);
    }

    /* access modifiers changed from: protected */
    public void a(String str, long j, String str2) {
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(str);
        hashMap.put(str, arrayList);
        hashMap.put("index", arrayList2);
        arrayList.add(str2);
        this.b.a(j, -1, -1, hashMap);
    }

    /* access modifiers changed from: protected */
    public void b(String type, long groupid, String msg) {
		DebugLoger.log(type,groupid+" "+msg);
		HashMap<String, ArrayList<String>> hashMap = new  HashMap<String, ArrayList<String>>();
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<String> arrayList2 = new ArrayList<String>();
		arrayList.add(msg);
        arrayList2.add(type);
        hashMap.put(type, arrayList);
        hashMap.put("index", arrayList2);
        
        this.b.a(-1, groupid, -1, hashMap);
    }
}
