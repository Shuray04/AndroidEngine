package com.andri.project.game;

import com.andri.project.engine.graphics.Scene;

public class SceneHandler {

    public static Scene currentScene;

    public static void start(){
        currentScene = new GameScene();
    }

    /**
     * Manages which Scene should be put as the current scene
     */
    public static void update(){

    }
}
