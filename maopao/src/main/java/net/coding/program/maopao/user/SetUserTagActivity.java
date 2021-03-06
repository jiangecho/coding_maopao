package net.coding.program.maopao.user;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.loopj.android.http.RequestParams;
import net.coding.program.R;
import net.coding.program.maopao.BaseActivity;
import net.coding.program.maopao.common.Global;
import net.coding.program.maopao.model.AccountInfo;
import net.coding.program.maopao.model.UserObject;
import org.androidannotations.annotations.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@EActivity(R.layout.activity_user_edit_tags_mp)
@OptionsMenu(R.menu.set_password)
public class SetUserTagActivity extends BaseActivity {

    @Extra("title")
    String title;

    String tags;

    UserObject user;

    @ViewById
    GridView gridView;

    JSONArray tagJSONArray;

    final String HOST_USERINFO = Global.HOST + "/api/user/updateInfo";

    String HOST_USER_TAG_LIST = Global.HOST + "/api/tagging/user_tag_list";

    UserTagAdapter adapter;

    @AfterViews
    void init() {
        showDialogLoading();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
        user = AccountInfo.loadAccount(this);
        tags = user.tags;
        getNetwork(HOST_USER_TAG_LIST, HOST_USER_TAG_LIST);
        tagJSONArray = new JSONArray();
        //adapter.set
        adapter = new UserTagAdapter(SetUserTagActivity.this, tags, tagJSONArray);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(onTagClickListener);
    }

    @OptionsItem(android.R.id.home)
    void back() {
        onBackPressed();
    }

    @OptionsItem
    void submit() {
        RequestParams params = new RequestParams();

        /*if(true){
            return;
        }*/
        user.tags = adapter.getSelected();
        //user.tags_str = adapter.getSelectedStr();
        try {
            params.put("email", user.email);
            params.put("lavatar", user.lavatar);
            params.put("name", user.name);
            params.put("sex", user.sex);
            params.put("phone", user.phone);
            params.put("birthday", user.birthday);
            params.put("location", user.location);
            params.put("company", user.company);
            params.put("slogan", user.slogan);
            params.put("introduction", user.introduction);
            params.put("job", user.job);
            params.put("tags", user.tags);

            postNetwork(HOST_USERINFO, params, HOST_USERINFO);
        } catch (Exception e) {
            showMiddleToast(e.toString());
        }
    }

    @Override
    public void parseJson(int code, JSONObject respanse, String tag, int pos, Object data) throws JSONException {
        if (tag.equals(HOST_USERINFO)) {
            if (code == 0) {
                user = new UserObject(respanse.getJSONObject("data"));
                AccountInfo.saveAccount(this, user);
                showButtomToast("修改成功");
                setResult(Activity.RESULT_OK);
                //AccountInfo.saveAccount(this, user);
                finish();
            } else {
                showErrorMsg(code, respanse);
            }
        } else if (tag.equals(HOST_USER_TAG_LIST)) {
            hideProgressDialog();
            if (code == 0) {
                tagJSONArray = respanse.getJSONArray("data");
                adapter.setTagJSONArray(tagJSONArray);
                adapter.notifyDataSetChanged();
            } else {
                showErrorMsg(code, respanse);
            }
        }
    }

    private AdapterView.OnItemClickListener onTagClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            adapter.setSelectedTag(position);
        }
    };

}
