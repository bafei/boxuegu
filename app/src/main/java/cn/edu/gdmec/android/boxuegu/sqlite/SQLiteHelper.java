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
    public static final String U_USERINFO = "userinfo";


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

    }
//数据库升级 数据库版本号增加 升级会调用此方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISIS"+U_USERINFO);
        onCreate(db);

    }
}
