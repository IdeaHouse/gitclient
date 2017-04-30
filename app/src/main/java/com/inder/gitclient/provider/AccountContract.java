package com.inder.gitclient.provider;

import com.inder.gitclient.BuildConfig;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by inder on 29/4/17.
 */

public class AccountContract {
    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_COMMIT_LIST = "commit_list";

    public interface CommitListColumns {
        String COMMIT_LIST_COMMIT_ID = "commit_list_commit_id";
        String COMMIT_LIST_USER_NAME = "commit_list_user_name";
        String COMMIT_LIST_USER_PIC_URL = "commit_list_user_pic_url";
        String COMMIT_LIST_DATE = "commit_list_date";
        String COMMIT_LIST_MESSAGE = "commit_list_message";

    }

    public static class CommitList implements BaseColumns, CommitListColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COMMIT_LIST).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.git_client.commit_list";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.git_client.commit_list";

        /**
         * Build {@link Uri} for requested {@link #_ID}.
         */
        public static Uri buildCommitListUri(String Id) {
            return CONTENT_URI.buildUpon().appendPath(Id).build();
        }

    }

}
