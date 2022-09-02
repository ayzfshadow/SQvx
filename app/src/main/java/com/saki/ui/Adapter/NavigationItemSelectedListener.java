package com.saki.ui.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import com.ayzf.sqvx.R;
import com.saki.ui.DeviceSettingsActivity;
import com.saki.ui.SQAuthActivity;
import com.saki.ui.SQSettingsActivity;

public class NavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener
{

	private Activity activity;
	
	public NavigationItemSelectedListener(Activity a){
		this.activity=a;
	}
	
	@Override
	public boolean onNavigationItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if(id == R.id.sq_devicesettings){
			Intent intent = new Intent(this.activity, DeviceSettingsActivity.class);
            this.activity.startActivity(intent);
		}
        if(id == R.id.sq_sqsettings){
            Intent intent = new Intent(this.activity, SQSettingsActivity.class);
            this.activity.startActivity(intent);
		}
        if(id == R.id.sq_auth){
            Intent intent = new Intent(this.activity, SQAuthActivity.class);
            this.activity.startActivity(intent);
		}
		return false;
	}
	
}
