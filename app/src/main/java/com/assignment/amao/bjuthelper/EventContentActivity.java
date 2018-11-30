package com.assignment.amao.bjuthelper;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class EventContentActivity extends Activity {

    TextView title, detail, money, address, customer_name, customer_phone, helper_name, helper_phone;
    BmobUser user;
    Button help, tip;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_content);
        Bmob.initialize(this,"587cb0becf1d9c8a1fea192b63e98e32");

        title = (TextView) findViewById(R.id.content_title);
        detail = (TextView) findViewById(R.id.content_detail);
        money = (TextView) findViewById(R.id.content_money);
        address = (TextView) findViewById(R.id.content_address);
        customer_name = (TextView) findViewById(R.id.content_customer_name);
        customer_phone = (TextView) findViewById(R.id.content_customer_phone);
        helper_name = (TextView) findViewById(R.id.content_helper_name);
        helper_phone = (TextView) findViewById(R.id.content_helper_phone);
        help = (Button) findViewById(R.id.content_button_help);
        tip = (Button) findViewById(R.id.content_button_Tip);

        user = BmobUser.getCurrentUser(User.class);
        Log.d("BMOB",user.getUsername());

        intent = getIntent();
        getEvent(intent.getStringExtra("id"));
        getCustomer(intent.getStringExtra("cid"));
        if(!(intent.getStringExtra("status").equals("finding"))) {
            getHelper(intent.getStringExtra("hid"));
            (findViewById(R.id.content_helper)).setVisibility(View.INVISIBLE);
        }
        if(user.getObjectId().equals(intent.getStringExtra("cid")))
            help.setVisibility(View.INVISIBLE);





    }

    void getEvent(String id){
        BmobQuery<Event> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(id, new QueryListener<Event>() {
            @Override
            public void done(Event category, BmobException e) {
                if (e == null) {
                    title.setText(category.getTitle());
                    detail.setText(category.getDetail());
                    money.setText("￥ "+category.getMoney());
                    address.setText(category.getAddress());

                } else {
                    Log.e("BMOB", e.toString());
                }
            }
        });
    }

    void getHelper(String id){
        BmobQuery<BmobUser> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(id, new QueryListener<BmobUser>() {
            @Override
            public void done(BmobUser category, BmobException e) {
                if (e == null) {
                    helper_name.setText(category.getUsername());
                    helper_phone.setText(category.getMobilePhoneNumber());

                } else {
                    Log.e("BMOB", e.toString());
                }
            }
        });
    }

    void getCustomer(String id){
        BmobQuery<BmobUser> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(id, new QueryListener<BmobUser>() {
            @Override
            public void done(BmobUser category, BmobException e) {
                if (e == null) {
                    customer_name.setText(category.getUsername());
                    customer_phone.setText(category.getMobilePhoneNumber());

                } else {
                    Log.e("BMOB", e.toString());
                }
            }
        });
    }


}
