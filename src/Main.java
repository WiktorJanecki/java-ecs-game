import render.Loader;
import render.Renderer;
import render.models.RawModel;
import render.shaders.StaticShader;
import state.GameState;
import state.State;
import window.WindowManager;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.glClear;

public class Main {
    public static void main(String[] args) {
        if ( !glfwInit() ){
            System.err.println("Unable to initialize GLFW");
            System.exit(1);
        }

        WindowManager wm = new WindowManager();

        State state;
        state = new GameState();

        wm.createWindow(1280,720,wm.getDeveloperTitle());

        long window = wm.getWindow();

        state.start(window);

        float[] vertices = {
                -0.5f, 0.5f, 0,
                -0.5f, -0.5f, 0,
                0.5f, -0.5f, 0,
                0.5f, 0.5f, 0f
        };

        int[] indices = {
                0,1,3,
                3,1,2
        };

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        RawModel model = loader.loadToVAO(vertices,indices);
        StaticShader shader = new StaticShader();

        while ( !glfwWindowShouldClose(window) ) {
            //events
            glfwPollEvents();

            //game update
            state.update();

            //prepare
            renderer.prepare();

            //render
            state.render();

            shader.start();
            renderer.render(model);
            shader.stop();

            glfwSwapBuffers(window); // swap the color buffers
        }
        loader.cleanUp();
        shader.cleanUp();
    }
}