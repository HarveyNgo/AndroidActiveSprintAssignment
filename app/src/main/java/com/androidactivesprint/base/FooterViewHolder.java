package com.androidactivesprint.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Hung on 10/2/2017.
 */

public class FooterViewHolder<T> extends RecyclerView.ViewHolder {

    private T data;

    public FooterViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public View findViewById(int id) {
        return itemView.findViewById(id);
    }
}
