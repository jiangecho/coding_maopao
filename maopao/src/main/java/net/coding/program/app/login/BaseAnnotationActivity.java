package net.coding.program.app.login;

import net.coding.program.R;
import net.coding.program.maopao.BaseActivity;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;


@EActivity(R.layout.activity_base_annotation_mp)
public class BaseAnnotationActivity extends BaseActivity {

    @AfterViews
    protected final void annotationDispayHomeAsUp() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OptionsItem(android.R.id.home)
    protected final void annotaionClose() {
        onBackPressed();
    }
}
