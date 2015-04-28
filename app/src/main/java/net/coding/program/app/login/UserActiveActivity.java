package net.coding.program.app.login;

import net.coding.program.R;
import net.coding.program.maopao.common.Global;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_reset_password_base)
public class UserActiveActivity extends ResetPasswordBaseActivity {

    @Override
    String getRequestHost() {
        return Global.HOST + "/api/activate";
    }
}
