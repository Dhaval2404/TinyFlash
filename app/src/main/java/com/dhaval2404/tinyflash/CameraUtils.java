package com.dhaval2404.tinyflash;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

/**
 * Created by DHAVAL on 9/25/2015.
 */
public class CameraUtils {
    private static Camera cam = null;

    public void flashLightOn(Context context) {
        try {
            if (hasFlashFeature(context)) {
                cam = Camera.open();
                Camera.Parameters p = cam.getParameters();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                cam.setParameters(p);
                cam.startPreview();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void flashLightOff(Context context) {
        try {
            if (hasFlashFeature(context) && cam != null) {
                cam.stopPreview();
                cam.release();
                cam = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean hasFlashFeature(Context context){
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

}
