package com.company.project.android.ui.main;

import android.Manifest.permission;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;

import com.company.project.android.R;
import com.company.project.android.base.BaseActivity;
import com.company.project.android.bean.Gank;
import com.company.project.android.ui.fragment.adsfragment.AdsFragment;
import com.company.project.android.utils.LogUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;


public class MainActivity extends BaseActivity<MainPresenter>
        implements MainContract.View {

    @BindView(R.id.return_value_name)
    TextView mRvNameTv;

    @Override
    public MainPresenter setPresenter() {
        return new MainPresenter();
    }

    @Override
    public int getlayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        Log.i("TAG","location====");
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Log.i("TAG","location===="+location);
        if(location!=null)
        Log.i("TAG","location===="+location.getLatitude()+ "   经度："+location.getLongitude());

    }

    @Override
    public void initDate() {
        mPresenter.getGank();
        Map<String, String> map = new HashMap<>();
        mPresenter.login(map);

        AdsFragment adsFragment = new AdsFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.context_fragment, adsFragment);
        //设置简单的过度动画
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        Camera    mCamera;
        boolean face=true;
        for (int i = 0, count = Camera.getNumberOfCameras(); i < count; i++) {
            if ( face) {
                mCamera = Camera.open(CameraInfo.CAMERA_FACING_FRONT);
                mCameraInfo.facing = 1;
                Camera.getCameraInfo(CameraInfo.CAMERA_FACING_FRONT, mCameraInfo);
                face=false;
            } else {
                face=true;
                //mCamera = Camera.open(CameraInfo.CAMERA_FACING_BACK);
                mCameraInfo.facing = 0;
                Camera.getCameraInfo(CameraInfo.CAMERA_FACING_BACK, mCameraInfo);
            }
            Camera.getCameraInfo(i, mCameraInfo);
            LogUtils.i("TAG","mCameraInfo=="+mCameraInfo.facing);

        }

    }
    private final Camera.CameraInfo mCameraInfo = new Camera.CameraInfo();




    @Override
    public void setLogin(String active) {
        mRvNameTv.setText(active);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void onSucceed(Gank data) {

    }

    @Override
    public void onFail(String err) {

    }

    @Override
    public void hideDialog() {

    }

    /**
     * 安卓Handler当做内部类，导致内存泄露的问题 自己来记录
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null) {
                // ...
            }
        }
    }

    private final MyHandler mHandler = new MyHandler(this);

    /**
     * Instances of anonymous classes do not hold an implicit
     * reference to their outer class when they are "static".
     */
    private static final Runnable sRunnable = new Runnable() {
        @Override
        public void run() { /* ... */ }
    };


}
