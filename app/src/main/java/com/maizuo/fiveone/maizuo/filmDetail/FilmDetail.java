package com.maizuo.fiveone.maizuo.filmDetail;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.maizuo.fiveone.maizuo.R;
import com.maizuo.fiveone.maizuo.main.Fragment.cinema.Cinema;
import com.maizuo.fiveone.maizuo.main.Fragment.cinema.CityAdaper;
import com.maizuo.fiveone.maizuo.main.Fragment.cinema.RequestCinema;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MyPC on 2019/7/29.
 */

public class FilmDetail extends AppCompatActivity {
    private RequestInfo requestInfo = new RequestInfo();
    private List<Actor> actorList = new ArrayList<Actor>();
    private ActorAdaper actorAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filmdetail_activity);


        LinearLayoutManager  manager = new LinearLayoutManager(getBaseContext());
        manager.setOrientation(GridLayoutManager.HORIZONTAL);

        RecyclerView recyclerView = findViewById(R.id.filmdetail_actor_recycler_view);
        recyclerView.setLayoutManager(manager);

        actorAdaper = new ActorAdaper(actorList, getBaseContext());
        recyclerView.setAdapter(actorAdaper);

        initCimenaCall();
        requestInfo.getFilmInfo();

    }

    class Actor {
        public String name;
        public String role;
        public String avatarAddress;
    }
    public void initCimenaCall() {
        requestInfo.setOnCall(new RequestInfo.OnCall() {
            @Override
            public void OnCallBack(JSONObject responseData) throws JSONException {
                JSONObject data = (JSONObject) responseData.get("data");
                JSONObject film = (JSONObject) data.get("film");
                JSONArray actors = (JSONArray) film.get("actors");

                for (int i = 0; i < actors.length(); i++) {
                    JSONObject object = (JSONObject) actors.get(i);
                    Actor actor = new Actor();

                    String address = "", name = ""; String lowPrice = "";
                    if (object.has("name")) actor.name = object.getString("name");
                    if (object.has("role")) actor.role = object.getString("role");
                    if (object.has("avatarAddress")) actor.avatarAddress = object.getString("avatarAddress");
                    actorList.add(actor);
                }
                actorAdaper.notifyDataSetChanged();

            }
        });

    }
}