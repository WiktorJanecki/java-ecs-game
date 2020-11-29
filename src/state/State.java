package state;


public abstract class State {
    public abstract void start();
    public abstract void render();
    public abstract void update();
    public abstract void end();
    protected abstract void tick();
}
