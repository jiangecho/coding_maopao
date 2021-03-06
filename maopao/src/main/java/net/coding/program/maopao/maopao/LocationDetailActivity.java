package net.coding.program.maopao.maopao;

import android.view.View;
import android.widget.TextView;
import net.coding.program.R;
import net.coding.program.maopao.BaseActivity;
import org.androidannotations.annotations.*;

/**
 * Created by Neutra on 2015/3/14.
 */
@EActivity(R.layout.activity_location_detail_mp)
public class LocationDetailActivity extends BaseActivity {

    @ViewById
    TextView nameText, addressText;

    @ViewById
    View map_button, customText;

    @Extra
    double latitude, longitude;

    @Extra
    String name, address;

    @Extra
    boolean isCustom;

    @AfterViews
    void afterViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(name == null) name = "";
        name = name.replaceFirst(".*" + MaopaoLocationArea.MAOPAO_LOCATION_DIVIDE,"");
        nameText.setText(name);
        addressText.setText(address);
        if (address == null || address.isEmpty()) {
            addressText.setText("未填写详细的地址");
        }

        customText.setVisibility(isCustom? View.VISIBLE:View.GONE);
        if (address != null) {
            map_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LocationMapActivity_.intent(LocationDetailActivity.this)
                            .latitude(latitude).longitude(longitude)
                            .name(name).address(address).start();
                }
            });
        }
    }

    @OptionsItem(android.R.id.home)
    void close() {
        onBackPressed();
    }
}
