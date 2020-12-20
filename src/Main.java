import managers.Manager;
import managers.StateManager;
import managers.TimeManager;
import managers.WindowManager;
import state.MenuState;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
    public static void main(String[] args) {
        if ( !glfwInit() ){
            System.err.println("Unable to initialize GLFW");
            System.exit(1);
        }

        WindowManager.createWindow(1280,720,WindowManager.getDeveloperTitle());
        StateManager.setCurrent(new MenuState());
        while ( !glfwWindowShouldClose(WindowManager.getWindow()) ) {
            //events
            glfwPollEvents();

            //game update
            TimeManager.countFPS();
            TimeManager.countDT();

            StateManager.getCurrent().update();

            //render
            StateManager.getCurrent().render();

            glfwSwapBuffers(WindowManager.getWindow()); // swap the color buffers
        }
        StateManager.getCurrent().end();
    }
}

//TODO
// Create TimeManager do every second callback and move windows title change to this callback from time manager
// Extract loader system from renderer
// Create terrain renderer
// Move shaders to shaderManager
// Think about static managers and implements
