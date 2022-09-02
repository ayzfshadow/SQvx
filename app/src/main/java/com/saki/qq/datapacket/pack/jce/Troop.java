package com.saki.qq.datapacket.pack.jce;

import android.graphics.Bitmap;
import java.util.ArrayList;
import com.qq.taf.jce.*;
import android.widget.*;
import android.util.*;

public class Troop extends JceStruct implements CompoundButton.OnCheckedChangeListener
{

	@Override
	public void writeTo(JceOutputStream paramJceOutputStream)
	{
		// TODO: Implement this method
	}

    public long code;
    public long id;
    public String name = "";
    public String info = "";
    public int memberCount;
    public int headerType;
    public int meesaageCount;
    public Bitmap headerBitmap;
    public boolean isShown;
    public boolean isEnabled;
    public ArrayList<Member> k;

    public void readFrom(JceInputStream classd) {
        this.code = classd.read(this.code, 0, true);
        this.id = classd.read(this.id, 1, true);
        this.name = classd.read(this.name, 4, true);
        this.info = classd.read(this.info, 5, true);
        this.memberCount = classd.read(this.memberCount, 19, true);
    }

   @Override public void onCheckedChanged(CompoundButton switchButton, boolean z) {
	   
        this.isEnabled = z;
    }

    public boolean equals(Object obj) {
        return (obj instanceof Troop) && ((Troop) obj).id == this.id;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Code:" + this.code + "\r\n");
        sb.append("Id:" + this.id + "\r\n");
        sb.append("Name:" + this.name + "\r\n");
        sb.append("Info:" + this.info + "\r\n");
        sb.append("MemberCnt::" + this.memberCount);
        return sb.toString();
    }
}
