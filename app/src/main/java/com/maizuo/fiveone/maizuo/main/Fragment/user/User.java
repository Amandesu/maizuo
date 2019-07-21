package com.maizuo.fiveone.maizuo.main.Fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maizuo.fiveone.maizuo.R;

/**
 * Created by MyPC on 2019/7/21.
 */

public class User extends Fragment{
    private View view;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment_user, container, false);
        return view;
    }
}
