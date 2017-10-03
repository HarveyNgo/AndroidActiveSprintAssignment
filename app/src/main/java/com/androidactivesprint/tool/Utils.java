package com.androidactivesprint.tool;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.androidactivesprint.MyApplication;

/**
 * Created by Hung on 10/3/2017.
 */

public class Utils {

    public static int getColor(int colorId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return ContextCompat.getColor(MyApplication.getContext(), colorId);
            } else {
                return MyApplication.getContext().getResources().getColor(colorId);
            }

        } catch (Exception e) {
            return 0;
        }

    }

    public static Drawable getDrawable(int resourceID) {
        return ContextCompat.getDrawable(MyApplication.getActiveActivity(), resourceID);
    }
}
