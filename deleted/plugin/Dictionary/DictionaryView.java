package com.saki.plugin.Dictionary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.saki.qq.userinfo.PluginCenter;
import com.saki.ui.DictionaryInputActivity;
import com.saki.ui.EditActivity;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import com.saki.sqv9.R;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageView;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.widget.ScrollView;

public class DictionaryView extends RelativeLayout implements SwipeRefreshLayout.OnRefreshListener, OnClickListener, OnCheckedChangeListener 
{

    public SwipeRefreshLayout swiperefreshlayout;
	
	public SwipeRefreshLayout swiperefreshlayout_extra;
	
    TextView k;
	
	TextView k_extra;

    private FloatingActionButton fab;
	
	private FloatingActionButton fab_extra;

    private ScrollView scroll;
	
	private ScrollView scroll_extra;
    
    StringBuilder sb = new StringBuilder();

	StringBuilder sb_extra = new StringBuilder();
	
    public DictionaryView(Context context)
    {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_view_dictionary, this);
		k = ((TextView) findViewById(R.id.dictionary_cmd_review));
		k_extra = ((TextView) findViewById(R.id.dictionary_cmd_review_extra));
        swiperefreshlayout = findViewById(R.id.swiperefreshlayout_dictionaryview);
		swiperefreshlayout_extra = findViewById(R.id.swiperefreshlayout_dictionaryview_extra);
        fab = this.findViewById(R.id.dictionary_fab);
		fab_extra=this.findViewById(R.id.dictionary_fab_extra);
        scroll=this.findViewById(R.id.swiperefreshlayout_scrollview);
		scroll_extra=this.findViewById(R.id.swiperefreshlayout_scrollview_extra);
		
