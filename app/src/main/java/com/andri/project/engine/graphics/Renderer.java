package com.andri.project.engine.graphics;

import static android.opengl.GLES20.*;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import com.andri.project.R;
import com.andri.project.engine.android.GameScreen;
import com.andri.project.engine.math.Vec2;
import com.andri.project.game.SceneHandler;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderer implements GLSurfaceView.Renderer {

    public static ShaderProgram textureProgram;
    public static ShaderProgram recolorProgram;

    private static final float[] vPMatrix = new float[16]; // ViewProjectionMatrix
    private static final float[] projectionMatrix = new float[16];
    private static final float[] viewMatrix = new float[16];

    public static float fps;
    private double firstTime, secondTime, lastTime;
    private float deltaTime;


    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        glClearColor(0f, 0.01f, 0.51f, 1);
        loadShader();
        SceneHandler.start();
    }

    /**
     * Called when the surface changed size.
     * Called after the surface is created and whenever
     * the OpenGL ES surface size changes.
     */
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        glViewport(0, 0, width, height);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glEnable(GL_TRIANGLES);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        Matrix.frustumM(projectionMatrix, 0, GameScreen.ratio, -GameScreen.ratio, -1, 1, 3, 7);
    }

    /**
     * Function gets called every frame (Refresh rate of the Screen)
     * @param unused This GL10 Instance is never used, we only use GL20
     */
    @Override
    public void onDrawFrame(GL10 unused) {
        secondTime = System.nanoTime();
        deltaTime = (float) (secondTime - firstTime);
        deltaTime /= 1000000000.0f;
        firstTime = System.nanoTime();

        glClear(GL_COLOR_BUFFER_BIT);
        SceneHandler.update();
        SceneHandler.currentScene.update();
        resetMatrix();
        SceneHandler.currentScene.render();

        fps++;
        if (System.nanoTime() > lastTime + 1000000000){
            System.out.println(fps);
            lastTime = System.nanoTime();
            fps = 0;
        }
    }

    /**
     * Load all shader
     */
    private void loadShader(){
        ShaderProgram.createVertexShader();
        textureProgram = new ShaderProgram(R.raw.texture);
        recolorProgram = new ShaderProgram(R.raw.recolor);
    }

    /**
     * Renders Texture with the default Texture Shader
     * @param m Model to render
     */
    public static void renderTexture(Model m){
        textureProgram.bind(m.getVertexBuffer(), vPMatrix);
        textureProgram.drawElements(m.getDrawListBuffer(), Model.getDrawOrder());
    }

    public static void renderRecoloredTexture(Model m, float[] rgba){
        recolorProgram.bind(m.getVertexBuffer(), vPMatrix);
        int colorHandle = GLES20.glGetUniformLocation(recolorProgram.getProgramId(), "color");
        GLES20.glUniform4fv(colorHandle, 1, rgba, 0);
        recolorProgram.drawElements(m.getDrawListBuffer(), Model.getDrawOrder());
    }

    /**
     * Translates the Matrix to a specific position. Used for drawing models to a specific location on the Screen
     * @param x Add the x coordinate to the matrix translation
     * @param y Add the y coordinate to the matrix translation
     */
    public static void translate(float x, float y){
        Matrix.translateM(viewMatrix, 0, x, y, 0);
        applyMatrixChange();
    }

    /**
     * Translates the Matrix to a specific position. Used for drawing models to a specific location on the Screen
     */
    public static void translate(Vec2 pos){
        Matrix.translateM(viewMatrix, 0, pos.x, pos.y, 0);
        applyMatrixChange();
    }

    /**
     * Rotate the matrix at the currently translated position
     * @param angle Adds the angle in degree to the matrix rotation
     */
    public static void rotate(float angle) {
        Matrix.rotateM(viewMatrix, 0, angle, 0, 0, -1.0f);
        applyMatrixChange();
    }

    /**
     * Scale the matrix and with that the rendered models.
     * @param scaleX Scales the matrix on the x axis
     * @param scaleY Scales the matrix on the y axis
     */
    public static void scale(float scaleX, float scaleY){
        Matrix.scaleM(viewMatrix, 0, scaleX, scaleY, 0);
        applyMatrixChange();
    }

    /**
     * Resets the Matrix to it's default values (0 rotation, 0 translation, 0 scaling)
     */
    public static void resetMatrix(){
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1f, 0.0f);
        applyMatrixChange();
    }

    /**
     * Apply changes to the vPMatrix.
     * Used after Rotations, Transformations, Scaling etc.
     */
    private static void applyMatrixChange(){
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
    }
}
