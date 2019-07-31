package com.maizuo.fiveone.maizuo.cinemas;



import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.maizuo.fiveone.maizuo.R;
import com.maizuo.fiveone.maizuo.filmDetail.ActorAdaper;
import com.maizuo.fiveone.maizuo.main.Fragment.cinema.Cinema;
import com.maizuo.fiveone.maizuo.main.Fragment.cinema.CityAdaper;
import com.maizuo.fiveone.maizuo.main.Fragment.cinema.ListAdaper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MyPC on 2019/7/20.
 */

public class CinemasActivity extends AppCompatActivity {
    private ListView listView;
    ViewGroup rangeTabs;

    private List allList = new ArrayList<Map<String, Object>>();
    private List dateList = new ArrayList<Map<String, Object>>();
    private List<Cinema.District> cityList = new ArrayList ();
    private ListAdaper adaper;
    private CityAdaper cityAdaper;
    private DateAdaper dateAdaper;

    private RequestCinemas requestCinemas = new RequestCinemas();
    private RequestCinemaList requestCinemaList = new RequestCinemaList();

    private JSONArray showCinemas;
    private int activeDateIndex = 0;
    private int activeRangeIndex = -1;
    private int cityId = 0;
    private String cityName = "全部";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cinemas_activity);
        // 注册请求响应
        initMovieCall();
        intCinmaListCall();
        initDateAdaper();
        initCityAdaper();
        initCinemasAdaper();
        initOnclickEvent();
        requestCinemas.getCinemas();
    }
    public void initDateAdaper(){
        LinearLayoutManager manager = new LinearLayoutManager(getBaseContext());
        manager.setOrientation(GridLayoutManager.HORIZONTAL);
        RecyclerView recyclerView = findViewById(R.id.date_recycler_view);
        recyclerView.setLayoutManager(manager);
        dateAdaper = new DateAdaper(dateList, getBaseContext());
        recyclerView.setAdapter(dateAdaper);
    }
    public void initDateItemClick(){
        dateAdaper.setOnItemClickListener(new DateAdaper.OnItemClickListener(){
            public void onItemClick(View v){

            }
        });
    }
    public void initCityAdaper(){
        GridLayoutManager manager = new GridLayoutManager(getBaseContext(), 4);
        RecyclerView recyclerView = findViewById(R.id.city_recycler_view);
        recyclerView.setLayoutManager(manager);
        cityAdaper = new CityAdaper(cityList, getBaseContext());
        recyclerView.setAdapter(cityAdaper);
    }
    public void initCinemasAdaper(){
        adaper = new ListAdaper(allList, getBaseContext());
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adaper);
    }
    public void initOnclickEvent(){
        initRangeSelectEvent();
        initCityItemClick();
        initDateItemClick();
    }
    public void initRangeSelectEvent(){
        rangeTabs = (ViewGroup)findViewById(R.id.range_select);
        for (int i = 0; i < rangeTabs.getChildCount(); i++) {
            View tabPane = rangeTabs.getChildAt(i);
            tabPane.setTag(i);
            tabPane.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setRangeSelectTab((Integer) view.getTag());
                }
            });
        }
    }
    public void initCityItemClick(){
        cityAdaper.setOnItemClickListener(new CityAdaper.OnItemClickListener(){
            public void onItemClick(View v){
                cityId = (Integer) v.getTag();
                for (int i = 0; i < cityList.size(); i++) {
                    Cinema.District d = cityList.get(i);
                    if (d.cityId == cityId) {
                        d.viewType = 2;
                        cityName = d.cityName;
                    } else {
                        d.viewType =1;
                    }
                }
                ViewGroup tabs = (ViewGroup)findViewById(R.id.range_select);
                TextView text = (TextView)tabs.getChildAt(0);
                text.setText(cityName);
                setRangeSelectTab(activeRangeIndex);
                cityAdaper.notifyDataSetChanged();
            }
        });
    }
    public void setRangeSelectTab(int index) {
        View mask = findViewById(R.id.mask);
        TextView activeTextView =  (TextView)rangeTabs.getChildAt(index);
        ViewGroup groupOptions =  (ViewGroup)findViewById(R.id.options);
        for (int j =0; j < groupOptions.getChildCount(); j++) {
            View opt = groupOptions.getChildAt(j);
            opt.setVisibility(View.GONE);
        }
        // 高亮
        for (int i = 0; i < rangeTabs.getChildCount(); i++) {
            TextView tabPane = (TextView)rangeTabs.getChildAt(i);
            tabPane.setTextColor(Color.parseColor("#191a1b"));
        }
        if (index != activeRangeIndex) {
            activeRangeIndex = index;

            listView.setVisibility(View.GONE);
            groupOptions.getChildAt(index).setVisibility(View.VISIBLE);
            mask.setVisibility(View.VISIBLE);
            activeTextView.setTextColor(Color.parseColor("#ff5f16"));

        } else {
            activeRangeIndex = -1;
            listView.setVisibility(View.VISIBLE);
            groupOptions.getChildAt(index).setVisibility(View.GONE);
            mask.setVisibility(View.GONE);
        }
    }
    public void initMovieCall() {
        requestCinemas.setOnCall(new RequestCinemas.OnCall() {
            @Override
            public void OnCallBack(JSONObject responseData) throws JSONException {
                List list = new ArrayList<Map<String, Object>>();
                JSONObject data = (JSONObject) responseData.get("data");
                showCinemas = (JSONArray) data.get("showCinemas");
                // 日期列表
                Map<String, String> dateMap = new HashMap<>();
                for (int i = 0; i < showCinemas.length();i++) {
                    Map<String, Object> map = new HashMap<>();
                    JSONObject cinema = (JSONObject)showCinemas.get(i);
                    map.put("name", new SimpleDateFormat( "EEEEMM月dd日" ).format( cinema.getLong("showDate")*1000));
                    dateList.add(map);
                }
                dateAdaper.notifyDataSetChanged();
                getCinemaList();
            }
        });
    }
    public void getCinemaList() throws JSONException{
        JSONObject cinema = (JSONObject)showCinemas.get(activeDateIndex);
        JSONArray cinemaList = (JSONArray)cinema.get("cinemaList");
        Map<String, String> params = new HashMap();
        JSONObject params1 = new JSONObject();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < cinemaList.length(); i++ ) {
            s.append(cinemaList.get(i).toString());
            if (i != cinemaList.length()-1) {
                s.append(",");
            }
        }
        params.put("cityId", "440300");
        params.put("cinemaIds", s.toString());
        params1.put("cityId", "440300");
        params1.put("cinemaIds", s.toString());
        requestCinemaList.setJsonBody(params1);
        requestCinemaList.getCinemaList();
    }
    public void intCinmaListCall() {
        requestCinemaList.setOnCall(new RequestCinemaList.OnCall() {
            @Override
            public void OnCallBack(JSONObject responseData) throws JSONException {
                JSONObject data = (JSONObject) responseData.get("data");
                JSONArray cinemas = (JSONArray)data.get("cinemas");

                Map<Integer, Cinema.District> cityMap = new HashMap();
                cityList.add(new Cinema.District(0, "全部", 2));
                for (int i = 0; i < cinemas.length(); i++) {
                    JSONObject object = (JSONObject) cinemas.get(i);
                    // 城市数据
                    int districtId = object.getInt("districtId")  ;
                    if (cityMap.get(districtId) == null) {
                        Cinema.District district = new Cinema.District(districtId, object.getString("districtName"), 1);
                        cityMap.put(districtId, district);
                        cityList.add(district);
                    }
                    // 电影院数据
                    Map map = new HashMap<String, Object>();
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

    /**
     * Created by MyPC on 2019/7/31.
     */


}