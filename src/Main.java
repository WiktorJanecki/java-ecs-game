import state.GameState;
import state.State;
import window.WindowManager;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.glClear;

public class Main {
    public static void main(String[] args) {
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        WindowManager wm = new WindowManager();

        State state;
        state = new GameState();

        wm.createWindow(1280,720,wm.getDeveloperTitle());

        long window = wm.getWindow();

        state.start(window);

        while ( !glfwWindowShouldClose(window) ) {

            state.update();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            state.render();

            glfwSwapBuffers(window); // swap the color buffers
            glfwPollEvents();
        }
    }
}