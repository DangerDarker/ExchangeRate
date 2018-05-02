package com.mt.exchangerate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mt.buddy.Currency;
import com.mt.buddy.CurrencyBuddy;
import com.mt.recyclerAdapter.CurrencyAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class AllCurrencyActivity extends AppCompatActivity {
    private SearchView sv ;
    private RecyclerView rv;
    private ProgressBar progressBar;
    private CurrencyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_currency);

        sv =  findViewById(R.id.currency_search);
        rv = findViewById(R.id.recycler_currency);
        progressBar = findViewById(R.id.progress_bar);
        sv.setSubmitButtonEnabled(true);
        final AcquireCurrency acquireCurrency = new AcquireCurrency();
        final Activity context = this;
        List<Currency> arr = DataSupport.findAll(Currency.class);
        if(arr.size() > 0 && MainActivity.isStarted){
            if(Config.CURRENCY_LIST.size()> 0)
                Config.CURRENCY_LIST.clear();
            for(Currency c : arr){
                Config.CURRENCY_LIST.add(new CurrencyBuddy(c.getEname() , c.getCname()));
                Config.CURRENCY_MAP.put(c.getCname() , new CurrencyBuddy(c.getEname() , c.getCname()));
            }
        }
        if(Config.CURRENCY_LIST.size() > 0){
            adapter  = new CurrencyAdapter(Config.CURRENCY_LIST , context);
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(context));

        }else{
            if(!Plugin.isNetworkAvailable(this)){
                Toast.makeText(this , "请打开网络连接..." , Toast.LENGTH_LONG).show();
            }else{
                progressBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<CurrencyBuddy> currencyBuddies ;
                        if(acquireCurrency.getCurrency() == Config.JSType.RESULT_CODE_OK){
                            currencyBuddies = acquireCurrency.getmCurrencyList();
                            for(CurrencyBuddy c :currencyBuddies){
                                Config.CURRENCY_MAP.put(c.getCname() , c);
                                Config.CURRENCY_LIST.add(c);
                                Currency currency = new Currency();
                                currency.setCname(c.getCname());
                                currency.setEname(c.getEname());
                                currency.save();
                            }
                            adapter = new CurrencyAdapter(currencyBuddies , context);

                            //  rv.setAdapter(currencyAdapter);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    rv.setAdapter(adapter);
                                    rv.setLayoutManager(new LinearLayoutManager(context));
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        }
                    }
                }).start();
            }
        }

        //sv.setSuggestionsAdapter(new SimpleCursorAdapter(AllCurrencyActivity.this , R.layout.item_layout , ));
     //   adapter = new ArrayAdapter(AllCurrencyActivity.this , android.R.layout.simple_list_item_1 ,arr );

    //    rv.setAdapter(adapter);
      //  ListView l;l.setFilterText();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                String name = "cname";
//                if(conValidate(newText)){
//                    name = "ename";
//                }
//               // Cursor cursor = TextUtils.isEmpty(newText) ? null : queryData(newText);
//                if (sv.getSuggestionsAdapter() == null) {
//                //    sv.setSuggestionsAdapter(new SimpleCursorAdapter(AllCurrencyActivity.this, R.layout.item_layout, cursor, new String[]{name}, new int[]{R.id.text1}));
//                } else {
//                   // sv.getSuggestionsAdapter().changeCursor(cursor);
//                }
                adapter.getFilter().filter(newText);

                return false;
            }
        });
    }

    private Cursor queryData(String s){

        Cursor cursor = null;
        if(conValidate(s)){
            String querySql = "select * from Currency where ename like '%" + s + "%'";
            cursor = DataSupport.findBySQL(querySql);//.where("ename like '% ? %'" , s).;
        }else{
            String querySql = "select * from Currency where cname like '%" + s + "%'";
            cursor = DataSupport.findBySQL(querySql);
        }
        return  cursor;
    }
    private boolean conValidate(String con) {  //判断 con  是否为英文
        if (null != con && !"".equals(con)) {
            if ((con.matches("^[A-Za-z]+$"))
                    && con.length() <= 10) {
                return true;
            }
        }
        return false;
    }
}
