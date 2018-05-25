package com.mt.chatrobot;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.mt.exchangerate.Plugin;
import com.mt.exchangerate.R;
import com.mt.request.HttpHelp;
import com.mt.robot.tuling.DensityUtil;
import com.mt.robot.tuling.Tuling;
import com.mt.robot.tuling.TulingJson;
import com.mt.robotAdapter.ConversationAdapter;
import com.squareup.okhttp.Response;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class RobotMain extends AppCompatActivity {

    private final String WELCOME_MESSAGE = "Master，有什么要吩咐小的的？小的可以陪您聊天，或者问小的您想知道的问题" +
            ".";

    private String userId;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private ConversationAdapter mAdapter;
    private List<Map<String, Object>> conversationDisplay = new ArrayList<>();
    private LinearLayout linearLayout;
    private EditText editText;
    private Button sendButton;
    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            String response = msg.obj.toString();
            parse(response);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_mian);
        Toolbar toolbar = findViewById(R.id.toolbar_robot);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUserId();
        setRecyclerView(); //设置聊天recyclerView
        setInput();  //设置输入框属性
        displayMessage(WELCOME_MESSAGE, true, false);  //显示欢迎消息
        //检查网络状态
        if (!Plugin.isNetworkAvailable(getApplicationContext())) {
            setNetworkMethod(this);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }


    public void setUserId() {//设置userId
        TelephonyManager telephonemanager = (TelephonyManager)
                this.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            String IMSI = telephonemanager.getSubscriberId();  //获取手机国际识别码IMEI
            userId = "chatRobot" + IMSI;
        } catch (Exception e) {
            e.printStackTrace();
            Random random = new Random(100);
            Long currentTimeMillis = System.currentTimeMillis();
            userId = random.toString() + currentTimeMillis.toString();
        }

    }


    public void setRecyclerView() {//设置recyclerView
        recyclerView =  findViewById(R.id.con_recycler);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ConversationAdapter(this, conversationDisplay);
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * 设置输入框
     */
    public void setInput() {
        linearLayout =  findViewById(R.id.input_line);
        editText = findViewById(R.id.input);
        sendButton =  findViewById(R.id.send_button);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int lines = editText.getLineCount();
                float dp;
                if (lines <= 4) {
                    dp = 60 + (lines - 1) * 60;
                } else {
                    dp = 200;
                }
                int px = DensityUtil.dip2px(RobotMain.this, dp);
                linearLayout.getLayoutParams().height = px;
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                } else {
                    sendButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public Response getResponse(String sendMessage) {//获取图灵机器人响应
        Log.d("wym", "message " + sendMessage);
        Tuling tuling = new Tuling();
        tuling.setInfo(sendMessage);
        tuling.setUserId(userId);
        String uri = tuling.generateUri();
        HttpHelp httpHelp = new HttpHelp(tuling);
        Response response = null;
        try {
            response = httpHelp.get(uri);
        } catch (Exception e) {
            Toast.makeText(RobotMain.this,
                    "网络异常", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return response;
    }


    public void parse(String response) {//解析响应消息
        Log.d("wym", response);
        try {
            TulingJson tulingJson = JSON.parseObject(response, TulingJson.class);
            String code = tulingJson.code;
            switch (code) {
                case "100000":
                    //文本类数据
                    String text = tulingJson.text;
                    displayMessage(text, true, false);
                    break;
                case "40001":
                    //key的长度错误（32位）
                    Toast.makeText(RobotMain.this,
                            "（系统）key的长度错误（32位）", Toast.LENGTH_SHORT).show();
                    break;
                case "40002":
                    //请求内容为空
                    Toast.makeText(RobotMain.this,
                            "（系统）请求内容为空", Toast.LENGTH_SHORT).show();
                    break;
                case "40003":
                    //key错误或帐号未激活
                    Toast.makeText(RobotMain.this,
                            "（系统）请求内容为空", Toast.LENGTH_SHORT).show();
                    break;
                case "40004":
                    //当天请求次数已用完
                    Toast.makeText(RobotMain.this,
                            "（系统）当天请求次数已用完", Toast.LENGTH_SHORT).show();
                    break;
                case "40005":
                    //暂不支持该功能
                    Toast.makeText(RobotMain.this,
                            "（系统）暂不支持该功能", Toast.LENGTH_SHORT).show();
                    break;
                case "40006":
                    //服务器升级中
                    Toast.makeText(RobotMain.this,
                            "（系统）服务器升级中", Toast.LENGTH_SHORT).show();
                    break;
                case "40007":
                    //服务器数据格式异常
                    Toast.makeText(RobotMain.this,
                            "（系统）服务器数据格式异常", Toast.LENGTH_SHORT).show();
                    break;
                    default:
                        String textRobot = "小的暂时不能理解这个，主人说个别的吧，嘻嘻。";
                        displayMessage(textRobot , true , false);
                        break;
            }
        } catch (Exception e) {
            Toast.makeText(RobotMain.this,
                    "网络异常，请检查网络是否被重定向或未登录", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendMessage(View view) {//发送消息
        //检查网络状态
        if (!Plugin.isNetworkAvailable(getApplicationContext())) {
            setNetworkMethod(this);
            return;
        }
        final String message = editText.getText().toString();
        displayMessage(message, false, false);
        editText.setText(null); //清空输入框
        new Thread(new Runnable() {

            @Override
            public void run() {
                Response response = getResponse(message);
                int response_code = 0;
                String response_string = null;
                try {
                    response_code = response.code();
                    Log.d("wym", "response_code " + response_code);
                    response_string = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (response_code == 200) {
                    Message message = Message.obtain();
                    message.obj = response_string;
                    RobotMain.this.handler1.sendMessage(message);
                } else {
                    Toast.makeText(RobotMain.this,
                            "网络错误，状态码：" + response_code, Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }
    public void displayMessage(String sendMessage, Boolean isRobot, Boolean isHtml) {//显示消息
        Map<String, Object> conversationMap = new HashMap<>();
        conversationMap.put("isRobot", isRobot);
        conversationMap.put("message", sendMessage);
        conversationMap.put("isHtml", isHtml);
        conversationMap.put("date", "");
        if (isRobot) {
            conversationMap.put("avatar", R.mipmap.robot);
        } else {
            conversationMap.put("avatar", R.mipmap.xiamu01);
        }
        conversationDisplay.add(0, conversationMap);
        mAdapter.notifyDataSetChanged();
    }
    public void setNetworkMethod(final Context context) {// 如果网络没有打开，提示打开网络设置界面
        new AlertDialog.Builder(this).setTitle("网络设置提示").setMessage("网络连接不可用,是否进行设置?")
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                        context.startActivity(intent);
                    }
                }).setNegativeButton("取消", null).show();
    }
}
