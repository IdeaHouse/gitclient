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

public class CommitList extends BaseData {
    @SerializedName("sha")
    private String mCommitId;
    @SerializedName("commit")
    private Commit mCommit;
    @SerializedName("author")
    private Author mAuthor;

    public String getCommitId() {
        return mCommitId;
    }

    public void setCommitId(String commitId) {
        mCommitId = commitId;
    }

    public Commit getCommit() {
        return mCommit;
    }

    public void setCommit(Commit commit) {
        mCommit = commit;
    }

    public Author getAuthor() {
        return mAuthor;
    }

    public void setAuthor(Author author) {
        mAuthor = author;
    }

    @Override
    protected Uri getUri() {
        return AccountContract.CommitList.CONTENT_URI;
    }

    @Override
    public ContentValues getContentValues(String syncAccountId) {
        ContentValues values = new ContentValues();
        values.put(AccountContract.CommitList.COMMIT_LIST_COMMIT_ID, mCommitId);
        if (mCommit != null && mCommit.getAuthor() != null) {

            values.put(AccountContract.CommitList.COMMIT_LIST_USER_NAME,
                    mCommit.getAuthor().getName());
            values.put(AccountContract.CommitList.COMMIT_LIST_MESSAGE, mCommit.getCommitMessage());
            values.put(AccountContract.CommitList.COMMIT_LIST_DATE, mCommit.getAuthor().getDate());

        }
        if (mAuthor != null) {

            values.put(AccountContract.CommitList.COMMIT_LIST_USER_PIC_URL, mAuthor.getAuthorPic());

        }

        return values;
    }

    @Override
    public void saveData(Context context, String syncAccountId) {
        ArrayList<ContentProviderOperation> batch = new ArrayList<>();
        batch.add(getContentProviderOperation(syncAccountId));
        applyBatch(context, batch);
    }
}
