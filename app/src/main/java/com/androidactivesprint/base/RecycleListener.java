package com.androidactivesprint.base;

import android.view.View;

/**
 * Created by Hung on 10/2/2017.
 */


public interface RecycleListener<T> {

    void onItemClick(View view, T item, int position, int clickType);
}
