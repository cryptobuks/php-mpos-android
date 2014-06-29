package com.kosherbacon.mmcfe_ng;

import android.content.Context;
import android.os.Bundle;
import android.widget.ViewFlipper;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MyActivity extends BaseActivity {

    public MyActivity() {
        super(R.string.main_title);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSlidingMenu().setMode(SlidingMenu.LEFT);
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        setContentView(R.layout.main);

        //getSupportFragmentManager()
        //        .beginTransaction()
        //        .replace(R.id.content_frame, new SampleListFragment())
        //        .commit();
    }

    @Override
    public void onBackPressed() {
        if (getSlidingMenu().isMenuShowing()) {
            getSlidingMenu().showContent();
        } else {
            super.onBackPressed();
        }
    }
}