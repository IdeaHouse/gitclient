package com.inder.gitclient.data;

import com.google.gson.annotations.SerializedName;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

/**
 * Created by inder on 29/4/17.
 */

public class Commit extends BaseData {
    @SerializedName("author")
    private Author mAuthor;
    @SerializedName("message")
    private String mCommitMessage;

    public Author getAuthor() {
        return mAuthor;
    }

    public void setAuthor(Author author) {
        mAuthor = author;
    }

    public String getCommitMessage() {
        return mCommitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        mCommitMessage = commitMessage;
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

    }
}
