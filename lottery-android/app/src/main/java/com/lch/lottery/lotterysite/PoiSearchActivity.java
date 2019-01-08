package com.lch.lottery.lotterysite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.bai.mapapi.overlayutil.PoiOverlay;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.lch.lottery.R;
import com.lch.lottery.util.MapUtils;


/**
 * 演示poi搜索功能
 */
public class PoiSearchActivity extends FragmentActivity implements
        OnGetPoiSearchResultListener {

    private PoiSearch mPoiSearch = null;
    private BaiduMap mBaiduMap = null;
    /**
     * 搜索关键字输入窗口
     */
    private int loadIndex = 0;

    private static final int RADIUS = 5000;
    private static final int PAGE_SIZE = 50;
    private LocationClient mLocationClient;
    private BDLocation mBDLocation;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poisearch);
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        mBaiduMap = ((SupportMapFragment) (getSupportFragmentManager()
                .findFragmentById(R.id.map))).getBaiduMap();

        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                mBDLocation = bdLocation;
                searchNearbyProcess();
            }
        });

        mLocationClient.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPoiSearch.destroy();
        mLocationClient.stop();
        super.onDestroy();
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    public void searchNearbyProcess() {
        if (mBDLocation == null) {
            Toast.makeText(PoiSearchActivity.this, "定位失败！请重试", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        com.apkfuns.logutils.LogUtils.e("LOC:"+mBDLocation.getLatitude());

        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption()
                .keyword("彩票")
                .sortType(PoiSortType.distance_from_near_to_far)
                .location(new LatLng(mBDLocation.getLatitude(), mBDLocation.getLongitude()))
                .scope(1)
                .radius(RADIUS).pageNum(loadIndex).pageCapacity(PAGE_SIZE);

        mPoiSearch.searchNearby(nearbySearchOption);
    }

    public void goToNextPage(View v) {
        loadIndex++;
    }


    /**
     * 获取POI搜索结果，包括searchInCity，searchNearby，searchInBound返回的搜索结果
     *
     * @param result
     */
    public void onGetPoiResult(PoiResult result) {
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(PoiSearchActivity.this, "未找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            mBaiduMap.clear();
            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result);
            overlay.addToMap();
            overlay.zoomToSpan();

            if (mBDLocation != null) {
                showNearbyArea(new LatLng(mBDLocation.getLatitude(), mBDLocation.getLongitude()), RADIUS);
            }

            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            Toast.makeText(PoiSearchActivity.this, strInfo, Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * 获取POI详情搜索结果，得到searchPoiDetail返回的搜索结果
     *
     * @param result
     */
    public void onGetPoiDetailResult(final PoiDetailResult result) {
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(PoiSearchActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("是否开始导航？目的地：" + result.getName() + "(" + result.getAddress() + ")")
                    .setTitle("提示")
                    .setPositiveButton("开始导航", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mBDLocation == null) {
                                Toast.makeText(PoiSearchActivity.this, "定位失败！请重试", Toast.LENGTH_LONG)
                                        .show();
                                return;
                            }
                            MapUtils.NavPlace from = new MapUtils.NavPlace().name("当前位置").latLng(new LatLng(mBDLocation.getLatitude(), mBDLocation.getLongitude()));
                            MapUtils.NavPlace to = new MapUtils.NavPlace().name(result.getName() + "-" + result.getAddress()).latLng(result.getLocation());

                            boolean isSuccess = MapUtils.baiduMapNavigate(from, to, getApplicationContext());
                            if (!isSuccess) {
                                MapUtils.gaodeiMapNavigate(to, getApplicationContext());
                            }


                        }
                    }).show();

        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }


    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            // }
            return true;
        }
    }

    /**
     * 对周边检索的范围进行绘制
     *
     * @param center
     * @param radius
     */
    public void showNearbyArea(LatLng center, int radius) {
        BitmapDescriptor centerBitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_geo);
        MarkerOptions ooMarker = new MarkerOptions().position(center).icon(centerBitmap);
        mBaiduMap.addOverlay(ooMarker);

//        OverlayOptions ooCircle = new CircleOptions().fillColor(0xCCCCCC00)
//                .center(center).stroke(new Stroke(5, 0xFFFF00FF))
//                .radius(radius);
//        mBaiduMap.addOverlay(ooCircle);
    }


}
