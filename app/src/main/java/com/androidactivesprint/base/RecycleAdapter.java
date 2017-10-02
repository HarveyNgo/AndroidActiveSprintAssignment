package com.androidactivesprint.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by Hung on 10/2/2017.
 */

public abstract class RecycleAdapter<T> extends RecyclerView.Adapter implements View.OnClickListener {

    public static final int HEADER_TYPE = 1;
    public static final int ITEM_TYPE = 2;
    public static final int FOOTER_TYPE = 3;
    protected final ArrayList<T> items;
    protected final RecycleListener<T> listener;
    private final LayoutInflater inflater;

    public RecycleAdapter(LayoutInflater inflater, ArrayList<T> items, RecycleListener<T> listener) {
        super();
        this.inflater = inflater;
        this.listener = listener;
        this.items = items;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder view = null;
        try {
            switch (viewType) {
                case HEADER_TYPE:
                    Constructor<?> header_constructor = getHeaderViewHolderClass().getConstructor(View.class);
                    view = (RecyclerView.ViewHolder) header_constructor.newInstance(inflater.inflate(getHeaderLayoutResource(), parent, false));
                    break;
                case FOOTER_TYPE:
                    Constructor<?> footer_constructor = getFooterViewHolderClass().getConstructor(View.class);
                    view = (RecyclerView.ViewHolder) footer_constructor.newInstance(inflater.inflate(getFooterLayoutResource(), parent, false));
                    break;
                case ITEM_TYPE:
                    Constructor<?> item_constructor = getItemViewHolderClass().getConstructor(View.class);
                    view = (RecyclerView.ViewHolder) item_constructor.newInstance(inflater.inflate(getItemLayoutResource(), parent, false));
                    break;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (view != null && view.itemView != null) {
            if (!view.isRecyclable())
                view.setIsRecyclable(true);
            view.itemView.setOnClickListener(this);
            view.itemView.setTag(view);
        }
        return view;
    }

    protected abstract Class<? extends ItemViewHolder> getItemViewHolderClass();

    @LayoutRes
    protected abstract int getItemLayoutResource();

    protected abstract void bindItemView(ItemViewHolder<T> holder, T data, int position);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            switch (holder.getItemViewType()) {
                case HEADER_TYPE:
                    HeaderViewHolder header = (HeaderViewHolder) holder;
                    bindHeaderView(header, items.get(position), position);
                    header.setData(items.get(position));
                    break;
                case ITEM_TYPE:
                    ItemViewHolder item = (ItemViewHolder) holder;
                    bindItemView(item, items.get(position), position);
                    item.setData(items.get(position));
                    break;
                case FOOTER_TYPE:
                    FooterViewHolder footer = (FooterViewHolder) holder;
                    bindFooterView(footer, items.get(position), position);
                    footer.setData(items.get(position));
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(View v) {
        Object holder = v.getTag();
        if (holder instanceof ItemViewHolder) {
            listener.onItemClick(v, (T) ((ItemViewHolder) holder).getData(), ((ItemViewHolder) holder).getAdapterPosition(), -1);
        }
    }

    @NonNull
    protected Class<?> getHeaderViewHolderClass() {
        // override if there is header
        return HeaderViewHolder.class;
    }

    @NonNull
    protected Class<?> getFooterViewHolderClass() {
        // override if there if footer
        return FooterViewHolder.class;
    }

    @LayoutRes
    protected int getHeaderLayoutResource() {
        // override if there is header
        return 0;
    }

    @LayoutRes
    protected int getFooterLayoutResource() {
        // override if there if footer
        return 0;
    }

    protected void bindFooterView(FooterViewHolder<T> holder, T data, int position) {
        // override if there is footer
    }

    protected void bindHeaderView(HeaderViewHolder<T> holder, T data, int position) {
        // override if there is header
    }
}