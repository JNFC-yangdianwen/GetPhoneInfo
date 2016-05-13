package com.example.yangdianwen.getphoneinfo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtn;
    private Button mBtn_cpu;
    private Button mBtn_wifi;
    private Button mBtn_dp;
    private Button mBtn_back;
    private Button mBtn_memory;
    private Button mBtn_root;
    private Button mBtn_camare;
    private TextView mTv_text;
    private Context mContext = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn = (Button) findViewById(R.id.btn);
        mBtn_cpu = (Button) findViewById(R.id.btn_cpu);
        mBtn_wifi = (Button) findViewById(R.id.btn_wifi);
        mBtn_dp = (Button) findViewById(R.id.btn_dp);
        mBtn_memory = (Button) findViewById(R.id.btn_memory);
        mBtn_root = (Button) findViewById(R.id.btn_root);
        mBtn_camare = (Button) findViewById(R.id.btn_camare);
        mBtn_back = (Button) findViewById(R.id.btn_back);
        mTv_text = (TextView) findViewById(R.id.tv);
        mBtn_back.setOnClickListener(this);
        mBtn.setOnClickListener(this);
        mBtn_cpu.setOnClickListener(this);
        mBtn_wifi.setOnClickListener(this);
        mBtn_dp.setOnClickListener(this);
        mBtn_memory.setOnClickListener(this);
        mBtn_root.setOnClickListener(this);
