package com.saki.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.ayzf.sqvx.R;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;

public class DeviceSettingsActivity extends AppCompatActivity 
{

	private Button sasave;

	private EditText ediedittextimei;

	private EditText edittextmanufacture;

	private EditText edittextmode;

	private Toolbar toolbar;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_devicesetting);
		
    }
	
	
}
