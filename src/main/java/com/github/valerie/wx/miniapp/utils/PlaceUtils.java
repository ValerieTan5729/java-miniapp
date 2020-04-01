package com.github.valerie.wx.miniapp.utils;

import java.util.Arrays;
import java.util.List;

/**
 * 打卡地点信息匹配的内容
 * */
public class PlaceUtils {
    public static String place1 = "place_1_duty_19830701";
    public static String place2 = "place_2_duty_19830710";
    public static String place3 = "place_3_duty_19840824";
    public static String place4 = "place_4_duty_19850928";
    public static String place5 = "place_5_duty_19860404";
    public static String place6 = "place_6_duty_19861015";
    public static String place7 = "place_7_duty_19870621";
    public static String place8 = "place_8_duty_19880203";

    private static List<String> place = Arrays.asList("place_1_duty_19830701", "place_2_duty_19830710", "place_3_duty_19840824",
                                                      "place_4_duty_19850928", "place_5_duty_19860404", "place_6_duty_19861015",
                                                      "place_7_duty_19870621", "place_8_duty_19880203");

    public static int findPlace(String data) {
        return place.indexOf(data);
    }
}
