package com.setqq.script;

import android.os.Parcel;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.io.*;

public class Msg implements /*Externalizable*/java.io.Serializable {
  
  
	private static final long serialVersionUID = 202107241306L;
	
  
  
	public static final int TYPE_GROUP_MSG = 0;// 群消息
	public static final int TYPE_BUDDY_MSG = 1;// 好友消息
	public static final int TYPE_DIS_MSG = 2;// 讨论组消息
	public static final int TYPE_SESS_MSG = 4;// 临时消息
	public static final int TYPE_SYS_MSG = 3;// 系统消息
	public static final int TYPE_ADMINTRANSE_MSG = 25;// 管理变更
	public static final int TYPE_MEMBERTRANSE_MSG = 26;// 有人退群/被踢
	public static final int TYPE_MEMBERENTER_MSG = 27;// 有人入群
	public static final int TYPE_MEMBERREQUEST_MSG = 28;// 有人请求入群
    public static final int TYPE_MSG_CANSEL = 29;// 有人撤回消息
    public static final int TYPE_MEMBER_SSHUTUP = 30;// 有人被禁言
    public static final int TYPE_TRANSFER_MSG = 31;// 转账
    public static final int TYPE_GROUPINVITE_MSG = 32;// 入群邀请
    

	

	public static final int TYPE_GET_GROUP_LIST = 5;// 群列表，发送后返回的包getMsg("troop")为群列表
	public static final int TYPE_GET_GROUP_INFO = 6;// 群信息，发送后返回的包getMsg("member")为成员列表
	public static final int TYPE_GET_GROUP_MEBMER = 7;// 群成员
	public static final int TYPE_FAVORITE = 8;// 点赞
	public static final int TYPE_SET_MEMBER_CARD = 9;// 设置群名片
	public static final int TYPE_SET_MEMBER_SHUTUP = 10;// 成员禁言
	public static final int TYPE_SET_GROUP_SHUTUP = 11;// 群禁言
	public static final int TYPE_DELETE_MEMBER = 12;// 删除群成员
	public static final int TYPE_REQUEST_DEAL = 13;// 处理入群请求
	public static final int TYPE_GET_LOGIN_ACCOUNT = 15;// 获取机器人QQ,发送后返回包的Uin为机器人QQ
	public static final int TYPE_RECALL_MSG = 16;// 撤回消息
    public static final int TYPE_UPDATE_GROUPLIST= 17;// 更新群列表
    public static final int TYPE_ENABLE_ALL_GROUP= 18;// 打开全部群开关
    public static final int TYPE_EXIT_GROUP= 19;// 退群
    public static final int TYPE_ENABLE_ONE_GROUP= 20;// 打开单个群开关
    public static final int TYPE_CLOSE_ONE_GROUP= 21;// 关闭单个群开关
    public static final int TYPE_CLOSE_ALL_GROUP= 22;// 关闭所有群开关
    public static final int TYPE_UPDATE_GROUPMEMBER_LIST =23; //刷新群成员
    
    public long code = -1;
    private HashMap<String, ArrayList> data = new HashMap<>();
    public String groupName;
    public long groupid = -1;
    public long time = -1;
    public String title;
    public int type = -1;
    public long uin = -1;
    public String uinName;
    public int value = -1;
    public long isfromgroup =-1;
    public long isgroupmessage=-1;

    public Msg reply;
    
    public String replytext="";

	public long operator = -1;
    
    public Msg() {
    }

    public Msg(int i) {
        this.type = i;
    }

    public void reply(String text,Msg msg)
    {
        replytext=text;
        this.reply=msg;
    }

    public void addMsg(String str, String str2) {
        ArrayList arrayList = (ArrayList) this.data.get("index");
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.data.put("index", arrayList);
        }
        arrayList.add(str);
        ArrayList arrayList2 = (ArrayList) this.data.get(str);
        if (arrayList2 == null) {
            arrayList2 = new ArrayList();
            this.data.put(str, arrayList2);
        }
        arrayList2.add(str2);
        
    }

    public void clearMsg() {
        this.data = new HashMap<>();
    }

    public HashMap<String, ArrayList> getData() {
        return this.data;
    }

    public ArrayList<String> getMsg(String str) {
        return (ArrayList) this.data.get(str);
    }

    public String getTextMsg() {
        StringBuilder sb = new StringBuilder("");
        ArrayList arrayList = (ArrayList) this.data.get("msg");
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                sb.append((String) it.next());
            }
        }
        return sb.toString();
    }

    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        this.type = objectInput.readInt();
        this.groupid = objectInput.readLong();
        this.code = objectInput.readLong();
        this.uin = objectInput.readLong();
        this.value = objectInput.readInt();
        this.groupName = objectInput.readUTF();
        this.uinName = objectInput.readUTF();
        this.title = objectInput.readUTF();
        this.data = (HashMap) objectInput.readObject();
    }

    public void readFromParcel(Parcel parcel) {
        this.type = parcel.readInt();
        this.groupid = parcel.readLong();
        this.code = parcel.readLong();
        this.uin = parcel.readLong();
        this.time = parcel.readLong();
        this.value = parcel.readInt();
        this.groupName = parcel.readString();
        this.uinName = parcel.readString();
        this.title = parcel.readString();
        parcel.readMap(this.data, getClass().getClassLoader());
    }

    public void setData(HashMap<String, ArrayList> hashMap) {
        if (hashMap != null) {
            this.data = hashMap;
        }
    }

    public String toString() {
        return this.groupName + "[" + this.groupid + "]" + this.uinName + "[" + this.uin + "]" + this.code + ":" + getTextMsg();
    }

    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeInt(this.type);
        objectOutput.writeLong(this.groupid);
        objectOutput.writeLong(this.code);
        objectOutput.writeLong(this.uin);
        objectOutput.writeLong(this.time);
        objectOutput.writeInt(this.value);
        objectOutput.writeUTF(this.groupName);
        objectOutput.writeUTF(this.uinName);
        objectOutput.writeUTF(this.title);
        objectOutput.writeObject(this.data);
    }
}
