package state;


public abstract class State {
    public long window;

    public void startScene(long window, StateList list){
        list.ISSTATECHANGING=false;
        start(window,list);
    }
    public abstract void start(long window, StateList list);
    public abstract void render();
    public abstract void update();
    public abstract void end();
    protected abstract void tick();
}
