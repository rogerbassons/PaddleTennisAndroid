package com.rogerbassonsrenart.paddletennis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

/**
 * Created by roger on 07/03/15.
 */
public class SquareBall {
    Rect r_;
    Paint p_;
    int dx_;
    int dy_;


    SquareBall(int size) {
        r_ = new Rect(0, 0, size, size);
        p_ = new Paint();
        p_.setStyle(Paint.Style.FILL);
        p_.setColor(Color.WHITE);
        dx_ = size/4;
        dy_ = 0;
    }

    public void moreSpeed() {
        dx_++;
    }

    public float exactCenterY() {
        return r_.exactCenterY();
    }

    public void center(int w, int h) {
        int newLeft = (w / 2) - (r_.width() / 2);
        int newTop = (h / 2) - (r_.height() / 2);
        r_.offsetTo(newLeft, newTop);
    }

    public void move(int w, int h) {
        if (r_.top - dy_ < 0) {
            r_.offsetTo(r_.left, 0);
            dy_ *= -1;
        } else if (r_.bottom + dy_ > h) {
            r_.offsetTo(r_.left, h - r_.height());
            dy_ *= -1;
        }

        r_.offset(dx_, dy_);
    }

    public boolean isOutsideRightSide(int w) {
        return r_.left > w;
    }

    public boolean isOutsideLeftSide() {
        return r_.right < 0;
    }

    public void randomizeVerticalSpeed() {
        int min = 0;
        int max = 5;
        Random r = new Random();
        int nextDy = r.nextInt(max - min + 1) + min;
        max = 1;
        int k = r.nextInt(max - min + 1) + min;
        if (k == 0) {
            k = -1;
        }
        dy_ = nextDy * k;
    }

    public void invertHoritzontalDirection() {
        dx_ *= -1;
    }

    public boolean hasCollided(Paddle p) {
        Rect r = p.getRect();
        return Rect.intersects(r, r_);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(r_, p_);
    }
}
