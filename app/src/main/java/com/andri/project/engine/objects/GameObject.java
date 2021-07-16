package com.andri.project.engine.objects;

import com.andri.project.engine.graphics.Scene;

public abstract class GameObject{

    public abstract void update(Scene scene);
    public abstract void render();
}
