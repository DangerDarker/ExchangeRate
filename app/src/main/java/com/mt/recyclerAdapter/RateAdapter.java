package com.mt.recyclerAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mt.buddy.ItemData;
import com.mt.buddy.RateBuddy;
import com.mt.buddy.RecyclerItem;
import com.mt.exchangerate.Config;
import com.mt.exchangerate.MainActivity;
import com.mt.exchangerate.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

/**
 * Created by Mr_L on 2018/4/27.
 */

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.ViewHolder>{

    ArrayList<RecyclerItem> mList ;
    private Activity mContext;

    public RateAdapter(ArrayList<RecyclerItem> list , Activity activity){
        this.mList = list;
        this.mContext = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.money_item , parent ,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.rateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setItems(new String[]{"删除"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(mList.size() == 1){
                            mList.clear();
                        }else{
                            mList.remove(viewHolder.position);
                        }
                        notifyItemRemoved(viewHolder.position );
                        MainActivity.rateBuddyMap.remove(viewHolder.cname.getText().toString());
                        notifyItemRangeChanged(viewHolder.position , mList.size() -1 );
                        if("人民币".equals(MainActivity.SOURCR)){
                            //if()
                            DataSupport.deleteAll(ItemData.class , "cname = ?" , viewHolder.cname.getText().toString());
                            //删除条目时也删除数据库中相应数据
                        }
                    }
                });
                builder.show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecyclerItem rateBuddy = mList.get(position);
        if(rateBuddy != null){
            Log.i("RateAddapter", "onBindViewHolder: "+rateBuddy.getRateBuddy().getEname() + "  "+rateBuddy.getRateBuddy().getCname());
            String Ename = Config.CURRENCY_MAP.get(rateBuddy.getRateBuddy().getCname()).getEname();
            holder.cname.setText(rateBuddy.getRateBuddy().getCname());
            holder.ename.setText(Ename);
            holder.rate.setText(rateBuddy.getRateBuddy().getRate());
            holder.date.setText(rateBuddy.getRateBuddy().getDate());
            String mo = String.valueOf(rateBuddy.getMoney());
            holder.money.setText(mo);
            holder.position = position;
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView cname ;
        private TextView ename ;
        private TextView rate ;
        private TextView date ;
        private TextView money;
        public View rateView;
        public int position ;
        public ViewHolder(View v ){
            super(v);
            rateView = v ;
            cname = v.findViewById(R.id.country_cname);
             ename = v.findViewById(R.id.country_ename);
            rate = v.findViewById(R.id.rate_money);
            date = v.findViewById(R.id.date);
            money = v.findViewById(R.id.money);

        }
    }
}
