package net.coding.program.model;

import android.text.SpannableString;
import android.text.Spanned;

import net.coding.program.common.Global;
import net.coding.program.common.HtmlContent;
import net.coding.program.common.MyImageGetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by cc191954 on 14-8-9.
 */
public class DynamicObject {

    static final String BLACK_HTML = "<font color='#666666'>%s</font>";
    static final int BLACK_COLOR = 0xff666666;

    public static class DynamicBaseObject implements Serializable {
        public String action = "";
        public String action_msg = "";
        public long created_at;
        public int id;
        public String target_type = "";

        public User user = new User();


        public Spanned title() {
            final String format = "%s %s";
            String title = String.format(format, user.getHtml(), action_msg);
            return Global.changeHyperlinkColor(title);
        }

        public Spanned content(MyImageGetter imageGetter) {
            return new SpannableString(action_msg);
        }

        public String jump() {
            return "";
        }

        protected String makeJump(String url) {
            return Global.HOST + url;
        }
    }

    static String black(String s) {
        return String.format(BLACK_HTML, s);
    }



    public static class Owner implements Serializable {
        public String avatar = "";
        public String global_key = "";
        public String name = "";
        public String path = "";

        public Owner(JSONObject json) throws JSONException {
            if (json.has("avatar")) {
                avatar = Global.replaceAvatar(json);
            }

            global_key = json.optString("global_key");
            name = json.optString("name");
            path = json.optString("path");
        }

        public Owner() {
        }

        public Owner(UserObject data) {
            avatar = data.avatar;
            global_key = data.global_key;
            name = data.name;
            path = data.path;
        }

        public String getHtml() {
            return HtmlContent.createUserHtml(global_key, name);
        }
    }

    public static class User implements Serializable {
        public String avatar = "";
        public String global_key = "";
        public String name = "";
        public String path = "";
        public boolean follow;
        public boolean followed;

        public User(JSONObject json) throws JSONException {
            if (json.has("avatar")) {
                avatar = Global.replaceAvatar(json);
            }

            global_key = json.optString("global_key");
            name = json.optString("name");
            path = json.optString("path");
            follow = json.optInt("follow") != 0;
            followed = json.optInt("followed") != 0;
        }

        public User() {
        }

        public String getHtml() {
            return HtmlContent.createUserHtml(global_key, name);
        }
    }

}


