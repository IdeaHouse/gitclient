package com.inder.gitclient.ui;

import com.inder.gitclient.constant.NotifyConstants;
import com.inder.gitclient.util.LogUtils;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import static com.inder.gitclient.util.LogUtils.LOGD;

/**
 * Created by inder on 29/4/17.
 */

public class BaseActivity extends AppCompatActivity {
    private final String TAG = LogUtils.makeLogTag(BaseActivity.class);
    protected ProgressDialog mProgressDialog;
    protected Toolbar mToolbar;
    private ResponseReceiver mResponseReceiver;
    private IntentFilter mStatusIntentFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this);
        mStatusIntentFilter = new IntentFilter(NotifyConstants.BROADCAST_ACTION);
        mResponseReceiver = new ResponseReceiver();
        LOGD(TAG,"register receiver");
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mResponseReceiver, mStatusIntentFilter);
    }

    protected void onReceiveSyncStatus(Intent intent) {

    }

    protected void unregisterReceiver() {
        LOGD(TAG, "unregistered Receiver");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mResponseReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    private class ResponseReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            LOGD(TAG, " broadcast received");
            onReceiveSyncStatus(intent);
        }
    }
}
