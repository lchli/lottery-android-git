package com.lch.lottery.util;

import android.app.Service;
import android.content.ClipData;
import android.content.Context;
import android.text.TextUtils;

/**
 * Created by lichenghang on 2018/1/29.
 */

public class Utils {

    /**
     * 复制到剪切板
     * @param content
     */
    public static void copy(String content, Context context) {
        if (!TextUtils.isEmpty(content)) {
            if (android.os.Build.VERSION.SDK_INT > 11) {
                android.content.ClipboardManager newCm = (android.content.ClipboardManager) context.getSystemService(Service.CLIPBOARD_SERVICE);
                newCm.setPrimaryClip(ClipData.newPlainText("data", content));
            } else {
                android.text.ClipboardManager oldCm = (android.text.ClipboardManager)context.getSystemService(Service.CLIPBOARD_SERVICE);
                oldCm.setText(content);
            }
        }
    }
}
