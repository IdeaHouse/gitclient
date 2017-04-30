package com.inder.gitclient.sync;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.inder.gitclient.constant.NotifyConstants;
import com.inder.gitclient.constant.SyncConstants;
import com.inder.gitclient.data.BaseData;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.Reader;
import java.io.StringReader;

import static com.inder.gitclient.util.LogUtils.LOGD;
import static com.inder.gitclient.util.LogUtils.LOGE;
import static com.inder.gitclient.util.LogUtils.makeLogTag;

/**
 * Created by inder on 29/4/17.
 */

public abstract class BaseSync {
    private static final String TAG = makeLogTag(BaseSync.class);
    protected Context mContext;

    public BaseSync(Context context) {
        mContext = context;
    }

    public abstract void performSync(Account account, Bundle extras, SyncResult syncResult);

    protected BaseData parseResponse(Context context, String cachePathOrResponse,
            String javaClassName) {
        int syncApiError = SyncConstants.SYNC_ERROR_PARSING;
        if (javaClassName == null) {
            LOGE(TAG, "JSON Parsing not implemented!!");
            return null;
        }
        BaseData parsedData = null;
        Reader reader;
        try {

            reader = new StringReader(cachePathOrResponse);
            Class<?> cls = Class.forName(javaClassName);

            Gson gson = new Gson();
            Object object = gson.fromJson(reader, cls);

            if (object instanceof BaseData) {
                parsedData = (BaseData) object;
            } else if (object instanceof BaseData[]) {
                BaseData[] baseDataList = (BaseData[]) object;
                if (baseDataList != null && baseDataList.length > 0) {
                    parsedData = baseDataList[0];
                }
            }
        } catch (ClassNotFoundException e) {
            LOGE(TAG, Log.getStackTraceString(e));
        } catch (ClassCastException e) {
            LOGE(TAG, Log.getStackTraceString(e));
        } catch (JsonSyntaxException e) {
            LOGE(TAG, Log.getStackTraceString(e));
        } catch (IllegalStateException e) {
            LOGE(TAG, Log.getStackTraceString(e));
        } catch (NumberFormatException e) {
            LOGE(TAG, Log.getStackTraceString(e));
        }
        return parsedData;
    }

    protected void notifyStatus(String apiName, String apiUrl, int status, String message) {
        final Intent intent = new Intent(NotifyConstants.BROADCAST_ACTION);
        intent.putExtra(NotifyConstants.NOTIFY_API_NAME, apiName);
        intent.putExtra(NotifyConstants.NOTIFY_API_URL, apiUrl);
        intent.putExtra(NotifyConstants.NOTIFY_API_MESSAGE, message);
        intent.putExtra(NotifyConstants.NOTIFY_API_STATUS, status);
        boolean isSend = LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
        LOGD(TAG, isSend + "");
    }
}
