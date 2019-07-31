package com.maizuo.fiveone.maizuo.main.Fragment.cinema;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.maizuo.fiveone.maizuo.R;


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

public class Cinema extends Fragment {
    private View view;
    private ListView listView;


    private List allList = new ArrayList<Map<String, Object>>();
    private List<District> cityList = new ArrayList ();
    private ListAdaper adaper;
    private CityAdaper cityAdaper;
    private RequestCinema requestCinema = new RequestCinema();

    private int activeIndex = -1;
    private String cityName = "全部";
    private int cityId = 0;
    private int ticketFlag = 1;
    private int threeOpt = 0;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment_cinema, container, false);
        return view;
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 获取电影院数据
        adaper = new ListAdaper(allList, getContext());
        listView = (ListView)view.findViewById(R.id.list);
        listView.setAdapter(adaper);
        initCimenaCall();
        requestCinema.getCinemaList();

        // 城市筛选
        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        RecyclerView recyclerView = view.findViewById(R.id.city_recycler_view);
        recyclerView.setLayoutManager(manager);
        cityAdaper = new CityAdaper(cityList, getContext());
        recyclerView.setAdapter(cityAdaper);
        // 初始化筛选事件
        initSelectEvent();
        // 设置筛选项的样式
        setOptionStyle();

        cityAdaper.setOnItemClickListener(new CityAdaper.OnItemClickListener(){
            public void onItemClick(View v){
                cityId = (Integer) v.getTag();

                TextView a = (TextView)v.findViewById(R.id.text);
                a.setSelected(true);

                for (int i = 0; i < cityList.size(); i++) {
                    District d = cityList.get(i);
                    if (d.cityId == cityId) {
                        d.viewType = 2;
                        cityName = d.cityName;
                    } else {
                        d.viewType =1;
                    }
                }
                ViewGroup tabs = (ViewGroup)view.findViewById(R.id.select);
                TextView text = (TextView)tabs.getChildAt(0);
                text.setText(cityName);
                setSelectTab(activeIndex);
                cityAdaper.notifyDataSetChanged();
            }
        });


    }
    public void initSelectEvent(){
        ViewGroup tabs = (ViewGroup)view.findViewById(R.id.select);
        for (int i = 0; i < tabs.getChildCount(); i++) {
            View tabPane = tabs.getChildAt(i);
            tabPane.setTag(i);
            tabPane.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSelectTab((Integer) view.getTag());
                }
            });
        }
    }
    public void setOptionStyle(){

    }
    public void setSelectTab(int i){
        View mask = view.findViewById(R.id.mask);
        View options = view.findViewById(R.id.options);
        ViewGroup groupOptions =  (ViewGroup)options;
        for (int j =0; j < groupOptions.getChildCount(); j++) {
            View opt = groupOptions.getChildAt(j);
            opt.setVisibility(View.GONE);
        }

        if (i != activeIndex) {
            activeIndex = i;
            mask.setVisibility(View.VISIBLE);
            options.setVisibility(View.VISIBLE);
            groupOptions.getChildAt(i).setVisibility(View.VISIBLE);

        } else {
            mask.setVisibility(View.GONE);
            options.setVisibility(View.GONE);
            activeIndex = -1;
        }

    }
    public static class District {
        public int cityId;
        public String cityName;
        public int viewType;
        public District (int cityId, String cityName, int viewType) {
            this.cityId = cityId;
            this.cityName = cityName;
            this.viewType = viewType;
        }
    }
    public void initCimenaCall(){
        requestCinema.setOnCall(new RequestCinema.OnCall() {
            @Override
            public void OnCallBack(JSONObject responseData) throws JSONException {
                JSONObject data = (JSONObject)responseData.get("data");
                JSONArray cinemas = (JSONArray)data.get("cinemas");
                List list = new ArrayList<Map<String, Object>>();

                Map<Integer, District> cityMap = new HashMap();

                cityList.add(new District(0, "全部", 2));

                for (int i = 0; i < cinemas.length(); i++) {
                    Map map = new HashMap<String, Object>();
                    JSONObject object = (JSONObject) cinemas.get(i);
                    int districtId = object.getInt("districtId")  ;
                    if (cityMap.get(districtId) == null) {
                        District district = new District(districtId, object.getString("districtName"), 1);
                        cityMap.put(districtId, district);
                        cityList.add(district);
                    }
                    String address = "", name = ""; String lowPrice = "";
                    if (object.has("name")) map.put("name", object.getString("name"));
                    if (object.has("address")) map.put("address", object.getString("address"));
                    if (object.has("lowPrice")){

                        map.put("lowPrice", Integer.toString(object.getInt("lowPrice")/100));
                    }
                    allList.add(map);
                }
                adaper.notifyDataSetChanged();
                cityAdaper.notifyDataSetChanged();
            }
        });
    }
}
