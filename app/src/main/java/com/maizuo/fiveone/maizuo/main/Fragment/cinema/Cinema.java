package com.maizuo.fiveone.maizuo.main.Fragment.cinema;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
    private ListAdaper adaper;
    private RequestCinema requestCinema = new RequestCinema();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment_cinema, container, false);
        return view;
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adaper = new ListAdaper(allList, getContext());
        listView = (ListView)view.findViewById(R.id.list);
        listView.setAdapter(adaper);
        initCimenaCall();
        requestCinema.getCinemaList();
    }
    public void initCimenaCall(){
        requestCinema.setOnCall(new RequestCinema.OnCall() {
            @Override
            public void OnCallBack(JSONObject responseData) throws JSONException {
                JSONObject data = (JSONObject)responseData.get("data");
                JSONArray cinemas = (JSONArray)data.get("cinemas");
                List list = new ArrayList<Map<String, Object>>();

                for (int i = 0; i < cinemas.length(); i++) {
                    Map map = new HashMap<String, Object>();
                    JSONObject object = (JSONObject) cinemas.get(i);
                    String address = "", name = ""; String lowPrice = "";
                    if (object.has("name")) map.put("name", object.getString("name"));
                    if (object.has("address")) map.put("address", object.getString("address"));
                    if (object.has("lowPrice")){

                        map.put("lowPrice", Integer.toString(object.getInt("lowPrice")/100));
                    }
                    allList.add(map);
                }
                adaper.notifyDataSetChanged();
            }
        });
    }
}
