uniform mat4 uMVPMatrix;
attribute vec4 vPosition;
attribute vec2 a_TextureCoordinates;
varying vec2 v_TextureCoordinates;
void main() {
    gl_Position = uMVPMatrix * vPosition;
    v_TextureCoordinates = a_TextureCoordinates;
}