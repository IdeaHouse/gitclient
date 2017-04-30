package com.inder.gitclient.net;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

import static com.inder.gitclient.util.LogUtils.LOGD;
import static com.inder.gitclient.util.LogUtils.makeLogTag;

/**
 * Created by inder on 29/4/17.
 */

public abstract class BaseHttpRequest {
    public static final String CONTENT_TYPE_JSON = "application/json";
    private static final String TAG = makeLogTag(BaseHttpRequest.class);
    private static final int CONNECT_TIMEOUT = 30 * 1000;
    private static final int READ_TIMEOUT = 60 * 1000;
    protected Context mContext;
    protected String mUrl;
    protected boolean mHttps;
    private String mContentType;
    private IHttpData mHttpData;

    BaseHttpRequest(Context context) {
        mContext = context;
        mHttpData = new HttpDataKeyValue();
    }

    protected HttpURLConnection getUrlConnection(String urlPath) throws IOException {
        HttpURLConnection urlConnection;
        URL url = new URL(urlPath);
        if (mHttps) {
            urlConnection = (HttpsURLConnection) url.openConnection();
        } else {
            urlConnection = (HttpURLConnection) url.openConnection();
        }
//        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("GET");
        urlConnection.setChunkedStreamingMode(0);
        urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
        urlConnection.setReadTimeout(READ_TIMEOUT);
        urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
        urlConnection.setRequestProperty("Accept","*/*");
        return urlConnection;
    }

    protected void setHttpHeaders(HttpURLConnection urlConnection) {
            urlConnection.setRequestProperty("Content-Type", CONTENT_TYPE_JSON);
    }


    protected void writeStream(OutputStream os) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        if (mHttpData.getData().length() == 0) {
            bw.flush();
        } else {
            bw.write(mHttpData.getData());
        }
        bw.close();
        os.close();
    }

    protected String readStream(URLConnection urlConnection)
            throws IOException, URISyntaxException {
        String contentType = urlConnection.getContentType();
        String contentEncoding = urlConnection.getContentEncoding();
        boolean gzippedResponse = false;
        if (contentEncoding != null && contentEncoding.indexOf("gzip") >= 0) {
            gzippedResponse = true;
        }
        LOGD(TAG, "Content-type: " + contentType + ", Gzipped response: " + gzippedResponse);
        return readStream(urlConnection.getInputStream(), gzippedResponse);
    }

    private String readStream(InputStream is, boolean gzippedResponse)
            throws IOException, URISyntaxException {
        File cacheFile = null;
        if (gzippedResponse) {
            return readGzipStream(is);
        } else {
            return readTextStream(is);
        }
    }

    private String readTextStream(InputStream is) throws IOException {
        return readStreamBase(new BufferedInputStream(is));
    }

    private String readGzipStream(InputStream is) throws IOException {
        return readStreamBase(new GZIPInputStream(is));
    }

    // XXX: Better TO MAKE DIFFERENT CLASSES of GZIP-Handling, JSON-Handling and Binary-Handling
    private String readStreamBase(InputStream is) throws IOException {
        String response = "";
        FileWriter fw = null;
        try {
            int bufLen = 64 * 1024;
            byte[] buf = new byte[bufLen];
            int len;
            while ((len = is.read(buf, 0, bufLen)) != -1) {
                String chunk = new String(buf, 0, len);
                response += chunk;
            }
        } finally {
            if (is != null) {
                is.close();
            }
            if (fw != null) {
                fw.close();
            }
        }
        return response;

    }
}

