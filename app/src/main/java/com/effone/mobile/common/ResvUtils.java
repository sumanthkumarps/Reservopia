package com.effone.mobile.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.effone.mobile.Activity.NoNetworkActivity;
import com.effone.mobile.MainActivity;
import com.effone.mobile.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sarith.vasu on 26-09-2017.
 */

public class ResvUtils {
    public static void createNetErrorDialog(final Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.internet_connection)
                .setTitle(R.string.titleMsg)
                .setCancelable(false)
                .setPositiveButton(R.string.settings,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                context.startActivity(i);
                            }
                        }
                )
                .setNegativeButton(R.string.button,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }


    public static void createErrorAlert(Context context, String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        if(!title.equals("")) {
            builder.setTitle(title);

        }
        builder.setMessage(msg)
                .setCancelable(false)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.show();
        alert.getWindow().getAttributes().gravity = Gravity.CENTER;

        int titleId =context.getResources().getIdentifier("alertTitle", "id", "android");
        TextView titleText = (TextView)alert.findViewById(titleId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            titleText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            titleText.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        }
        titleText.setGravity(Gravity.CENTER);
        alert.show();
    }
    public static void enableHomeButton(final Activity context){
        context.findViewById(R.id.iv_home_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
        ((ImageView)context.findViewById(R.id.iv_home_btn)).setImageDrawable(ContextCompat.getDrawable(context, R.drawable.home_btn));
    }
    public static void enableBackButton(final Activity context){
        context.findViewById(R.id.iv_back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });
    }
    public static ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext,R.style.AppCompatAlertDialogStyle);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //    dialog.setContentView(R.layout.common_progressbar);
        // dialog.setMessage(Message);
        return dialog;
    }

    public static void createOKAlert(Context context,String title,String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        if(!title.equals("")) {
            builder.setTitle(title);

        }
        builder.setMessage(msg)
                .setCancelable(false)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.show();
        alert.getWindow().getAttributes().gravity = Gravity.CENTER;
        TextView messageText = (TextView)alert.findViewById(android.R.id.message);
        int titleId =context.getResources().getIdentifier("alertTitle", "id", "android");
        TextView titleText = (TextView)alert.findViewById(titleId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            titleText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            titleText.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        }
        titleText.setGravity(Gravity.CENTER);
        messageText.setGravity(Gravity.CENTER);
        alert.show();
    }
    public static void createOKAlert(Context context,String title,String msg ,DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        if(!title.equals("")) {
            builder.setTitle(title);

        }
        builder.setMessage(msg)
                .setCancelable(false)
                .setNeutralButton("OK", listener);

        AlertDialog alert = builder.show();
        alert.getWindow().getAttributes().gravity = Gravity.CENTER;
        TextView messageText = (TextView)alert.findViewById(android.R.id.message);
        int titleId =context.getResources().getIdentifier("alertTitle", "id", "android");
        TextView titleText = (TextView)alert.findViewById(titleId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            titleText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            titleText.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        }
        titleText.setGravity(Gravity.CENTER);
        messageText.setGravity(Gravity.CENTER);
        alert.show();
    }
    public static void createYesOrNoDialog(Context context, String title, DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(title).setPositiveButton("Yes", listener)
                .setNegativeButton("No", listener).show();
    }
    public static String parseDateToddMMyyyy(String dateStr,String inputPattern, String outputPattern) {

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(dateStr);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public static final class Operations {
        private Operations() throws InstantiationException {
            throw new InstantiationException("This class is not for instantiation");
        }
        /**
         * Checks to see if the device is online before carrying out any operations.
         *
         * @return
         */
        public static boolean isOnline(Context context) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }
            return false;
        }
        public static void showNoNetworkActivity(Activity context){
            Intent intent = new Intent(context, NoNetworkActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ((Activity)context).startActivity(intent);
        }
    }

}
