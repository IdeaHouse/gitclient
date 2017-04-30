package com.inder.gitclient.net;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static com.inder.gitclient.util.LogUtils.LOGE;
import static com.inder.gitclient.util.LogUtils.LOGI;
import static com.inder.gitclient.util.LogUtils.makeLogTag;

/**
 * Created by inder on 29/4/17.
 */

public class HttpGet extends BaseHttpRequest {
    private String TAG = makeLogTag(HttpGet.class);

    public HttpGet(Context context) {
        super(context);
    }

    public String doGet(String url) {
        mUrl = url;
        String response = "";
        HttpURLConnection urlConnection = null;

        if (mUrl == null || mUrl.length() == 0) {
            LOGE(TAG, "Error !!! URL is NOT SET !!!");
            return "";
        }

        LOGI(TAG, "URL: " + mUrl);
        if (mUrl.startsWith("https")) {
            mHttps = true;
        }
        try {
            urlConnection = getUrlConnection(mUrl);
//            setHttpHeaders(urlConnection);
//            writeStream(urlConnection.getOutputStream());
            response = readStream(urlConnection);
            LOGI(TAG, "Response received: " + response);
        } catch (MalformedURLException e) {
            LOGE(TAG, Log.getStackTraceString(e));
        } catch (IOException e) {
            LOGE(TAG, Log.getStackTraceString(e));
        } catch (URISyntaxException e) {
            LOGE(TAG, Log.getStackTraceString(e));
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return response;
    }
}
