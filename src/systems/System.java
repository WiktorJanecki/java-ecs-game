package systems;

import managers.Manager;

public abstract class System {
    protected Manager manager;
    public abstract void start();
    public abstract void render();
    public abstract void update();
    public abstract void end();

    public void setManager(Manager manager){
        this.manager = manager;
    }
}
