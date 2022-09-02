package com.saki.ui.Adapter;

import android.content.*;
import android.graphics.*;
import android.os.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.saki.qq.datapacket.pack.jce.*;
import com.saki.qq.userinfo.*;
import com.ayzf.sqvx.*;
import com.saki.util.*;
import java.util.*;
import android.util.*;
import com.saki.loger.DebugLoger;

public class GroupListViewAdapter extends BaseAdapter {
    private final LayoutInflater layoutinflater;
    private final GroupListHandler groupAdapterHandler = new GroupListHandler();

	

    class GroupItemStore {
        public ImageView groupHeader;
        public TextView groupNameTextView;
        public TextView groupIDTextView;
        public TextView messageCountTextView;
        public SwitchCompat groupSwitchButton;
        GroupItemStore() {
        }
    }

    class GroupListHandler extends Handler {

        class GroupHeaherStore {
            public long groupid;
            public Bitmap header;

            public GroupHeaherStore(long j, Bitmap bitmap) {
                this.groupid = j;
                this.header = bitmap;
            }
        }

        GroupListHandler() {
        }

		//群列表更新
        public void updateGroupList() {
            sendEmptyMessage(0);
        }

		//消息计数增加
        public void onMessageIncrease(long j) {
            Message obtain = Message.obtain();
            obtain.what = 1;
            obtain.obj = Long.valueOf(j);
            sendMessage(obtain);
        }

		//群头像更新
        public void onGroupHeaderUpdate(long j, Bitmap bitmap) {
            Message obtain = Message.obtain();
            obtain.what = 2;
            obtain.obj = new GroupHeaherStore(j, bitmap);
            sendMessage(obtain);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 0:
                    GroupListViewAdapter.this.notifyDataSetChanged();
                    return;
                case 1:
                    GroupListViewAdapter.this.increaseMessageCount(((Long) message.obj).longValue());
                    return;
                case 2:
                    GroupHeaherStore store = (GroupHeaherStore) message.obj;
                    GroupListViewAdapter.this.setHeaderBitmap(store.groupid, getCircleBitmap(store.header,14));
                    return;
                default:
                    return;
            }
        }
    }

    //把图片整成圆的
    public Bitmap getCircleBitmap(Bitmap bitmap, int n) {
        Bitmap bitmap2 = Bitmap.createBitmap((int)bitmap.getWidth(), (int)bitmap.getHeight(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
   
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        n = bitmap.getWidth();
        canvas.drawCircle((float)(n / 2), (float)(n / 2), (float)(n / 2), paint);
        paint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return bitmap2;
	}
    public GroupListViewAdapter(Context context) {
        this.layoutinflater = LayoutInflater.from(context);
    }

	//根据群号获取群对象
    private Troop getToopById(long j) {
        Iterator it = TroopDataCenter.getListener().troopList.iterator();
        while (it.hasNext()) {
            Troop troop = (Troop) it.next();
            if (troop.id == j) {
                return troop;
            }
        }
        return null;
    }

	
	public void setallgroupenable(boolean e)
	{
		//DebugLoger.log("setenabled",e+"");
		for (int i=0;i<this.getCount();i++){
			GroupItemStore store= (GroupItemStore) this.getView(i,null,null).getTag();//RelativeLayout是listview的item父布局
			//TextView text= (TextView) relativeLayout.getChildAt(0);//父布局里面的子控件是第几个就填第几个getChildAt
			store.groupSwitchButton.setChecked(e);
			//DebugLoger.log("yyyy",e+"");
			
		}
        
        super.
		notifyDataSetChanged();
	}
	
	
	
    /* access modifiers changed from: private */
	//传入群头像
    public boolean setHeaderBitmap(long id, Bitmap bitmap) {
        Troop troop = getToopById(id);
        if (troop == null) {
            return false;
        }
        troop.headerBitmap = bitmap;
        notifyDataSetChanged();
        return true;
    }

    /* access modifiers changed from: private */
	//消息计数增加
    public boolean increaseMessageCount(long id) {
        Troop troop = getToopById(id);
        if (troop == null) {
            return false;
        }
        troop.meesaageCount++;
        notifyDataSetChanged();
        return true;
    }

    /* renamed from: a */
    public Troop getItem(int index) {
        return TroopDataCenter.getListener().troopList.get(index);
    }

    
    public void updateGroupList() {
        this.groupAdapterHandler.updateGroupList();
        
    }

    public void onMessageIncrease(long id) {
        this.groupAdapterHandler.onMessageIncrease(id);
    }

    public void onGroupHeaderUpdate(long id, Bitmap bitmap) {
        this.groupAdapterHandler.onGroupHeaderUpdate(id, bitmap);
    }

    public int getCount() {
        return TroopDataCenter.getListener().troopList.size();
    }

    public long getItemId(int i) {
        Troop a2 = getItem(i);
        if (a2 == null) {
            return 0;
        }
        return a2.id;
    }

    public int getItemViewType(int i) {
        return getItem(i).headerType;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        GroupItemStore aVar=null;
        if (view == null) {
            GroupItemStore aVar2 = new GroupItemStore();
            view = this.layoutinflater.inflate(R.layout.layout_item_group, null);
            aVar2.groupHeader = view.findViewById(R.id.group_header_image);
            aVar2.messageCountTextView = view.findViewById(R.id.group_messagecount_textview);
            aVar2.groupIDTextView = view.findViewById(R.id.group_id_textview);
            aVar2.groupNameTextView = view.findViewById(R.id.group_name_textview);
            aVar2.groupSwitchButton = view.findViewById(R.id.group_switchbutton_textview);
            view.setTag(aVar2);
            aVar = aVar2;
        } else {
            aVar = (GroupItemStore) view.getTag();
        }
        final Troop a2 = getItem(i);
        if (a2 != null) {
            aVar.messageCountTextView.setText(String.valueOf(a2.meesaageCount));
            if (a2.headerBitmap != null) {
                aVar.groupHeader.setImageBitmap(a2.headerBitmap);
            } else {
                aVar.groupHeader.setImageResource(R.drawable.drawable0061);
                if (!a2.isShown) {
                    a2.isShown = true;
                    ImageUtil.getHeader(a2.id + "", getItemViewType(i), (BitmapCallback) new BitmapCallback() {
                        public void callBack(Bitmap bitmap) {
                            if (bitmap != null) {
                                GroupListViewAdapter.this.onGroupHeaderUpdate(a2.id, bitmap);
                            }
                        }
                    });
                }
            }
            aVar.groupIDTextView.setText(String.valueOf(a2.id));
            aVar.groupNameTextView.setText(a2.name);
			
			aVar.groupSwitchButton.setOnCheckedChangeListener(a2);
			
			aVar.groupSwitchButton.setChecked(a2.isEnabled);
            
        }
        return view;
    }

    public int getViewTypeCount() {
        return 2;
    }
}
