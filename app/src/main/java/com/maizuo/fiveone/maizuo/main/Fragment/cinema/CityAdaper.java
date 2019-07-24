package com.maizuo.fiveone.maizuo.main.Fragment.cinema;

import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maizuo.fiveone.maizuo.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityAdaper extends RecyclerView.Adapter<CityAdaper.ViewHolder>  {
    private List<Cinema.District> Datas;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public CityAdaper(List<Cinema.District> datas, Context mContext) {
        Datas = datas;
        this.mContext = mContext;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.textView = view.findViewById(R.id.text);
        }
    }
    public static interface OnItemClickListener {
        void onItemClick(View view);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public int getItemViewType(int position) {
        Cinema.District item = (Cinema.District)Datas.get(position);
        return (Integer) item.viewType;
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("aa",""+viewType);
        View view;
        if (viewType == 1) {
            view = (View) LayoutInflater.from(mContext).inflate(R.layout.main_fragment_cinema_cityitem, parent, false);
        } else {
            view = (View) LayoutInflater.from(mContext).inflate(R.layout.main_fragment_cinema_cityitem_visible, parent, false);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v);
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return Datas.size();
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        Cinema.District item = (Cinema.District)Datas.get(position);
        String cityName =  item.cityName;
        //holder.textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        holder.textView.setText(cityName);
        holder.view.setTag(item.cityId);
    }
}
