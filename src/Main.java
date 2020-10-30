import org.joml.Vector3f;
import render.Loader;
import render.Renderer;
import render.models.RawModel;
import render.models.TexturedModel;
import render.shaders.StaticShader;
import state.GameState;
import state.State;
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
        float[] textureCoords = {
                0,0,
                0,1,
                1,1,
                1,0
        };

        StaticShader shader = new StaticShader();
        Loader loader = new Loader();
        Renderer renderer = new Renderer(shader);
        RawModel model = loader.loadToVAO(vertices,textureCoords,indices);
        int texture = loader.loadTexture("texture");
        TexturedModel texturedModel = new TexturedModel(model,texture);
        Entity entity = new Entity(texturedModel,new Vector3f(0,0,-1),0,0,0,1,1,1);

        while ( !glfwWindowShouldClose(window) ) {
            //events
            glfwPollEvents();

            //game update
            state.update();
            entity.increasePosition(0f,0f,-0.001f);

            //prepare
            renderer.prepare();

            //render
            state.render();

            shader.start();
            renderer.render(entity,shader);
            shader.stop();

            glfwSwapBuffers(window); // swap the color buffers
        }
        loader.cleanUp();
        shader.cleanUp();
    }
}