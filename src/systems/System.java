package systems;

import managers.Manager;

public abstract class System {
    protected Manager manager;
    protected long window;
    public abstract void start();
    public abstract void render();
    public abstract void update();
    public abstract void end();

    public void setWindow(long window){this.window = window;}
    public void setManager(Manager manager){
        this.manager = manager;
    }
}
