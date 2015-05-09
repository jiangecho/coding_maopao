package net.coding.program.maopao.common;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import net.coding.program.R;
import net.coding.program.maopao.BaseActivity;

public class TermsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_terms_mp);

        WebView webView = (WebView) findViewById(R.id.webview);
        Global.initWebView(webView);

        try {
            webView.loadDataWithBaseURL(null, Global.readTextFile(getAssets().open("terms")), "text/html", "UTF-8", null);
        } catch (Exception e) {
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
