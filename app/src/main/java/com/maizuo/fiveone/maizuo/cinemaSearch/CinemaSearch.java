package com.maizuo.fiveone.maizuo.cinemaSearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.maizuo.fiveone.maizuo.R;

/**
 * Created by MyPC on 2019/8/1.
 */

public class CinemaSearch extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cinemasearch_activity);

        View parent = findViewById(R.id.parent);
        View child = findViewById(R.id.child);

        parent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.i("aa", "parent");
                return true;
            }
            /*public boolean dispatchTouchEvent(MotionEvent ev) {

                return true;
            }*/
        });


        child.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                System.out.println("super.onTouchEvent: " + 2+ " event: " + motionEvent.getAction());
                Log.i("aa", "child");
                return true;
            }

        });
    }
}
