package com.maizuo.fiveone.maizuo.main;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.maizuo.fiveone.maizuo.R;
import com.maizuo.fiveone.maizuo.main.Fragment.cinema.Cinema;
import com.maizuo.fiveone.maizuo.main.Fragment.movie.ListAdaper;
import com.maizuo.fiveone.maizuo.main.Fragment.movie.Movie;
import com.maizuo.fiveone.maizuo.main.Fragment.movie.RequestMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ViewGroup footerTab;
    private Fragment movie = new Movie();
    private Fragment cinema = new Cinema();
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
        mTransaction.add(R.id.main_layout, movie);
        mTransaction.add(R.id.main_layout, cinema);
        mTransaction.hide(movie);
        mTransaction.hide(cinema);
        mTransaction.commit();
        initFooterTab();

    }
    public void setFragment(int index){
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
        mTransaction.hide(movie);
        mTransaction.hide(cinema);
        if (index == 0) {
            mTransaction.show(movie);
        } else if (index == 1) {
            mTransaction.show(cinema);
        }
        mTransaction.commit();
    }
    public void initFooterTab(){
        footerTab = (ViewGroup)findViewById(R.id.main_footer);
        int count = footerTab.getChildCount();
        for (int i = 0 ; i < count; i++){
            ViewGroup child = (ViewGroup)footerTab.getChildAt(i);
            child.setTag(i);
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setFragment((Integer) view.getTag());
                    updateTab((Integer) view.getTag());
                }
            });
        }
        updateTab(0);
        setFragment(0);
    }
    public void updateTab(int index) {

        for (int i = 0; i < footerTab.getChildCount(); i++) {
            ViewGroup tab = (ViewGroup) footerTab.getChildAt(i);
            TextView text = (TextView)tab.getChildAt(1);
            ImageView img = (ImageView)tab.getChildAt(0);
            text.setTextColor(Color.parseColor("#797d82"));
            img.getDrawable().setLevel(0);
        }
        ViewGroup activeTab = (ViewGroup)footerTab.getChildAt(index);
        TextView activetext = (TextView) activeTab.getChildAt(1);
        ImageView activeimg = (ImageView)activeTab.getChildAt(0);
        activetext.setTextColor(Color.parseColor("#ff5f16"));
        activeimg.getDrawable().setLevel(1);
    }


}
