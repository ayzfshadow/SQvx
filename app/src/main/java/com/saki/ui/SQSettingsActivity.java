package com.saki.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.ayzf.sqvx.R;
import android.preference.PreferenceActivity;
import android.preference.Preference;
import android.util.Log;

public class SQSettingsActivity extends AppCompatActivity 
{

    private Toolbar toolbar;

 
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.layout_activity_sqsetting);
 
        
     toolbar = (Toolbar) findViewById(R.id.toolbar_sqsettings);
      setSupportActionBar(toolbar);
        StatusBarUtil.setTransparent(this);
//        sasave = (Button) findViewById(R.id.button_devicesetting_save);
//        ediedittextimei = (EditText) findViewById(R.id.edittext_devicesetting_imei);
//        edittextmanufacture = (EditText) findViewById(R.id.edittext_devicesetting_manufacture);
//        edittextmode = (EditText) findViewById(R.id.edittext_devicesetting_mode);
//        ediedittextimei.setText(getSharedPreferences("device", 0).getString("imei", ""));
//        edittextmanufacture.setText(getSharedPreferences("device", 0).getString("manufacture", ""));
//        edittextmode.setText(getSharedPreferences("device", 0).getString("mode", ""));
    
//
//        sasave.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v)
//                {
//                    SharedPreferences.Editor edit = getSharedPreferences("device", 0).edit();
//                    edit.putString("imei", ediedittextimei.getText().toString());
//                    edit.putString("manufacture", edittextmanufacture.getText().toString());
//                    edit.putString("mode", edittextmode.getText().toString());
//                    edit.commit();
//                    Snackbar.make(toolbar,"已保存",1000).show();
//
//                }
//
//
//            });

    }


}
