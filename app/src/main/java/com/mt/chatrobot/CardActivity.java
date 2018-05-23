package com.mt.chatrobot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;


import com.mt.exchangerate.R;
import com.mt.robot.tuling.Lists;
import com.mt.robot.tuling.TulingJson;
import com.mt.robotAdapter.FoodAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private FoodAdapter mAdapter;
    private List<Map<String, Object>> foodDisplay = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        init();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void init() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setRecyclerView();
        Bundle bundleObject = getIntent().getExtras();
        TulingJson tulingJson = (TulingJson) bundleObject.getSerializable("TulingJson");
        parseJson(tulingJson);
    }

    public void setRecyclerView() {
        recyclerView =  findViewById(R.id.display_recycler);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FoodAdapter(this, foodDisplay);
        recyclerView.setAdapter(mAdapter);
    }


    public void parseJson(TulingJson tulingJson) {   //存储json数据
        String code = tulingJson.code;
        switch (code) {
            case "308000":
                parseFood(tulingJson);
                break;
            case "311000":
                parsePrice(tulingJson);
                break;
        }

    }


    public void parseFood(TulingJson tulingJson) {  // 菜谱
        String text = tulingJson.text;
        for (Lists list : tulingJson.list) {
            Map<String, Object> foodMap = new HashMap<>();
            String name = list.name;
            String info = list.info;
            String detailUrl = list.detailurl;
            String icon = list.icon;
            foodMap.put("name", name);
            foodMap.put("info", "原料：" + info);
            foodMap.put("detailUrl", detailUrl);
            foodMap.put("icon", icon);
            foodMap.put("sort", "food");
            foodDisplay.add(foodMap);
        }
    }

    public void parsePrice(TulingJson tulingJson) {  //价格
        String text = tulingJson.text;
        for (Lists list : tulingJson.list) {
            Map<String, Object> foodMap = new HashMap<>();
            String name = list.name;
            String price = list.price;
            String detailUrl = list.detailurl;
            String icon = list.icon;
            foodMap.put("name", name);
            foodMap.put("price", price);
            foodMap.put("detailUrl", detailUrl);
            foodMap.put("icon", icon);
            foodMap.put("sort", "price");
            foodDisplay.add(foodMap);
        }
    }
}
