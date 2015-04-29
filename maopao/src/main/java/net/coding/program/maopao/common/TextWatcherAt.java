package net.coding.program.maopao.common;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import net.coding.program.maopao.user.UsersListActivity;
import net.coding.program.maopao.user.UsersListActivity_;


/**
 * Created by chaochen on 14-10-29.
 */
public class TextWatcherAt implements TextWatcher {

    Context mContext;
    StartActivity mStartActivity;
    int mResult;


    public TextWatcherAt(Context mContext, StartActivity mStartActivity, int mResult ) {
        this.mContext = mContext;
        this.mStartActivity = mStartActivity;
        this.mResult = mResult;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newEnter = s.toString().substring(start, start + count);
//        if (newEnter.equals("@")) {
//            if (mProjectObject == null) {
//                startUserFollowList(mContext, mStartActivity, mResult);
//            } else {
//                Intent intent;
//                intent = new Intent(mContext, MembersSelectActivity_.class);
//                intent.putExtra("mProjectObject", mProjectObject);
//                mStartActivity.startActivityForResult(intent, mResult);
//            }
//        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    public static void startUserFollowList(Context mContext, StartActivity mStartActivity, int mResult) {
        Intent intent;
        intent = new Intent(mContext, UsersListActivity_.class);
        intent.putExtra("type", UsersListActivity.Friend.Follow);
        intent.putExtra("select", true);
        mStartActivity.startActivityForResult(intent, mResult);
    }
}
