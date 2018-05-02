package com.mt.recyclerAdapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import com.mt.buddy.CurrencyBuddy;
import com.mt.exchangerate.MainActivity;
import com.mt.exchangerate.R;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> implements Filterable {
    private ArrayList<CurrencyBuddy> mList;
    private Activity mContext;
    private ArrayList<CurrencyBuddy> list_all;
    public class ViewHolder extends RecyclerView.ViewHolder{
        //private RelativeLayout relativeLayout;
        private TextView cname ;
        private TextView ename;
        private boolean isChoose ;
        private View currencyView;
        ViewHolder(View view){
            super(view);
            currencyView = view ;
            ename = view.findViewById(R.id.ename);
            cname = view.findViewById(R.id.cname);
        }
    }
    public CurrencyAdapter(ArrayList<CurrencyBuddy> mList , Activity context){
        this.mList = mList;
        this.mContext = context;
        this.list_all = this.mList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_item , parent ,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.currencyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(view.)
                Intent intents = mContext.getIntent();
                String type = intents.getStringExtra("type");
                if("target".equals(type)){
                    if(MainActivity.rateBuddyMap.get(viewHolder.cname.getText()) != null){
                        Toast.makeText(mContext , "已添加！勿重复添加！" ,Toast.LENGTH_SHORT).show();
                    }else if(MainActivity.SOURCR.equals(viewHolder.cname.getText().toString())){
                        Toast.makeText(mContext , "请选择其他币种！！" ,Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent();
                        intent.putExtra("ename" , viewHolder.ename.getText());
                        intent.putExtra("cname" , viewHolder.cname.getText());
                        mContext.setResult(RESULT_OK , intent);
                        mContext.finish();
                    }
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("ename" , viewHolder.ename.getText());
                    intent.putExtra("cname" , viewHolder.cname.getText());
                    mContext.setResult(RESULT_OK , intent);
                    mContext.finish();
                }


            }
        });

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CurrencyBuddy currencyBuddy = mList.get(position);
        holder.ename.setText(currencyBuddy.getEname());
        holder.cname.setText(currencyBuddy.getCname());

    }
    @Override
    public int getItemCount(){
        return mList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if(charSequence == null || charSequence.length() == 0){
                    filterResults.count = list_all.size();
                    filterResults.values = list_all;
                }else{
                    ArrayList<CurrencyBuddy> resultData = new ArrayList<>();
                    if(conValidate(charSequence.toString())){
                        String search = charSequence.toString().toUpperCase();
                        for(CurrencyBuddy c : list_all){
                            if(c.getEname().contains(search))
                                resultData.add(c);
                        }
                    }else{
                        String search = charSequence.toString();
                        for(CurrencyBuddy c : list_all){
                            if(c.getCname().contains(search))
                                resultData.add(c);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;
                }
                return filterResults;
            }
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                //listFilter.clear();
                ArrayList<CurrencyBuddy> cu = (ArrayList<CurrencyBuddy>) filterResults.values;
                if(cu.size() > 0){
                    mList = cu;

                }else{
                    mList.clear();
                    Toast.makeText(mContext , "无匹配！" ,Toast.LENGTH_LONG).show();
                }
                notifyDataSetChanged();

            }
        };
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
