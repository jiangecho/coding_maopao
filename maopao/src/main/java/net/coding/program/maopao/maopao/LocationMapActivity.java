package net.coding.program.maopao.maopao;

import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import net.coding.program.R;
import net.coding.program.maopao.BaseActivity;
import org.androidannotations.annotations.*;

/**
 * Created by Neutra on 2015/3/14.
 */
@EActivity(R.layout.location_map_mp)
public class LocationMapActivity extends BaseActivity {
    @ViewById
    MapView mapView;

    @Extra
    double latitude, longitude;
    @Extra
    String name, address;

    private boolean isInfoWindowShown;

    @AfterViews
    void afterViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LatLng position = new LatLng(latitude, longitude);
        BaiduMap map = mapView.getMap();
        map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        map.setMapStatus(MapStatusUpdateFactory.newLatLngZoom(position, 17));
        Marker marker = (Marker) map.addOverlay(new MarkerOptions()
                .perspective(false)
                .position(position)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_point))
                .title(TextUtils.isEmpty(address) ? name : (name + "\n" + address))
                .draggable(false));
        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                toggleInfoWindow(marker);
                return false;
            }
        });
        isInfoWindowShown = false;
        toggleInfoWindow(marker);
    }

    private void toggleInfoWindow(Marker marker) {
        if (marker == null || mapView == null || mapView.getMap() == null) return;
        if (isInfoWindowShown) {
            mapView.getMap().hideInfoWindow();
            isInfoWindowShown = false;
        } else {
            // sdk示例代码是用application context来创建infoWindow上的View的
            // 如果该view直接放在activity上，重复执行show/hide/show会出错
            View view = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.locatino_map_point_mp, null);
            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(marker.getTitle());
            int yOffset = (int)(0.5f+TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()));
            mapView.getMap().showInfoWindow(new InfoWindow(
                    BitmapDescriptorFactory.fromView(textView),
                    new LatLng(latitude, longitude), -yOffset, null));
            isInfoWindowShown = true;
        }
    }

    @OptionsItem(android.R.id.home)
    void close() {
        onBackPressed();
    }
}
