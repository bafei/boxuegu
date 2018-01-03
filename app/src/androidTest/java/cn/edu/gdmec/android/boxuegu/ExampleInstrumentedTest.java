package cn.edu.gdmec.android.boxuegu;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;

import static java.lang.Thread.sleep;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExampleInstrumentedTest {
    //包名
    private static final String BASIC_SAMPLE_PACKAGE
            = "cn.edu.gdmec.android.boxuegu";
    //超时时间
    private static final int LAUNCH_TIMEOUT = 5000;
    //设备实例
    private UiDevice mDevice;
    private String str,str1,str2;
    private UiObject result;
    private List<UiObject2> results;
    UiObject2 username,pwd1,pwd2,oldpwd;
    UiScrollable appList;
    @Before
    public void startMainActivityFromHomeScreen()  {
        // 初始化 UiDevice 实例
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // 按home键，返回到主界面
        mDevice.pressHome();

        // 等待应用装载运行
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // 启动应用
        Context context = InstrumentationRegistry.getContext();

        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        // 开始新的acivity，移除以前的所有实例
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // 等待应用启动
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
                LAUNCH_TIMEOUT);
    }

    @Test
    public void t01Register() throws Exception {
        sleep(3000);
        result = mDevice.findObject(new UiSelector().textStartsWith("我"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("点击登录"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("立即注册"));
        result.clickAndWaitForNewWindow();
        results = mDevice.findObjects(By.clazz(EditText.class));
        UiObject2 username = results.get(0);
        username.setText("a");
        UiObject2 pwd1 = results.get(1);
        pwd1.setText("a");
        UiObject2 pwd2 = results.get(2);
        pwd2.setText("a");
        result = mDevice.findObject(new UiSelector().textStartsWith("注 册"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("a"));
        if(!result.exists()){
            throw new Exception("Can't register user.");
        }
    }
    @Test
    public void t02LoginCheck() throws Exception {
        sleep(3000);
        result = mDevice.findObject(new UiSelector().textStartsWith("我"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("点击登录"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("登 录"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("登 录"));
        if(!result.exists()){
            throw new Exception("Can't login check.");
        }
    }
    @Test
    public void t03Login() throws Exception {
        sleep(3000);
        result = mDevice.findObject(new UiSelector().textStartsWith("我"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("点击登录"));
        result.clickAndWaitForNewWindow();
        results = mDevice.findObjects(By.clazz(EditText.class));
        username = results.get(0);
        username.setText("a");
        pwd1 = results.get(1);
        pwd1.setText("a");
        result = mDevice.findObject(new UiSelector().textStartsWith("登 录"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("我"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("a"));
        if(!result.exists()){
            throw new Exception("Can't login.");
        }
    }
    @Test
    public void t04ChangePassword() throws Exception {
        sleep(3000);
        result = mDevice.findObject(new UiSelector().textStartsWith("我"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("设置"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("修改密码"));
        result.clickAndWaitForNewWindow();
        results = mDevice.findObjects(By.clazz(EditText.class));
        oldpwd = results.get(0);
        oldpwd.setText("a");
        pwd1 = results.get(1);
        pwd1.setText("b");
        pwd2 = results.get(2);
        pwd2.setText("b");
        result = mDevice.findObject(new UiSelector().textStartsWith("保存"));
        result.clickAndWaitForNewWindow();
        results = mDevice.findObjects(By.clazz(EditText.class));
        username = results.get(0);
        username.setText("a");
        pwd1 = results.get(1);
        pwd1.setText("b");
        result = mDevice.findObject(new UiSelector().textStartsWith("登 录"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("a"));
        result.clickAndWaitForNewWindow();
        if(!result.exists()){
            throw new Exception("Can't change password.");
        }
    }
    @Test
    public void t05SetupPassSecurity() throws Exception {
        sleep(3000);
        result = mDevice.findObject(new UiSelector().textStartsWith("我"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("设置"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("设置密保"));
        result.clickAndWaitForNewWindow();
        results = mDevice.findObjects(By.clazz(EditText.class));
        oldpwd = results.get(0);
        oldpwd.setText("York");
        result = mDevice.findObject(new UiSelector().textStartsWith("验证"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("设置"));
        if(!result.exists()){
            throw new Exception("Can't set password security.");
        }
    }
    @Test
    public void t06Logout() throws Exception {
        sleep(3000);
        result = mDevice.findObject(new UiSelector().textStartsWith("我"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("设置"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("退出登录"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("点击登录"));
        if(!result.exists()){
            throw new Exception("Can't logout.");
        }
    }
    @Test
    public void t07ResetPassword() throws Exception {
        sleep(3000);
        result = mDevice.findObject(new UiSelector().textStartsWith("我"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("点击登录"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("找回密码"));
        result.clickAndWaitForNewWindow();
        results = mDevice.findObjects(By.clazz(EditText.class));
        oldpwd = results.get(0);
        oldpwd.setText("a");
        pwd1 = results.get(1);
        pwd1.setText("York");
        result = mDevice.findObject(new UiSelector().textStartsWith("验证"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textContains("123456"));
        if(!result.exists()){
            throw new Exception("Can't reset password.");
        }
    }
    @Test
    public void t08ChangeNickname() throws Exception {
        sleep(3000);
        result = mDevice.findObject(new UiSelector().textStartsWith("我"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("点击登录"));
        result.clickAndWaitForNewWindow();
        results = mDevice.findObjects(By.clazz(EditText.class));
        username = results.get(0);
        username.setText("a");
        pwd1 = results.get(1);
        pwd1.setText("123456");
        result = mDevice.findObject(new UiSelector().textStartsWith("登 录"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("我"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("a"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("昵"));
        result.clickAndWaitForNewWindow();
        results = mDevice.findObjects(By.clazz(EditText.class));
        username = results.get(0);
        username.setText("Yorkcui");
        result = mDevice.findObject(new UiSelector().textStartsWith("保存"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("Yorkcui"));
        if(!result.exists()){
            throw new Exception("Can't change nickname.");
        }
    }
    @Test
    public void t09ChangeGender() throws Exception {
        sleep(3000);
        result = mDevice.findObject(new UiSelector().textStartsWith("我"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("a"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("性"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("女"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("女"));
        if(!result.exists()){
            throw new Exception("Can't change gender.");
        }
    }
    @Test
    public void t10ChangeSignature() throws Exception {
        sleep(3000);
        result = mDevice.findObject(new UiSelector().textStartsWith("我"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("a"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("签"));
        result.clickAndWaitForNewWindow();
        results = mDevice.findObjects(By.clazz(EditText.class));
        username = results.get(0);
        username.setText("打击垫");
        result = mDevice.findObject(new UiSelector().textStartsWith("保存"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("打击垫"));
        if(!result.exists()){
            throw new Exception("Can't change signature.");
        }
    }
    @Test
    public void t11Course() throws Exception {
        sleep(3000);
        appList = new UiScrollable(new UiSelector().className("android.widget.ListView"));
        appList.flingToEnd(5);
        result = mDevice.findObject(new UiSelector().textStartsWith("第10章"));
        if(!result.exists()){
            throw new Exception("Can't find course.");
        }
    }
    @Test
    public void t12Videolist() throws Exception {
        sleep(3000);
        appList = new UiScrollable(new UiSelector().className("android.widget.ListView"));
        result = appList.getChild(new UiSelector().instance(1));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textContains("视"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textContains("笔记软件"));
        if(!result.exists()){
            throw new Exception("Can't enter video list.");
        }
    }
    @Test
    public void t13PlayVideo() throws Exception {
        sleep(3000);
        appList = new UiScrollable(new UiSelector().className("android.widget.ListView"));
        result = appList.getChild(new UiSelector().instance(1));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textContains("视"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textContains("Android系统简介"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().className("android.widget.VideoView"));
        result.click();
        result = mDevice.findObject(new UiSelector().textStartsWith("00"));
        if(!result.exists()){
            throw new Exception("Can't enter play list.");
        }
    }
    @Test
    public void t14PlayList() throws Exception {
        sleep(3000);
        result = mDevice.findObject(new UiSelector().textStartsWith("我"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("播放记录"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().textStartsWith("第1章"));
        result.clickAndWaitForNewWindow();
        result = mDevice.findObject(new UiSelector().className("android.widget.VideoView"));
        result.click();
        result = mDevice.findObject(new UiSelector().textStartsWith("00"));
        if(!result.exists()){
            throw new Exception("Can't exercise.");
        }
    }
    @Test
    public void t15Exercises() throws Exception {
        sleep(3000);
        result = mDevice.findObject(new UiSelector().textStartsWith("习题"));
        result.clickAndWaitForNewWindow();
        appList = new UiScrollable(new UiSelector().className("android.widget.ListView"));
        appList.flingToEnd(5);
        result = mDevice.findObject(new UiSelector().textStartsWith("第10章"));
        result.clickAndWaitForNewWindow();
        appList = new UiScrollable(new UiSelector().className("android.widget.ListView"));
        appList.flingToEnd(5);
        result = mDevice.findObject(new UiSelector().textStartsWith("5.下面"));
        if(!result.exists()){
            throw new Exception("Can't play video.");
        }
    }
}
