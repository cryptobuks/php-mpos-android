package com.kosherbacon.mmcfe_ng;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.FontAwesomeIcon;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.io.IOException;

public class BaseActivity extends SlidingFragmentActivity {

    private int mTitleRes;
    protected ListFragment mFrag;

    protected static SlidingMenu sm;

    public BaseActivity(int titleRes) {
        mTitleRes = titleRes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(mTitleRes);

        // set the Behind View
        setBehindContentView(R.layout.menu_frame);
        if (savedInstanceState == null) {
            FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
            mFrag = new ServerListFragment();
            t.replace(R.id.menu_frame, mFrag);
            t.commit();
        } else {
            mFrag = (ListFragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
        }

        // customize the SlidingMenu
        sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeDegree(0.35f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        //getSlidingMenu().setSecondaryMenu(R.layout.menu_frame_two);
        //getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadow);
        //getSupportFragmentManager()
        //        .beginTransaction()
        //        .replace(R.id.menu_frame_two, new WorkerListFragment())
        //        .commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadPreferences();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void loadPreferences() {
        try {
            Preferences.servers = Preferences.fromString(Preferences.loadPreferences(Preferences.toString(Preferences.servers), getApplicationContext()));
            Log.w("12345", "Loaded Preferences");
            Log.w("12345", "" + Preferences.servers.size());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

/*    public void savePreferences() {
        try {
            Preferences.savePreferences(Preferences.toString(Preferences.servers), getApplicationContext());
            Log.w("12345", "Saved Preferences");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toggle();
                return true;
            case R.id.addServer:
                Intent addServer =  new Intent(this, AddServerActivity.class);
                this.startActivity(addServer);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            case R.id.expand_currencies_button:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        IconicFontDrawable iconicFontDrawable = new IconicFontDrawable(getApplicationContext());
        iconicFontDrawable.setIcon(FontAwesomeIcon.MONEY);
        iconicFontDrawable.setIconColor(Color.GREEN);
        getSupportMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}