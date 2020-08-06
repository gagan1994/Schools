package com.gagan.school.library.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.gagan.easyupi.model.PaymentApp;
import com.gagan.school.home.adapters.HomeViewItems;
import com.gagan.school.picassos.CircleTransform;
import com.gagan.school.roles.ROLE;
import com.gagan.school.roles.SuperAdmin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.gagan.school.library.utils.NetworkUtil.NETWORK_STATUS_MOBILE;
import static com.gagan.school.library.utils.NetworkUtil.NETWORK_STATUS_NOT_CONNECTED;
import static com.gagan.school.library.utils.NetworkUtil.NETWORK_STAUS_WIFI;

/**
 * Created by Gagan S Patil on 26/7/19.
 */
public class Utils {
    public static final String PAYMENT = "payment";
    public static final String TRANSACTION_ID = "TRANSACTION_ID";
    public static final String TRANSACTION_FAILURE_REASON = "TRANSACTION_FAILURE_REASON";
    public static final String PAYMENT_OBJECT = "PAYMENT_OBJECT";
    public static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static ROLE isCheckRole(String email, String password) {
        if (email.equals(SuperAdmin.SUPER_ADDMIN_EMAIL) && password.equals(SuperAdmin.SUPER_ADMIN_PASSWORD)) {
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
        String first = TextUtils.isEmpty(string1) ? "" : string1;
        String second = TextUtils.isEmpty(string2) ? "" : string2;
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString txtSpannable = new SpannableString(first);
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

    public static String currencyFormatter(String num) {
        return NumberFormat.getNumberInstance(Locale.getDefault()).format(Double.valueOf(num));
    }

    public static String getTimeFormat(String dateTime) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
            Date past = format.parse(dateTime);
            Date now = new Date();
            long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

            if (seconds < 60) {
                System.out.println(seconds + "s");
                return "just now";
            } else if (minutes < 60) {
                System.out.println(minutes + "m");
                return minutes + "m";
            } else if (hours < 24) {
                System.out.println(hours + "h");
                return hours + "h";
            } else if (days >= 7) {
                if (days > 360) {
                    System.out.println((days / 360) + "y");
                    return (days / 360) + "y";
                } else if (days > 30) {
                    System.out.println((days / 30) + "M");
                    return (days / 30) + "M";
                } else {
                    System.out.println((days / 7) + "w");
                    return (days / 7) + "w";
                }
            } else if (days < 7) {
                System.out.println(days + "d");
                return days + "d";
            }
        } catch (Exception j) {
            j.printStackTrace();
        }
        return "";
    }

    public static boolean isPackageInstalled(PaymentApp packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName.getPackageName(), 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void setProfileImage(Context activity, ImageView imageProfile) {
        for (UserInfo item : FirebaseAuth.getInstance().getCurrentUser().getProviderData()) {
            String avatarUrl = item.getPhotoUrl().toString().replace("s96-c", "s192-c");
            Picasso.with(activity)
                    .load(avatarUrl)
                    .transform(new CircleTransform())
                    .into(imageProfile);
        }
    }
}
