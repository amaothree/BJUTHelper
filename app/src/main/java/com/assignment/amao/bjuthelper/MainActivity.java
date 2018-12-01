package com.assignment.amao.bjuthelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

import java.util.ArrayList;
import java.util.List;

import static cn.bmob.v3.b.From.e;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BmobUser user;
    private List<Event> eventList = new ArrayList<>();
    private EventAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bmob.initialize(this,"587cb0becf1d9c8a1fea192b63e98e32");
        user = BmobUser.getCurrentUser();

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog myDialog = new MyDialog(MainActivity.this, R.style.MyDialogStyle,BmobUser.getCurrentUser());
                myDialog.show();
                initEvents();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);

        Log.d("BMOB", user.getEmail());

        TextView nameView =(TextView) hView.findViewById(R.id.main_name);
        TextView emailView =(TextView) hView.findViewById(R.id.main_email);
        TextView phoneView =(TextView) hView.findViewById(R.id.main_phone);

        nameView.setText(user.getUsername());
        emailView.setText(user.getEmail());
        phoneView.setText(user.getMobilePhoneNumber());

        initEvents();

        adapter =new EventAdapter(eventList,user);
        recyclerView = (RecyclerView) findViewById(R.id.list_event);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Log.d("list","At the last of oncreate"+eventList.size());



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_square) {
            initEvents();
            setTitle("Event Square");
        } else if (id == R.id.nav_yours) {
            initYourEvents();
            setTitle("Your Event");

        } else if (id == R.id.nav_logout) {
            BmobUser.logOut();
            BmobUser currentUser = BmobUser.getCurrentUser();
            finish();
        } else if (id == R.id.nav_tasks) {
            initYourTasks();
            setTitle("Processing Tasks");

        } else if (id == R.id.nav_history){
            initYourTasksHistory();
            setTitle("Tasks History");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initEvents(){
        eventList.clear();
        BmobQuery<Event> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("status", "finding");
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Event>() {
            @Override
            public void done(List<Event> categories, BmobException e) {
                if (e == null) {
                    for(Event i: categories){
                        eventList.add(i);
                        Log.d("BMOB", i.getTitle());
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("BMOB", e.toString());
                }
            }
        });
        Log.d("list","At the last of init"+eventList.size());
    }

    private void initYourEvents(){
        eventList.clear();
        BmobQuery<Event> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("customerId", user.getObjectId());
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Event>() {
            @Override
            public void done(List<Event> categories, BmobException e) {
                if (e == null) {
                    for(Event i: categories){
                        eventList.add(i);
                        Log.d("BMOB", i.getTitle());
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("BMOB", e.toString());
                }
            }
        });
        Log.d("list","At the last of init"+eventList.size());
    }

    private void initYourTasks(){
        eventList.clear();
        BmobQuery<Event> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("helperId", user.getObjectId());
        bmobQuery.addWhereEqualTo("status", "processing");
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Event>() {
            @Override
            public void done(List<Event> categories, BmobException e) {
                if (e == null) {
                    for(Event i: categories){
                        eventList.add(i);
                        Log.d("BMOB", i.getTitle());
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("BMOB", e.toString());
                }
            }
        });
        Log.d("list","At the last of init"+eventList.size());
    }

    private void initYourTasksHistory(){
        eventList.clear();
        BmobQuery<Event> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("helperId", user.getObjectId());
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Event>() {
            @Override
            public void done(List<Event> categories, BmobException e) {
                if (e == null) {
                    for(Event i: categories){
                        eventList.add(i);
                        Log.d("BMOB", i.getTitle());
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("BMOB", e.toString());
                }
            }
        });
        Log.d("list","At the last of init"+eventList.size());
    }


    @Override
    protected void onDestroy() {
        BmobUser.logOut();
        BmobUser currentUser = BmobUser.getCurrentUser();
        super.onDestroy();
    }
}
