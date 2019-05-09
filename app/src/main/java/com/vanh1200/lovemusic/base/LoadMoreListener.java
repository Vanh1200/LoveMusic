package com.vanh1200.lovemusic.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

public abstract class LoadMoreListener extends RecyclerView.OnScrollListener {
    private boolean isScrolling = false;

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
            isScrolling = true;
        }
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if(isScrolling && linearLayoutManager.findLastVisibleItemPosition() == recyclerView.getAdapter().getItemCount() - 1){
            isScrolling = false;
            loadMore();
        }
    }

    public abstract void loadMore();

}
