package com.inder.gitclient.net;

/**
 * Created by inder on 29/4/17.
 */

public interface IHttpData {
    String getData();
    void setData(String value);
    void addParams(String key, String value);

}
