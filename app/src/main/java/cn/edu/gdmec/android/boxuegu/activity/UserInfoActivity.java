package cn.edu.gdmec.android.boxuegu.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.bean.Userbean;
import cn.edu.gdmec.android.boxuegu.utils.DBUtils;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout rl_title_bar;
    private RelativeLayout rl_userName;
    private TextView tv_user_name;
    private RelativeLayout rl_nickName;
    private TextView tv_nivkName;
    private RelativeLayout rl_sex;
    private TextView tv_sex;
    private RelativeLayout rl_signature;
    private TextView tv_signature;
    private String spUserName = "bafei"; //暂时测试赋值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        //上一章的 spUserName = A
        init();
        initDate();
        setListener();
    }

    private void setListener() {
        tv_back.setOnClickListener(this);
        rl_nickName.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_signature.setOnClickListener(this);

    }

    private void initDate() {
        Userbean bean = null;
        bean = DBUtils.getInstanse(this).getUserInfo(spUserName);
        if(bean == null){
            bean =  new Userbean();
            bean.userName = spUserName;
            bean.nickName = "问答精灵";
            bean.sex = "男";
            bean.signature = "问答精灵";
            //保存
            DBUtils.getInstanse(this).saveUserInfo(bean);

        }
        setValue(bean);

    }

    private void setValue(Userbean bean) {
        tv_nivkName.setText(bean.nickName);
        tv_user_name.setText(bean.userName);
        tv_sex.setText(bean.sex);
        tv_signature.setText(bean.signature);
    }


    private void init() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("个人资料");
        rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF0"));

        rl_userName = (RelativeLayout) findViewById(R.id.rl_account);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);

        rl_nickName = (RelativeLayout) findViewById(R.id.rl_nickName);
        tv_nivkName = (TextView) findViewById(R.id.tv_nick_name);

        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        tv_sex = (TextView) findViewById(R.id.tv_sex);

        rl_signature = (RelativeLayout) findViewById(R.id.rl_signature);
        tv_signature = (TextView) findViewById(R.id.tv_signature);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tv_back:
                this.finish();
                break;
            case  R.id.rl_nickName: //昵称的点击事件
                break;
            case  R.id.rl_sex:
                String sex = tv_sex.getText().toString();
                sexDialog(sex);
                break;
            case  R.id.rl_signature:   //签名的点击事件
                break;

        }

    }
//设置性别的弹出框
    private void sexDialog(String sex) {
        int sexFlag = 0;
        if("男".equals(sex)){
            sexFlag =0;
        }else if ("女".equals(sex)){
            sexFlag = 1;
        }

         final String items[] = {"男" ,"女"};
        AlertDialog.Builder  builder =  new AlertDialog.Builder(this);
        builder.setTitle("性别");
        builder.setSingleChoiceItems(items, sexFlag, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Toast.makeText(UserInfoActivity.this,items[i],Toast.LENGTH_SHORT).show();
                setSex(items[i]);
            }


        });
        builder.show();


    }
    //更新界面上的性别数据

    private void setSex(String sex) {
        tv_sex.setText(sex);
        //更新数据库中字段
        DBUtils.getInstanse(this).updateUserInfo("sex",sex,spUserName);

    }
}
