package cn.edu.gdme.android.boxuegu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdme.android.boxuegu.R;
import cn.edu.gdme.android.boxuegu.utils.MD5Utils;

import static cn.edu.gdme.android.boxuegu.R.id.btn_register;

public class RegisterActivity extends AppCompatActivity {

    private TextView tv_main_title;
    private TextView tv_back;
    private RelativeLayout rl_title_bar;
    private EditText et_user_name;
    private EditText et_pwd;
    private EditText et_pwd_again;
    private Button btn_register;
    private String userName;
    private String pwd;
    private String pwdAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册");
        tv_back = (TextView) findViewById(R.id.tv_back);
        rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.TRANSPARENT);

        //从activity_regist.xml获取对应的UI控件
        btn_register = (Button)findViewById(R.id.btn_register);
        et_user_name = (EditText) findViewById(R.id.tv_username);
        et_pwd = (EditText) findViewById(R.id.tv_pwd);
        et_pwd_again = (EditText) findViewById(R.id.tv_pwd_again);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取控件中的字符串
                getEditString();
                if (TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else  if (TextUtils.isEmpty(pwd)){
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else  if (TextUtils.isEmpty(pwdAgain)){
                    Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else  if (!et_pwd.equals(pwdAgain)){
                    Toast.makeText(RegisterActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }else  if (isExistUserameN(userName)){
                    Toast.makeText(RegisterActivity.this, "用户名已经存在", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                  //保存用户信息
                    saveRigistInfo(userName,pwd);
                    //注册成功后把用户名传给登录界面
                    Intent data = new Intent();
                    data.putExtra("userName",userName);
                    setResult(RESULT_OK,data);
                    RegisterActivity.this.finish();

                }
            }


        });


    }

    private void saveRigistInfo(String userName,String pwd) {
        String mdsPwd = MD5Utils.md5(pwd);//把密码用MD5加密
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_APPEND);//loginInfo是sp的文件名
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(userName,mdsPwd);
        editor.commit();//提交注册
    }

    //判断用户是否存在
    private boolean isExistUserameN(String userName) {

        boolean has_userName = false;
        SharedPreferences sp = getSharedPreferences("logininfo",MODE_PRIVATE);
        String spPwd = sp.getString(userName,"");
        if (!TextUtils.isEmpty(spPwd)){
            has_userName = true;
        }
        return has_userName;

    }
    //获取控件中的字符串
    private void getEditString() {
        userName = et_user_name.getText().toString().trim();
        pwd = et_pwd.getText().toString();
        pwdAgain = et_pwd_again.getText().toString().trim();



    }

    
}
