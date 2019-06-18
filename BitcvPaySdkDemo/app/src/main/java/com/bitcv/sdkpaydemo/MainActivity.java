package com.bitcv.sdkpaydemo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.bitcv.bitcvsdkpay.BitcvBroadcast;
import com.bitcv.bitcvsdkpay.BitcvCallBack;
import com.bitcv.bitcvsdkpay.BitcvPayBean;
import com.bitcv.bitcvsdkpay.BitcvSdkReceiver;
import com.bitcv.bitcvsdkpay.SdkPayManager;


/**
 * 安卓开发由一个APP拉起另一个APP的方法总结
 * 转载请标明出处：http://blog.csdn.net/zang_chen/article/details/76677846
 * 本文出自陈小二来巡山的博客：https://blog.csdn.net/zang_chen
 */
public class MainActivity extends AppCompatActivity implements BitcvCallBack {


    private TextView tv_result;
    private BitcvSdkReceiver locationReceiver;
    private BitcvBroadcast broadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationReceiver = new BitcvSdkReceiver();
        locationReceiver.setBitcvSdkCallBack(this);
        broadcast = new BitcvBroadcast();
        broadcast.registerReceiver(this, locationReceiver);
        TextView textView = (TextView) findViewById(R.id.textView);
        tv_result = (TextView) findViewById(R.id.tv_result);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SdkPayManager.isApkInstalled(MainActivity.this, "bitcv.com.wallet")) {


                    long timeStampSec = System.currentTimeMillis() / 1000;
                    long expireTs = timeStampSec + 50000;

                    BitcvPayBean bitcvPayBean = new BitcvPayBean();
                    bitcvPayBean.setAppKey("your appkey");
                    bitcvPayBean.setAppName("我来自第三方");
                    bitcvPayBean.setOutTradeNo("TEST" + timeStampSec);
                    bitcvPayBean.setProductName("sdk支付测试-Android");
                    bitcvPayBean.setPayTokenId("280");
                    bitcvPayBean.setPayTokenSymbol("BOCAI");
                    bitcvPayBean.setPayAmount("0.01");
                    bitcvPayBean.setMemo("bcvtest1-Android");
                    bitcvPayBean.setRequestTs(timeStampSec + "");
                    bitcvPayBean.setExpireTs(expireTs + "");
                    bitcvPayBean.setSdkSign("your sign");

                    String s = SdkPayManager.SendBitcvSdkPay(MainActivity.this, bitcvPayBean);
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "未安装目标应用", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


//    public boolean isApkInstalled(Context context, String packageName) {
//        if (TextUtils.isEmpty(packageName)) {
//            return false;
//        }
//        try {
//            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
//            return true;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }


    @Override
    protected void onDestroy() {
        if (broadcast != null) {
            broadcast.unregisterReceiver(this, locationReceiver);
        }
        super.onDestroy();
    }

    @Override
    public void bitcvSdkCallBack(String rs) {
        tv_result.setText(rs);
    }


}
