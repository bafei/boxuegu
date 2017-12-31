package cn.edu.gdmec.android.boxuegu.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by student on 17/12/27.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "bxg.db";
    public static final int DB_VERSION = 1;
    public static final String U_USERINFO = "userinfo";  //个人资料
    public static final String U_VIDEO_PLAY_LIST = "videoplaylist"; //视频播放列表

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
//创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        * 创建用户信息表*/
        db.execSQL("CREATE TABLE IF NOT EXISTS "+U_USERINFO+"( "
        +"_id INTEGER PRIMARY KEY AUTOINCREMENT,"
        +"userName VARCHAR,"
                        +"nickName VARCHAR,"
                        +"sex VARCHAR,"
                        +"signature VARCHAR"+" )"

        );
        //创建视频播放记录表
        db.execSQL("CREATE TABLE IF NOT EXISTS " + U_VIDEO_PLAY_LIST+"("
            +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            +"userName VARCHAR,"  //用户名
            +"chapterId INT"  //章节ID号
            +"videoId INT,"  //小节ID
            +"videoPath VARCHAR,"  //视频地址
            +"title VARCHAR,"  //视频的章节名称
            +"secondTitle VARCHAR,"  //视频名称
            +")"
        );
    }
    //数据库升级 数据库版本号增加 升级会调用此方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+U_USERINFO);
        db.execSQL("DROP TABLE IF EXISTS"+U_VIDEO_PLAY_LIST);
        onCreate(db);
    }
}
