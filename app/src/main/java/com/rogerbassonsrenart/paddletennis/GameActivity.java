package com.rogerbassonsrenart.paddletennis;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;


public class GameActivity extends Activity {

    Game g_;
    GameView gv_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        g_ = new Game();
        gv_ = new GameView(this, g_);
        setContentView(gv_);
    }
    @Override
    protected void onPause() {
        super.onPause();
        gv_.stopView();

    }

    @Override
    protected void onResume() {
        super.onPause();
        gv_ = new GameView(this, g_);
        setContentView(gv_);
    }
}
