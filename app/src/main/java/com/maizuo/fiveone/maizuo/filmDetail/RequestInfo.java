package com.maizuo.fiveone.maizuo.filmDetail;

/**
 * Created by MyPC on 2019/7/29.
 */

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.maizuo.fiveone.maizuo.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * Created by MyPC on 2019/7/16.
 */

public class RequestInfo {
    private List<RequestInfo.OnCall> callList = new ArrayList();
    private String filmId = "";
    private Handler handler;
    private JSONObject requeseData;
    public RequestInfo(){
        handler = new Handler(){
            public void handleMessage(Message msg) {
                if (msg.what == 1 ) {
                    for (int i = 0; i < callList.size(); i++) {
                        RequestInfo.OnCall onCall = callList.get(i);
                        try {
                            onCall.OnCallBack(requeseData);
                        } catch (JSONException e) {

                        }

                    }
                }
            }
        };
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public void setRequeseData(JSONObject requeseData) {
        this.requeseData = requeseData;
    }

    public void setOnCall(RequestInfo.OnCall onCall){
        callList.add(onCall);
    }
    public interface OnCall {
        void OnCallBack(JSONObject obj) throws JSONException;
    }

    public void getFilmInfo(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .build();
                Request request = new Request.Builder()
                        .url("https://m.maizuo.com/gateway?filmId="+filmId+"&k=592010")
                        .addHeader("X-Host", "mall.film-ticket.film.info")
                        .build();
                try {
                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
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
