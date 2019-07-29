package com.maizuo.fiveone.maizuo.filmDetail;

import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maizuo.fiveone.maizuo.R;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActorAdaper extends RecyclerView.Adapter<ActorAdaper.ViewHolder>  {
    private List<FilmDetail.Actor> Datas;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public ActorAdaper(List<FilmDetail.Actor> datas, Context mContext) {
        Datas = datas;
        this.mContext = mContext;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView actors_poster;
        public TextView actors_name;
        public TextView actors_role;
        public View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.actors_poster = view.findViewById(R.id.actors_poster);
            this.actors_name = view.findViewById(R.id.actors_name);
            this.actors_role = view.findViewById(R.id.actors_role);
        }
    }
    public static interface OnItemClickListener {
        void onItemClick(View view);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        view = (View) LayoutInflater.from(mContext).inflate(R.layout.filmdetail_actoritem, parent, false);

       /* view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v);
            }
        });*/
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return Datas.size();
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        final FilmDetail.Actor item = (FilmDetail.Actor)Datas.get(position);

        holder.actors_name.setText(item.name);
        holder.actors_role.setText(item.role);

        new Thread(new Runnable(){
            @Override
            public void run() {
                final Drawable drawable = loadImageFromNetwork(item.avatarAddress);
                holder.actors_poster.post(new Runnable(){
                    @Override
                    public void run() {
                        holder.actors_poster.setImageDrawable(drawable) ;
                    };
                });
            }
        }).start();
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
