package com.andri.project.engine.input;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import com.andri.project.engine.android.GameScreen;
import com.andri.project.engine.math.Vec2;

import java.util.ArrayList;

public class TouchHandler implements View.OnTouchListener {

    public static ArrayList<Vec2> pointers = new ArrayList<>();
    

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent e) {
        pointers.clear();
        for (int i = 0; i < e.getPointerCount(); i++){
            pointers.add(new Vec2(((e.getX(0) / GameScreen.screenSize.x) * 2 - 1) * GameScreen.ratio, -(e.getY(0) / GameScreen.screenSize.y) * 2 + 1));
            System.out.println(((e.getX(0) / GameScreen.screenSize.x) * 2 - 1) * GameScreen.ratio + "           " + -(e.getY(0) / GameScreen.screenSize.y) * 2 + 1);
        }
        return true;
    }
}
