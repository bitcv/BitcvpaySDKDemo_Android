package com.bitcv.sdkpaydemo.bitcvapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;


import com.bitcv.bitcvsdkpay.BitCvSdkConst;
import com.bitcv.bitcvsdkpay.SdkPayManager;

/**
 * 安卓开发由一个APP拉起另一个APP的方法总结
 * 转载请标明出处：http://blog.csdn.net/zang_chen/article/details/76677846
 * 本文出自陈小二来巡山的博客：https://blog.csdn.net/zang_chen
 */
public class ResultActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String result = getIntent().getStringExtra(BitCvSdkConst.SDK_RESULT);
        if (!TextUtils.isEmpty(result)) {
            SdkPayManager.sendBroadcast(ResultActivity.this, result);
        }
        finish();

    }


}
