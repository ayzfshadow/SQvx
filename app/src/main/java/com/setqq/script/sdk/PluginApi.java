package com.setqq.script.sdk;

import com.saki.qq.userinfo.User;
import com.saki.qq.userinfo.TroopDataCenter;
import com.saki.qq.datapacket.pack.jce.Member;
import com.saki.qq.datapacket.pack.jce.Troop;
import com.saki.service.NewService;
import com.setqq.script.Msg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import com.saki.loger.DebugLoger;

public class PluginApi implements com.setqq.script.sdk.PluginApiInterface
  {

    @Override
    public void log ( int tag, String data )
	  {
        switch ( tag )
		  {
            case 0:
			  com.saki.loger.ViewLoger.info ( data );
			  break;
            case 1:
			  com.saki.loger.ViewLoger.warnning ( data );
			  break;
            case 2:
			  com.saki.loger.ViewLoger.erro ( data );
			  break;
		  }

	  }

    private NewService a;

    public PluginApi ( NewService newService )
	  {
        this.a = ( newService == null ?NewService.getService ( ): newService );
	  }

    public Msg send ( Msg msg )
	  {
        boolean z = true;
        boolean z2 = false;
        switch ( msg.type )
		  {
            case 0:
			case 25:
			case 26:
			case 27:
			case 28:
			case 29:
			case 30:
			  //DebugLoger.log("群号",msg.groupid+"");
			  this.a.a ( -1, msg.groupid, -1, msg );
			  break;
            case 1:
			  this.a.a ( -1, -1, msg.uin, msg.getData ( ) );
			  break;
            case 2:
			  this.a.a ( msg.groupid, -1, -1, msg.getData ( ) );
			  break;
            case 4:
			  if ( msg.uin >= 9999 && msg.code >= 9999 )
                {
				  this.a.a ( -1, msg.code, msg.uin, msg.getData ( ) );
				  break;
                }
			  else
                {
				  return null;
                }

            case 5:
			  ArrayList b = TroopDataCenter.getListener ( ).getTroopList ( );
			  HashMap hashMap = new HashMap ( );
			  ArrayList arrayList = new ArrayList ( );
			  Iterator it = b.iterator ( );
			  while ( it.hasNext ( ) )
                {
				  arrayList.add ( ( (Troop) it.next ( ) ).id );
                }
			  hashMap.put ( "troop", arrayList );
			  msg.setData ( hashMap );
			  return msg;
            case 7:
			  ArrayList a2 = TroopDataCenter.getListener ( ).getMembersById ( msg.groupid );
			  if ( a2 == null )
                {
				  return null;
                }
			  HashMap hashMap2 = new HashMap ( );
			  ArrayList arrayList2 = new ArrayList ( );
			  Iterator it2 = a2.iterator ( );
			  while ( it2.hasNext ( ) )
                {
				  arrayList2.add ( ( (Member) it2.next ( ) ).uin );
                }
			  hashMap2.put ( "member", arrayList2 );
			  msg.setData ( hashMap2 );
			  return msg;
            case 8:
			  this.a.b ( msg.uin );
			  break;
            case 9:
			  this.a.a ( msg.groupid, msg.uin, msg.title );
			  break;
            case 10:
			  this.a.a ( msg.groupid, msg.uin, msg.value );
			  break;
            case 11:
			  NewService newService = this.a;
			  long j = msg.groupid;
			  if ( msg.value != 0 )
                {
				  z = false;
                }
			  newService.a ( j, z );
			  break;
            case 12:
			  this.a.a ( msg.groupid, msg.uin );
			  break;
            case 13:
			  if ( msg.value == 0 )
				{
				  this.a.passreq ( msg.time );
				}
			  else if ( msg.value == 1 )
				{
				  this.a.unpassreq ( msg.time );
				}
			  break;
            case 15:
			  msg.uin = User.uin;
			  return msg;
            case 16:
			  this.a.withDraw ( msg.groupid, msg.isfromgroup, msg.isgroupmessage );
			  break;
			case 17:
			  this.a.updateGroupList ( );
			  break;
            case 18:
			  this.a.setallgroupenabled ( );
			  break;
            case 19:
			  this.a.exit ( msg.groupid );
			  break;
			case 20:
			  this.a.setgroupenabled ( msg.groupid );
			  break;
			case 21:
			  this.a.setgroupclosed ( msg.groupid );
			  break;
			case 22:
			  this.a.setallgroupclosed ( );
			  break;
			case 23:
			  this.a.a ( msg.groupid );
			  break;
            default:
			  com.saki.loger.ViewLoger.info ( msg.getTextMsg ( ) );
			  break;
		  }
        return null;
	  }
  }
