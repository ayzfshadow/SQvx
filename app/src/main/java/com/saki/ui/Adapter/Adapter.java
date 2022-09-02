package com.saki.ui.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public abstract class Adapter extends PagerAdapter {
 
    protected Context b;
    private final View[] c = new View[4];

    public Adapter(Context context) {
        
        this.b = context;
        
    }

    /* access modifiers changed from: protected */
    public abstract View b(View view, int i);

    
   

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView(this.c[i % 4]);
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View b2 = b(this.c[i % 4], i);
        viewGroup.addView(b2);
        return b2;
    }

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public void notifyDataSetChanged() {
        if (this.getCount() > 0 ) {
            
        }
        super.notifyDataSetChanged();
    }
}
