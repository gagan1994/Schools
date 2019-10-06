package com.gagan.school.library.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.gagan.school.home.adapters.HomeViewItems;
import com.gagan.school.roles.ROLE;
import com.gagan.school.roles.SuperAdmin;

import java.util.List;
import static com.gagan.school.library.utils.NetworkUtil.NETWORK_STATUS_MOBILE;
import static com.gagan.school.library.utils.NetworkUtil.NETWORK_STATUS_NOT_CONNECTED;
import static com.gagan.school.library.utils.NetworkUtil.NETWORK_STAUS_WIFI;

/**
 * Created by Gagan S Patil on 26/7/19.
 */
public class Utils {
    public static ROLE isCheckRole(String email, String password){
        if(email.equals(SuperAdmin.SUPER_ADDMIN_EMAIL)&&password.equals(SuperAdmin.SUPER_ADMIN_PASSWORD)){
            return ROLE.SUPER_ADMIN;
        }
        return ROLE.DEFAULT;
    }


    public static boolean getConnectivityStatusBoolean(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        int status = 0;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = NETWORK_STAUS_WIFI;
            return true;
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = NETWORK_STATUS_MOBILE;
            return true;
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = NETWORK_STATUS_NOT_CONNECTED;
            return false;
        }
        return false;
    }

    public static void setSpanString(String string1, String string2, TextView textView) {
        String first = TextUtils.isEmpty(string1) ? "" : string1 ;
        String second = TextUtils.isEmpty(string2) ? "" : string2 ;
        SpannableStringBuilder builder=new SpannableStringBuilder();
        SpannableString txtSpannable= new SpannableString(first);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        txtSpannable.setSpan(boldSpan, 0, first.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(txtSpannable);
        builder.append(second);
        textView.setText(builder, TextView.BufferType.SPANNABLE);
    }

    public static boolean isInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    public static boolean isConnectedToNetwork(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager)
                    context.getSystemService(Activity.CONNECTIVITY_SERVICE);

            NetworkInfo info = manager.getActiveNetworkInfo();

            if (info != null && info.isConnected()) {
                return true;
            }
        }

        return false;
    }

    public static List<HomeViewItems> getHomeScreenItems() {
      return HomeViewItems.getItems();
    }

}
