package com.vanh1200.lovemusic.screen.search.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.base.BaseRecyclerViewAdapter;
import com.vanh1200.lovemusic.data.model.History;

import java.util.List;

public class HistoryAdapter extends BaseRecyclerViewAdapter<History, HistoryAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history, viewGroup, false);
        return new ViewHolder(view, mData, mItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mData.get(i));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView mTextHistory;
        private List<History> mHistories;
        private ItemListener<History> mListener;

        public ViewHolder(@NonNull View itemView, List<History> histories, ItemListener<History> listener) {
            super(itemView);
            mHistories = histories;
            mListener = listener;
            initViews(itemView);
            registerEvents(itemView);
        }

        private void registerEvents(View itemView) {
            itemView.setOnLongClickListener(this);
        }

        private void initViews(View itemView) {
            mTextHistory = itemView.findViewById(R.id.text_history);
        }

        private void bindData(History history) {
            mTextHistory.setText(history.getContent());
        }

        @Override
        public boolean onLongClick(View v) {
            mListener.onItemLongClicked(mHistories.get(getAdapterPosition()), getAdapterPosition());
            return true;
        }
    }
}
