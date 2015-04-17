package net.coding.program.user;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import net.coding.program.BaseActivity;
import net.coding.program.R;
import net.coding.program.common.Global;
import net.coding.program.model.UserObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@EActivity(R.layout.activity_add_follow)
public class AddFollowActivity extends BaseActivity {

    String HOST_SEARCH_USER = Global.HOST + "/api/user/search?key=%s";

    String urlAddUser = "";

    ArrayList<UserObject> mData = new ArrayList<UserObject>();

    boolean mNeedUpdate = false;

    @ViewById
    ListView listView;

    int flag = 0;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == flag) {
                getNetwork(String.format(HOST_SEARCH_USER, Global.encodeUtf8((String) msg.obj)), HOST_SEARCH_USER);
            }
        }
    };

    BaseAdapter baseAdapter;

    @AfterViews
    void init() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        baseAdapter = new FollowAdapter();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserObject userObject = mData.get((int) id);
                UserDetailActivity_
                        .intent(AddFollowActivity.this)
                        .globalKey(userObject.global_key)
                        .start();
            }
        });
        listView.setAdapter(baseAdapter);
    }

    @Override
    public void parseJson(int code, JSONObject respanse, String tag, int pos, Object data) throws JSONException {
        if (tag.equals(HOST_SEARCH_USER)) {
            if (code == 0) {
                JSONArray jsonArray = respanse.getJSONArray("data");
                mData.clear();
                for (int i = 0; i < jsonArray.length(); ++i) {
                    UserObject user = new UserObject(jsonArray.getJSONObject(i));
                    mData.add(user);
                }
            } else {
                showErrorMsg(code, respanse);
            }
            baseAdapter.notifyDataSetChanged();

        } else if (tag.equals(UsersListActivity.HOST_FOLLOW)) {
            if (code == 0) {
                mNeedUpdate = true;
                showButtomToast(R.string.follow_success);
                mData.get(pos).followed = true;
            } else {
                showButtomToast(R.string.follow_fail);
            }
            baseAdapter.notifyDataSetChanged();
        } else if (tag.equals(UsersListActivity.HOST_UNFOLLOW)) {
            if (code == 0) {
                mNeedUpdate = true;
                showButtomToast("取消关注成功");
                mData.get(pos).followed = false;
            } else {
                showButtomToast("取消关注失败");
            }
            baseAdapter.notifyDataSetChanged();
        } else if (tag.equals(urlAddUser)) {
            if (code == 0) {
                mNeedUpdate = true;
                showMiddleToast(String.format("添加项目成员 %s 成功", ((UserObject) data).name));
            } else {
                showErrorMsg(code, respanse);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_follow_activity, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        menuItem.expandActionView();
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        searchView.setQueryHint("用户名，email，个性后缀");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                search(s.toString());
                return true;
            }
        });

        return true;
    }

    void search(String s) {
        int flagHandler = ++flag;
        Message message = Message.obtain(mHandler, flagHandler, s);
        mHandler.sendMessageDelayed(message, 1000);
    }

    class AddProjectAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.activity_add_follow_list_item, parent, false);
                holder = new ViewHolder();
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.mutual = (CheckBox) convertView.findViewById(R.id.followed);
                holder.mutual.setVisibility(View.INVISIBLE);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final UserObject data = (UserObject) getItem(position);

            iconfromNetwork(holder.icon, data.avatar);
            holder.name.setText(String.format("%s - %s", data.name, data.global_key));

            return convertView;
        }
    }

    class FollowAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.activity_add_follow_list_item, parent, false);
                holder = new ViewHolder();
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.mutual = (CheckBox) convertView.findViewById(R.id.followed);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final UserObject data = (UserObject) getItem(position);

            iconfromNetwork(holder.icon, data.avatar);
            holder.name.setText(String.format("%s - %s", data.name, data.global_key));

            int drawableId = data.follow ? R.drawable.checkbox_fans : R.drawable.checkbox_follow;
            holder.mutual.setButtonDrawable(drawableId);
            holder.mutual.setChecked(data.followed);

            holder.mutual.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestParams params = new RequestParams();
                    params.put("users", data.global_key);
                    if (((CheckBox) v).isChecked()) {
                        postNetwork(UsersListActivity.HOST_FOLLOW, params, UsersListActivity.HOST_FOLLOW, position, null);
                    } else {
                        postNetwork(UsersListActivity.HOST_UNFOLLOW, params, UsersListActivity.HOST_UNFOLLOW, position, null);
                    }
                }
            });

            return convertView;
        }
    }

    ;

    static class ViewHolder {
        ImageView icon;
        TextView name;
        CheckBox mutual;
    }

    @OptionsItem(android.R.id.home)
    void close() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        setResult(mNeedUpdate ? RESULT_OK : RESULT_CANCELED);
        finish();
    }
}
