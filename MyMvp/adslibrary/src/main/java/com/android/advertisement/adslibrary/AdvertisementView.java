package com.android.advertisement.adslibrary;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.doordu.android.cachewebview.CacheWebView;

import java.io.File;

/**
 * @author Administrator
 * @name MyMvp
 * @class name：com.android.advertisement.adslibrary
 * @class describe
 * @time 2018/2/26 14:01
 * @change
 * @class describe
 */

public class AdvertisementView extends FrameLayout {
    public AdvertisementView(@NonNull Context context) {
        super(context);
        initUIView(context);
    }

    public AdvertisementView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUIView(context);
    }

    public AdvertisementView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUIView(context);
        CacheWebView.getCacheConfig().init(context.getApplicationContext(), h5Cache().getAbsolutePath(),1024*1024*50,1024*1024*10)
                    .enableDebug(true);
    }
    private static final String CACHE_NAME = "doordu/html5";
    public static File h5Cache(){
        return  new File(Environment.getExternalStorageDirectory(), CACHE_NAME);
    }


    /**
     * 初始化广告UI
     *
     * @param context
     */
    private void initUIView(Context context) {
        FrameLayout frame = new FrameLayout(context);
        final int m = ViewGroup.LayoutParams.MATCH_PARENT;
        ViewGroup.LayoutParams layout = new ViewGroup.LayoutParams(m, m);
        frame.setLayoutParams(layout);

        ImageView mDefImage = new ImageView(context);
        mDefImage.setLayoutParams(layout);
        mDefImage.setScaleType(ScaleType.CENTER_CROP);
        mDefImage.setImageResource(R.drawable.aa);
        frame.addView(mDefImage);
        addView(frame);
        initWebView(context);
    }

    private void initWebView(Context context) {
        FrameLayout frame = new FrameLayout(context);
        final int m = ViewGroup.LayoutParams.MATCH_PARENT;
        ViewGroup.LayoutParams layout = new ViewGroup.LayoutParams(m, m);
        frame.setLayoutParams(layout);
        frame.setVisibility(GONE);

        CacheWebView  mWebView = new CacheWebView(context);
        mWebView.setLayoutParams(layout);
      //  initWebSettings();

        frame.addView(mWebView);
      //  mWebviewFrame = frame;
        addView(frame);
    }
}
