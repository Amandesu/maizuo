package com.maizuo.fiveone.maizuo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MyPC on 2019/7/20.
 */

public class Utils {
    static public City city = new City();
    static public class City {
        private int cityId;
        private String cityName;
        public int getCityId() {
            return cityId;
        }
        public void setCityId(int cityId) {
            this.cityId = cityId;
        }
        public String getCityName() {
            return cityName;
        }
        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
    }
    static {
        city.setCityId(440300);
        city.setCityName("深圳");
    }
    public static City getCityInfo(){
        return city;
    }
}
