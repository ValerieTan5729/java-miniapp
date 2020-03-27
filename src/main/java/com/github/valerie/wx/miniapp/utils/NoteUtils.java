package com.github.valerie.wx.miniapp.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用来处理数据库的note信息
 * */
public class NoteUtils {

    public static String note(String name, String type) {
        String res = name != null ? name : "System";
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String template = "于time进行type操作";
        res += template.replace("time", time).replace("type", type);
        return res;
    }
}
