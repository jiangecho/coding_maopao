package net.coding.program.common.htmltext;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import net.coding.program.ImagePagerActivity_;
import net.coding.program.WebActivity_;
import net.coding.program.common.Global;
import net.coding.program.maopao.MaopaoDetailActivity;
import net.coding.program.maopao.MaopaoDetailActivity_;
import net.coding.program.message.MessageListActivity_;
import net.coding.program.user.UserDetailActivity_;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chaochen on 15/1/12.
 */
public class URLSpanNoUnderline extends URLSpan {

    private int color;

    public URLSpanNoUnderline(String url, int color) {
        super(url);
        this.color = color;
    }

    public static void openActivityByUri(Context context, String uriString, boolean newTask) {
        openActivityByUri(context, uriString, newTask, true);
    }

    public static boolean openActivityByUri(Context context, String uriString, boolean newTask, boolean defaultIntent) {
        Log.d("", "yes reload");
        Intent intent = new Intent();
        if (newTask) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        // 用户名
        final String atSomeOne = "^(?:https://[\\w.]*)?/u/([\\w.-]+)$";
        Pattern pattern = Pattern.compile(atSomeOne);
        Matcher matcher = pattern.matcher(uriString);
        if (matcher.find()) {
            String global = matcher.group(1);
            intent.setClass(context, UserDetailActivity_.class);
            intent.putExtra("globalKey", global);
            context.startActivity(intent);
            return true;
        }


        // 冒泡
        // https://coding.net/u/8206503/pp/9275
        final String maopao = "^(?:https://[\\w.]*)?/u/([\\w.-]+)/pp/([\\w.-]+)$";
        pattern = Pattern.compile(maopao);
        matcher = pattern.matcher(uriString);
        if (matcher.find()) {
            intent.setClass(context, MaopaoDetailActivity_.class);
            MaopaoDetailActivity.ClickParam param = new MaopaoDetailActivity.ClickParam(
                    matcher.group(1), matcher.group(2));
            intent.putExtra("mClickParam", param);
            context.startActivity(intent);
            return true;
        }


        // 私信推送
        // https://coding.net/user/messages/history/1984
        final String message = "^(?:https://[\\w.]*)?/user/messages/history/([\\w-]+)$";
        pattern = Pattern.compile(message);
        matcher = pattern.matcher(uriString);
        if (matcher.find()) {
            Log.d("", "gg " + matcher.group(1));
            intent.setClass(context, MessageListActivity_.class);
            intent.putExtra("mGlobalKey", matcher.group(1));
            context.startActivity(intent);
            return true;
        }



        // 图片链接
        final String imageSting = "(http|https):.*?.[.]{1}(gif|jpg|png|bmp)";
        pattern = Pattern.compile(imageSting);
        matcher = pattern.matcher(uriString);
        if (matcher.find()) {
            intent.setClass(context, ImagePagerActivity_.class);
            intent.putExtra("mSingleUri", uriString);
            context.startActivity(intent);
            return true;
        }

        // 跳转图片链接
        // https://coding.net/api/project/78813/files/137849/imagePreview
        final String imageJumpString = Global.HOST + "/api/project/\\d+/files/\\d+/imagePreview";
        pattern = Pattern.compile(imageJumpString);
        matcher = pattern.matcher(uriString);
        if (matcher.find()) {
            intent.setClass(context, ImagePagerActivity_.class);
            intent.putExtra("mSingleUri", uriString);
            context.startActivity(intent);
            return true;
        }

        try {
            if (defaultIntent) {
                intent = new Intent(context, WebActivity_.class);

                if (newTask) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                if (uriString.startsWith("/u/")) {
                    uriString = Global.HOST + uriString;
                }

                intent.putExtra("url", uriString);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(context, "" + uriString.toString(), Toast.LENGTH_LONG).show();
            Global.errorLog(e);
        }

        return false;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
        ds.setColor(color);
    }

    @Override
    public void onClick(View widget) {
        openActivityByUri(widget.getContext(), getURL(), false);
    }
}
