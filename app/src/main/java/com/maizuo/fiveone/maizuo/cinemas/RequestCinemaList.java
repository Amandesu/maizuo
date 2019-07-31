package com.maizuo.fiveone.maizuo.cinemas;


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
import org.json.JSONStringer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by MyPC on 2019/7/16.
 */

public class RequestCinemaList {
    private List<RequestCinemaList.OnCall> callList = new ArrayList();
    private Handler handler;
    private JSONObject requeseData;
    private Map<String, String> formBody;
    private JSONObject jsonBody;

    public void setFormBody(Map formBody) {
        this.formBody = formBody;
    }

    public void setJsonBody(JSONObject jsonBody) {
        this.jsonBody = jsonBody;
    }

    public RequestCinemaList(){
        handler = new Handler(){
            public void handleMessage(Message msg) {
                if (msg.what == 1 ) {
                    for (int i = 0; i < callList.size(); i++) {
                        RequestCinemaList.OnCall onCall = callList.get(i);
                        try {
                            onCall.OnCallBack(requeseData);
                        } catch (JSONException e) {

                        }

                    }
                }
            }
        };
    }

    public void setRequeseData(JSONObject requeseData) {
        this.requeseData = requeseData;
    }

    public void setOnCall(RequestCinemaList.OnCall onCall){
        callList.add(onCall);
    }
    public interface OnCall {
        void OnCallBack(JSONObject obj) throws JSONException;
    }

    public void getCinemaList(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .build();
               // RequestBody formBody=builder.build();
                MediaType JSON = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(JSON, jsonBody.toString());
                Request request=new  Request.Builder()
                        .url("https://m.maizuo.com/gateway?k=699365")
                        .post(body)
                        .addHeader("X-Host", "mall.film-ticket.cinema.batch-cinema")
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
