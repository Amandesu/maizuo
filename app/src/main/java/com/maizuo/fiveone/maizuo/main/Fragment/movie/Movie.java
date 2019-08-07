package com.maizuo.fiveone.maizuo.main.Fragment.movie;


import android.content.Intent;
import android.support.v4.app.Fragment ;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ListView;
import android.widget.ScrollView;

import com.maizuo.fiveone.maizuo.R;
import com.maizuo.fiveone.maizuo.RN.DevActivity;
import com.maizuo.fiveone.maizuo.RN.MyReactActivity;
import com.maizuo.fiveone.maizuo.cinemas.CinemasActivity;
import com.maizuo.fiveone.maizuo.filmDetail.FilmDetail;
import com.maizuo.fiveone.maizuo.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MyPC on 2019/7/20.
 */

public class Movie extends Fragment {
    private View view;
    private ViewGroup Tabs;
    private ViewGroup HeaderTabs;
    private ListView listView;
    private View footer;
    private ListAdaper adaper;
    private RequestMovie requestMovie = new RequestMovie();
    private int totalPageNum;
    private boolean loading = false;
    private int activeIndex = -1;
    private List allList = new ArrayList<Map<String, Object>>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment_movie, container, false);
        footer = inflater.inflate(R.layout.main_fragment_movie_item_footer, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Tabs = (ViewGroup)view.findViewById(R.id.tabs);
        HeaderTabs =  (ViewGroup)view.findViewById(R.id.header_tabs);


        initMovieCall();
        initMovieAdaper();
        intEvent();
        // 滚动刷新
        srollView();
    }
    public void  intEvent(){
        // 点击城市
        view.findViewById(R.id.city).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyReactActivity.class);
                intent.putExtra("module", "city");
                startActivity(intent);
            }
        });
        view.findViewById(R.id.rndev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DevActivity.class);
                startActivity(intent);
            }
        });
        // tab事件
        initTabsEvent(Tabs);
        initTabsEvent(HeaderTabs);
        setSelectTab(Tabs.getChildAt(0));
    }
    public void initMovieAdaper(){
        adaper = new ListAdaper(allList, getContext());
        listView = (ListView)view.findViewById(R.id.list);
        listView.setAdapter(adaper);
        adaper.setOnItemClickListener(new ListAdaper.OnItemClickListener(){
            @Override
            public void onItemClick(String filmId) {
                Intent intent = new Intent(getActivity(), FilmDetail.class);
                intent.putExtra("filmId", filmId);
                startActivity(intent);
            }
            @Override
            public void onBtnClick(String filmId) {
                Intent intent = new Intent(getActivity(), CinemasActivity.class);
                intent.putExtra("filmId", filmId);
                startActivity(intent);
            }
        });

    }
    // 注册回调数据
    public void initMovieCall(){
        requestMovie.setOnCall(new RequestMovie.OnCall() {
            @Override
            public void OnCallBack(JSONObject responseData) throws JSONException {
                loading = false;
                listView.removeFooterView(footer);
                List list = new ArrayList<Map<String, Object>>();
                JSONObject data = (JSONObject)responseData.get("data");
                JSONArray films = (JSONArray)data.get("films");
                totalPageNum = (int)Math.ceil(data.getInt("total")/10);
                for (int i = 0; i < films.length(); i++) {
                    Map map = new HashMap<String, Object>();
                    JSONObject object = (JSONObject) films.get(i);
                    JSONArray actors = object.getJSONArray("actors");
                    String poster = "", name = "", grade="", nation="", runtime=""; StringBuffer actor = new StringBuffer();
                    for (int j = 0; j < actors.length(); j++) {
                        JSONObject _actor = (JSONObject) films.get(i);
                        actor.append(_actor.getString("name"));
                    }
                    if (object.has("poster")) map.put("poster", object.getString("poster"));
                    if (object.has("name")) map.put("name", object.getString("name"));
                    if (object.has("grade")) map.put("grade", object.getString("grade"));
                    if (object.has("nation")) map.put("nation", object.getString("nation"));
                    if (object.has("filmId")) map.put("filmId", object.getString("filmId"));
                    if (object.has("runtime")) map.put("runtime", object.getString("runtime"));

                    map.put("actor", actor.toString());
                    map.put("isPresale", object.getString("isPresale"));
                    map.put("filmType", ""+(activeIndex+1));

                    allList.add(map);
                }
                adaper.notifyDataSetChanged();
            }
        });
    }
    // 初始化tab点击事件
    public void initTabsEvent(ViewGroup Tabs){
        for (int i = 0; i < Tabs.getChildCount(); i++) {
            final View TabPane = Tabs.getChildAt(i);
            TabPane.setTag(i);
            TabPane.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSelectTab(view);
                }
            });
        }
    }
    public void setSelectTab(View TabPane){
        Integer index = (Integer) TabPane.getTag();
        if (index != activeIndex) {
            loading = true;
            activeIndex = index;
            ViewGroup [] alltab = {Tabs, HeaderTabs};
            for (ViewGroup tabs :alltab) {
                for (int i = 0; i < tabs.getChildCount(); i++) {
                    requestMovie.setType(activeIndex+1);
                    requestMovie.setPageNum(1);
                    tabs.getChildAt(i).setSelected(false);
                }
                tabs.getChildAt(activeIndex).setSelected(true);
            }
            allList.clear();
            adaper.notifyDataSetChanged();
            requestMovie.getMovieingList();
            listView.removeFooterView(footer);
            listView.addFooterView(footer);
        }
    }
    // 滚动刷新
    public void srollView(){
        final ScrollView scrollView = view.findViewById(R.id.scrollview);
        final View tabs = view.findViewById(R.id.tabs);
        final View headerTabs= view.findViewById(R.id.header_tabs);
        final View banner = view.findViewById(R.id.banner);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (scrollView.getScrollY() >= banner.getBottom()){
                    headerTabs.setVisibility(View.VISIBLE);
                    tabs.setVisibility(View.GONE);
                } else {
                    headerTabs.setVisibility(View.GONE);
                    tabs.setVisibility(View.VISIBLE);
                }
                if (scrollView.getScrollY() + scrollView.getHeight() - scrollView.getPaddingTop() - scrollView.getPaddingBottom() == scrollView.getChildAt(0).getHeight()) {
                    int pageNum = requestMovie.getPageNum();
                    if (pageNum<= totalPageNum && loading == false) {
                        loading = true;
                        requestMovie.setPageNum(pageNum+1);
                        requestMovie.getMovieingList();
                        listView.addFooterView(footer);
                    }
                }
            }
        });
    }
}
