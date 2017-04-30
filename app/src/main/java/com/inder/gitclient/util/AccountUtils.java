package com.inder.gitclient.util;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.inder.gitclient.util.LogUtils.LOGD;
import static com.inder.gitclient.util.LogUtils.makeLogTag;

/**
 * Created by inder on 29/4/17.
 */

public class AccountUtils {
    public static final String ACCOUNT_TYPE = "com.inder.gitClient";
    public static final String AUTHORITY = "com.example.android.datasync.provider";
    // The account name
    public static final String ACCOUNT = "git_client";
    private static final String PREF_ACTIVE_ACCOUNT = "chosen_account";
    private final static String TAG = makeLogTag(AccountUtils.class);

    private static SharedPreferences getSharedPreferences(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean setActiveAccountName(final Context context, final String accountName) {
        LOGD(TAG, "Set active account to: " + accountName);
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(PREF_ACTIVE_ACCOUNT, accountName).commit();
        return true;
    }

    public static String getActiveAccountName(final Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(PREF_ACTIVE_ACCOUNT, null);
    }

    public static Account getActiveAccount(final Context context) {
        String accountName = getActiveAccountName(context);
        if (accountName != null) {
            return new Account(accountName, ACCOUNT_TYPE);
        } else {
            return null;
        }
    }
}
