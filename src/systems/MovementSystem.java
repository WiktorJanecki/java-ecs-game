package systems;

import components.PhysicsComponent;
import entities.Player;
import events.*;
import managers.Manager;
import entities.Entity;
import org.joml.Vector3f;


public class MovementSystem extends System implements onEvent {

    @Override
    public void start() {

    }

    @Override
    public void render() {

    }

    @Override
    public void update() {

    }

    @Override
    public void end() {

    }

    @Override
    public void onEvent(Event event) {
        if(event.getClass() == GamepadEvent.class) {
            GamepadEvent gev = (GamepadEvent) event;
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
}
