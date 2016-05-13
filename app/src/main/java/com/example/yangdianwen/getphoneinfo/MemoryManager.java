package com.example.yangdianwen.getphoneinfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.widget.Toast;

/**
 *
 */
public class MemoryManager {


    public static String getPhoneInSDCardPath() {
        String sdcardState = Environment.getExternalStorageState();
        if (!sdcardState.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        return Environment.getExternalStorageDirectory()
                .getAbsolutePath();
    }


    public static String getPhoneOutSDCardPath() {
        Map<String, String> map = System.getenv();
        if (map.containsKey("SECONDARY_STORAGE")) {
            String paths = map.get("SECONDARY_STORAGE");
            // /storage/extSdCard
            String path[] = paths.split(":");
            if (path.length <= 0) {
                return null;
            }
            return path[0];
        }
        return null;
    }

    /**
     *
     */
    public static long getPhoneOutSDCardSize(Context context) {
        try {
            File path = new File(getPhoneOutSDCardPath());
            if (path == null) {
                return 0;
            }
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long blockCount = stat.getBlockCount();
            return blockCount * blockSize;
        } catch (Exception e) {
            Toast.makeText(context, "", Toast.LENGTH_SHORT)
                    .show();
            return 0;
        }
    }

    /**
     *
     */
    public static long getPhoneOutSDCardFreeSize(Context context) {
        try {
            File path = new File(getPhoneOutSDCardPath());
            if (path == null) {
                return 0;
            }
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availaBlock = stat.getAvailableBlocks();
            return availaBlock * blockSize;
        } catch (Exception e) {
            Toast.makeText(context, "", Toast.LENGTH_SHORT)
                    .show();
            return 0;
        }
    }

    /**
     *
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getPhoneSelfSize() {
        File path = Environment.getRootDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long blockCount = stat.getBlockCountLong();
        long rootFileSize = blockCount * blockSize;

        path = Environment.getDownloadCacheDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSizeLong();
        blockCount = stat.getBlockCountLong();
        long cacheFileSize = blockCount * blockSize;

        return rootFileSize + cacheFileSize;
    }

    /**
     *
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getPhoneSelfFreeSize() {

        File path = Environment.getRootDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long blockCount = stat.getAvailableBlocksLong();
        long rootFileSize = blockCount * blockSize;

        path = Environment.getDownloadCacheDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSizeLong();
        blockCount = stat.getAvailableBlocksLong();
        long cacheFileSize = blockCount * blockSize;

        return rootFileSize + cacheFileSize;
    }

    /**
     *
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getPhoneSelfSDCardSize() {
        String sdcardState = Environment.getExternalStorageState();
        if (!sdcardState.equals(Environment.MEDIA_MOUNTED)) {
            return 0;
        }
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long blockCount = stat.getBlockCountLong();
        return blockCount * blockSize;
    }

    /**
     *
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getPhoneSelfSDCardFreeSize() {
        String sdcardState = Environment.getExternalStorageState();
        if (!sdcardState.equals(Environment.MEDIA_MOUNTED)) {
            return 0;
        }
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availaBlock = stat.getAvailableBlocksLong();
        return availaBlock * blockSize;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getPhoneAllSize() {
        File path = Environment.getRootDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long blockCount = stat.getBlockCountLong();
        long rootFileSize = blockCount * blockSize;

        path = Environment.getDataDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSizeLong();
        blockCount = stat.getBlockCountLong();
        long dataFileSize = blockCount * blockSize;

        path = Environment.getDownloadCacheDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSizeLong();
        blockCount = stat.getBlockCountLong();
        long cacheFileSize = blockCount * blockSize;

        return rootFileSize + dataFileSize + cacheFileSize;
    }


    public static long getPhoneAllFreeSize() {
        File path = Environment.getRootDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long blockCount = stat.getAvailableBlocks();
        long rootFileSize = blockCount * blockSize;

        path = Environment.getDataDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSize();
        blockCount = stat.getAvailableBlocks();
        long dataFileSize = blockCount * blockSize;

        path = Environment.getDownloadCacheDirectory();
        stat = new StatFs(path.getPath());
        blockSize = stat.getBlockSize();
        blockCount = stat.getAvailableBlocks();
        long cacheFileSize = blockCount * blockSize;

        return rootFileSize + dataFileSize + cacheFileSize;
    }


    public static long getPhoneFreeRamMemory(Context context) {
        MemoryInfo info = new MemoryInfo();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        am.getMemoryInfo(info);
        return info.availMem;
    }


    public static long getPhoneTotalRamMemory() {
        try {
            FileReader fr = new FileReader("/proc/meminfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split("\\s+");
            return Long.valueOf(array[1]) * 1024; // 鍘熶负kb, 杞负b
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
