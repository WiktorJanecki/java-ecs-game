package state;

import components.CameraComponent;
import components.MeshComponent;
import components.TextureComponent;
import components.TransformComponent;
import events.KeyboardEvent;
import input.Keyboard;
import managers.Manager;
import org.joml.Vector3f;
import entities.*;
import shapes.Quad;
import systems.InputSystem;
import systems.RenderSystem;


import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.opengl.GL11.glClearColor;


public class MenuState extends State {

    private Entity entity = new Entity();
    private Entity camera = new Entity();
    private Manager manager = new Manager();

    private long window;
    private StateList list;

    @Override
    public void start(long window, StateList list) {
        this.list = list;
        this.window = window;

        manager.addEntity(entity);
        manager.addEntity(camera);

        manager.addComponent(entity,new TransformComponent(new Vector3f(0,0,0.5f),0,0,0,1,1,1));
        manager.addComponent(entity,new MeshComponent(Quad.vertices,Quad.textureCoords,Quad.indieces));
        manager.addComponent(entity,new TextureComponent("menu"));

        manager.addComponent(camera, new CameraComponent(new Vector3f(0,0,5),0,0,0));
        manager.addComponent(camera, new TransformComponent(new Vector3f(0,0,1),0,0,0,1,1,1));

        RenderSystem rsys = new RenderSystem(manager);
        InputSystem isys = new InputSystem(manager);
        isys.setWindow(window);
        manager.addSystem(rsys);
        manager.addSystem(isys);


        for(var sys : manager.getSystems()){
            sys.start();
        }

        manager.listen(KeyboardEvent.class);
   }

    @Override
    public void render() {
        glClearColor(0,0,0,0);
        for(var sys : manager.getSystems()){
            sys.render();
        }
    }

    @Override
    public void update() {
        for(var sys : manager.getSystems()){
            sys.update();
        }
        if(manager.getEvent(KeyboardEvent.class) != null) {
            KeyboardEvent ev = manager.getEvent(KeyboardEvent.class);
            if (ev.key == GLFW_KEY_SPACE) {
                manager.clearEvents();
                this.list.changeState(1);
            }
        }
    }

    @Override
    public void end() {
        for(var sys : manager.getSystems()){
            sys.end();
        }
    }

    @Override
    protected void tick() {

    }
}