        fab.setOnClickListener(this);
        fab.setScaleType(ImageView.ScaleType.FIT_XY);
        fab_extra.setOnClickListener(this);
        fab_extra.setScaleType(ImageView.ScaleType.FIT_XY);
        this.swiperefreshlayout.setOnRefreshListener(this);
		this.swiperefreshlayout_extra.setOnRefreshListener(this);
		
		
      //  this.k.setMovementMethod(new ScrollingMovementMethod());

    }

    @SuppressLint({"NewApi"})
    private boolean a(String str, String str2)
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dictionary", 0);
        HashSet hashSet = new HashSet();
        sharedPreferences.getStringSet(str, hashSet);
        return hashSet.contains(str2);
    }

    @SuppressLint({"NewApi"})
    private boolean a(String str, String... strArr)
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dictionary", 0);
        Editor edit = sharedPreferences.edit();
        HashSet hashSet = new HashSet();
        sharedPreferences.getStringSet(str, hashSet);
        for (int i2 = 0; i2 < strArr.length; i2++)
        {
            if (!hashSet.contains(strArr[i2]))
            {
                hashSet.add(strArr[i2]);
            }
        }
        edit.putStringSet(str, hashSet);
        return edit.commit();
    }

    private boolean storeSettings(String str, String str2)
    {
        Editor edit = getContext().getSharedPreferences("dictionary", 0).edit();
        edit.putString(str, str2);
        return edit.commit();
    }

    @SuppressLint({"NewApi"})
    private boolean b(String str, String... strArr)
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dictionary", 0);
        Editor edit = sharedPreferences.edit();
        HashSet hashSet = new HashSet();
        sharedPreferences.getStringSet(str, hashSet);
        for (String remove : strArr)
        {
            hashSet.remove(remove);
        }
        edit.putStringSet(str, hashSet);
        return edit.commit();
    }

    private String getCharset()
    {
        return getContext().getSharedPreferences("dictionary", 0).getString("charset", "UTF-8");
    }

    private String getMaster()
    {
        return getContext().getSharedPreferences("dictionary", 0).getString("master", "0");
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z)
    {
        PluginCenter.getInstence().getDicPlugin().dicfileloader.setEnabled(z);
		PluginCenter.getInstence().getDicPlugin().dicfileloader_extra.setEnabled(z);
    }


    @Override
    public void onRefresh()
    {
        if(this.swiperefreshlayout.isRefreshing()){
			//Snackbar.make(swiperefreshlayout, "swiperefreshlayout.isRefreshing", 1000).show();
			DicStream classe = new DicStream();
			try
			{
				classe.loadFile(getCharset());
				this.sb.setLength(0);
				this.k.setText("");
				for (String str : classe.b.keySet())
				{
					this.sb.append("[指令]:" + str + "\r\n");
				}
			}
			catch (IOException e3)
			{
				this.k.setText(e3.toString());
			}
			this.k.setText(this.sb.toString());
			DictionaryView.this.swiperefreshlayout.setRefreshing(false);
			this.scroll.computeScroll();
		}else if(this.swiperefreshlayout_extra.isRefreshing()){
			DicStream classe = new DicStream();
			//Snackbar.make(swiperefreshlayout, "swiperefreshlayout_extra.isRefreshing", 1000).show();
			try
			{
				classe.loadFile_extra(getCharset());
				this.sb_extra.setLength(0);
				this.k_extra.setText("");
				for (String str : classe.b.keySet())
				{
					this.sb_extra.append("[指令]:" + str + "\r\n");
				}
			}
			catch (IOException e3)
			{
				this.k_extra.setText(e3.toString());
			}
			this.k_extra.setText(this.sb_extra.toString());
			DictionaryView.this.swiperefreshlayout_extra.setRefreshing(false);
			this.scroll_extra.computeScroll();
		}
    }

    private void showPopupMenu(final View view)
    {
		
        if(view.getId()==R.id.dictionary_fab){
			final PopupMenu popupMenu = new PopupMenu(this.getContext(), view);
			//menu 布局
			popupMenu.getMenuInflater().inflate(R.menu.dictionary_menu, popupMenu.getMenu());
			//点击事件
			popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item)
					{
						switch (item.getItemId())
						{
							case R.id.menu_item_editdic:{
								    Intent g  = new Intent(getContext(), EditActivity.class);
									g.putExtra("dicfile",DicStream.dicfile.getAbsolutePath());
									getContext().startActivity(g);
								}break;
							case R.id.menu_item_encryptdic:{
									DicStream classe2 = new DicStream();
									try
									{
										classe2.loadFile(getCharset());
										classe2.a();
										File file = new File(Environment.getExternalStorageDirectory() + "/SQ/dictionaryV8/dic_encode.txt");
										if (!file.exists())
										{
											file.getParentFile().mkdirs();
											file.createNewFile();
										}
										classe2.b(file, getCharset());
										Snackbar.make(swiperefreshlayout, "加密文件已保存:" + file.getAbsolutePath(), 1000).show();

									}
									catch (IOException e4)
									{
										e4.printStackTrace();
									}
								}break;
							case R.id.menu_item_editencoding:{
									final EditText et = new EditText(DictionaryView.this.getContext());
									et.setText(getCharset());
									new AlertDialog.Builder(DictionaryView.this.getContext()).setTitle("请输入词库文本编码")

										.setView(et)
										.setPositiveButton("确定", new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialogInterface, int i)
											{
												//按下确定键后的事件
												String obj = et.getText().toString();
												if (obj == null || obj.equals(""))
												{
													Snackbar.make(swiperefreshlayout, "请输入词库文本编码", 1000).show();
													return;
												}
												try
												{
													new InputStreamReader(System.in, obj);
													storeSettings("charset", obj);
												}
												catch (UnsupportedEncodingException e2)
												{
													Snackbar.make(swiperefreshlayout, "非法字符编码！此字段不建议修改，请确认您已经了解此项功能的作用，并确实需要修改！", 1000).show();
													return;
												}
												// Snackbar.make(getApplicationContext(), et.getText().toString(), Toast.LENGTH_LONG).show();
											}
										}).setNegativeButton("取消", null).show();
								}break;
							case R.id.menu_item_editmaster:{
									final EditText et = new EditText(DictionaryView.this.getContext());
									et.setText(getMaster());
									new AlertDialog.Builder(DictionaryView.this.getContext()).setTitle("请输入主人的号码")

										.setView(et)
										.setPositiveButton("确定", new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialogInterface, int i)
											{
												//按下确定键后的事件
												String obj3 = et.getText().toString();
												if (obj3 == null || obj3.equals(""))
												{
													Snackbar.make(swiperefreshlayout, "请输入主人的号码", 1000).show();
													return;
												}
												storeSettings("master", obj3);
												// Snackbar.make(getApplicationContext(), et.getText().toString(), Toast.LENGTH_LONG).show();
											}
										}).setNegativeButton("取消", null).show();
								}break;
							case R.id.menu_item_editblacklist:{
									final EditText et = new EditText(DictionaryView.this.getContext());

									new AlertDialog.Builder(DictionaryView.this.getContext()).setTitle("请输入要添加或者移除的黑名单群号/qq号")
										.setView(et)
										.setPositiveButton("确定", new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialogInterface, int i)
											{
												//按下确定键后的事件
												String obj2 = et.getText().toString();
												if (obj2 == null || obj2.equals(""))
												{
													Snackbar.make(swiperefreshlayout, "请输入添加到黑名单的号码或群号", 3000).show();
													return;
												}
												else if (a("black", obj2))
												{
													storeSettings("black", obj2);
												}
												else
												{
													a("black", obj2);
												}
												// Snackbar.make(getApplicationContext(), et.getText().toString(), Toast.LENGTH_LONG).show();
											}
										}).setNegativeButton("取消", null).show();
								}break;

							default:
								break;
						}
						return false;
					}
				});
			//关闭事件
			popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
					@Override
					public void onDismiss(PopupMenu menu)
					{
						//Snackbar.make(view.getContext(),"close",Toast.LENGTH_SHORT).show();
					}
				});
			//显示菜单，不要少了这一步
			popupMenu.show();
		}else if(view.getId()==R.id.dictionary_fab_extra){
			final PopupMenu popupMenu = new PopupMenu(this.getContext(), view);
			//menu 布局
			popupMenu.getMenuInflater().inflate(R.menu.dictionary_menu_extra, popupMenu.getMenu());
			//点击事件
			popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item)
					{
						switch (item.getItemId())
						{
							case R.id.menu_item_editdic_extra:{
									Intent g  = new Intent(getContext(), EditActivity.class);
									g.putExtra("dicfile",DicStream.dicfile_extra.getAbsolutePath());
									getContext().startActivity(g);
								}break;
							case R.id.menu_item_encryptdic_extra:{
									DicStream classe2 = new DicStream();
									try
									{
										classe2.loadFile_extra(getCharset());
										classe2.a();
										File file = new File(Environment.getExternalStorageDirectory() + "/SQ/dictionaryV8/dic_extra_encode.txt");
										if (!file.exists())
										{
											file.getParentFile().mkdirs();
											file.createNewFile();
										}
										classe2.b(file, getCharset());
										Snackbar.make(swiperefreshlayout, "加密文件已保存:" + file.getAbsolutePath(), 3000).show();
									}
									catch (IOException e4)
									{
										e4.printStackTrace();
									}
								}break;
							default:
								break;
						}
						return false;
					}
				});
			//关闭事件
			popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
					@Override
					public void onDismiss(PopupMenu menu)
					{
						//Snackbar.make(view.getContext(),"close",Toast.LENGTH_SHORT).show();
					}
				});
			//显示菜单，不要少了这一步
			popupMenu.show();
		}
		
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.dictionary_fab:{
                    showPopupMenu(this.fab);
                }break;
			case R.id.dictionary_fab_extra:{
                    showPopupMenu(this.fab_extra);
                }
        }
