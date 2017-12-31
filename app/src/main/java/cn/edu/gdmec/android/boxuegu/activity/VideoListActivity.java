package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.adapter.VideoListAdapter;
import cn.edu.gdmec.android.boxuegu.bean.VideoBean;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;
import cn.edu.gdmec.android.boxuegu.utils.DBUtils;

public class VideoListActivity extends AppCompatActivity {
    private TextView tv_intro,tv_video,tv_chapter_intro;
    private ListView lv_video_list;
    private ScrollView sv_chapter_intro;
    private VideoListAdapter adapter;
    private List<VideoBean> videoList;
    private int chapterId;
    private String intro;
    private DBUtils db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //从课程界面传递过来的章节id
        chapterId = getIntent().getIntExtra("id",0);
        //从课程界面传递过来的章节简介
        intro = getIntent().getStringExtra("intro");
        //创建数据库工具类的对象
        db = DBUtils.getInstanse(VideoListActivity.this);
        initData();
        init();
    }
    /*
    * 初始化界面UI控件
    * */
    private void initData() {
        tv_intro = ((TextView) findViewById(R.id.tv_intro));
        tv_video = (TextView) findViewById(R.id.tv_video);
        lv_video_list = (ListView)findViewById(R.id.lv_video_list);
        tv_chapter_intro = (TextView)findViewById(R.id.tv_chapter_intro);
        sv_chapter_intro = (ScrollView)findViewById(R.id.sv_chapter_intro);
        adapter = new VideoListAdapter(this, new VideoListAdapter.OnSelectListener() {
            @Override
            public void onSelect(int position, ImageView iv) {
                adapter.setSelectedPosition(position); //设置配适器的选中项
                VideoBean bean = videoList.get(position);
                String videoPath = bean.videoPath;
                adapter.notifyDataSetChanged();//更新列表框
                if(TextUtils.isEmpty(videoPath)){
                    Toast.makeText(VideoListActivity.this, "本地没有此视频，暂无法播放", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    //判断是否登录，若登录则把此视频添加到数据库
                    if(readLoginStatus()){
                        String userName = AnalysisUtils.readLoginUserName(VideoListActivity.this);
                        db.saveVideoPlayList(videoList.get(position),userName);
                    }
                    //调整到视频播放界面
                    Intent intent = new Intent(VideoListActivity.this,VideoPlayActivity.class);
                    //TODO:
                }
            }
        });
    }

    private boolean readLoginStatus() {
        SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("isLogin",false);
        return isLogin;
    }

    private void init() {

    }
}
