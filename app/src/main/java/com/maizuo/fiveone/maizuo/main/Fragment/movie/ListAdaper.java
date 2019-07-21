package com.maizuo.fiveone.maizuo.main.Fragment.movie;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
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

/**
 * Created by MyPC on 2019/7/16.
 */

public class ListAdaper extends BaseAdapter {
    private List<Map<String, Object>> Datas;
    private Drawable drawable;
    private Context mContext;

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
        public ImageView poster;
        public TextView grade;
        public TextView name;
        public TextView nation;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final HashMap<String, String> item = (HashMap)getItem(i);
        View view;
        final ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.main_fragment_movie_item,viewGroup,false);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.nation = (TextView) view.findViewById(R.id.nation);
            holder.grade = (TextView) view.findViewById(R.id.grade);
            holder.poster = (ImageView)view.findViewById(R.id.poster);
            new Thread(new Runnable(){
                @Override
                public void run() {
                    final Drawable drawable = loadImageFromNetwork(item.get("poster"));
                    holder.poster.post(new Runnable(){
                        @Override
                        public void run() {
                            holder.poster.setImageDrawable(drawable) ;
                        };
                    });
                }
            }).start();
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

        holder.name.setText(item.get("name"));
        holder.nation.setText(item.get("nation"));
        holder.grade.setText(item.get("grade"));

        return view;
    }

    private Drawable loadImageFromNetwork(String imageUrl)
    {
        Drawable drawable = null;
        try {
            // 可以在这里通过文件名来判断，是否本地有此图片
            drawable = Drawable.createFromStream(
                    new URL(imageUrl).openStream(), "image.jpg");
        } catch (IOException e) {

        }

        return drawable ;
    }
}
