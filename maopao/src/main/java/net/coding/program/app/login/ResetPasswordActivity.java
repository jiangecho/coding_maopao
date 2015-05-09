package net.coding.program.app.login;

import net.coding.program.R;
import net.coding.program.maopao.common.Global;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_reset_password_base_mp)
public class ResetPasswordActivity extends ResetPasswordBaseActivity {

    @Override
    String getRequestHost() {
        return Global.HOST + "/api/resetPassword";
    }
}
