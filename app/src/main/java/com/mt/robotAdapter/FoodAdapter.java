package com.mt.robotAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mt.exchangerate.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private List<Map<String, Object>> foodDisplay = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public FoodAdapter(Context context, List<Map<String, Object>> foodDisplay) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.foodDisplay = foodDisplay;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_display, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        vh.pic = view.findViewById(R.id.pic);
        vh.title =  view.findViewById(R.id.info_title);
        vh.rawMaterial =  view.findViewById(R.id.raw_material);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int i) {
        String sort = (String) foodDisplay.get(i).get("sort");
        String name = (String) foodDisplay.get(i).get("name");
        String detailUrl = (String) foodDisplay.get(i).get("detailUrl");
        String icon = (String) foodDisplay.get(i).get("icon");
        switch (sort) {
            case "food":
                String info = (String) foodDisplay.get(i).get("info");
                vh.title.setText(name);
                vh.rawMaterial.setText(info);
                break;
            case "price":
                String price = (String) foodDisplay.get(i).get("price");
                vh.title.setText(price);
                vh.rawMaterial.setText(name);
                break;
        }
        Picasso.with(context).load(icon).into(vh.pic);
    }

    @Override
    public int getItemCount() {
        return foodDisplay.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView pic;
        public TextView title;
        public TextView rawMaterial;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
