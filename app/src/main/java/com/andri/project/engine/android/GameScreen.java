package com.andri.project.engine.android;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import androidx.appcompat.app.AppCompatActivity;

import com.andri.project.R;
import com.andri.project.engine.input.TouchHandler;
import com.andri.project.engine.math.Vec2;

@SuppressLint("ViewConstructor")
public class GameScreen extends GLSurfaceView {

    public static Vec2 screenSize;
    public static float ratio;
    public static Resources resources;

    /**
     * Screen Wrapper that implements Renderer and InputHandler
     * @param app Parent Activity
     */
    public GameScreen(AppCompatActivity app) {
        super(app);
        resources = getResources();
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);

        Point p = new Point();
        app.getWindowManager().getDefaultDisplay().getRealSize(p);
        screenSize = new Vec2(p.x, p.y);
        ratio = screenSize.x / screenSize.y;


        setOnTouchListener(new TouchHandler());
        setRenderer(new com.andri.project.engine.graphics.Renderer());
    }
}