//        mBtn_camare.setOnClickListener(this);

    }

    @Override
    public void onClick(final View v) {
        mBtn.setVisibility(View.INVISIBLE);
        mBtn_dp.setVisibility(View.INVISIBLE);
        mBtn_cpu.setVisibility(View.INVISIBLE);
        mBtn_wifi.setVisibility(View.INVISIBLE);
        mBtn_memory.setVisibility(View.INVISIBLE);
        mBtn_root.setVisibility(View.INVISIBLE);
        mBtn_camare.setVisibility(View.INVISIBLE);
        mBtn_back.setVisibility(View.VISIBLE);
        mTv_text.setVisibility(View.VISIBLE);

        switch (v.getId()) {
            case R.id.btn:
                TelephonyManager telManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                int networkType = telManager.getNetworkType();
                 String subscriberId = telManager.getSubscriberId();
                String deviceId = telManager.getDeviceId();
                String line1Number = telManager.getLine1Number();
                int dataState = telManager.getDataState();
                 String simOperatorName = telManager.getSimOperatorName();
                int phoneType = telManager.getPhoneType();
                String radioVersion = Build.getRadioVersion();
                String simCountryIso = telManager.getSimCountryIso();
                String baseOs = Build.VERSION.BASE_OS;
                int sdkInt = Build.VERSION.SDK_INT;
                String release = Build.VERSION.RELEASE;
                String s = "手机IMEI:" + deviceId
                        + "\n手机号码:" + line1Number
                        + "\n网络类型:" + networkType
                        + "\n数据状态:" + dataState
                        + "\n基带版本:" + radioVersion
                        + "\n操作系统:" + baseOs
                        + "\nsdk:" + sdkInt
                        +"\nSIM提供商的国家代码:"+simCountryIso
                        +"\n设备运营商:"+simOperatorName
                        +"\n移动终端的类型:"+phoneType
                        +"\n手机IMSI:"+subscriberId
                        + "\n系统版本号:" + release;
                mTv_text.setText(s);

                break;
            case R.id.btn_cpu:
                File dir = new File("/sys/devices/system/cpu");
                File[] files = dir.listFiles(new CpuFilter());
                int length = files.length;
                mTv_text.setText("CPU数量:" + length);
                break;
            case R.id.btn_wifi:
                WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String bssid = wifiInfo.getBSSID();
                boolean hiddenSSID = wifiInfo.getHiddenSSID();
                int networkId = wifiInfo.getNetworkId();
                String ssid = wifiInfo.getSSID();
                int ipAddress = wifiInfo.getIpAddress();
                String macAddress = wifiInfo.getMacAddress();
                int rssi = wifiInfo.getRssi();
                int linkSpeed = wifiInfo.getLinkSpeed();
                String ss = "BSSID:" + bssid
                        +"\nSSID是否被隐藏:"+hiddenSSID
                        + "\n网络id:" + networkId
                        + "\nIP地址:" + ipAddress
                        +"\n物理MAC地址:"+macAddress
                        +"\n802.11n网络信号:"+rssi
                        +"\nSSID:"+ssid
                        + "\n网速:" + linkSpeed;
                mTv_text.setText(ss);
                break;
            case R.id.btn_dp:
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                DisplayMetrics dm2 = getResources().getDisplayMetrics();
                //获取屏幕默认分辨率
                Display d = getWindowManager().getDefaultDisplay();
                int heightPixels = dm.heightPixels;
                int widthPixels = dm.widthPixels;
                String dp = "屏幕宽度:" + widthPixels
                        + "\n屏幕高度:" + heightPixels
                        + "\n分辨率:" + widthPixels
                        + "*" + heightPixels;
                mTv_text.setText(dp);
                break;
            case R.id.btn_memory:

                ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
                ActivityManager manager = (ActivityManager) mContext.getSystemService(
                        ACTIVITY_SERVICE);
                manager.getMemoryInfo(info);
                long availMem = info.availMem / 1024 / 1024;
                String sdCardPath = MemoryManager.getPhoneInSDCardPath();
                long phoneSelfSize = MemoryManager.getPhoneSelfSize() / 1024 / 1024; // 1397428224 byte鍊?/1024 kb /1024 mb
//                long mb = phoneSelfSize / 1024 / 1024;
                long phoneSelfFreeSize = MemoryManager.getPhoneSelfFreeSize() / 1024 / 1024;
                float phoneSize = MemoryManager.getPhoneSelfSDCardSize() / 1024 / 1024; // 鎵嬫満鍐呴儴鍌ㄥ瓨 涓嶆槸SD鍗?                Log.d(TAG, "phoneSize: " + (phoneSize / 1024 / 1024 / 1024));  // 5204983808  绾︾瓑浜?G
                //  4963MB 4.847519GB
                float phoneFreeSize = MemoryManager.getPhoneSelfSDCardFreeSize() / 1024 / 1024; // 绾?00MB
                long phoneAllSize = MemoryManager.getPhoneAllSize() / 1024 / 1024;
                long phoneAllFreeSize = MemoryManager.getPhoneAllFreeSize() / 1024 / 1024;
                String s1 = "总内存:" + availMem
                        + "\nSD卡路径:" + sdCardPath
                        + "\n机身内存:" + phoneSelfSize
                        + "\n" + phoneSelfFreeSize
                        + "\n" + phoneSize
                        + "\n手机可用内存" + phoneFreeSize
                        + "\n剩余内存:" + phoneAllFreeSize
                        + "\n" + phoneAllSize;
                mTv_text.setText(s1);
                break;
            case R.id.btn_root:
                boolean root = isRoot();
                mTv_text.setText("root");
                break;
            case R.id.btn_camare:

                Camera camera = Camera.open();
                Camera.Parameters parameters = camera.getParameters();
                List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
                Camera.Size sss = null;
                for (Camera.Size size : sizes) {
                    if (sss == null) {
                        sss = size;
                    } else if (sss.width * sss.height < size.width * size.height) {
                        sss = size;
                    }
                }
                camera.release();
                int S=  sss.width + sss.height; // 3264px---2448px 1920*1080
                mTv_text.setText(S);
                break;
            case R.id.btn_back:
                mBtn.setVisibility(View.VISIBLE);
                mBtn_dp.setVisibility(View.VISIBLE);
                mBtn_cpu.setVisibility(View.VISIBLE);
                mBtn_wifi.setVisibility(View.VISIBLE);
                mBtn_memory.setVisibility(View.VISIBLE);
                mBtn_root.setVisibility(View.VISIBLE);
                mBtn_camare.setVisibility(View.VISIBLE);
                mBtn_back.setVisibility(View.INVISIBLE);
                mTv_text.setVisibility(View.INVISIBLE);
                break;

        }

    }

    private class CpuFilter implements FileFilter {

        @Override
        public boolean accept(File pathname) {
            if (Pattern.matches("(cpu[0-9])", pathname.getName())) {
                return true;
            }
            return false;
        }
    }

    public boolean isRoot() {
        boolean bool = false;
        try {
            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
                bool = false;
            } else {
                bool = true;
            }
        } catch (Exception e) {
        }
        return bool;
    }


}
