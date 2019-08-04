package com.maizuo.fiveone.maizuo.filmDetail;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.maizuo.fiveone.maizuo.R;
import com.maizuo.fiveone.maizuo.Utils;
import com.maizuo.fiveone.maizuo.cinemas.CinemasActivity;
import com.maizuo.fiveone.maizuo.main.Fragment.cinema.Cinema;
import com.maizuo.fiveone.maizuo.main.Fragment.cinema.CityAdaper;
import com.maizuo.fiveone.maizuo.main.Fragment.cinema.RequestCinema;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
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
    private List<String> photoList = new ArrayList<String>();
    private ActorAdaper actorAdaper;
    private PhotoAdaper photoAdaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filmdetail_activity);

        String filmId = getIntent().getStringExtra("filmId");
        requestInfo.setFilmId(filmId);

        initActorAdaper();
        initPhotoAdaper();
        initCimenaCall();
        initBindEvent();
        requestInfo.getFilmInfo();
    }
    public void initBindEvent(){
        findViewById(R.id.footer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FilmDetail.this, CinemasActivity.class);
                startActivity(intent);
            }
        });
    }
    public void initActorAdaper(){
        LinearLayoutManager  manager = new LinearLayoutManager(getBaseContext());
        manager.setOrientation(GridLayoutManager.HORIZONTAL);
        RecyclerView recyclerView = findViewById(R.id.filmdetail_actor_recycler_view);
        recyclerView.setLayoutManager(manager);
        actorAdaper = new ActorAdaper(actorList, getBaseContext());
        recyclerView.setAdapter(actorAdaper);
    }
    public void initPhotoAdaper(){
        LinearLayoutManager  manager = new LinearLayoutManager(getBaseContext());
        manager.setOrientation(GridLayoutManager.HORIZONTAL);
        RecyclerView recyclerView = findViewById(R.id.filmdetail_photo_listview);
        recyclerView.setLayoutManager(manager);
        photoAdaper = new PhotoAdaper(photoList, getBaseContext());
        recyclerView.setAdapter(photoAdaper);
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
                // 渲染详情
                renderDetail(film);

                // 演员列表
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

                // 剧照
                JSONArray photos = (JSONArray) film.get("photos");
                for (int i = 0; i < photos.length(); i++) {
                    photoList.add(photos.getString(i));
                }
                photoAdaper.notifyDataSetChanged();

            }
        });
    }
    public void renderDetail(final JSONObject detail) throws JSONException{
          //String name = detail.getString("name");
          final ImageView poster = (ImageView)findViewById(R.id.poster);
          TextView name = (TextView) findViewById(R.id.name);
          TextView category = (TextView) findViewById(R.id.category);
          TextView premiereAt = (TextView)findViewById(R.id.premiereAt);
          TextView nation_runtime =   (TextView)findViewById(R.id.nation_runtime);
          TextView film_synopsis = (TextView)findViewById(R.id.film_synopsis);

          final String posterUrl = detail.getString("poster");


          new Thread(new Runnable(){
                @Override
                public void run() {
                    final Drawable drawable = Utils.loadImageFromNetwork(posterUrl);
                    poster.post(new Runnable(){
                        @Override
                        public void run() {
                            poster.setImageDrawable(drawable) ;
                        };
                    });
                }}).start();

          name.setText(detail.getString("name"));
          category.setText(detail.getString("category"));
          premiereAt.setText(new SimpleDateFormat( "yyyy-MM-dd" ).format( detail.getLong("premiereAt")*1000)+"上映");
          nation_runtime.setText(detail.getString("nation")+"|"+detail.getString("runtime")+"分钟");
          film_synopsis.setText(detail.getString("synopsis"));
    }
}