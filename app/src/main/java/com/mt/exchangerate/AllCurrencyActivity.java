package com.mt.exchangerate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.mt.buddy.CurrencyBuddy;
import com.mt.recyclerAdapter.CurrencyAdapter;

import java.util.ArrayList;

public class AllCurrencyActivity extends AppCompatActivity {
    private SearchView sv ;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_currency);

        sv =  findViewById(R.id.currency_search);
        rv = findViewById(R.id.recycler_currency);
        final AcquireCurrency acquireCurrency = new AcquireCurrency();
        final Activity context = this;
        if(Config.CURRENCY_LIST.size() > 0){
            CurrencyAdapter currencyAdapter = new CurrencyAdapter(Config.CURRENCY_LIST , context);
            rv.setAdapter(currencyAdapter);
            rv.setLayoutManager(new LinearLayoutManager(context));

        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<CurrencyBuddy> currencyBuddies ;
                    if(acquireCurrency.getCurrency() == Config.JSType.RESULT_CODE_OK){
                        currencyBuddies = acquireCurrency.getmCurrencyList();
                        for(CurrencyBuddy c :currencyBuddies){
                            Config.CURRENCY_MAP.put(c.getCname() , c);
                            Config.CURRENCY_LIST.add(c);
                        }
                        final CurrencyAdapter currencyAdapter = new CurrencyAdapter(currencyBuddies , context);

                        //  rv.setAdapter(currencyAdapter);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                rv.setAdapter(currencyAdapter);
                                rv.setLayoutManager(new LinearLayoutManager(context));
                            }
                        });
                    }
                }
            }).start();
        }


        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
}
