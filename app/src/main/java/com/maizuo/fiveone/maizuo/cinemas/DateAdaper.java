package com.maizuo.fiveone.maizuo.cinemas;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maizuo.fiveone.maizuo.R;
import com.maizuo.fiveone.maizuo.main.Fragment.cinema.Cinema;

import java.util.List;
import java.util.Map;

public class DateAdaper extends RecyclerView.Adapter<DateAdaper.ViewHolder>  {
    private List<Map<String, Object>> Datas;
    private Context mContext;
    private DateAdaper.OnItemClickListener mOnItemClickListener;

    public DateAdaper(List<Map<String, Object>> datas, Context mContext) {
        Datas = datas;
        this.mContext = mContext;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;


        public ViewHolder(TextView view) {
            super(view);
            this.textView = view;
        }
    }
    public static interface OnItemClickListener {
        void onItemClick(View view);
    }
    public void setOnItemClickListener(DateAdaper.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public DateAdaper.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TextView view;
        view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.cinemas_dateitem, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v);
            }
        });
        return new DateAdaper.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return Datas.size();
    }

    public void onBindViewHolder(DateAdaper.ViewHolder holder, int position) {
        Map<String, Object> item = (Map<String, Object>)Datas.get(position);

        holder.textView.setText((String)item.get("name"));

    }
}
