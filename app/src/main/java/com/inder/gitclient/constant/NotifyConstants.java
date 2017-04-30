package com.inder.gitclient.constant;

import com.inder.gitclient.BuildConfig;

/**
 * Created by inder on 29/4/17.
 */

public class NotifyConstants {
    // Defines a custom Intent action
    public static final String BROADCAST_ACTION = BuildConfig.APPLICATION_ID + ".BROADCAST";
    public static final String NOTIFY_API_NAME = BuildConfig.APPLICATION_ID
            + ".NOTIFY_API_NAME";
    public static final String NOTIFY_API_MESSAGE = BuildConfig.APPLICATION_ID
            + ".NOTIFY_API_MESSAGE";
    public static final String NOTIFY_API_STATUS = BuildConfig.APPLICATION_ID
            + ".NOTIFY_API_STATUS";
    public static final String NOTIFY_API_URL = BuildConfig.APPLICATION_ID
            + ".NOTIFY_API_URL";
}
