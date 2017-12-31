package cn.edu.gdmec.android.boxuegu.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.boxuegu.bean.UserBean;
import cn.edu.gdmec.android.boxuegu.bean.VideoBean;
import cn.edu.gdmec.android.boxuegu.sqlite.SQLiteHelper;


/**
 * Created by student on 17/12/27.
 */

public class DBUtils {

    private static SQLiteHelper helper;
    private static SQLiteDatabase db;
    private static DBUtils instance = null;


    public DBUtils(Context context) {
        helper = new SQLiteHelper(context);
        db = helper.getWritableDatabase();

    }

    public static DBUtils getInstanse(Context context) {
        if (instance == null) {
            instance = new DBUtils(context);
        }
        return instance;
    }

    //保存个人信息
    public void saveUserInfo(UserBean userbean) {
        ContentValues cv = new ContentValues();
        cv.put("userName", userbean.userName);
        cv.put("nickName", userbean.nickName);
        cv.put("sex", userbean.sex);
        cv.put("signature", userbean.signature);
        db.insert(SQLiteHelper.U_USERINFO, null, cv);
    }

    //查询个人资料
    public UserBean getUserInfo(String userName) {
        String sql = "SELECT * FROM " + SQLiteHelper.U_USERINFO + " WHERE userName =?";
        Cursor cursor = db.rawQuery(sql, new String[]{userName});
        UserBean userbean = null;
        while (cursor.moveToNext()) {
            userbean = new UserBean();
            userbean.userName = cursor.getString(cursor.getColumnIndex("userName"));
            userbean.nickName = cursor.getString(cursor.getColumnIndex("nickName"));
            userbean.sex = cursor.getString(cursor.getColumnIndex("sex"));
            userbean.signature = cursor.getString(cursor.getColumnIndex("signature"));
        }
        cursor.close();
        return userbean;

    }

    /*
    * 修改个人资料
    * */
    public void updateUserInfo(String key, String value, String userName) {
        ContentValues cv = new ContentValues();
        cv.put(key, value);
        db.update(SQLiteHelper.U_USERINFO, cv, "userName=?", new String[]{userName});
    }

    /*
    * 保存播放记录
    * */
    public void saveVideoPlayList(VideoBean bean, String userName) {
        //判断如果里面有此播放记录则需要删除重新存放
        if (hasVideoPlay(bean.chapterId, bean.videoId, userName)) {
            //删除之前存入的播放记录
            boolean isDelete = delVideoPlay(bean.chapterId,bean.videoId,userName);
            if(!isDelete){
                //没有删除成功时，则需跳出此方法不再执行下面的语句
                return;
            }
        }
        ContentValues cv = new ContentValues();
        cv.put("userName",userName);
        cv.put("chapterId",bean.chapterId);
        cv.put("videoId",bean.videoId);
        cv.put("videoPath",bean.videoPath);
        cv.put("title",bean.title);
        cv.put("secondTitle",bean.secondTitle);
        db.insert(SQLiteHelper.U_VIDEO_PLAY_LIST,null,cv);
    }
    /*
    * 删除已经存在的视频记录
    **/
    public boolean delVideoPlay(int chapterId, int videoId, String userName) {
        boolean delSuccess = false;
        int row = db.delete(SQLiteHelper.U_VIDEO_PLAY_LIST,
                " chapterId=? AND videoId=? AND userName=?",new String[]{ chapterId+"",
                    videoId+"",userName});
        if(row>0){
            delSuccess = true;
        }
        return delSuccess;
    }

    /*
    * 判断视频记录是否存在
    * */
    private boolean hasVideoPlay(int chapterId, int videoId, String userName) {
        boolean hasVideo = false;
        String sql = "SELECT * FROM " + SQLiteHelper.U_VIDEO_PLAY_LIST
                + "WHERE chapterId=? AND videoId=? AND userName=?";
        Cursor cursor = db.rawQuery(sql, new String[]{chapterId + "",
                videoId + "", userName});
        if(cursor.moveToFirst()){
            hasVideo = true;
        }
        cursor.close();
        return hasVideo;
    }
    /*
    * 获取视频记录信息
    * */
    public List<VideoBean> getVideoHistory(String userName){
        String sql = "SELECT * FROM " + SQLiteHelper.U_VIDEO_PLAY_LIST+"WHERE userName+?";
        Cursor cursor = db.rawQuery(sql,new String[]{userName});
        List<VideoBean> vbl = new ArrayList<>();
        VideoBean bean = null;
        while(cursor.moveToNext()){
            bean = new VideoBean();
            bean.chapterId = cursor.getInt(cursor.getColumnIndex("chapterId"));
            bean.videoId = cursor.getInt(cursor.getColumnIndex("videoId"));
            bean.videoPath = cursor.getString(cursor.getColumnIndex("videoPath"));
            bean.title = cursor.getString(cursor.getColumnIndex("title"));
            bean.secondTitle = cursor.getString(cursor.getColumnIndex("secondTitle"));
            vbl.add(bean);
            bean = null;
        }
        cursor.close();
        return vbl;
    }
}





















