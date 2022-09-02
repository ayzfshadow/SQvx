package com.saki.qq.datapacket.pack.jce;


import java.util.ArrayList;
import com.qq.taf.jce.*;

public class TroopList extends JceStruct
{

	@Override
	public void writeTo(JceOutputStream paramJceOutputStream)
	{
		// TODO: Implement this method
	}

    long id;
    int listCount;
    public ArrayList<Troop> list = new ArrayList<Troop>();

    public void readFrom(JceInputStream classd) {
        this.id = classd.read(this.id, 0, true);
        this.listCount = classd.read(this.listCount, 1, true);
        this.list.add(new Troop());
        this.list = (ArrayList) classd.readobj(this.list, 5, true);
    }

    public void a(Troop classe) {
    }
}
