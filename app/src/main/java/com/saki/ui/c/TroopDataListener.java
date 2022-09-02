package com.saki.ui.c;

import com.saki.qq.datapacket.pack.jce.Member;
import com.saki.qq.datapacket.pack.jce.Troop;
import java.util.ArrayList;

public interface TroopDataListener {
    void onGroupMemberDataUpdate(long j, ArrayList<Member> arrayList);

    void onGroupDataUpdate(ArrayList<Troop> arrayList);
}
