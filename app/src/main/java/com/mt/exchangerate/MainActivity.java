package com.mt.exchangerate;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mt.buddy.Currency;
import com.mt.buddy.CurrencyBuddy;
import com.mt.buddy.ItemData;
import com.mt.buddy.RateBuddy;
import com.mt.buddy.RecyclerItem;
import com.mt.chatrobot.RobotMain;
import com.mt.recyclerAdapter.RateAdapter;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private EditText et ;
    private TextView tv ;
    private String EName;
    private String CName;
    private Button add;
    private Button Ok ;
    public static boolean isStarted = false;
    public static  HashMap<String ,RateBuddy> rateBuddyMap;
    public static String SOURCR;
    private ArrayList<RateBuddy> rateBuddies;
    private ArrayList<RecyclerItem> buddies;
    private AcquireSingleRate acquireSingleRate ;
    RateAdapter rateAdapter;
    private String source;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        isStarted = true;
        rateBuddies = new ArrayList<>();
        rateBuddyMap = new HashMap<>();
        buddies = new ArrayList<>();
        Connector.getDatabase();// 建立数据库
        linearLayout = findViewById(R.id.source_currency);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.country_rate);
        et = findViewById(R.id.edit_money);
        tv = findViewById(R.id.edit_country);
        SOURCR = tv.getText().toString();
        tv.setOnClickListener(this);
        add = findViewById(R.id.add_currency);
        add.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Currency> arr = DataSupport.findAll(Currency.class);
        if(arr.size() > 0 && MainActivity.isStarted){
            if(Config.CURRENCY_LIST.size()> 0)
                Config.CURRENCY_LIST.clear();
            for(Currency c : arr){
                Config.CURRENCY_LIST.add(new CurrencyBuddy(c.getEname() , c.getCname()));
                Config.CURRENCY_MAP.put(c.getCname() , new CurrencyBuddy(c.getEname() , c.getCname()));
            }
        }
        if(!Plugin.isNetworkAvailable(this)){
            //获取数据库数据  ItemData 转为 RateBuddy ， 并  new RecyclerItem(ratebuddy , money.getText());
            List<ItemData> list = DataSupport.findAll(ItemData.class);
            buddies.clear();
            if(!list.isEmpty()){
                for(ItemData item : list){
                    RateBuddy rateBuddy = new RateBuddy(item.getEname() ,
                            item.getCname() , item.getRate() , item.getDate());
                    buddies.add(new RecyclerItem(rateBuddy , "0"));
                }
            }
        }else{

        }
        rateAdapter = new RateAdapter(buddies , this);
        recyclerView.setAdapter(rateAdapter);
        Ok = findViewById(R.id.input_ok);
        Ok.setOnClickListener(this);
        et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        et.setText(s);
                        et.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    et.setText(s);
                    et.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        et.setText(s.subSequence(0, 1));
                        et.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });
        source = tv.getText().toString();

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
        }else if(v.getId() == R.id.input_ok){
            String rate = et.getText().toString();
            if(!("".equals(rate) && rate != null)){
                if(rate.contains(".")){
                    String[] str = rate.split(".");
                    if(str.length == 1){
                        rate = str[0];
                        et.setText(rate);
                    }
                }
                float fo = Float.parseFloat(rate);
                Log.i("OK Click", "onClick: "+ fo);
                for(RecyclerItem ri : buddies){
                    ri.setMoney(fo);
                }
                rateAdapter.notifyDataSetChanged();
            }

        }
    }
    @Override
    protected  void onActivityResult(int requestCode , int resultCode ,  Intent data){
        if(requestCode == 7 && resultCode == RESULT_OK){
            CName = data.getStringExtra("cname");
            EName = data.getStringExtra("ename");

            //isChanged = true;
            //原 币  改变  则list中的rate也会改变

            changeSource(CName);
        }else if(requestCode == 8 && resultCode == RESULT_OK){
            progressBar.setVisibility(View.VISIBLE);
            addRate(data.getStringExtra("ename"));  // 根据英文 获取
            String cname = data.getStringExtra("cname"); // 获取中文
        }
    }

    public void addRate(final String cname){
        final  String[] str = tv.getText().toString().split(":");
        Log.i("MAINACTIVITY" , "addRate: "+str[0]);

        if(!Plugin.isNetworkAvailable(this)){
            Toast.makeText(this , "请打开网络连接..." , Toast.LENGTH_LONG).show();
        }else{

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String m = et.getText().toString();
                    if(m == null || "".equals(m)){
                        m = "0.0";
                    }
                    if(!str[0].equals(source)){
                        acquireSingleRate = new AcquireSingleRate(Config.CURRENCY_MAP.get(str[0]).getEname());
                    }else{
                        source = str[0];
                    }
                    if(acquireSingleRate == null){
                        acquireSingleRate = new AcquireSingleRate(Config.CURRENCY_MAP.get(str[0]).getEname());
                    }

                    if(acquireSingleRate.getResultCode() == Config.JSType.RESULT_CODE_OK){
                        RateBuddy rateBuddy = acquireSingleRate.getRateBuddy(cname);
                        rateBuddyMap.put( rateBuddy.getCname(),rateBuddy );
                        rateBuddies.add(rateBuddyMap.get(rateBuddy.getCname())); // 放入list
                        final RecyclerItem rt = new RecyclerItem(rateBuddy , m);
                        if("人民币".equals(SOURCR)){
                            ItemData itemData = new ItemData();
                            itemData.setCname(rateBuddy.getCname());
                            itemData.setEname(rateBuddy.getEname());
                            itemData.setDate(rateBuddy.getDate());
                            itemData.setRate(rateBuddy.getRate());
                            itemData.save();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                buddies.add(rt);
                                rateAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            }).start();
        }
    }

    public void changeSource(final String source){
        if(!Plugin.isNetworkAvailable(this) && "人民币".equals(SOURCR)){
            Toast.makeText(this , "请打开网络连接..." , Toast.LENGTH_LONG).show();
        }else{
            rateBuddies.clear();
            buddies.clear();
            final Iterator iter  = rateBuddyMap.entrySet().iterator();
            if(!Plugin.isNetworkAvailable(this)){

            }else{
                SOURCR = source;
                tv.setText(source);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean isSource = false;
                        acquireSingleRate = new AcquireSingleRate(Config.CURRENCY_MAP.get(source).getEname());
                        if(acquireSingleRate.getResultCode() == Config.JSType.RESULT_CODE_OK){
                            while(iter.hasNext()){
                                String m = et.getText().toString();
                                if(m == null || "".equals(m)){
                                    m = "0.0";
                                }
                                Map.Entry entry = (Map.Entry)iter.next();
                                RateBuddy rateBuddy = (RateBuddy)entry.getValue();
                                String cname = (String)entry.getKey();
                                String ename = rateBuddy.getEname();
                                if(!cname.equals(source)){
                                    rateBuddy = acquireSingleRate.getRateBuddy(Config.CURRENCY_MAP.get(cname).getEname());
                                    rateBuddyMap.put(cname , rateBuddy);
                                    Log.i("ChangeSource", "run: "+ cname + "  "+ rateBuddy.getEname());
                                    rateBuddies.add(rateBuddy);
                                    buddies.add(new RecyclerItem(rateBuddy , m));
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


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu_robot , menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.robot){
            Intent intent = new Intent(MainActivity.this , RobotMain.class);
            startActivity(intent);
        }
        return  true;
    }

}
