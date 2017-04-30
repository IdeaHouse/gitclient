package com.inder.gitclient.ui;

import com.inder.gitclient.R;
import com.inder.gitclient.constant.ApiConstants;
import com.inder.gitclient.constant.NotifyConstants;
import com.inder.gitclient.provider.AccountContract;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by inder on 29/4/17.
 */

public class CommitListFragment extends BaseFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private final int LOADER_COMMIT_LIST = 100;
    private final String SEARCH_TEXT = "search_text";
    private RecyclerView mCommitListRecyclerView;
    private RecyclerCommitListAdapter mRecyclerCommitListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mRoot = inflater.inflate(R.layout.fragment_commit_list, container, false);
        mCommitListRecyclerView = (RecyclerView) mRoot.findViewById(R.id.commit_list_recycler_view);

        syncManually(0);
        return mRoot;
    }

    @Override
    public void onResume() {
        super.onResume();
        restartLoader(null, LOADER_COMMIT_LIST);
    }

    private void restartLoader(Bundle args, int loaderId) {
        getActivity().getSupportLoaderManager()
                .restartLoader(loaderId, args, CommitListFragment.this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String searchText = "";
        if (args != null) {
            searchText = args.getString(SEARCH_TEXT) == null ? "" : args.getString(SEARCH_TEXT);
        }
        String selection = AccountContract.CommitList.COMMIT_LIST_USER_NAME + " LIKE ?";
        String selectionArgs[] = new String[]{"%" + searchText + "%"};

        return new CursorLoader(getContext(), AccountContract.CommitList.CONTENT_URI, null,
                selection,
                selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mCommitListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (data != null) {
            mRecyclerCommitListAdapter = new RecyclerCommitListAdapter(getContext(), data);
            mCommitListRecyclerView.swapAdapter(mRecyclerCommitListAdapter, false);
        }

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    protected void onReceiveSyncStatus(Intent intent) {
        super.onReceiveSyncStatus(intent);
        int status = intent.getIntExtra(NotifyConstants.NOTIFY_API_STATUS, 0);
        String apiUrl = intent.getStringExtra(NotifyConstants.NOTIFY_API_URL);
        if (apiUrl != null && apiUrl.equals(ApiConstants.GET_GIT_COMMIT_LIST)) {
            if (status == 1) {
                restartLoader(null, LOADER_COMMIT_LIST);
            }
        }
    }

    public void searchText(String searchText) {
        Bundle bundle = new Bundle();
        bundle.putString(SEARCH_TEXT, searchText);
        restartLoader(bundle, LOADER_COMMIT_LIST);

    }
}
