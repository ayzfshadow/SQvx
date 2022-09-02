package com.saki;

import android.content.*;
import android.os.*;
import android.widget.*;

public class ShowToast
  {
	public static void show ( final Context c, final String msg )
	  {
		new Handler ( Looper.getMainLooper ( ) ).post ( new Runnable ( ){
			  @Override
			  public void run ( )
				{
				  Toast.makeText ( c, msg, 0 ).show ( );
				}
			} );
	  }
  }

