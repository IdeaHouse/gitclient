package com.inder.gitclient.ui;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.inder.gitclient.R;
import com.inder.gitclient.provider.AccountContract;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by inder on 29/4/17.
 */

public class RecyclerCommitListAdapter
        extends RecyclerView.Adapter<RecyclerCommitListAdapter.ViewHolder> {


    private Cursor mCursor;
    private Context mContext;

    public RecyclerCommitListAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_commit_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        String name = mCursor.getString(mCursor.getColumnIndex(AccountContract.CommitList
                .COMMIT_LIST_USER_NAME));
        String commit = mCursor.getString(mCursor.getColumnIndex(AccountContract.CommitList
                .COMMIT_LIST_COMMIT_ID));
        String message = mCursor.getString(mCursor.getColumnIndex(AccountContract.CommitList
                .COMMIT_LIST_MESSAGE));
        String imageUrl = mCursor.getString(mCursor.getColumnIndex(AccountContract.CommitList
                .COMMIT_LIST_USER_PIC_URL));

        holder.mNameTextView.setText(name);

        holder.mMessageTextView.setText(mContext.getResources().getString(R.string
                .commit_message) + message);

        holder.mCommitTextView.setText(mContext.getResources().getString(R.string.commit_id) +
                commit);

        Glide.with(mContext).load(imageUrl).
                placeholder(R.drawable.ic_account_circle_black_48dp).
                into(holder.mUserPicImageView);

        Glide.with(mContext).load(imageUrl).asBitmap().centerCrop().into(new BitmapImageViewTarget
                (holder.mUserPicImageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.mUserPicImageView.setImageDrawable(circularBitmapDrawable);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mUserPicImageView;
        TextView mNameTextView;
        TextView mCommitTextView;
        TextView mMessageTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            mUserPicImageView = (ImageView) itemView.findViewById(R.id.user_pic_image_view);
            mNameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            mCommitTextView = (TextView) itemView.findViewById(R.id.commit_text_view);
            mMessageTextView = (TextView) itemView.findViewById(R.id.message_text_view);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
