package state;

import input.Keyboard;
import org.joml.Vector3f;
import render.Loader;
import render.Renderer;
import render.models.TexturedModel;
import render.shaders.StaticShader;
import entities.*;
import shapes.Quad;


import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.opengl.GL11.glClearColor;


public class MenuState extends State {

    private Renderer renderer;
    private Loader loader;
    private Entity entity;
    private long window;
    private StaticShader shader;
    private StateList list;

    @Override
    public void start(long window, StaticShader shader, StateList list) {
        this.shader = shader;
        this.list = list;
        this.window = window;
        renderer = new Renderer(shader);
        loader = new Loader();
        entity = new Entity(new TexturedModel(loader.loadToVAO(Quad.vertices,Quad.textureCoords,Quad.indieces),loader.loadTexture("menu")),new Vector3f(0,0,-1),0,0,0,1,1,1);
    }

    @Override
    public void render() {
        renderer.prepare();

        glClearColor(0,0,0,1);

        renderer.render(entity,shader);
    }

    @Override
    public void update() {
        if(Keyboard.isKeyDown(GLFW_KEY_SPACE,this.window)){
            this.list.changeState(1);
        }
    }

    @Override
    public void end() {
        loader.cleanUp();
        shader.cleanUp();
    }

    @Override
    protected void tick() {

    }
}