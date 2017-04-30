package com.inder.gitclient.data;

import com.google.gson.annotations.SerializedName;

import com.inder.gitclient.provider.AccountContract;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by inder on 29/4/17.
 */

public class CommitListHolder extends BaseData {
    @SerializedName("commitList")
    private ArrayList<CommitList> mCommitList;

    public ArrayList<CommitList> getCommitList() {
        return mCommitList;
    }

    public void setCommitList(ArrayList<CommitList> commitList) {
        mCommitList = commitList;
    }

    @Override
    protected Uri getUri() {
        return null;
    }

    @Override
    public ContentValues getContentValues(String syncAccountId) {
        return null;
    }

    @Override
    public void saveData(Context context, String syncAccountId) {
        if (mCommitList != null && mCommitList.size() > 0) {
            ArrayList<ContentProviderOperation> batch = new ArrayList<>();
            for (CommitList commitList : mCommitList) {
                batch.add(commitList.getContentProviderOperation(syncAccountId));
            }
            applyBatch(context, batch);
        }
    }

    @Override
    public boolean deleteData(Context context, String syncAccountId) {
        context.getContentResolver().delete(AccountContract.CommitList.CONTENT_URI, null, null);
        return true;
    }
}
