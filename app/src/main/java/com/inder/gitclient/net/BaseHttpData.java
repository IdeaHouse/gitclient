package com.inder.gitclient.net;

import java.util.HashMap;

/**
 * Created by inder on 30/4/17.
 */

public class BaseHttpData implements IHttpData {
    // Key value pair
    HashMap<String, String> mParams = new HashMap<>();
    // JSON data
    private String mData;

    @Override
    public String getData() {
        return mData;
    }

    @Override
    public void setData(String value) {
        mData = value;
    }

    @Override
    public void addParams(String key, String value) {
        mParams.put(key, value);
    }
}
