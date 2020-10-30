import render.shaders.StaticShader;
import state.GameState;
import state.MenuState;
import state.State;
import state.StateList;
import window.WindowManager;
import entities.*;

import static org.lwjgl.glfw.GLFW.*;

public class Main {

    public static void main(String[] args) {
        if ( !glfwInit() ){
            System.err.println("Unable to initialize GLFW");
            System.exit(1);
        }

        WindowManager wm = new WindowManager();
        long fpsStart = System.currentTimeMillis();
        long fpsStop;
        int fpsCount = 0;


        StateList stateList = new StateList();

        stateList.addState(new MenuState());
        stateList.addState(new GameState());

        State state;

        wm.createWindow(1280,720,wm.getDeveloperTitle());

        long window = wm.getWindow();

        StaticShader shader = new StaticShader();
        Camera camera = new Camera();

        String title = wm.getDeveloperTitle();

        while ( !glfwWindowShouldClose(window) ) {
            state = stateList.getState(stateList.CHANGINGSTATEINDEX);
            state.start(window, shader, stateList);
            while( !stateList.ISSTATECHANGING) {
                //events
                glfwPollEvents();

                //fps counter
                {
                    fpsCount++;
                    fpsStop = System.currentTimeMillis();
                    if (fpsStop - fpsStart > 1000) {
                        fpsStart = System.currentTimeMillis();
                        glfwSetWindowTitle(wm.getWindow(), (title + "               FPS : " + "" + fpsCount));
                        fpsCount = 0;
                    }
                }
                //

                //game update
                state.update();

                //render
                shader.start();

                shader.loadViewMatrix(camera);

                state.render();

                shader.stop();

                glfwSwapBuffers(window); // swap the color buffers
                if(glfwWindowShouldClose(window)) break;
            }
        }
        stateList.cleanUp();
    }
}