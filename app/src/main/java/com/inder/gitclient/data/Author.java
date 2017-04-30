package com.inder.gitclient.data;

import com.google.gson.annotations.SerializedName;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.net.wifi.ScanResult;

/**
 * Created by inder on 29/4/17.
 */

public class Author extends BaseData {
    @SerializedName("name")
    private String mName;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("date")
    private String mDate;
    @SerializedName("avatar_url")
    private String mAuthorPic;


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getAuthorPic() {
        return mAuthorPic;
    }

    public void setAuthorPic(String authorPic) {
        mAuthorPic = authorPic;
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
