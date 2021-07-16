package com.andri.project.engine.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Model {

    private final FloatBuffer vertexBuffer;
    private final ShortBuffer drawListBuffer;
    private static final short[] DRAW_ORDER = { 0, 1, 2, 0, 2, 3 };

    /**
     * Creates a Model that can be rendered with a texture
     * @param vertices Vertices of the Model
     */
    public Model(float[] vertices) {
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(DRAW_ORDER.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(DRAW_ORDER);
        drawListBuffer.position(0);
    }

    public Model(float width, float height) {
        float[] vertices = createVertices(width, height);
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(DRAW_ORDER.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(DRAW_ORDER);
        drawListBuffer.position(0);
    }

    /**
     * Creates float[] of vertices based on the parameters
     * @param width modelWidth
     * @param height modelHeight
     */
    private float[] createVertices(float width, float height){
        return new float[]{
                -width/2, height/2, 0.0f,
                width/2, height/2, 0.0f,
                width/2, -height/2, 0.0f,
                -width/2, -height/2, 0.0f
        };
    }

    public FloatBuffer getVertexBuffer() {
        return vertexBuffer;
    }

    public ShortBuffer getDrawListBuffer() {
        return drawListBuffer;
    }

    public static short[] getDrawOrder() {
        return DRAW_ORDER;
    }
}
