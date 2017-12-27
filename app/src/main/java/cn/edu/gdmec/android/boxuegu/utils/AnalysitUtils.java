package cn.edu.gdmec.android.boxuegu.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by student on 17/12/27.
 */

public class AnalysitUtils {
    /*
    * 从sharedPreference中读取用户名
    * */
    public static String readLoginUserName(Context context){
        SharedPreferences sp = context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String userName = sp.getString("loginUserName","");//获取登录时候的用户名
        return userName;
    }
}
