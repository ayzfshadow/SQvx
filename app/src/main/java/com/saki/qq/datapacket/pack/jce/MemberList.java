package com.saki.qq.datapacket.pack.jce;


import java.util.ArrayList;
import com.qq.taf.jce.*;

public class MemberList extends JceStruct
{
	@Override
	public void writeTo(JceOutputStream p1)
	{
		// TODO: Implement this method
	}

    public long code;
    public long groupid;
    public ArrayList<Member> list = new ArrayList<>();

    public void readFrom(JceInputStream in) {
        this.code = in.read(Long.MAX_VALUE, 1, true);
        this.groupid = in.read(Long.MAX_VALUE, 2, true);
        this.list.add(new Member());
        this.list = (ArrayList) in.readobj(this.list, 3, true);
    }
}
