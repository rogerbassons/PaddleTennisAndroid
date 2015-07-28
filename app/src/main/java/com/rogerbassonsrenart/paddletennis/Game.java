package com.rogerbassonsrenart.paddletennis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by roger on 28/07/15.
 */
public class Game {
    private SquareBall b_;
    private Paddle rightPaddle_;
    private Paddle leftPaddle_;
    private int hits_;
    private boolean initialized_;

    private int viewWidth_, viewHeight_;

    Game() {
        hits_ = 0;
        b_ = null;
        rightPaddle_ = null;
        leftPaddle_ = null;
        viewHeight_ = 0;
        viewWidth_ = 0;
        initialized_ = false;
    }

    public boolean isInitialized() {
        return initialized_;
    }

    public void initialize(int width, int height) {
        viewWidth_ = width;
        viewHeight_ = height;
        int paddleHeight = viewHeight_/4;
        int paddleWidth = paddleHeight/6;

        rightPaddle_ = new Paddle(paddleWidth,paddleHeight);
        rightPaddle_.setRight(viewWidth_, viewHeight_);

        leftPaddle_ = new Paddle(paddleWidth,paddleHeight);
        leftPaddle_.setLeft(viewWidth_, viewHeight_);

        b_ = new SquareBall(paddleHeight/5);
        b_.center(viewWidth_, viewHeight_);

        initialized_ = true;
    }

    public void play(float rightPaddleTouch) {

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

        b_.move(viewWidth_, viewHeight_);
        rightPaddle_.move(viewHeight_);
        leftPaddle_.move(viewHeight_);

        boolean outside = false;
        if (b_.isOutsideRightSide(viewWidth_)) {
            outside = true;
        } else if (b_.isOutsideLeftSide()) {
            outside = true;
        } else if ((b_.hasCollided(rightPaddle_) || b_.hasCollided(leftPaddle_))) {
            b_.invertHoritzontalDirection();
            b_.randomVerticalSpeed();
            hits_++;
            if (hits_ == 50) {
                b_.moreSpeed();
                hits_ = 0;
            }
        }
        if (outside) {
            b_.center(viewWidth_, viewHeight_);
            b_.randomVerticalSpeed();
            b_.randomHoritzontalDirection();
        }
    }

    public void draw(Canvas c) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        c.drawPaint(paint);

        paint.setColor(Color.WHITE);
        c.drawLine(viewWidth_ / 2, 0, viewWidth_ / 2, viewHeight_, paint);

        b_.draw(c);

        rightPaddle_.draw(c);
        leftPaddle_.draw(c);
    }
}
