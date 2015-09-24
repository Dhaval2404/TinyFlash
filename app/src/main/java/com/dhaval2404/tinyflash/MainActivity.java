package com.dhaval2404.tinyflash;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements View.OnClickListener {

    private ImageView flashBtn;
    private RelativeLayout flashBtnLyt;
    private boolean flashInitialStatus = true;
    private CameraUtils cameraUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flashBtnLyt = (RelativeLayout) findViewById(R.id.rl_flash);
        flashBtnLyt.setOnClickListener(this);

        flashBtn = (ImageView) findViewById(R.id.btn_flash);
        flashBtn.setImageResource(R.drawable.ic_flash_white);

        cameraUtils = new CameraUtils();
        setFlashStatus(flashInitialStatus);
        cameraUtils.flashLightOn(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
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

}
