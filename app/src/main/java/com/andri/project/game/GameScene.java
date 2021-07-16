package com.andri.project.game;

import com.andri.project.R;
import com.andri.project.engine.graphics.Model;
import com.andri.project.engine.graphics.Renderer;
import com.andri.project.engine.graphics.Scene;
import com.andri.project.engine.graphics.Texture;
import com.andri.project.engine.input.TouchHandler;

public class GameScene extends Scene {

    private final Texture tex = new Texture(R.drawable.bird);
    private final Model model = new Model(.5f, .5f);

    public GameScene(){

    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        if (TouchHandler.pointers.size() != 0) Renderer.translate(TouchHandler.pointers.get(0));
        for (int i = 0; i < 50; i++){
            tex.bind();
            Renderer.renderTexture(model);
        }
    }
}