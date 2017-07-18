package com.project.rdc.onehelp.ui;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.project.rdc.onehelp.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.ArrayList;
import java.util.List;

import com.project.rdc.onehelp.adapter.LocationListAdapter;


/**
 * Created by Administrator on 2016/11/15.
 */

public class PublishLocationActivity extends Activity {

    public static final String action = "chooseLocation";

    private LatLng currentLL;
    private PoiInfo poiInfo;
    private static MapView mapView;
    private BaiduMap baiduMap;
    private MapStatusUpdate mapStatusUpdate;
    private ListView listview;
    protected List<PoiInfo> datas;
    private LocationListAdapter locationListAdapter;
    private GeoCoder geoCoder;
    private PoiSearch poiSearch;
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private ImageView backButton;
    private String city;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplication());
        setContentView(R.layout.activity_publish_location);
        init();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void init() {
        listview = (ListView) findViewById(R.id.map_list);
        datas = new ArrayList<PoiInfo>();

        backButton = (ImageView) findViewById(R.id.iv_publishLocation_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mapView = (MapView) findViewById(R.id.mapview);
        int count = mapView.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = mapView.getChildAt(i);
            if (child instanceof ImageView) {
                child.setVisibility(View.INVISIBLE);
            }
        }
        baiduMap = mapView.getMap();
        baiduMap.setOnMapStatusChangeListener(new MyMapStatusChangeListener());

        geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(new MyGetGeoCodeResultListener());

        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(new MyGetPoiSearchResultListener());

        locationClient = new LocationClient(this);
        locationClient.registerLocationListener(new MyLocationListener());

        locationClientOption = new LocationClientOption();
        locationClientOption.setOpenGps(true);
        locationClientOption.setCoorType("gcj02");
        locationClientOption.setIsNeedAddress(true);
        locationClientOption.setScanSpan(10000);
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        locationClient.setLocOption(locationClientOption);
        locationClient.start();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("PublishLocation Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null) {
                return;
            }
            if (poiInfo != null) {
                return;
            }
            poiInfo = new PoiInfo();
            baiduMap.clear();
            LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            poiInfo.location = latLng;
            poiInfo.address = bdLocation.getAddrStr();
            poiInfo.name = "位置";
            LatLng ll = new LatLng(bdLocation.getLatitude() - 0.0002, bdLocation.getLongitude());
            CoordinateConverter converter = new CoordinateConverter();
            converter.coord(ll);
            converter.from(CoordinateConverter.CoordType.COMMON);
            LatLng convertlatlng = converter.convert();
            OverlayOptions myself = new MarkerOptions().position(convertlatlng).icon(
                    BitmapDescriptorFactory.fromResource(R.drawable.publish_local)).zIndex(4).draggable(false);
            baiduMap.addOverlay(myself);
            mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(convertlatlng, 17.0f);
            baiduMap.animateMapStatus(mapStatusUpdate);
            city = bdLocation.getCity();
        }
    }

    private class MyGetPoiSearchResultListener implements OnGetPoiSearchResultListener {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            //Toast.makeText(LocationActivity.this,poiResult.getAllPoi().get(1).address,Toast.LENGTH_LONG).show();
            datas.clear();
            datas.addAll(poiResult.getAllPoi());
            locationListAdapter = new LocationListAdapter(PublishLocationActivity.this, datas, R.layout.publish_maplist_item);
            listview.setAdapter(locationListAdapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(action);
                    intent.putExtra("city",city);
                    intent.putExtra("location", datas.get(position).address);
                    sendBroadcast(intent);
                    finish();
                }
            });
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

    }

    private class MyGetGeoCodeResultListener implements OnGetGeoCoderResultListener {

        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
            if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                return;
            }
            poiInfo.location = reverseGeoCodeResult.getLocation();
            poiInfo.address = reverseGeoCodeResult.getAddress();
            poiInfo.name = "位置";
            datas.add(poiInfo);
            //Toast.makeText(LocationActivity.this,poiInfo.address,Toast.LENGTH_LONG).show();
        }
    }

    private class MyMapStatusChangeListener implements BaiduMap.OnMapStatusChangeListener {

        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChange(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus) {
            currentLL = mapStatus.target;
            geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(currentLL));
            poiSearch.searchNearby(new PoiNearbySearchOption().keyword("小区").location(currentLL).radius(10000));
        }
    }
}
