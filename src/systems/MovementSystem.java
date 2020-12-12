package systems;

import components.PhysicsComponent;
import entities.Player;
import events.Event;
import events.GamepadEvent;
import managers.Manager;
import entities.Entity;
import org.joml.Vector3f;


public class MovementSystem extends System {

    @Override
    public void start() {
        Manager.listen(GamepadEvent.class);
    }

    @Override
    public void render() {

    }

    @Override
    public void update() {
        for(var ev : Manager.getEvent(GamepadEvent.class)) {
            GamepadEvent gev = (GamepadEvent) ev;
            if(gev.getLeftStick().x != 0 || gev.getLeftStick().y != 0 || gev.getRightStick().x != 0 || gev.getRightStick().y != 0) {
                for (Entity ent : Manager.arrayOfEntitiesWith(PhysicsComponent.class)) {
                    if (ent.getClass() == Player.class) {
                        try {
                            PhysicsComponent pc = (PhysicsComponent) Manager.getComponent(ent, PhysicsComponent.class);
                            pc.setAcceleration(new Vector3f(5 * gev.getLeftStick().x, -5 * gev.getLeftStick().y, 0));

                        } catch (Exception e) {
                            java.lang.System.err.println(e);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void end() {

    }
}
