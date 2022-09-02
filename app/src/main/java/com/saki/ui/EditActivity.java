package com.saki.ui;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import com.ayzf.sqvx.R;
import com.saki.ui.StatusBarUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class EditActivity extends AppCompatActivity implements OnClickListener {
    EditText edittext;
	File dicfile;
    FloatingActionButton button;
    boolean e = true;
    Handler edithandler = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 0) {
                EditActivity.this.edittext.setText(message.obj.toString());
                EditActivity.this.edittext.setSelection(EditActivity.this.edittext.getText().length());
                EditActivity.this.e = false;
            } else if (message.what == 1) {
                int selectionStart = EditActivity.this.edittext.getSelectionStart();
                EditActivity.this.edittext.getText().insert(selectionStart, message.obj.toString());
                if (message.arg1 == -1) {
                    EditActivity.this.edittext.setSelection(EditActivity.this.edittext.getText().length());
                } else {
                    EditActivity.this.edittext.setSelection(selectionStart + message.arg1);
                }
                EditActivity.this.e = false;
            } else {
                Snackbar.make(EditActivity.this.button, message.obj.toString(), 1000)
                .show();
           
            }
        }
    };

    /* access modifiers changed from: private */
    public void a(String str) {
        Message obtain = Message.obtain();
        obtain.what = 2;
        obtain.obj = str;
        this.edithandler.sendMessage(obtain);
    }

    private void a(String str, int i) {
        Message obtain = Message.obtain();
        obtain.what = 1;
        obtain.obj = str;
        obtain.arg1 = i;
        this.edithandler.sendMessage(obtain);
    }

    /* access modifiers changed from: private */
    public void b(String str) {
        Message obtain = Message.obtain();
        obtain.what = 0;
        obtain.obj = str;
        this.edithandler.sendMessage(obtain);
    }

    public void onClick(View view) {
        if (view == this.button) {
            String obj = this.edittext.getText().toString();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(this.dicfile);
                fileOutputStream.write(obj.getBytes("UTF-8"));
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e2) {
                a("保存失败:" + e2.getMessage());
            }
            a("保存完毕");
            this.e = true;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.layout_activity_dicedit);
        StatusBarUtil.setTransparent(this);
        this.edittext = findViewById(R.id.edittext_dicedit);
        this.button = findViewById(R.id.layout_diceditfab);
		this.dicfile=new File(this.getIntent().getStringExtra("dicfile"));
        new Thread(new Runnable() {
            public void run() {
                EditActivity.this.a("正在打开dic...");
                try {
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(dicfile));
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine != null) {
                            sb.append(readLine + "\n");
                        } else {
                            EditActivity.this.b(sb.toString());
                            bufferedReader.close();
                            EditActivity.this.a("读取完毕");
                            return;
                        }
                    }
                } catch (IOException e) {
                    EditActivity.this.a("找不到dic文件/dic读取失败");
                }
            }
        }).start();
        this.button.setOnClickListener(this);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 || this.e) {
            return super.onKeyDown(i, keyEvent);
        }
        new Builder(this).setTitle("警告").setMessage("当前dic未保存,退出将丢失所有进度，确定退出吗？").setNegativeButton("确定退出", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                EditActivity.this.finish();
            }
        }).setPositiveButton("取消", null).create().show();
        return true;
    }
}
