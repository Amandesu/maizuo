package com.maizuo.fiveone.maizuo.customview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.support.v7.widget.AppCompatImageView;
import android.widget.LinearLayout;

import com.maizuo.fiveone.maizuo.R;

import java.text.AttributedCharacterIterator;

/**
 * Created by MyPC on 2019/7/30.
 */

public class RadioGoBack extends LinearLayout {
    public RadioGoBack(Context context){
        super(context);
    }
    public RadioGoBack(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view =  LayoutInflater.from(context).inflate(R.layout.common_gaback_radio, this);



        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) getContext()).finish();
            }
        });

        //LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


}
