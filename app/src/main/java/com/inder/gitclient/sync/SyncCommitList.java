package com.inder.gitclient.sync;

import com.inder.gitclient.constant.ApiConstants;
import com.inder.gitclient.constant.SyncConstants;
import com.inder.gitclient.data.BaseData;
import com.inder.gitclient.data.CommitListHolder;
import com.inder.gitclient.net.HttpGet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import static com.inder.gitclient.util.LogUtils.LOGD;
import static com.inder.gitclient.util.LogUtils.makeLogTag;

/**
 * Created by inder on 29/4/17.
 */

public class SyncCommitList extends BaseSync {
    private final String TAG = makeLogTag(SyncCommitList.class);

    public SyncCommitList(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void performSync(Account account, Bundle extras, SyncResult syncResult) {

        String url = ApiConstants.GET_GIT_COMMIT_LIST;

        HttpGet httpGet = new HttpGet(mContext);
        String response = httpGet.doGet(url);
        try {
            JSONArray jr = new JSONArray(response);
            JSONObject jo = new JSONObject();
            jo.put("commitList", jr);
            response = jo.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        BaseData data = parseResponse(mContext, response, CommitListHolder.class.getName());
        if (data == null) {
            LOGD(TAG, "No data save for " + url);
            notifyStatus("", url, SyncConstants.SYNC_STATUS_NOT_SYNCED, "");
        } else {
            if (data.deleteData(mContext, "")) {
                data.saveData(mContext, "");
                notifyStatus("", url, SyncConstants.SYNC_STATUS_SYNCED, "");
            }
        }

    }
}
