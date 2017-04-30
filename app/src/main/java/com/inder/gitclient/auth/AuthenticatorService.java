package com.inder.gitclient.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by inder on 29/4/17.
 */

public class AuthenticatorService extends Service {
    AccountAuthenticator mAccountAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        mAccountAuthenticator=new AccountAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAccountAuthenticator.getIBinder();
    }
}
