package com.arunyadav.jphposts.util;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.arunyadav.jphposts.R;

public class ErrorDialogUtil {

    public static AlertDialog show(@NonNull Context context,
                                   @Nullable String title,
                                   String msg,
                                   @NonNull String positiveBtnText,
                                   DialogInterface.OnClickListener positiveBtnClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialogStyle)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton(positiveBtnText, positiveBtnClickListener);

        if (!TextUtils.isEmpty(msg)) {
            builder.setMessage(msg);
        }
        AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        return alert;
    }

    public static AlertDialog show(@NonNull Context context,
                                   @Nullable String title,
                                   String msg,
                                   @NonNull String positiveBtnText) {
        return show(context, title, msg, positiveBtnText, null);
    }
}
