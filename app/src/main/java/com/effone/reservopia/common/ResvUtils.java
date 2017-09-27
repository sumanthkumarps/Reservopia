package com.effone.reservopia.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by sarith.vasu on 26-09-2017.
 */

public class ResvUtils {
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
}
