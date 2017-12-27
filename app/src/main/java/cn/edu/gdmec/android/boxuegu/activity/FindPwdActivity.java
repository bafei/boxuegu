package cn.edu.gdmec.android.boxuegu.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.utils.AnalysitUtils;
import cn.edu.gdmec.android.boxuegu.utils.MD5Utils;

public class FindPwdActivity extends AppCompatActivity {
    private String from;
    private TextView tv_main_title;
    private TextView tv_back;
    private EditText et_validate_name;
    private TextView tv_reset_pwd;
    private EditText et_user_name;
    private TextView tv_user_name;
    private Button btn_validate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);
        //获取从登录界面和设置界面传递的数据
        from = getIntent().getStringExtra("from");
        init();
    }

    private void init() {
        tv_main_title = ((TextView) findViewById(R.id.tv_main_title));
        btn_validate = (Button)findViewById(R.id.btn_validate);
        et_validate_name = (EditText)findViewById(R.id.et_validate_name);
        tv_reset_pwd = ((TextView) findViewById(R.id.tv_reset_pwd));
        et_user_name = ((EditText) findViewById(R.id.et_user_name));
        tv_user_name = ((TextView) findViewById(R.id.tv_user_name));
        tv_back = ((TextView) findViewById(R.id.tv_back));
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FindPwdActivity.this.finish();
            }
        });
        if("security".equals(from)){  //security安全
            tv_main_title.setText("设置密保");
        }else{
            tv_main_title.setText("找回密码");
            tv_user_name.setVisibility(View.VISIBLE);
            et_user_name.setVisibility(View.VISIBLE);
        }

        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String validateName = et_validate_name.getText().toString().trim();
                if("security".equals(from)){  //设置密保，只需要填入密保姓名,from是变量
                    if(TextUtils.isEmpty(validateName)){
                        Toast.makeText(FindPwdActivity.this, "请输入要验证的姓名", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        Toast.makeText(FindPwdActivity.this, "设置密保成功", Toast.LENGTH_SHORT).show();
                        saveSecurity(validateName);
                        FindPwdActivity.this.finish();
                    }
                }else{  //找回密码，需要输入用户名，密保名
                    String userName = et_user_name.getText().toString().trim();  //用户名
                    String sp_security = readSecurity(userName); //密保名
                    if(TextUtils.isEmpty(userName)){
                        Toast.makeText(FindPwdActivity.this, "请输入您的用户名", Toast.LENGTH_SHORT).show();
                        return;
                    }else if(isExitUserName(userName)){
                        Toast.makeText(FindPwdActivity.this, "您输入的用户不存在", Toast.LENGTH_SHORT).show();
                        return;
                    }else if(TextUtils.isEmpty(validateName)){
                        Toast.makeText(FindPwdActivity.this, "请输入密保名字", Toast.LENGTH_SHORT).show();
                        return;
                    }else if(!validateName.equals(sp_security)){
                        Toast.makeText(FindPwdActivity.this, "密保不正确", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        //用户输入密保正确，重新设置新密码
                        tv_reset_pwd.setVisibility(View.VISIBLE);
                        tv_reset_pwd.setText("初始密码:123456");
                        savePwd(userName);
                    }
                }
            }
        });
    }
    /*
    * 保存初始化密码
    * */
    private void savePwd(String userName) {
        String md5Pwd = MD5Utils.md5("123456");
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(userName,md5Pwd);
        editor.commit();
    }

    private boolean isExitUserName(String userName) {
        boolean hasUserName = false;
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        String spPwd = sp.getString(userName, "");
        if(TextUtils.isEmpty(spPwd)){
            hasUserName = true;
        }
        return hasUserName;
    }

    private String readSecurity(String userName) {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        String security = sp.getString(userName + "_security", "");
        return security;
    }

    private void saveSecurity(String validateName) {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(AnalysitUtils.readLoginUserName(this)+"_security",validateName);
        editor.commit();
    }
}
