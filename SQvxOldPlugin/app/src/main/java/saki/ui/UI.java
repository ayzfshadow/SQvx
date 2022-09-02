package saki.ui;

import android.app.Activity;
import android.os.Bundle;
import com.example.vxdemo.R;


public class UI extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StatusBarUtil.setTransparent(this);
		super.onCreate(savedInstanceState);
        setContentView(R.layout.layout001d);
		
	}
}


