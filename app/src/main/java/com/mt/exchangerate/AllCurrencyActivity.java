package com.mt.exchangerate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
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
        Toolbar toolbar = findViewById(R.id.toolbar_currency);
        setSupportActionBar(toolbar);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("选择币种");
        }
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
        //sv.setIconifiedByDefault(false);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            this.finish();
        }
        return  true;
    }
}
