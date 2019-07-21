package com.maizuo.fiveone.maizuo.main.Fragment.movie;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.maizuo.fiveone.maizuo.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by MyPC on 2019/7/16.
 */

public class RequestMovie {
    private List<OnCall> callList = new ArrayList();
    private int pageNum = 1;
    private int pageSize = 10;
    private int type = 1;
    private Handler handler;
    private JSONObject requeseData;
    public RequestMovie(){
        handler = new Handler(){
            public void handleMessage(Message msg) {
            if (msg.what == 1 ) {
                for (int i = 0; i < callList.size(); i++) {
                    OnCall onCall = callList.get(i);
                    try {
                        onCall.OnCallBack(requeseData);
                    } catch (JSONException e) {

                    }

                }
            }
            }
        };
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setRequeseData(JSONObject requeseData) {
        this.requeseData = requeseData;
    }

    public void setOnCall(OnCall onCall){
        callList.add(onCall);
    }
    public interface OnCall {
        void OnCallBack(JSONObject obj) throws JSONException;
    }

    public void getMovieingList(){
        final int curType = getType();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .build();
                Request request = new Request.Builder()
                        .url("https://m.maizuo.com/gateway?cityId="+ Utils.getCityInfo().getCityId()+"&pageNum="+pageNum+"&pageSize="+pageSize+"&type="+type+"&k=7927547")
                        .addHeader("X-Host", "mall.film-ticket.film.list")
                        .build();
                try {
                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (curType != getType()) {
                                return;
                            }
                            String responseData=response.body().string();
                            try {
                                setRequeseData(new JSONObject(responseData));
                                Message msg = new Message();
                                msg.what = 1;
                                handler.sendMessage(msg);

                            } catch (JSONException e) {
                                Log.i("fail", e.toString());
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("requeset", "fail");
                        }

                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}
