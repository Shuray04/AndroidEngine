precision mediump float;
uniform sampler2D u_TextureUnit;
uniform vec4 color;
varying vec2 v_TextureCoordinates;

void main() {
    vec4 pixel = texture2D(u_TextureUnit, v_TextureCoordinates);
    if (pixel.a == 0.0){
        discard;
    }else{
        gl_FragColor = color;
    }
}