package systems;

import events.KeyboardEvent;
import managers.Manager;
import managers.WindowManager;
import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

public class InputSystem extends System {

    public InputSystem(Manager manager){
        this.manager = manager;
    }
    @Override
    public void start() {
        GLFWKeyCallback keyCallback;
        glfwSetKeyCallback(WindowManager.getWindow(),keyCallback = new GLFWKeyCallback(){
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if(manager.isListening(KeyboardEvent.class)&&action == GLFW_PRESS){
                    manager.initEvent(new KeyboardEvent(key));
                }
            }
        });
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
}
