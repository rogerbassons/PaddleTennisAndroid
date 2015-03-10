package com.rogerbassonsrenart.paddletennis;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;


public class MainActivity extends Activity {

    GameView gv_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        gv_ = new GameView(this);
        setContentView(gv_);
    }
    @Override
    protected void onPause() {
        super.onPause();
        gv_.stopView();

    }
}
