package com.dhaval2404.tinyflash;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by DHAVAL on 9/25/2015.
 */
public class Utils {

    public static boolean hasFlashFeature(Context context){
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

}
