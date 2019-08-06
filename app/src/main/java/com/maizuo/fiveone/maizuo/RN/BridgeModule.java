package com.maizuo.fiveone.maizuo.RN;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.maizuo.fiveone.maizuo.filmDetail.FilmDetail;
import com.maizuo.fiveone.maizuo.main.MainActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BridgeModule extends ReactContextBaseJavaModule{

    public BridgeModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "BridgeModule";
    }
    @ReactMethod
    public void closeWindow(){
        getCurrentActivity().finish();
    }

    @ReactMethod
    public void getDataFromIntent(Callback callback){
        try{
            Activity currentActivity=getCurrentActivity();
            String result =currentActivity.getIntent().getStringExtra("data");
            if(TextUtils.isEmpty(result)){
                callback.invoke();
            }else{
                callback.invoke(result);
            }
        }catch (Exception e){
            callback.invoke("error");
        }
    }
    // 活动映射
    public static Map<String, Class> mapActivity = new HashMap();
    static {
        mapActivity.put("filmDetail", FilmDetail.class);
        mapActivity.put("main", MainActivity.class);
    }

    @ReactMethod
    public void openLink(String link) {
        //String link = "links://fimeDetail?filmId=12&a=12";
        Class activity = mapActivity.get("main");


        // 获取当前模块
        String patternActiviy = "links://([^?]+)";
        Matcher m1 = Pattern.compile(patternActiviy).matcher(link);
        if (m1.find()) {
            String name = m1.group(1);
            activity = mapActivity.get(name);
        }
        Intent intent = new Intent(getCurrentActivity(), activity);
        // 提取参数
        String patternParams = "([^=&?]+)=([^=&?]+)";
        Matcher m = Pattern.compile(patternParams).matcher(link);
        if (m.find()) {
            String key = m.group(1);
            String value = m.group(2);
            intent.putExtra(key, value);
        }
        getCurrentActivity().startActivity(intent);
    }



    @Override
    public Map<String, Object> getConstants() {
        Map<String,Object> constants = new HashMap<>();
        return constants;
    }

    @ReactMethod
    public void sendEvent(){
        onScanningResult();
    }
    @ReactMethod
    public void testAndroidCallbackMethod(String msg, Callback callback){
        Toast.makeText(getReactApplicationContext(),msg,Toast.LENGTH_LONG).show();
        callback.invoke("abc");
    }

    @ReactMethod
    public void textAndroidPromiseMethod(String msg, Promise promise){
        Toast.makeText(getReactApplicationContext(),msg,Toast.LENGTH_SHORT).show();
        String result="leiwuyi";
        promise.resolve(result);
    }

    public void onScanningResult(){
        WritableMap params = Arguments.createMap();
        params.putString("key", "myData");
        sendEvent(getReactApplicationContext(),"EventName",params);
    }

    public static void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    public void nativeCallRn(){
        onScanningResult();
    }

    @Override
    public boolean canOverrideExistingModule() {
        return true;
    }

}