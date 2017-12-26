package cn.edu.gdmec.android.boxuegu;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_back;
    private RelativeLayout rl_title_bar;
    private TextView tv_main_title;
    private FrameLayout mBodyLayout;
    private RelativeLayout mCourseBtn;
    private LinearLayout mBottomLayout;
    private RelativeLayout mExercisesBtn;
    private RelativeLayout mMyInfoBtn;
    private TextView tv_course;
    private TextView tv_exercises;
    private TextView tv_myInfo;
    private ImageView iv_course;
    private ImageView iv_exercises;
    private ImageView iv_myInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initBottomBar();
        setListener();
        setInitStatus();//设置默认进入主页的显示
    }

    private void setInitStatus() {
        clearBottomImageState();
        setSelectedStatus(0);
        createView(0);
    }

    private void setListener() {
        for (int i = 0; i < mBottomLayout.getChildCount(); i++) {
            mBottomLayout.getChildAt(i).setOnClickListener(this);
        }
    }

    private void initBottomBar() {
        mBottomLayout = (LinearLayout) findViewById(R.id.main_bottom_bar);
        /*底部导航栏的三个按钮，实为RelativeLayout组件*/
        mCourseBtn = (RelativeLayout) findViewById(R.id.bottom_bar_course_btn);
        mExercisesBtn = (RelativeLayout) findViewById(R.id.bottom_bar_exercise_btn);
        mMyInfoBtn = (RelativeLayout) findViewById(R.id.bottom_bar_myInfo_btn);
        /*三个选项的具体部件*/
        tv_course = (TextView) findViewById(R.id.bottom_bar_text_course);
        tv_exercises = (TextView) findViewById(R.id.bottom_bar_text_exercises);
        tv_myInfo = (TextView) findViewById(R.id.bottom_bar_text_myInfo);
        iv_course = ((ImageView) findViewById(R.id.bottom_bar_image_course));
        iv_exercises = ((ImageView) findViewById(R.id.bottom_bar_image_exercises));
        iv_myInfo = ((ImageView) findViewById(R.id.bottom_bar_image_myInfo));
    }

    /*获取界面UI控件*/
    private void init() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("博学谷课程");
        rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4ff"));
        tv_back.setVisibility(View.GONE);
        initBodyLayout();
    }

    private void initBodyLayout() {
        mBodyLayout = (FrameLayout) findViewById(R.id.main_body);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_bar_course_btn:
                clearBottomImageState();
                selectDisplayView(0);
                break;
            case R.id.bottom_bar_exercise_btn:
                clearBottomImageState();
                selectDisplayView(1);
                break;
            case R.id.bottom_bar_myInfo_btn:
                clearBottomImageState();
                selectDisplayView(2);
                break;
        }
    }

    /*
    * 显示对应的页面
    * */
    private void selectDisplayView(int index) {
        removeAllView();
        createView(index);
        setSelectedStatus(index);
    }
    /*
    * 设置底部按钮的选中状态
    * */
    private void setSelectedStatus(int index) {
        switch (index){
            case 0:
                mCourseBtn.setSelected(true);  //设置为选用
                iv_course.setImageResource(R.drawable.main_course_icon_selected );
                tv_course.setTextColor(Color.parseColor("#0097f7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("博学谷课程");
                break;
            case 1:
                mExercisesBtn.setSelected(true);
                iv_exercises.setImageResource(R.drawable.main_exercises_icon_selected );
                tv_exercises.setTextColor(Color.parseColor("#0097f7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("博学谷习题");
                break;
            case 2:
                mMyInfoBtn.setSelected(true);
                iv_myInfo.setImageResource(R.drawable.main_my_icon_selected );
                tv_myInfo.setTextColor(Color.parseColor("#0097f7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("个人资料");
                break;
        }
    }
    /*
    * 选择视图
    * */
    private void createView(int viewIndex) {
        switch (viewIndex){
            case 0:
                //TODO:课程界面
                break;
            case 1:
                //习题界面
                break;
            case 2:
                //我的界面
                break;
        }
    }
    /*
    * 移除不需要的视图
    * */
    private void removeAllView() {
        for(int i = 0;i<mBodyLayout.getChildCount();i++){
            mBodyLayout.getChildAt(i).setVisibility(View.GONE);
        }
    }

    private void clearBottomImageState() {
        tv_course.setTextColor(Color.parseColor("#666666"));
        tv_exercises.setTextColor(Color.parseColor("#666666"));
        tv_myInfo.setTextColor(Color.parseColor("#666666"));
        iv_course.setImageResource(R.drawable.main_course_icon);
        iv_exercises.setImageResource(R.drawable.main_exercises_icon);
        iv_myInfo.setImageResource(R.drawable.main_my_icon);
        for (int i = 0; i < mBottomLayout.getChildCount(); i++) {
            mBottomLayout.getChildAt(i).setSelected(false); //设置为未选中，有什么用捏，我不知道
        }
    }
    protected long exitTime; //上一次点击时间
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if(System.currentTimeMillis() - exitTime >2000){
                Toast.makeText(this, "再点击一次退出应用", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }else{
                MainActivity.this.finish();
                if(readLoginStatus()){
                    clearLoginStatus();
                }
                System.exit(0);
            }
            return true; //清除返回按钮事件
        }
        return super.onKeyDown(keyCode, event);
    }
    /*清除登录状态和登录名，自动填入登录名来源于登录页面的作用于注册的startActivityForResult方法，删除用户名暂时无实际作用，删除状态作用未知*/
    private void clearLoginStatus() {
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin",false);   //第二个参数表设置
        editor.putString("loginUserName","");
        editor.commit();
    }

    private boolean readLoginStatus() {
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("isLogin", false); //第二个参数表默认
        return isLogin;
    }
}
