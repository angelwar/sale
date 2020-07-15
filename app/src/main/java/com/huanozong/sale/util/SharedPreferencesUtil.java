package com.huanozong.sale.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * 基本数据类型的存储
 * Created by hudongwx on 16-12-29.
 */
public class SharedPreferencesUtil {
    protected static String FILE_NAME="huanzong_sale";
    private static String FILE_NAME_USER = "huanzong";

    //获取SharedPerences对象
    public static SharedPreferences getShared(Context context, String file_name){
        SharedPreferences sharedPreferences = context.getSharedPreferences(file_name, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    //保存登录用户的名字
    public  static void addUserName(Context context, String name){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putString("name",name).apply();
    }
    //查询登录用户的名字
    public static String searchUserName(Context context){
        SharedPreferences sharedPreferences= getShared(context,FILE_NAME_USER);
        return sharedPreferences.getString("name","无姓名");
    }

    //保存是否是管理员admin_id=7就是管理员
    public  static void setAdmin(Context context, boolean isAdmin){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putBoolean("isAdmin",isAdmin).apply();
    }
    //查询登录用户的名字
    public static boolean isAdmin(Context context){
        SharedPreferences sharedPreferences= getShared(context,FILE_NAME_USER);
        return sharedPreferences.getBoolean("isAdmin",false);
    }

    public static void addUserDoor(Context context, String userDoor){
        SharedPreferences shared = getShared(context, FILE_NAME_USER);
        SharedPreferences.Editor edit = shared.edit();
        edit.putString("userDoor", userDoor).apply();
    }

    public static String queryUserDoor(Context context){
        SharedPreferences sharedPreferences= getShared(context,FILE_NAME_USER);
        return sharedPreferences.getString("userDoor","");
    }

    /**
     * 保存用户是否认证
     * @param context
     * @param status
     */
    public static void addIsIdentify(Context context,int status){
        SharedPreferences shared = getShared(context, FILE_NAME_USER);
        shared.edit().putInt("iden",status).apply();
    }
    /**
     * 查询用户是否认证
     * @param context
     * @return
     */
    public static int queryIdentify(Context context){
        SharedPreferences shared = getShared(context, FILE_NAME_USER);
        return shared.getInt("iden",0);
    }


    //保存token
    public  static void addToken(Context context, String token){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putString("token",token).commit();
    }

    //保存蓝牙号
    public  static void addUserBluetooth(Context context, String bluetooth){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putString("bluetooth",bluetooth).apply();
    }
    //查询蓝牙号
    public  static String queryUserBluetooth(Context context){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        return sharedPreferences.getString("bluetooth","");
    }

    //保存蓝牙号
    public  static void addUserEditId(Context context, int id){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putInt("edit_id",id).apply();
    }
    //查询蓝牙号
    public  static int queryUserEditid(Context context){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        return sharedPreferences.getInt("edit_id",0);
    }

    //保存蓝牙mac地址
    public  static void addBluetoothMac(Context context, String mac){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putString("mac",mac).apply();
    }
    //查询蓝牙mac地址
    public  static String queryBluetoothMac(Context context){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        return sharedPreferences.getString("mac",null);
    }


    public  static void addAccount(Context context, String account){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putString("account",account).commit();
    }

    public  static void addPassword(Context context, String password){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putString("password",password).commit();
    }

    public  static void addIsYouke(Context context, boolean isYouke){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putBoolean("youke",isYouke).commit();
    }

    public static String queryAccount(Context context){
        SharedPreferences sharedPreferences= getShared(context,FILE_NAME_USER);
        return sharedPreferences.getString("account",null);
    }

    public static String queryPassword(Context context){
        SharedPreferences sharedPreferences= getShared(context,FILE_NAME_USER);
        return sharedPreferences.getString("password",null);
    }



    //查询融云token
    public static boolean queryIsYouke(Context context){
        SharedPreferences sharedPreferences= getShared(context,FILE_NAME_USER);
        return sharedPreferences.getBoolean("youke", true);
    }

    //保存融云token
    public  static void addRongYunToken(Context context, String token){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putString("ryToken",token).commit();
    }

    //查询融云token
    public static String queryRongYunToken(Context context){
        SharedPreferences sharedPreferences= getShared(context,FILE_NAME_USER);
        return sharedPreferences.getString("ryToken","");
    }

    //查询token
    public static String queryToken(Context context){
        SharedPreferences sharedPreferences= getShared(context,FILE_NAME_USER);
        return sharedPreferences.getString("token",null);
    }



    /**
     * 保存别名
     * @param context
     * @param
     */
    public static boolean addAlias(Context context, int i){
        SharedPreferences shared = getShared(context, FILE_NAME_USER);
        SharedPreferences.Editor edit = shared.edit();
        return edit.putInt("alias", i).commit();
    }

    public static int queryAlias(Context context){
        SharedPreferences sharedPreferences= getShared(context,FILE_NAME_USER);
        return sharedPreferences.getInt("alias",0);
    }
    /**
     * 保存登录用户的id
     * @param context
     * @param userID
     */
    public static boolean addUserID(Context context, Integer userID){
        SharedPreferences shared = getShared(context, FILE_NAME_USER);
        SharedPreferences.Editor edit = shared.edit();
        return edit.putInt("userID", userID).commit();
    }

    /**
     * 查询登录用户的应急组id
     * @param context
     * @return
     */
    public static int queryUserOrgID(Context context){
        SharedPreferences shared = getShared(context,FILE_NAME_USER);
        return shared.getInt("orgID", -1);
    }


    /**
     * 查询登录用户的id
     * @param context
     * @return
     */
    public static int queryUserID(Context context){
        SharedPreferences shared = getShared(context,FILE_NAME_USER);
        return shared.getInt("userID", -1);
    }
    /**
     * 清楚登录用户
     * @param context
     */
    public static void deleteUser(Context context){
        getShared(context,FILE_NAME_USER).edit().clear().apply();
    }

    //读取历史数据
    public static List<String> getHisData(Context context){
        SharedPreferences shared = getShared(context, FILE_NAME);
        int number = shared.getInt("number", 0);
        List<String> strings=new ArrayList<>();
        if(number!=0){
            for (int i = number-1; i >= 0; i--) {
                String string = shared.getString(FILE_NAME + i, null);
                strings.add(string);
            }
        }
        return strings;
    }
    //删除历史数据
    public static void deleHisData(Context context){
        SharedPreferences shared = getShared(context, FILE_NAME);
        shared.edit().clear().commit();
    }
    //添加历史数据
    public static void addHisData(Context context, String data){
        SharedPreferences shared = getShared(context, FILE_NAME);
        List<String> hisData = getHisData(context);
        boolean isExist=false;
        for (String str : hisData) {
            if(str.equals(data)){
                isExist=true;
            }
        }
        if(!isExist){
            SharedPreferences.Editor edit = shared.edit();
            int number = shared.getInt("number", 0);
            List<String> strings=new ArrayList<>();
            edit.putString(FILE_NAME+number,data);
            edit.putInt("number",number+1);
            edit.commit();
        }
    }


    //保存用户的小区id
    public  static void addDoorNameid(Context context, int cid){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putInt("door_cid",cid).apply();
    }
    //查询用户的小区id
    public static int searchDoorNameid(Context context){
        SharedPreferences sharedPreferences= getShared(context,FILE_NAME_USER);
        return sharedPreferences.getInt("door_cid",0);
    }

    //保存用户的小区名字
    public  static void addDoorName(Context context, String cid){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putString("door_cid_name",cid).apply();
    }
    //查询用户的小区名字
    public static String searchDoorName(Context context){
        SharedPreferences sharedPreferences= getShared(context,FILE_NAME_USER);
        return sharedPreferences.getString("door_cid_name",null);
    }


    //保存用户的单元名字
    public  static void addFloorName(Context context, int cid){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putInt("floor_cid",cid).apply();
    }
    //查询用户的单元名字
    public static String searchFloorName(Context context){
        SharedPreferences sharedPreferences= getShared(context,FILE_NAME_USER);
        return sharedPreferences.getString("floor_cid",null);
    }



    //保存用户的房间号
    public  static void addHouseName(Context context, String name){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putString("house_name",name).apply();
    }
    //查询用户的房间号
    public static String searchHouseName(Context context){
        SharedPreferences sharedPreferences= getShared(context,FILE_NAME_USER);
        return sharedPreferences.getString("house_name",null);
    }

    //保存用户的房间id hid
    public  static void addHouseid(Context context, int hid){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putInt("house_id",hid).apply();
    }
    //查询用户的房间id hid
    public static int searchHouseid(Context context){
        SharedPreferences sharedPreferences= getShared(context,FILE_NAME_USER);
        return sharedPreferences.getInt("house_id",0);
    }

    //保存用户的房间号
    public  static void addHouseFee(Context context, String name){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putString("house_fee",name).apply();
    }
    //查询用户的房间号
    public static String searchHouseFee(Context context){
        SharedPreferences sharedPreferences= getShared(context,FILE_NAME_USER);
        return sharedPreferences.getString("house_fee",null);
    }





//    //存储短信倒计时
//    public static void saveMsgTime(Context context,long times){
//        SharedPreferences shared = getShared(context,USER_FILE);
//        SharedPreferences.Editor edit = shared.edit();
//        edit.putLong("time",times);
//        edit.commit();
//    }
//    //查询短信倒计时
//    public static long queryMsgTime(Context context){
//        SharedPreferences shared = getShared(context,USER_FILE);
//        long time = shared.getLong("time", 0l);
//        return time;
//    }

    //保存用户房屋信息
}
