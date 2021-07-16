package com.andri.project.engine.graphics;

import com.andri.project.R;
import com.andri.project.engine.android.GameScreen;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Scanner;
import static android.opengl.GLES20.*;

public class ShaderProgram {

    public static int VERTEX_SHADER;
    private static final int CORDS_PER_VERTEX = 3;
    private final int programId;
    private int positionHandle;

    public static void createVertexShader(){
        VERTEX_SHADER = loadShader(GL_VERTEX_SHADER, getShaderCode(R.raw.vertex));
    }

    public ShaderProgram(int resourceId){
        programId = glCreateProgram();
        glAttachShader(programId, VERTEX_SHADER);
        glAttachShader(programId, loadShader(GL_FRAGMENT_SHADER, getShaderCode(resourceId)));
        glLinkProgram(programId);
    }

    /**
     * Used to get the Code from a shader file
     * Shader files are stored in R.raw.yourshader
     * @param resourceId resourceID from the Shader file
     * @return Returns the Code from the file as a String
     */
    private static String getShaderCode(int resourceId){
        Scanner s = new java.util.Scanner(GameScreen.resources.openRawResource(resourceId)).useDelimiter("\\A");
        String code = s.hasNext() ? s.next() : "";
        s.close();
        return code;
    }

    /**
     *
     * @param type Shader Type(Vertex or Fragment)
     * @param shaderCode The Shader Code
     * @return returns the shaderId
     */
    private static int loadShader(int type, String shaderCode){
        int shader = glCreateShader(type);
        glShaderSource(shader, shaderCode);
        glCompileShader(shader);
        return shader;
    }

    /**
     * Bind the Shader and pass the default Information
     */
    public void bind(FloatBuffer vertexBuffer, float[] MVPMatrix){
        glUseProgram(programId);

        int positionHandle = glGetAttribLocation(programId, "vPosition");
        glEnableVertexAttribArray(positionHandle);
        this.positionHandle = positionHandle;

        int vertexStride = CORDS_PER_VERTEX * 4;
        glVertexAttribPointer(positionHandle, CORDS_PER_VERTEX, GL_FLOAT, false, vertexStride, vertexBuffer);

        int MVPMatrixHandle = glGetUniformLocation(programId, "uMVPMatrix");
        glUniformMatrix4fv(MVPMatrixHandle, 1, false, MVPMatrix, 0);
    }

    /**
     * Draw the Model with the applied Texture and finish up
     */
    public void drawElements(ShortBuffer drawListBuffer, short[] drawOrder){
        glDrawElements(GL_TRIANGLES, drawOrder.length, GL_UNSIGNED_SHORT, drawListBuffer);
        glDisableVertexAttribArray(positionHandle);
    }

    public int getProgramId() {
        return programId;
    }
}