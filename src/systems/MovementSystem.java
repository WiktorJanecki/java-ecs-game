package systems;

import components.PhysicsComponent;
import entities.Player;
import events.*;
import managers.Manager;
import entities.Entity;
import managers.StateManager;
import org.joml.Vector2f;
import org.joml.Vector3f;
import state.GameState;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;


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
                            pc.setAcceleration(new Vector2f(5 * gev.getLeftStick().x, -5 * gev.getLeftStick().y));

                        } catch (Exception e) {
                            java.lang.System.err.println(e);
                        }
                    }
                }
            }
        }
        if(event.getClass() == KeyboardEvent.class){
            KeyboardEvent kev = (KeyboardEvent) event;
            if (kev.getKey() == GLFW_KEY_D) {
                for (Entity ent : Manager.arrayOfEntitiesWith(PhysicsComponent.class)) {
                    if (ent.getClass() == Player.class) {
                        try {
                            PhysicsComponent pc = (PhysicsComponent) Manager.getComponent(ent, PhysicsComponent.class);
                            pc.setAcceleration(new Vector2f(4f,0f));

                        } catch (Exception e) {
                            java.lang.System.err.println(e);
                        }
                    }
                }
            }
        }
    }
}
