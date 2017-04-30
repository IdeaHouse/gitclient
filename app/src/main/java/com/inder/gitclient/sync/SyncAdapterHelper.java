package com.inder.gitclient.sync;

import android.accounts.Account;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * Created by inder on 29/4/17.
 */

public class SyncAdapterHelper {

    public static synchronized void syncAll(Account account, Context context, Bundle extras,
            SyncResult syncResult) {
        new SyncCommitList(context).performSync(account, extras, syncResult);

    }
}
