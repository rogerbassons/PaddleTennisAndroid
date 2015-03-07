package com.rogerbassonsrenart.paddletennis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

/**
 * Created by roger on 07/03/15.
 */
public class GameThread extends Thread {
    private GameView gv_;
    private boolean run_;
    private SurfaceHolder sh_;

    private SquareBall b_;
    private Paddle rightPaddle_;
    private Paddle leftPaddle_;
    private int hits_;

    private float rightPaddleTouch;


    public void setRunning(boolean running) {
        run_ = running;
    }

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        sh_ = surfaceHolder;
        gv_ = gameView;
    }

    @Override
    public void run() {
        initializeObjects();
        while (run_) {
            update();
            Canvas c = sh_.lockCanvas();
            draw(c);
            sh_.unlockCanvasAndPost(c);
        }
    }

    private void initializeObjects() {
        hits_ = 0;
        int viewWidth = gv_.getWidth();
        int viewHeight = gv_.getHeight();

        rightPaddleTouch = -1;

        int paddleHeight = viewHeight/4;
        int paddleWidth = paddleHeight/6;

        rightPaddle_ = new Paddle(paddleWidth,paddleHeight,10);
        rightPaddle_.setRight(viewWidth, viewHeight);

        leftPaddle_ = new Paddle(paddleWidth,paddleHeight,10);
        leftPaddle_.setLeft(viewWidth, viewHeight);

        b_ = new SquareBall(paddleHeight/5);
        b_.center(viewWidth, viewHeight);
    }



    private void update() {
        int viewWidth = gv_.getWidth();
        int viewHeight = gv_.getHeight();

        if (rightPaddleTouch == -1) {
            rightPaddle_.stop();
        } else {
            if (rightPaddleTouch > rightPaddle_.getYCenter()) {
                rightPaddle_.accelerateDown();
            } else if (rightPaddleTouch < rightPaddle_.getYCenter()) {
                rightPaddle_.accelerateUp();
            }
        }

        leftPaddle_.follow(b_);

        b_.move(viewWidth, viewHeight);
        rightPaddle_.move(viewHeight);
        leftPaddle_.move(viewHeight);

        if (b_.isOutsideRightSide(viewWidth) || b_.isOutsideLeftSide()) {
            b_.center(viewWidth, viewHeight);
            b_.randomizeVerticalSpeed();
        } else if (b_.hasCollided(rightPaddle_) || b_.hasCollided(leftPaddle_)) {
            b_.invertHoritzontalDirection();
            b_.randomizeVerticalSpeed();
            hits_++;
            if (hits_ == 6) {
                b_.moreSpeed();
            }
        }
    }

    private void draw(Canvas c) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        c.drawPaint(paint);
        paint.setColor(Color.WHITE);
        c.drawLine(gv_.getWidth() / 2, 0,gv_.getWidth() / 2, gv_.getHeight(), paint);
        b_.draw(c);
        rightPaddle_.draw(c);
        leftPaddle_.draw(c);
    }

    public boolean sendEvent(MotionEvent e) {
        boolean res = false;
        if (e.getAction() == MotionEvent.ACTION_UP) {
            rightPaddleTouch = -1;
        } else {
            if (e.getAction() == MotionEvent.ACTION_DOWN || e.getAction() == MotionEvent.ACTION_MOVE) {
                rightPaddleTouch = e.getY();
                res = true;
            }
        }
        return res;
    }

}
