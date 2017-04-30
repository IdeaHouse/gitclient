package com.inder.gitclient.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import static com.inder.gitclient.util.LogUtils.LOGD;
import static com.inder.gitclient.util.LogUtils.makeLogTag;

/**
 * Created by inder on 30/4/17.
 */

public class HttpDataKeyValue extends BaseHttpData {
    private final String TAG = makeLogTag(HttpDataKeyValue.class);

    @Override
    public String getData() {
        if (mParams == null || mParams.size() == 0) {
            return "";
        }
        StringBuilder paramsEncoded = new StringBuilder();
        try {
            boolean first = true;
            for (Map.Entry<String, String> entry : mParams.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    paramsEncoded.append("&");
                }
                paramsEncoded.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                paramsEncoded.append("=");
                paramsEncoded.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        LOGD(TAG, "Encoded params: " + paramsEncoded.toString());
        return paramsEncoded.toString();
    }

}
