package com.assignment.amao.bjuthelper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MyDialog extends Dialog implements View.OnClickListener{


	Button submit;
	Context context;
	BmobUser user;

	public interface MyDialogListener{
		public void onClick(View view);
	}
	
    public MyDialog(Context context) {
        super(context);
        this.context = context;
    }
    
    public MyDialog(Context context, int theme,BmobUser user){
        super(context, theme);
        this.context = context;
        this.user=user;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_add);
        submit = (Button) findViewById(R.id.event_submit);
        submit.setOnClickListener(this);
        
    }

	@Override
	public void onClick(View v) {
		switch ( v.getId()) {
		case R.id.event_submit:
			EventSubmit();
			this.dismiss();
			break;
		default:
			break;
		}
	}

	private void EventSubmit() {

		
		final EditText event_address = (EditText) findViewById(R.id.event_address);
		final EditText event_detail = (EditText) findViewById(R.id.event_detail);
		final EditText event_money = (EditText) findViewById(R.id.event_money);
		final EditText event_title = (EditText) findViewById(R.id.event_title);

		Event event = new Event(event_title.getText().toString(),
				event_detail.getText().toString(),
				Integer.parseInt(event_money.getText().toString()),
				event_address.getText().toString(),"finding",user);

		event.save(new SaveListener<String>() {
			@Override
			public void done(String objectId, BmobException e) {
				if (e == null) {
					Log.d("BMOB","saved 新增成功");
//					mObjectId = objectId;
//					Snackbar.make(mBtnSave, "新增成功：" + mObjectId, Snackbar.LENGTH_LONG).show();
				} else {
					Log.e("BMOB", e.toString());
//					Snackbar.make(mBtnSave, e.getMessage(), Snackbar.LENGTH_LONG).show();
				}
			}
		});
		Log.d("db", "submit all");
		
	}



}
