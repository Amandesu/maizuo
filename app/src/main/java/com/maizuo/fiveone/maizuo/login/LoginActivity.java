package com.maizuo.fiveone.maizuo.login;


import android.content.Intent;
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
import com.maizuo.fiveone.maizuo.Utils;
import com.maizuo.fiveone.maizuo.main.Fragment.cinema.Cinema;
import com.maizuo.fiveone.maizuo.main.Fragment.movie.ListAdaper;
import com.maizuo.fiveone.maizuo.main.Fragment.movie.Movie;
import com.maizuo.fiveone.maizuo.main.Fragment.movie.RequestMovie;
import com.maizuo.fiveone.maizuo.main.Fragment.user.User;
import com.maizuo.fiveone.maizuo.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private ViewGroup footerTab;
    private Fragment movie = new Movie();
    private Fragment cinema = new Cinema();
    private Fragment user = new User();
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        findViewById(R.id.sumit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.userInfo.setUsername("leiwuyi456");
                Utils.userInfo.setPassword("123456");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
