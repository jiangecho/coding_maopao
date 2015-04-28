package net.coding.program.app.login;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import net.coding.program.BaseAnnotationActivity;
import net.coding.program.R;
import net.coding.program.common.Global;
import net.coding.program.common.enter.SimpleTextWatcher;
import net.coding.program.common.network.MyAsyncHttpClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.Header;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EActivity(R.layout.activity_base_send_email)
public class SendEmailBaseActivity extends BaseAnnotationActivity {

    @ViewById
    protected EditText  editValify;

    @ViewById
    protected ImageView imageValify;

    @ViewById
    protected EditText editName;

    @ViewById
    protected Button loginButton;

    @AfterViews
    protected final void initBaseResend() {
        downloadValifyPhoto();
    }

    @Click
    protected final void imageValify() {
        editValify.requestFocus();
        downloadValifyPhoto();
    }

    protected void downloadValifyPhoto() {
        String host = Global.HOST + "/api/getCaptcha";
        AsyncHttpClient client = MyAsyncHttpClient.createClient(this);

        client.get(host, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                imageValify.setImageBitmap(BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length));
                editValify.setText("");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showMiddleToast("获取验证码失败");
            }
        });
    }

    protected String getEmail() {
        return editName.getText().toString();
    }

    protected String getValify() {
        return editValify.getText().toString();
    }

    protected boolean isEnterSuccess() {
        return isValifyEmail(this, getEmail())
                && isEnterValifyCode();
    }

    private boolean isEnterValifyCode() {
        if (getValify().isEmpty()) {
            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public static boolean isValifyEmail(Context context, String email) {
        String match = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
        Pattern pattern = Pattern.compile(match);
        Matcher matcher = pattern.matcher(email);
        if (matcher.find()) {
            return true;
        }

        Toast.makeText(context, "邮箱格式错误", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * Created by chaochen on 15/1/6.
     */
    public static class LoginEditText extends EditText {

        Drawable drawable;

        public LoginEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            drawable = getResources().getDrawable(R.drawable.delete_edit_login);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

            addTextChangedListener(new SimpleTextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    displayDelete(s.length() > 0);
                }
            });
        }

        private void displayDelete(boolean show) {
            if (show) {
                setDrawableRight(drawable);
            } else {
                setDrawableRight(null);
            }
        }

        private void setDrawableRight(Drawable drawable) {
            setCompoundDrawables(null, null, drawable, null);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (getCompoundDrawables()[2] != null) {
                    boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                            && (event.getX() < ((getWidth() - getPaddingRight())));

                    if (touchable) {
                        this.setText("");
                    }
                }
            }

            return super.onTouchEvent(event);
        }
    }
}
