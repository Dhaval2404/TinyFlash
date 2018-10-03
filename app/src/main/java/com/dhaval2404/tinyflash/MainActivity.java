package com.dhaval2404.tinyflash;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements View.OnClickListener {

    private ImageView flashBtn;
    private RelativeLayout flashBtnLyt;
    private boolean flashInitialStatus = false;
    private CameraUtils cameraUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flashBtnLyt = findViewById(R.id.rl_flash);
        flashBtnLyt.setOnClickListener(this);

        flashBtn = findViewById(R.id.btn_flash);

        cameraUtils = new CameraUtils();
        setFlashStatus(false);
    }

    @Override
    public void onClick(View v) {
        if(isPermissionGranted()) {
            toggleFlash();
        }else{
            requestPermission();
        }
    }

    private void toggleFlash(){
        setFlashStatus(!flashInitialStatus);
        new Handler(). postDelayed(new Runnable() {
            @Override
            public void run() {
                if(flashInitialStatus) {
                    cameraUtils.flashLightOff(MainActivity.this);
                } else{
                    cameraUtils.flashLightOn(MainActivity.this);
                }
                flashInitialStatus = !flashInitialStatus;
            }
        }, 50);
    }

    private void setFlashStatus(boolean status){
        if(status){
            flashBtn.setColorFilter(getResources().getColor(R.color.flash_on_img), PorterDuff.Mode.SRC_ATOP);
            flashBtnLyt.setBackgroundColor(getResources().getColor(R.color.flash_on_lyt));
        }else{
            flashBtn.setColorFilter(getResources().getColor(R.color.flash_off_img), PorterDuff.Mode.SRC_ATOP);
            flashBtnLyt.setBackgroundColor(getResources().getColor(R.color.flash_off_lyt));
        }
    }

    @Override
    protected void onDestroy() {
        cameraUtils.flashLightOff(this);
        super.onDestroy();
    }

    private void requestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
        }else{
            toggleFlash();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(isPermissionGranted()){
            toggleFlash();
        }
    }

    private boolean isPermissionGranted(){
        String permission = Manifest.permission.CAMERA;
        int res = checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

}
