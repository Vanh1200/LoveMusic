package com.vanh1200.lovemusic.screen.timer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.utils.Constants;
import com.vanh1200.lovemusic.utils.StringUtils;

public class TimerDialogFragment extends DialogFragment implements View.OnClickListener {
    private static final int REQUEST_FRAGMENT_TIMER = 1;
    private Switch mSwitchTimer;
    private EditText mEditTime;
    private Button mButtonCancel;
    private Button mButtonDone;
    private int mMinute;

    public static TimerDialogFragment newInstance(int timer) {
        Bundle args = new Bundle();
        args.putInt(Constants.KEY_TIMER, timer);
        TimerDialogFragment fragment = new TimerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_timer, container, false);
        initViews(view);
        registerEvents();
        getArgumentsIncoming();
        return view;
    }

    private void getArgumentsIncoming() {
        Bundle bundle = getArguments();
        if (bundle == null) return;
        mMinute = bundle.getInt(Constants.KEY_TIMER);
        if (mMinute != 0) {
            mSwitchTimer.setChecked(true);
            mEditTime.setText(String.valueOf(mMinute));
        }
    }

    private void registerEvents() {
        mButtonDone.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);
    }

    private void initViews(View view) {
        mSwitchTimer = view.findViewById(R.id.switch_alarm);
        mEditTime = view.findViewById(R.id.edit_content);
        mButtonCancel = view.findViewById(R.id.button_cancel);
        mButtonDone = view.findViewById(R.id.button_done);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_cancel:
                handleCancel();
                break;
            case R.id.button_done:
                handleDone();
                break;
            default:
                break;
        }
    }

    private void handleDone() {
        Intent intent = new Intent();
        String minute = mEditTime.getText().toString();
        if (!minute.isEmpty() && !minute.equals(getString(R.string.default_time)) && mSwitchTimer
                .isChecked()) {
            intent.putExtra(Constants.KEY_TIMER, Integer.parseInt(minute));
            Toast.makeText(getActivity(),
                    StringUtils.merge(getString(R.string.text_notify_timer), minute,
                            getString(R.string.text_minutes)), Toast.LENGTH_SHORT).show();
        } else if (!mSwitchTimer.isChecked() && mMinute != Integer.parseInt(getString(R.string.default_time))) {
            intent.putExtra(Constants.KEY_TIMER, String.valueOf(getString(R.string.default_time)));
        }
        getTargetFragment().onActivityResult(getTargetRequestCode(), REQUEST_FRAGMENT_TIMER, intent);
        dismiss();
    }

    private void handleCancel() {
        dismiss();
    }
}
