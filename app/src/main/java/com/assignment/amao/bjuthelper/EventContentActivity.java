package com.assignment.amao.bjuthelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

import java.net.URISyntaxException;

public class EventContentActivity extends Activity {

    TextView title, detail, money, address, customer_name, customer_phone, helper_name, helper_phone;
    private static BmobUser user = BmobUser.getCurrentUser();
    Button help, tip, delete;
    Intent intent;
    LinearLayout helper;

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
        delete = (Button) findViewById(R.id.content_button_del);
        helper = (LinearLayout) findViewById(R.id.content_helper);



        intent = getIntent();
        user.setObjectId(intent.getStringExtra("uid"));
        Log.d("BMOB",user.getUsername());
        Boolean isCustomer = (user.getObjectId()).equals(intent.getStringExtra("cid"));
        Log.d("BMOB",user.getObjectId()+"<>"+intent.getStringExtra("cid"));

        getEvent(intent.getStringExtra("id"));
        getCustomer(intent.getStringExtra("cid"));
        String status = intent.getStringExtra("status");

        Log.d("BMOB",isCustomer.toString());

        delete.setVisibility(View.GONE);
        help.setVisibility(View.GONE);
        tip.setVisibility(View.GONE);
        switch (status){
            case "finding":{
                if(isCustomer){
                    delete.setVisibility(View.VISIBLE);
                }else {
                    help.setVisibility(View.VISIBLE);
                }
                break;
            }
            case "processing":{
                getHelper(intent.getStringExtra("hid"));
                helper.setVisibility(View.VISIBLE);
                if(isCustomer){
                    tip.setVisibility(View.VISIBLE);
                }
                break;
            }
            case "done":{
                getHelper(intent.getStringExtra("hid"));
                helper.setVisibility(View.VISIBLE);
                break;
            }
            default:
                delete.setVisibility(View.GONE);
                help.setVisibility(View.GONE);
                tip.setVisibility(View.GONE);
                break;
        }



        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event event = new Event();
                event.setHelper(user);
                event.setHelperId(user.getObjectId());
                event.setHelperName(user.getUsername());
                event.setHelperPhone(user.getMobilePhoneNumber());
                event.setStatus("processing");
                event.update(intent.getStringExtra("id"), new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(EventContentActivity.this,"Every Helper all are Beautiful",Toast.LENGTH_LONG).show();
                            finish();
                        }else{
//                            toast("更新失败：" + e.getMessage());
                        }
                    }

                });
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(EventContentActivity.this)
                        .setMessage("DELETE THIS EVENT?")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Event event = new Event();
                                event.setObjectId(intent.getStringExtra("id"));
                                event.delete(new UpdateListener() {

                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            Toast.makeText(EventContentActivity.this,"Delete Successful",Toast.LENGTH_LONG).show();
                                            finish();
                                        }else{
                                            Toast.makeText(EventContentActivity.this,"Error",Toast.LENGTH_LONG).show();
                                        }
                                    }

                                });

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });


        tip.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                Event event = new Event();
                event.setStatus("done");
                String urlCode="FKX07818CPBEQS4KKLUO5A";
                Intent intentpay= null;
                try {
                    event.update(intent.getStringExtra("id"), new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(EventContentActivity.this,"Task Finish, Enjoy!",Toast.LENGTH_LONG).show();
                                finish();
                            }else{
//                            toast("更新失败：" + e.getMessage());
                            }
                        }

                    });
                    intentpay = Intent.parseUri("intent://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2F{urlCode}%3F_s%3Dweb-other&_t=1472443966571#Intent;scheme=alipayqr;package=com.eg.android.AlipayGphone;end".replace("{urlCode}", urlCode),1);
                    startActivity(intentpay);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    Toast.makeText(EventContentActivity.this,"No Pay No Gain",Toast.LENGTH_LONG).show();
                }


            }
        });

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
