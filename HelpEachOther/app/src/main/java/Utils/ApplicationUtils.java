package Utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.util.Log;


import java.util.List;

/**
 * Created by Zdy on 2016/4/14.
 */
public class ApplicationUtils {

    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        String versioncode = null;
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = String.valueOf(pi.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versioncode;
    }

    public ActivityManager.RunningTaskInfo getTopTask(Context context) {
        ActivityManager mActivityManager;
        mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = mActivityManager.getRunningTasks(1);
        if (tasks != null && !tasks.isEmpty()) {
            return tasks.get(0);
        }
        return null;
    }

    public static boolean isTopActivity(ActivityManager.RunningTaskInfo topTask, String packageName, String activityName) {
        if (topTask != null) {
            ComponentName topActivity = topTask.topActivity;
            if (topActivity.getPackageName().equals(packageName) &&
                    topActivity.getClassName().equals(activityName)) {
                return true;
            }
        }

        return false;
    }

    public static boolean shouldInit(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAppInstalled(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            //System.out.println("没有安装");
            return false;
        } else {
            //System.out.println("已经安装");
            return true;
        }
    }


    public static boolean isMyServiceRunning(Context context, String serviceName) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    public static Class getTopActivity(Context context) {
        Class result = null;
        Log.v("ApplicationUtils", context.getPackageName());
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        String MY_PKG_NAME = context.getPackageName();
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
                try {
                    result = Class.forName(info.topActivity.getClassName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Log.i("ApplicationUtils,true", " info.baseActivity.getPackageName()=" + info.baseActivity.getPackageName());
                Log.i("ApplicationUtils,true", " info.baseActivity.getClass()=" + info.baseActivity.getClass());
                break;
            }
            Log.i("ApplicationUtils", "info.topActivity==" + info.topActivity + "  info.baseActivity==" + info.baseActivity.getPackageName());
        }
        return result;
    }

    /**
     * 判断应用程序进程是否存在
     *
     * @param context
     * @return
     */
//    public static boolean isApplicationAlive(Context context) {
//        List<ActivityManager.RunningAppProcessInfo> processes = AndroidProcesses.getRunningAppProcessInfo(context);
//        for (int i = 0; i < processes.size(); i++) {
//            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = processes.get(i);
//            if (runningAppProcessInfo.processName.equals(context.getPackageName()))
//                return true;
//        }
//        return false;
//    }
//
//    /**
//     * 判断程序是否前台运行
//     *
//     * @param context
//     * @return
//     */
//    public static boolean isApplicationForeground(Context context) {
//        return AndroidProcesses.isMyProcessInTheForeground();
//    }
//

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }

        return null;
    }

    public static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }

    public static void restartApplication(Context context) {
        Log.v("重新启动", "重新启动");
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("flag", "restart");
        context.startActivity(intent);
    }


    public static String getIMEICode(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static boolean hasAudioRecordPermissions() {
//        if(AudioRecord.ERROR_INVALID_OPERATION != read){
//            // 做正常的录音处理
//        } else {
//            //录音可能被禁用了，做出适当的提示
//        }

        return false;
    }
}
