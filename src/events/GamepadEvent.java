package events;

import org.joml.Vector2f;

import java.util.Map;

public class GamepadEvent extends Event{
    private Vector2f leftStick;
    private Vector2f rightStick;

    public GamepadEvent(Vector2f leftStick, Vector2f rightStick, float leftTrigger, float rightTrigger, Map<Integer, Boolean> buttons) {
        this.leftStick = leftStick;
        this.rightStick = rightStick;
        this.leftTrigger = leftTrigger;
        this.rightTrigger = rightTrigger;
        this.buttons = buttons;
    }

    private float leftTrigger;
    private float rightTrigger;

    public Vector2f getLeftStick() {
        return leftStick;
    }

    public Vector2f getRightStick() {
        return rightStick;
    }

    public float getLeftTrigger() {
        return leftTrigger;
    }

    public float getRightTrigger() {
        return rightTrigger;
    }

    public Map<Integer, Boolean> getButtons() {
        return buttons;
    }

    private Map<Integer, Boolean> buttons;
}
