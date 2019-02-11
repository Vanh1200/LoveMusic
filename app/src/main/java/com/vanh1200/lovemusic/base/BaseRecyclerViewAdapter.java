package com.vanh1200.lovemusic.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    private List<T> mData;
    private ItemListener<T> mItemListener;

    public BaseRecyclerViewAdapter() {
        mData = new ArrayList<>();
    }

    public void setItemListener(ItemListener<T> listener) {
        if (listener != null)
            mItemListener = listener;
    }

    public void setData(List<T> data) {
        if (data != null) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public List<T> getData() {
        return mData;
    }

    public void addItem(T t) {
        mData.add(t);
        notifyItemInserted(mData.size() - 1);
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public interface ItemListener<T> {
        void onItemClicked(T t, int position);
    }

}
