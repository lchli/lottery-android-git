package com.lch.lottery.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;

/**
 * Created by lichenghang on 2018/1/29.
 */

public final class DialogUtil {

    public static Dialog showLoadingDialog(Activity activity, String msg) {
        Dialog dialog = createDialog(activity, msg);
        dialog.show();

        return dialog;
    }

    public static Dialog showLoadingDialog(Activity activity) {
        Dialog dialog = createDialog(activity);
        dialog.show();

        return dialog;
    }

    public static Dialog createDialog(Activity activity, String msg) {
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setIndeterminate(true);
        dialog.setTitle(msg);
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    public static Dialog createDialog(Activity activity) {
        return createDialog(activity, "加载中...");
    }
}
