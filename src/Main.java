import managers.StateManager;
import managers.WindowManager;
import state.GameState;
import state.MenuState;
import state.State;

import static org.lwjgl.glfw.GLFW.*;

public class Main {

    public static void main(String[] args) {
        if ( !glfwInit() ){
            System.err.println("Unable to initialize GLFW");
            System.exit(1);
        }

        long fpsStart = System.currentTimeMillis();
        long fpsStop;
        int fpsCount = 0;

        WindowManager.createWindow(1280,720,WindowManager.getDeveloperTitle());

        long window = WindowManager.getWindow();

        String title = WindowManager.getDeveloperTitle();

        StateManager.setCurrent(new MenuState());
        while ( !glfwWindowShouldClose(window) ) {
            //events
            glfwPollEvents();

            //fps counter
            {
                fpsCount++;
                fpsStop = System.currentTimeMillis();
                if (fpsStop - fpsStart > 1000) {
                    fpsStart = System.currentTimeMillis();
                    glfwSetWindowTitle(WindowManager.getWindow(), (title + "               FPS : " + "" + fpsCount));
                    fpsCount = 0;
                }
            }
            //

            //game update
            StateManager.getCurrent().update();

            //render
            StateManager.getCurrent().render();

            glfwSwapBuffers(window); // swap the color buffers
        }
        StateManager.getCurrent().end();
    }
}

//TODO
// Entity parent - children system
// Entity parent - system multiply transform comp
