package com.saki.qq.userinfo;

import com.saki.qq.datapacket.pack.jce.Member;
import com.saki.qq.datapacket.pack.jce.Troop;
import com.saki.ui.c.TroopDataListener;
import java.util.ArrayList;
import java.util.Iterator;
import android.util.*;

public class TroopDataCenter implements TroopDataListener {
    private static TroopDataCenter _this = new TroopDataCenter();
    private static ArrayList<TroopDataListener> listeners = new ArrayList<>();
    public ArrayList<Troop> troopList = new ArrayList<>();

    public static TroopDataCenter getListener() {
        return _this;
    }

    public ArrayList<Member> getMembersById(long j) {
        Iterator it = this.troopList.iterator();
        while (it.hasNext()) {
            Troop classe = (Troop) it.next();
            if (classe.id == j) {
                return classe.k;
            }
        }
        return null;
    }

    public void onGroupMemberDataUpdate(long j, ArrayList<Member> arrayList) {
           com.saki.loger.DebugLoger.log(this, "id=" + j);
        
        Iterator it2 = getTroopList().iterator();
        while (it2.hasNext()) {
            Troop classe = (Troop) it2.next();
            if (classe.id == j) {
                classe.k = arrayList;
            }
        }
    }

    public void addListener(TroopDataListener classc) {
        if (!listeners.contains(classc)) {
            listeners.add(classc);
        }
    }

    public void onGroupDataUpdate(ArrayList<Troop> arrayList) {
        Iterator it = this.troopList.iterator();
        while (it.hasNext()) {
            Troop classe = (Troop) it.next();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                Troop classe2 = (Troop) it2.next();
                if (classe.equals(classe2)) {
                   classe2.isEnabled = classe.isEnabled;
                    
                    classe2.meesaageCount = classe.meesaageCount;
                }
                if(User.switch_enableallgroupautomatically){
                    classe2.isEnabled=true;
                }
            }
        }
        this.troopList = arrayList;
        Iterator it3 = listeners.iterator();
        while (it3.hasNext()) {
            ((TroopDataListener) it3.next()).onGroupDataUpdate(this.troopList);
        }
        
    }

    public long getGroupCodeById(long j) {
        Iterator it = this.troopList.iterator();
        while (it.hasNext()) {
            Troop classe = (Troop) it.next();
            if (classe.id == j) {
                return classe.code;
            }
        }
        return j;
    }

    public ArrayList<Troop> getTroopList() {
        return this.troopList;
    }

    public void removeListener(TroopDataListener classc) {
        listeners.remove(classc);
    }

    public boolean isGroupEnabled(long j) {
        Iterator it = this.troopList.iterator();
        while (it.hasNext()) {
            Troop classe = (Troop) it.next();
            if (classe.id == j) {
				//Log.d("found","cccc");
                return classe.isEnabled;
            }
        }
        return false;
    }
}
