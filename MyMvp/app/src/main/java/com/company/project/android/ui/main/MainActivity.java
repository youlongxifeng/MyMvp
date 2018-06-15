package com.company.project.android.ui.main;

import android.Manifest.permission;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.company.project.android.R;
import com.company.project.android.base.BaseActivity;
import com.company.project.android.bean.Gank;
import com.company.project.android.service.LocationUpdateService;
import com.company.project.android.ui.fragment.adsfragment.AdsFragment;
import com.company.project.android.utils.LogUtils;
import com.company.project.android.utils.NetWorkUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Log.i("TAG", "location====");
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
        Log.i("TAG", "location====" + location);
        if (location != null) {
            Log.i("TAG", "location====" + location.getLatitude() + "   经度：" + location.getLongitude());
        }

        getPhotoLocation(Environment.getExternalStorageDirectory() + File.separator + "a3.jpg");
        showDialog();

        PowerManager  mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        WakeLock mIncallWakeLock = mPowerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.SCREEN_DIM_WAKE_LOCK, "incall");
        if (mIncallWakeLock != null && mIncallWakeLock.isHeld()) {
            mIncallWakeLock.release();
        }
        }

    /**
     * 通过一张图片查询拍照地理位置
     *
     * @param imagePath
     */
    public void getPhotoLocation(String imagePath) {
        File file = new File(imagePath);
        LogUtils.i("getPhotoLocation=" + file.getAbsolutePath() + "  =" + file.exists());
        if (file.exists()) {
            try {
                ExifInterface exifInterface = new ExifInterface(imagePath);
                String datetime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);// 拍摄时间
                String deviceName = exifInterface.getAttribute(ExifInterface.TAG_MAKE);// 设备品牌
                String deviceModel = exifInterface.getAttribute(ExifInterface.TAG_MODEL); // 设备型号
                String latValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                String lngValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                String latRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
                String lngRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
                LogUtils.i("output1=" + " latValue=" + latValue + "  latRef=" + latRef);
                LogUtils.i("output2=" + " lngValue=" + lngValue + "  latRef=" + lngRef);
                float output1 = convertRationalLatLonToFloat(latValue, latRef);
                float output2 = convertRationalLatLonToFloat(lngValue, lngRef);
                LogUtils.i("output1=" + output1 + " latValue=" + latValue + "  latRef=" + latRef);
                LogUtils.i("output2=" + output2 + " lngValue=" + lngValue + "  latRef=" + lngRef);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private static float convertRationalLatLonToFloat(
            String rationalString, String ref) {
        String[] parts = rationalString.split(",");
        String[] pair;
        pair = parts[0].split("/");
        double degrees = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());
        pair = parts[1].split("/");
        double minutes = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());
        pair = parts[2].split("/");
        double seconds = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());
        double result = degrees + (minutes / 60.0) + (seconds / 3600.0);
        if ((ref.equals("S") || ref.equals("W"))) {
            return (float) -result;
        }
        return (float) result;
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

        LogUtils.i("============initDate=" + NetWorkUtil.connection(this)+"   ="+NetWorkUtil.isNetworkAvailable(this));
        startService(new Intent(this, LocationUpdateService.class));//启动位置定位服务

        new Thread(new Runnable() {
            @Override
            public void run() {
               String group= getOuterNetFormCmyIP();
                LogUtils.i("group========="+group);
            }
        }).start();
       // setScreenBrightness(1);
    }

    /**
     * 设置屏幕的亮度
     */
    private void setScreenBrightness(int process) {

        //设置当前窗口的亮度值.这种方法需要权限android.permission.WRITE_EXTERNAL_STORAGE
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        float f = process / 255.0F;
        localLayoutParams.screenBrightness = f;
        getWindow().setAttributes(localLayoutParams);
        //修改系统的亮度值,以至于退出应用程序亮度保持
        saveBrightness(getContentResolver(),process);

    }
    public static void saveBrightness(ContentResolver resolver, int brightness) {
        //改变系统的亮度值
        //这里需要权限android.permission.WRITE_SETTINGS
        //设置为手动调节模式
        Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        //保存到系统中
        Uri uri = android.provider.Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
        android.provider.Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
        resolver.notifyChange(uri, null);
    }

    /**
     * 通过CmyIP获取获取外网外网地址  需在异步线程中访问
     * @return 外网IP
     */
    public static String getOuterNetFormCmyIP() {
        String response =  GetOuterNetIp("https://cmyip.com/");
        LogUtils.i("group========response="+response);
        Pattern pattern = Pattern
                .compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
        Matcher matcher = pattern.matcher(response.toString());
        if (matcher.find()) {
            String group = matcher.group();
            LogUtils.i("group========="+group);
            return group;
        }

        return null;
    }

    /**
     * 获取获取外网外网地址  需在异步线程中访问
     * @param ipaddr 提供外网服务的服务器ip地址
     * @return       外网IP
     */
    public static String GetOuterNetIp(String ipaddr) {
        URL infoUrl = null;
        InputStream inStream = null;
        try {
            infoUrl = new URL(ipaddr);
            URLConnection connection = infoUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
                StringBuilder strber = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                    strber.append(line + "\n");
                inStream.close();
                return strber.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


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
