package state;

public abstract class State {
    public long window;

    public abstract void start(long window);
    public abstract void render();
    public abstract void update();
    protected abstract void tick();
}
