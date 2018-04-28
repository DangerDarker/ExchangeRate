package com.mt.recyclerAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mt.buddy.RateBuddy;
import com.mt.exchangerate.MainActivity;
import com.mt.exchangerate.R;

import java.util.ArrayList;

/**
 * Created by Mr_L on 2018/4/27.
 */

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.ViewHolder>{

    ArrayList<RateBuddy> mList ;
    private Activity mContext;

    public RateAdapter(ArrayList<RateBuddy> list , Activity activity){
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
                        mList.remove(viewHolder.position);
                       // notifyItemRangeRemoved(viewHolder.position , mList.size()-);
                        notifyItemRemoved(viewHolder.position );
                        MainActivity.rateBuddyMap.remove(viewHolder.cname);
                        notifyItemRangeChanged(viewHolder.position , mList.size() -1 );
                    }
                });
                builder.show();
              //  notifyDataSetChanged();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RateBuddy rateBuddy = mList.get(position);
        if(rateBuddy != null){
            holder.cname.setText(rateBuddy.getCname());
          //  holder.ename.setText(rateBuddy.getEname());
            holder.money.setText(rateBuddy.getRate());
            holder.date.setText(rateBuddy.getDate());
            holder.position = position;
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView cname ;
        //private TextView ename ;
        private TextView money ;
        private TextView date ;
        public View rateView;
        public int position ;
        public ViewHolder(View v ){
            super(v);
            rateView = v ;
            cname = v.findViewById(R.id.country_cname);
          //  ename = v.findViewById(R.id.country_ename);
            money = v.findViewById(R.id.rate_money);
            date = v.findViewById(R.id.date);

        }
    }
}