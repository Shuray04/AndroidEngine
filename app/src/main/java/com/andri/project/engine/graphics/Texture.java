package com.andri.project.engine.graphics;

import static android.opengl.GLES20.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import com.andri.project.engine.android.GameScreen;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Texture {

    private final int textureDataHandle;
    private FloatBuffer textureBuffer;
    final float[] previewTextureCoordinateData =  {
            0.0f,0.0f, // top left
            -1.0f,0.0f, // Top-right
            -1.0f,1.0f, // Bottom-right
            0.0f,1.0f,  // Bottom-left
    };

    public Texture(int textureId){
        textureDataHandle = createTexture(textureId);
        buffer();
    }

    /*public Texture(int textureId, int x, int y, int width, int height){
        textureDataHandle = createSprite(textureId, x, y, width, height);
        buffer();
    }

    public Texture(Bitmap bitmap){
        textureDataHandle = createTextTexture(bitmap);
        buffer();
    }*/

    /**
     * Bind the Texture
     */
    public void bind(){
        int textureCoordinateHandle = GLES20.glGetAttribLocation(Renderer.textureProgram.getProgramId(), "a_TextureCoordinates");
        GLES20.glVertexAttribPointer(textureCoordinateHandle, 2, GLES20.GL_FLOAT, false, 0, textureBuffer);
        GLES20.glEnableVertexAttribArray(textureCoordinateHandle);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureDataHandle);
    }

    private void buffer(){
        ByteBuffer texCoordinates = ByteBuffer.allocateDirect(previewTextureCoordinateData.length * 4);
        texCoordinates.order(ByteOrder.nativeOrder());
        textureBuffer = texCoordinates.asFloatBuffer();
        textureBuffer.put(previewTextureCoordinateData);
        textureBuffer.position(0);

        glBindTexture(GL_TEXTURE_2D, textureDataHandle);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    }

    private static int createTexture(int resourceId){
        final int[] textureHandle = new int[1];

        glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            final Bitmap bitmap = BitmapFactory.decodeResource(GameScreen.resources, resourceId, options);
            loadTexture(bitmap, textureHandle);
        }
        return textureHandle[0];
    }

    /*private static int createSprite(int resourceId, int x, int y, int width, int height){
        final int[] textureHandle = new int[1];
        GLES20.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;   // No pre-scaling

            Bitmap bmap = BitmapFactory.decodeResource(res, resourceId, options);
            bmap = Bitmap.createBitmap(bmap, x , y, width, height);

            loadTexture(bmap, textureHandle);
        }

        return textureHandle[0];
    }*/


    private static void loadTexture(Bitmap bitmap, int[] textureHandle){
        glBindTexture(GL_TEXTURE_2D, textureHandle[0]);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
    }
}
