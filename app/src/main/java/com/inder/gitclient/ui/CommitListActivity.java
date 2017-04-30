package com.inder.gitclient.ui;

import com.inder.gitclient.R;
import com.inder.gitclient.util.AccountUtils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import static com.inder.gitclient.util.LogUtils.LOGD;
import static com.inder.gitclient.util.LogUtils.makeLogTag;

public class CommitListActivity extends BaseActivity {
    private final String TAG = makeLogTag(CommitListActivity.class);
    Account mAccount;
    private CommitListFragment mCommitListFragment;

    public static Account CreateSyncAccount(Context context) {

        Account newAccount = new Account(
                AccountUtils.ACCOUNT, AccountUtils.ACCOUNT_TYPE);
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);

        accountManager.addAccountExplicitly(newAccount, null, null);

        AccountUtils.setActiveAccountName(context, AccountUtils.ACCOUNT);
        return newAccount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_list);
        // creating dummy account if not exist
        if (AccountUtils.getActiveAccount(this) == null) {
            mAccount = CreateSyncAccount(this);
        } else {
            mAccount = AccountUtils.getActiveAccount(this);
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mCommitListFragment = new CommitListFragment();
        Bundle bundle = new Bundle();
        mCommitListFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.commit_list_fragment, mCommitListFragment,
                getString(R.string.commit_list));
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_commit_list, menu);
        setupSearchView(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sync) {
            if (mCommitListFragment != null) {
                mCommitListFragment.syncManually(0);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onReceiveSyncStatus(Intent intent) {
        super.onReceiveSyncStatus(intent);
        if (mCommitListFragment != null) {
            LOGD(TAG, " broadcast received ");
            mCommitListFragment.onReceiveSyncStatus(intent);
        }
    }

    @Override
    protected void onSearchTextChanged(String searchText) {
        super.onSearchTextChanged(searchText);
        if (mCommitListFragment != null) {
            mCommitListFragment.searchText(searchText);
        }
    }
}
