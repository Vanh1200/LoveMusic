package com.vanh1200.lovemusic.screen.playinglist.adapter;

public interface ItemTouchListener {
    void onMove(int oldPosition, int newPosition);

    void swipe(int position, int direction);
}