//        if (view == this.d) {
//            getContext().startActivity(new Intent(getContext(), DictionaryInputActivity.class));
//        } else if (view == this.e) {
//            String obj = this.h.getText().toString();
//            if (obj == null || obj.equals("")) {
//                Snackbar.make(getContext(), "请输入词库文本编码", 0).show();
//                return;
//            }
//            try {
//                new InputStreamReader(System.in, obj);
//                b("charset", obj);
//            } catch (UnsupportedEncodingException e2) {
//                Snackbar.make(getContext(), "非法字符编码！此字段不建议修改，请确认您已经了解此项功能的作用，并确实需要修改！", 1).show();
//                return;
//            }
//        } else if (view == this.a) {
//            String obj2 = this.j.getText().toString();
//            if (obj2 == null || obj2.equals("")) {
//                Snackbar.make(getContext(), "请输入添加到黑名单的号码或群号", 0).show();
//                return;
//            } else if (a("black", obj2)) {
//                b("black", obj2);
//            } else {
//                a("black", obj2);
//            }
//        } else if (view == this.b) {
//            String obj3 = this.i.getText().toString();
//            if (obj3 == null || obj3.equals("")) {
//                Snackbar.make(getContext(), "请输入主人的号码", 0).show();
//                return;
//            }
//            b("master", obj3);
//        } else if (view == this.c) {
//            classe classe = new classe();
//            try {
//                classe.a(getCharset());
//                this.k.setText("");
//                for (String str : classe.b.keySet()) {
//                    this.k.append("[指令]:" + str + "\r\n");
//                }
//                return;
//            } catch (IOException e3) {
//                this.k.setText(e3.toString());
//                return;
//            }
//        } else if (view == this.f) {
//            classe classe2 = new classe();
//            try {
//                classe2.a(getCharset());
//                classe2.a();
//                File file = new File(Environment.getExternalStorageDirectory() + "/SQ/dictionaryV8/dic_encode.txt");
//                if (!file.exists()) {
//                    file.getParentFile().mkdirs();
//                    file.createNewFile();
//                }
//                classe2.b(file, getCharset());
//                Snackbar.make(getContext(), "加密文件已保存:" + file.getAbsolutePath(), 1).show();
//                return;
//            } catch (IOException e4) {
//                e4.printStackTrace();
//            }
//        } else if (view == this.g) {
//            getContext().startActivity(new Intent(getContext(), EditActivity.class));
//            return;
//        }
        // Snackbar.make(getContext(), "操作完成", 0).show();
    }
}
