package com.maizuo.fiveone.maizuo.main.Fragment.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maizuo.fiveone.maizuo.R;
import com.maizuo.fiveone.maizuo.Utils;
import com.maizuo.fiveone.maizuo.login.LoginActivity;
import com.maizuo.fiveone.maizuo.main.MainActivity;

/**
 * Created by MyPC on 2019/7/21.
 */

public class User extends Fragment{
    private View view;
    View login, ticket, mycard, mybalance, systemset;
    Boolean islogin = Utils.userInfo.getUsername() == null;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment_user, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        login = view.findViewById(R.id.login);
        ticket = view.findViewById(R.id.ticket);
        mycard = view.findViewById(R.id.mycard);
        mybalance = view.findViewById(R.id.mybalance);
        systemset = view.findViewById(R.id.systemset);

        initRender();
        initClickEvent();
    }
    public Boolean isLogin(){
        if (islogin) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
        return islogin;
    }
    public void initRender(){
        if (islogin == false){
            TextView username = (TextView) login.findViewById(R.id.username);
            ImageView image = (ImageView) login.findViewById(R.id.avator);
            image.setImageResource(R.drawable.main_fragment_user_avator);


            username.setText(Utils.userInfo.getUsername());
        }
    }
    public void initClickEvent(){
        // 登录
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLogin()){

                }
            }
        });
        // 卖座券
        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // 组合钱包
        mycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // 余额
        mybalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // 设置
        systemset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
