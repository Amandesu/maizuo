package com.maizuo.fiveone.maizuo.main.Fragment.cinema;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maizuo.fiveone.maizuo.R;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListAdaper extends BaseAdapter {
    private List<Map<String, Object>> Datas;
    private Drawable drawable;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public ListAdaper(List<Map<String, Object>> datas, Context mContext) {
        Datas = datas;
        this.mContext = mContext;
    }

    /**
     * 返回item的个数
     * @return
     */
    @Override
    public int getCount() {
        return Datas.size();
    }

    /**
     * 返回每一个item对象
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return Datas.get(i);
    }

    /**
     * 返回每一个item的id
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView name;
        public TextView address;
        public TextView lowPrice;
    }
    public static interface OnItemClickListener {
        void onItemClick(View view ,String cinemaId);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final HashMap<String, String> item = (HashMap)getItem(i);
        View view;
        final ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.main_fragment_cinema_item,viewGroup,false);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.address = (TextView) view.findViewById(R.id.address);
            holder.lowPrice = (TextView) view.findViewById(R.id.lowprice);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, item.get("cinemaId"));
            }
        });

        holder.name.setText(item.get("name"));
        holder.address.setText(item.get("address"));
        holder.lowPrice.setText("￥"+item.get("lowPrice")+"起");
        return view;
    }
}
