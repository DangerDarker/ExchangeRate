package com.mt.exchangerate;


import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mt.buddy.CurrencyBuddy;
import com.mt.buddy.RateBuddy;
import com.mt.database.DatabaseHelper;
import com.mt.recyclerAdapter.RateAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
   // private DrawerLayout  drawerLayout ;
   // private NavigationView navigationView ;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private EditText et ;
    private TextView tv ;
    private String EName;
    private String CName;
    private Button add;
    private static boolean isChanged = false;
    public static  HashMap<String ,RateBuddy> rateBuddyMap;
    public static String SOURCR;
    private ArrayList<RateBuddy> rateBuddies;
    private AcquireSingleRate acquireSingleRate ;
    RateAdapter rateAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        rateBuddies = new ArrayList<>();
        rateBuddyMap = new HashMap<>();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        linearLayout = findViewById(R.id.source_currency);
        //drawerLayout = findViewById(R.id.drawer_layout);
       // navigationView = findViewById(R.id.nav_view);

        recyclerView = findViewById(R.id.country_rate);
        et = findViewById(R.id.edit_money);
        tv = findViewById(R.id.edit_country);SOURCR = tv.getText().toString();
        tv.setOnClickListener(this);
        add = findViewById(R.id.add_currency);
        add.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rateAdapter = new RateAdapter(rateBuddies , this);
        recyclerView.setAdapter(rateAdapter);

    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.edit_country){
            Intent intent = new Intent(this , AllCurrencyActivity.class);
            intent.putExtra("type" , "source");
            startActivityForResult(intent , 7);
        }else if(v.getId() == R.id.add_currency){
            Intent intent = new Intent(this , AllCurrencyActivity.class);
            intent.putExtra("type" , "target");
            startActivityForResult(intent , 8);
        }
    }
    @Override
    protected  void onActivityResult(int requestCode , int resultCode ,  Intent data){
        if(requestCode == 7 && resultCode == RESULT_OK){
            CName = data.getStringExtra("cname");
            EName = data.getStringExtra("ename");
            tv.setText(CName);
            isChanged = true;
            //原 币  改变  则list中的rate也会改变
            SOURCR = CName;
            changeSource(CName);
        }else if(requestCode == 8 && resultCode == RESULT_OK){
            addRate(data.getStringExtra("ename"));  // 根据英文 获取
            String cname = data.getStringExtra("cname"); // 获取中文
           // rateBuddies.add(rateBuddyMap.get(cname)); // 放入list
            //rateAdapter.notifyDataSetChanged();
        }
    }

    public void addRate(final String cname){
        final  String[] str = tv.getText().toString().split(":");
        Log.i("MAINACTIVITY" , "addRate: "+str[0]);
        new Thread(new Runnable() {
            @Override
            public void run() {
                acquireSingleRate = new AcquireSingleRate(Config.CURRENCY_MAP.get(str[0]).getEname());
                if(acquireSingleRate.getResultCode() == Config.JSType.RESULT_CODE_OK){
                    RateBuddy rateBuddy = acquireSingleRate.getRateBuddy(cname);
                    rateBuddyMap.put( rateBuddy.getCname(),rateBuddy );
                    rateBuddies.add(rateBuddyMap.get(rateBuddy.getCname())); // 放入list
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rateAdapter.notifyDataSetChanged();
                        }
                    });

                }
            }
        }).start();
    }

    public void changeSource(final String source){
        rateBuddies.clear();

        final Iterator iter  = rateBuddyMap.entrySet().iterator();

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isSource = false;
                acquireSingleRate = new AcquireSingleRate(Config.CURRENCY_MAP.get(source).getEname());
                if(acquireSingleRate.getResultCode() == Config.JSType.RESULT_CODE_OK){
                    while(iter.hasNext()){

                        Map.Entry entry = (Map.Entry)iter.next();
                        RateBuddy rateBuddy = (RateBuddy)entry.getValue();
                        String cname = (String)entry.getKey();
                        String ename = rateBuddy.getEname();
                        if(!cname.equals(source)){
                            rateBuddy = acquireSingleRate.getRateBuddy(Config.CURRENCY_MAP.get(cname).getEname());
                            rateBuddyMap.put(cname , rateBuddy);
                            Log.i("ChangeSource", "run: "+ cname + "  "+ rateBuddy.getEname());
                            rateBuddies.add(rateBuddy);
                        }else{
                           isSource = true;
//                            entry = (Map.Entry)iter.next();
                        }

                    }
                    if(isSource){
                        rateBuddyMap.remove(source);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rateAdapter.notifyDataSetChanged();
                        }
                    });
                }

            }
        }).start();
    }
}
