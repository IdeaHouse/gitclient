package com.inder.gitclient.provider;

import com.inder.gitclient.util.LogUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by inder on 29/4/17.
 */

public class AccountDatabase extends SQLiteOpenHelper {

    private static final String TAG = LogUtils.makeLogTag(AccountDatabase.class.getName());
    public static final int DATABASE_VERSION = 100;
    private static final String DATABASE_NAME = "gitclient.db";
    private Context mContext;

    public AccountDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + Tables.COMMIT_LIST + " (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                AccountContract.CommitList.COMMIT_LIST_COMMIT_ID + " TEXT NOT NULL," +
                AccountContract.CommitList.COMMIT_LIST_USER_NAME + " TEXT DEFAULT ''," +
                AccountContract.CommitList.COMMIT_LIST_USER_PIC_URL + " TEXT DEFAULT ''," +
                AccountContract.CommitList.COMMIT_LIST_DATE + " TEXT DEFAULT ''," +
                AccountContract.CommitList.COMMIT_LIST_MESSAGE + " TEXT DEFAULT ''" +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public interface Tables {
        String COMMIT_LIST = "commit_list";
    }
}
