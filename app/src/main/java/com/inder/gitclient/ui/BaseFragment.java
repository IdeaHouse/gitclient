package com.inder.gitclient.ui;

import com.inder.gitclient.provider.AccountContract;
import com.inder.gitclient.util.AccountUtils;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by inder on 29/4/17.
 */

public class BaseFragment extends Fragment {

    protected LayoutInflater mLayoutInflater;
    protected View mRoot;

    protected void syncManually(int syncType) {
        if (getActivity() == null) {
            return;
        }
        Account account = AccountUtils.getActiveAccount(getContext());
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.setSyncAutomatically(account, AccountContract.CONTENT_AUTHORITY, true);
        ContentResolver.setIsSyncable(account, AccountContract.CONTENT_AUTHORITY, 1);
        ContentResolver.requestSync(account, AccountContract.CONTENT_AUTHORITY, settingsBundle);
    }

    protected void onReceiveSyncStatus(Intent intent) {
    }
}
