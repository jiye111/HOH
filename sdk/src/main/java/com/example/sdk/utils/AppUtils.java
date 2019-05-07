package com.example.sdk.utils;

import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.sdk.global.GlobalApplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

//import android.util.Log;

/**
 *
 * <p>
 * App工具类
 */
public class AppUtils {

    /**
     * 获取上下文对象
     *
     * @return 上下文对象
     */
    public static Context getContext() {
        return GlobalApplication.getContext();
    }

    /**
     * 获取全局handler
     *
     * @return 全局handler
     */
    public static Handler getHandler() {
        return GlobalApplication.getHandler();
    }

    /**
     * 获取主线程id
     *
     * @return 主线程id
     */
    public static int getMainThreadId() {
        return GlobalApplication.getMainThreadId();
    }

    /**
     * 获取版本名称
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            LogUtils.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 获取版本号
     */
    public static int getAppVersionCode(Context context) {
        int versioncode = -1;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;
        } catch (Exception e) {
            LogUtils.e("VersionInfo", "Exception", e);
        }
        return versioncode;
    }

    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context
                .TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 显示软键盘
     */
    public static void openSoftInput(EditText et) {
        InputMethodManager inputMethodManager = (InputMethodManager) et.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(et, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(EditText et) {
        InputMethodManager inputMethodManager = (InputMethodManager) et.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager
                .HIDE_NOT_ALWAYS);
    }

    /**
     * 获取SD卡路径
     *
     * @return 如果sd卡不存在则返回null
     */
    public static File getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment
                .MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir;
    }

    /**
     * 安装文件
     *
     * @param data
     */
    public static void promptInstall(Context context, Uri data) {
        Intent promptInstall = new Intent(Intent.ACTION_VIEW)
                .setDataAndType(data, "application/vnd.android.package-archive");
        // FLAG_ACTIVITY_NEW_TASK 可以保证安装成功时可以正常打开 app
        promptInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(promptInstall);
    }

    public static void copy2clipboard(Context context, String text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context
                .CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("clip", text);
        cm.setPrimaryClip(clip);
    }

    /**
     * 判断是否运行在主线程
     *
     * @return true：当前线程运行在主线程
     * fasle：当前线程没有运行在主线程
     */
    public static boolean isRunOnUIThread() {
        // 获取当前线程id, 如果当前线程id和主线程id相同, 那么当前就是主线程
        int myTid = android.os.Process.myTid();
        if (myTid == getMainThreadId()) {
            return true;
        }
        return false;
    }

    /**
     * 运行在主线程
     *
     * @param r 运行的Runnable对象
     */
    public static void runOnUIThread(Runnable r) {
        if (isRunOnUIThread()) {
            // 已经是主线程, 直接运行
            r.run();
        } else {
            // 如果是子线程, 借助handler让其运行在主线程
            getHandler().post(r);
        }
    }

    /**
     * 检测手机是否root
     * @return
     */
    public static boolean isDeviceRooted() {
        if (checkDeviceDebuggable()){return true;}//check buildTags
        if (checkSuperuserApk()){return true;}//Superuser.apk
        //if (checkRootPathSU()){return true;}//find su in some path
        //if (checkRootWhichSU()){return true;}//find su use 'which'
        if (checkBusybox()){return true;}//find su use 'which'
        if (checkAccessRootData()){return true;}//find su use 'which'
//        if (checkGetRootAuth()){return true;}//exec su
        return false;
    }

    /**
     * 检查系统是否测试版本
     * @return
     */
    public static boolean checkDeviceDebuggable(){
        String buildTags = android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }
        return false;
    }

    /**
     * 检查是否存在Superuser.apk
     * @return
     */
    public static boolean checkSuperuserApk(){
        try {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                return true;
            }
        } catch (Exception e) { }
        return false;
    }

    /**
     * 执行busybox
     * @return
     */
    public static synchronized boolean checkBusybox()
    {
        try
        {
            String[] strCmd = new String[] {"busybox","df"};
            ArrayList<String> execResult = executeCommand(strCmd);
            if (execResult != null){
                return true;
            }else{
                return false;
            }
        } catch (Exception e)
        {
            return false;
        }
    }

    /**
     * 返回app运行状态
     *
     * @param context
     *            一个context
     * @param packageName
     *            要判断应用的包名
     * @return int 1:前台 2:后台 0:不存在
     */
    public static int isAppAlive(Context context, String packageName) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> listInfos = activityManager
                .getRunningTasks(20);
        // 判断程序是否在栈顶
        if (listInfos.get(0).topActivity.getPackageName().equals(packageName)) {
            return 1;
        } else {
            // 判断程序是否在栈里
            for (ActivityManager.RunningTaskInfo info : listInfos) {
                if (info.topActivity.getPackageName().equals(packageName)) {
                    return 2;
                }
            }
            return 0;// 栈里找不到，返回3
        }
    }

    public static ArrayList<String> executeCommand(String[] shellCmd){
        String line = null;
        ArrayList<String> fullResponse = new ArrayList<String>();
        Process localProcess = null;
        try {
            localProcess = Runtime.getRuntime().exec(shellCmd);
        } catch (Exception e) {
            return null;
        }
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(localProcess.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
        try {
            while ((line = in.readLine()) != null) {
                fullResponse.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fullResponse;
    }

    public static synchronized boolean checkAccessRootData()
    {
        try
        {
            String fileContent = "test_ok";
            Boolean writeFlag = writeFile("/data/su_test",fileContent);
            if (writeFlag){
            }else{
            }

            String strRead = readFile("/data/su_test");
            if(fileContent.equals(strRead)){
                return true;
            }else {
                return false;
            }
        } catch (Exception e)
        {
            return false;
        }
    }
    public static Boolean writeFile(String fileName, String message){
        try{
            FileOutputStream fout = new FileOutputStream(fileName);
            byte [] bytes = message.getBytes();
            fout.write(bytes);
            fout.close();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static String readFile(String fileName){
        File file = new File(fileName);
        try {
            FileInputStream fis= new FileInputStream(file);
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            while((len=fis.read(bytes))>0){
                bos.write(bytes, 0, len);
            }
            String result = new String(bos.toByteArray());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 执行su，看能否获取到root权限
     * @return
     */
    public static synchronized boolean checkGetRootAuth()
    {
        Process process = null;
        DataOutputStream os = null;
        try
        {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("exit\n");
            os.flush();
            int exitValue = process.waitFor();
            if (exitValue == 0)
            {
                return true;
            } else
            {
                return false;
            }
        } catch (Exception e)
        {
            return false;
        } finally
        {
            try
            {
                if (os != null)
                {
                    os.close();
                }
                process.destroy();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

}
