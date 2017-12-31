package cn.edu.gdmec.android.boxuegu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.bean.UserBean;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;
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
    private String spUserName ;


    private  static final  int CHANGE_NICKNAME = 1;
    private  static final  int CHANGE_SIGNATURE = 2;
    private String new_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        //上一章的
        spUserName = AnalysisUtils.readLoginUserName(this);
        init();
        initDate();
        setListener();
    }

    public void enterACtivityForResurlt(Class<?> to,int requestCode , Bundle bundle){
        Intent i = new Intent(this,to);
        i.putExtras(bundle);
        startActivityForResult(i,requestCode);
    }

    private void setListener() {
        tv_back.setOnClickListener(this);
        rl_nickName.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_signature.setOnClickListener(this);

    }

    private void initDate() {
        UserBean bean = null;
        bean = DBUtils.getInstanse(this).getUserInfo(spUserName);
        if(bean == null){
            bean =  new UserBean();
            bean.userName = spUserName;
            bean.nickName = "问答精灵";
            bean.sex = "男";
            bean.signature = "问答精灵";
            //保存
            DBUtils.getInstanse(this).saveUserInfo(bean);

        }
        setValue(bean);

    }

   private void setValue(UserBean bean) {
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
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));

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
            case  R.id.rl_nickName://昵称的点击事件

                String name = tv_nivkName.getText().toString();
                Bundle bdName = new Bundle();
                bdName.putString("content",name);
                bdName.putString("title","昵称");
                bdName.putInt("flag",1); //表示1是修改昵称
                enterACtivityForResurlt(ChangeUserInfoActivity.class,CHANGE_NICKNAME,bdName);
                break;
            case  R.id.rl_sex:
                String sex = tv_sex.getText().toString();
                sexDialog(sex);
                break;
            case  R.id.rl_signature:   //签名的点击事件

                String signature = tv_signature.getText().toString();
                Bundle bdsignature = new Bundle();
                bdsignature.putString("content",signature);
                bdsignature.putString("title","签名");
                bdsignature.putInt("flag",2); //表示2是修改签名
                enterACtivityForResurlt(ChangeUserInfoActivity.class,CHANGE_SIGNATURE,bdsignature);
                break;
            default:
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CHANGE_NICKNAME:
                if (data != null) {
                    new_info = data.getStringExtra("nickName");
                    if (TextUtils.isEmpty(new_info)) {
                        return;
                    }
                    tv_nivkName.setText(new_info);
                    //更新数据库中昵称字段
                    DBUtils.getInstanse(UserInfoActivity.this).updateUserInfo("nickName", new_info, spUserName);

                }
                break;
            case CHANGE_SIGNATURE:
                if (data != null) {
                    new_info = data.getStringExtra("signature");
                    if (TextUtils.isEmpty(new_info)) {
                        return;
                    }
                    tv_signature.setText(new_info);
                    //更新数据库中签名字段
                    DBUtils.getInstanse(UserInfoActivity.this).updateUserInfo("signature", new_info, spUserName);

                }
                break;
            default:
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
