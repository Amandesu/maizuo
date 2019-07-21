package com.maizuo.fiveone.maizuo.city;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.maizuo.fiveone.maizuo.R;

/**
 * Created by MyPC on 2019/7/20.
 */

public class CityActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("aa", "11");
        setContentView(R.layout.city_activity);
    }
}
