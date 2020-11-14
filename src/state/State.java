package state;

import render.shaders.StaticShader;

public abstract class State {
    public long window;

    public void startScene(long window, StaticShader shader, StateList list){
        list.ISSTATECHANGING=false;
        start(window,shader,list);
    }
    public abstract void start(long window, StaticShader shader, StateList list);
    public abstract void render();
    public abstract void update();
    public abstract void end();
    protected abstract void tick();
}
