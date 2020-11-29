package systems;

import components.TransformComponent;
import events.ChangePositionEvent;
import managers.Manager;

public class PhysicsSystem extends System {
    @Override
    public void start() {

    }

    @Override
    public void render() {

    }

    @Override
    public void update() { //totally for testing
        try {
            TransformComponent transf = Manager.getComponent(Manager.getEntity(0),TransformComponent.class);
            transf.increasePosition(0.0002f,0,0);
            ChangePositionEvent ev = new ChangePositionEvent();
            ev.setMovedEntityID(0);
            Manager.initEvent(ev);
        } catch (Exception e) {
            java.lang.System.err.println(e);
        }
        try {
            TransformComponent transf = Manager.getComponent(Manager.getEntity(2),TransformComponent.class);
            transf.increasePosition(0.0003f,0,0);
            ChangePositionEvent ev = new ChangePositionEvent();
            ev.setMovedEntityID(2);
            Manager.initEvent(ev);
        } catch (Exception e) {
            java.lang.System.err.println(e);
        }
    }

    @Override
    public void end() {

    }
}
