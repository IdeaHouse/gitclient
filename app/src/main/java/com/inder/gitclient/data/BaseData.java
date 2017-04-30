package com.inder.gitclient.data;

import com.inder.gitclient.provider.AccountContract;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;

import static com.inder.gitclient.util.LogUtils.LOGE;
import static com.inder.gitclient.util.LogUtils.makeLogTag;

/**
 * Created by inder on 29/4/17.
 */

public abstract class BaseData {
    private static final String TAG = makeLogTag(BaseData.class);

    protected abstract Uri getUri();

    // added syncAccount id to handle multiple account in future.
    public abstract ContentValues getContentValues(String syncAccountId);

    public ContentProviderOperation getContentProviderOperation(String syncAccountId) {
        if (getContentValues(syncAccountId) != null && getUri() != null) {
            ContentProviderOperation.Builder builder = ContentProviderOperation
                    .newInsert(getUri());
            builder.withValues(getContentValues(syncAccountId));
            return builder.build();
        }
        return null;
    }

    public abstract void saveData(Context context, String syncAccountId);

    public boolean deleteData(Context context, String syncAccountId) {
        return true;
    }

    protected ContentProviderResult[] applyBatch(Context context,
            ArrayList<ContentProviderOperation> batch) {
        if (batch == null || batch.size() == 0) {
            return null;
        }
        ContentProviderResult[] results = null;
        try {
            results = context.getContentResolver()
                    .applyBatch(AccountContract.CONTENT_AUTHORITY, batch);
        } catch (RemoteException e) {
            LOGE(TAG, Log.getStackTraceString(e));
        } catch (OperationApplicationException e) {
            LOGE(TAG, Log.getStackTraceString(e));
        }
        return results;
    }

}
