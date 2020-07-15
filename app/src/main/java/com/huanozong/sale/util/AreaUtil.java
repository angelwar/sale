package com.huanozong.sale.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.huanozong.sale.R;
import com.huanozong.sale.bean.AreaBean;

import java.util.ArrayList;
import java.util.List;

public class AreaUtil {


    public static List<AreaBean> getList(Context context) {
        return parseJson(context);
    }

    private static List<AreaBean> parseJson(Context context) {
        //Json的解析类对象
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(context.getString(R.string.area_json)).getAsJsonArray();

        Gson gson = new Gson();
        List list = new ArrayList<>();

        //循环遍历json数组
        for (JsonElement json : jsonArray) {
            //使用GSON，转成Bean对象
            AreaBean bean = gson.fromJson(json, AreaBean.class);
            list.add(bean);
        }
        return list;
    }

    //返回一个String列表
    public static List<String> getListString(Context context) {
        List<AreaBean> listBean = getList(context);
        if (listBean==null||listBean.size()==0){
            return null;
        }
        List list = new ArrayList();
        for (int i = 0;i<listBean.size();i++){
            list.add(listBean.get(i).getName());
        }

        return list;
    }


    //返回一个String数组
    public static String[] getArrayString(Context context) {
        List<AreaBean> listBean = getList(context);
        if (listBean==null||listBean.size()==0){
            return null;
        }
        String[] strings = new String[listBean.size()];
        for (int i = 0;i<listBean.size();i++){
            strings[i] = listBean.get(i).getName();
        }

        return strings;
    }
}
