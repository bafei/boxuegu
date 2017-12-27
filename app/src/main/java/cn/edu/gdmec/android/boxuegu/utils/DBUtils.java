package cn.edu.gdmec.android.boxuegu.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.edu.gdmec.android.boxuegu.bean.Userbean;
import cn.edu.gdmec.android.boxuegu.sqlite.SQLiteHelper;


/**
 * Created by student on 17/12/27.
 */

public class DBUtils {

    private static SQLiteHelper helper;
    private static SQLiteDatabase db;
    private static DBUtils instanse = null;


    public DBUtils(Context context) {
        helper = new SQLiteHelper(context);
        db = helper.getWritableDatabase();

    }
    public static DBUtils getInstanse(Context context){
        if(instanse == null){
            instanse = new DBUtils(context);
        }
        return instanse;
    }
    //保存个人信息
    public void saveUserInfo(Userbean userbean){
        ContentValues cv = new ContentValues();
        cv.put("userName",userbean.userName);
        cv.put("nickName",userbean.nickName);
        cv.put("sex",userbean.sex);
        cv.put("signature",userbean.signature);
        db.insert(SQLiteHelper.U_USERINFO,null,cv);
    }
    //查询个人资料
    public  Userbean getUserInfo(String userName){
        String sql = "SELECT * FROM "+SQLiteHelper.U_USERINFO+" WHERE userName =?";
        Cursor cursor = db.rawQuery(sql, new String[]{userName});
        Userbean userbean = null;
        while (cursor.moveToNext()){
            userbean = new Userbean();
            userbean.userName = cursor.getString(cursor.getColumnIndex("userName"));
            userbean.nickName = cursor.getString(cursor.getColumnIndex("nickName"));
            userbean.sex = cursor.getString(cursor.getColumnIndex("sex"));
            userbean.signature = cursor.getString(cursor.getColumnIndex("signature"));
        }
        cursor.close();
        return userbean;

    }
    public  void updateUserInfo(String key,String value,String userName){
        ContentValues cv = new ContentValues();
        cv.put(key,value);
        db.update(SQLiteHelper.U_USERINFO,cv,"userName=?",new String[]{userName});

    }
}





















