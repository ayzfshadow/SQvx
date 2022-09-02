//
// Decompiled by Jadx - 794ms
//
package com.saki.qq.datapacket.pack.jce;
import com.qq.taf.jce.*;


public class Member extends JceStruct
{
	@Override
	public void writeTo(JceOutputStream p1)
	{
		// TODO: Implement this method
	}
	
	public long uin;
	public String nick;
	public String card;
	public String title;

	public void readFrom(JceInputStream in) {
		this.uin = in.read((long)Long.MAX_VALUE, 0, true);
		this.nick = in.read("", 4, true);
		this.card = in.read("", 8, true);
		this.title = in.read("", 23, true);
	}
}
