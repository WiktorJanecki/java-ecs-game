package systems;

import events.GamepadEvent;
import events.KeyboardEvent;
import managers.Manager;
import managers.WindowManager;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWGamepadState;
import org.lwjgl.glfw.GLFWKeyCallback;


import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class InputSystem extends System {


    private boolean calcController = false;
    float controllerDeadzone = 0.1f;

    public InputSystem(){}
    @Override
    public void start() {
        GLFWKeyCallback keyCallback;
        glfwSetKeyCallback(WindowManager.getWindow(),keyCallback = new GLFWKeyCallback(){
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if(action == GLFW_PRESS){
                    Manager.initEvent(new KeyboardEvent(key));
                }
            }
        });
        if(glfwGetJoystickName(GLFW_JOYSTICK_1) != null){
            java.lang.System.out.println("Connected controllers:"+ glfwGetJoystickName(GLFW_JOYSTICK_1));
            if(glfwJoystickIsGamepad(GLFW_JOYSTICK_1)){
                java.lang.System.out.println("Controller is available");
                calcController = true;
            }
            else{
                java.lang.System.out.println("Controller is unavailable");
            }

        }
    }

    @Override
    public void render() {

    }

    @Override
    public void update() {
        if(calcController){
            GLFWGamepadState state = GLFWGamepadState.calloc();
            glfwGetGamepadState(GLFW_JOYSTICK_1,state);
            float LXAxis = state.axes(GLFW_GAMEPAD_AXIS_LEFT_X);
            float LYAxis = state.axes(GLFW_GAMEPAD_AXIS_LEFT_Y);
            float RXAxis = state.axes(GLFW_GAMEPAD_AXIS_RIGHT_X);
            float RYAxis = state.axes(GLFW_GAMEPAD_AXIS_RIGHT_Y);
            Map<Integer,Boolean> buttons = new HashMap<>();

            //leftx
            if(LXAxis < controllerDeadzone && LXAxis > 0){
                LXAxis = 0;
            }
            else if(LXAxis < 0 && LXAxis > -controllerDeadzone){
                LXAxis = 0;
            }

            //lefty
            if(LYAxis < controllerDeadzone && LYAxis > 0){
                LYAxis = 0;
            }
            else if(LYAxis < 0 && LYAxis > -controllerDeadzone){
                LYAxis = 0;
            }

            //rightx
            if(RXAxis < controllerDeadzone && RXAxis > 0){
                RXAxis = 0;
            }
            else if(RXAxis < 0 && RXAxis > -controllerDeadzone){
                RXAxis = 0;
            }

            //righty
            if(RYAxis < controllerDeadzone && RYAxis > 0){
                RYAxis = 0;
            }
            else if(RYAxis < 0 && RYAxis > -controllerDeadzone){
                RYAxis = 0;
            }


            for(int i = 0;i < state.buttons().limit();i++){
                buttons.put(i,state.buttons().get(i) == 1);
            }
            Manager.initEvent(new GamepadEvent(new Vector2f(LXAxis,LYAxis),new Vector2f(RXAxis,RYAxis),state.axes(GLFW_GAMEPAD_AXIS_LEFT_TRIGGER),state.axes(GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER),buttons));
        }
    }

    @Override
    public void end() {

    }
}
