package state;

import components.CameraComponent;
import components.MeshComponent;
import components.TextureComponent;
import components.TransformComponent;
import events.Event;
import events.GamepadEvent;
import events.KeyboardEvent;
import events.Listener;
import managers.Manager;
import managers.StateManager;
import org.joml.Vector3f;
import entities.*;
import shapes.Quad;
import systems.InheritanceSystem;
import systems.InputSystem;
import systems.RenderSystem;


import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.opengl.GL11.glClearColor;


public class MenuState extends State implements Listener {

    private Entity entity = new Entity();
    private Entity camera = new Entity();


    @Override
    public void start() {
        Manager.addEntity(entity);
        Manager.addEntity(camera);

        Manager.addComponent(entity,new TransformComponent(new Vector3f(0,0,0),0,0,0,1,1,1));
        Manager.addComponent(entity,new MeshComponent(Quad.vertices,Quad.textureCoords,Quad.indieces));
        Manager.addComponent(entity,new TextureComponent("menu"));

        Manager.addComponent(camera, new CameraComponent(0,0,0));
        Manager.addComponent(camera, new TransformComponent(new Vector3f(0,0,1),0,0,0,1,1,1));

        Manager.addSystem(new RenderSystem());
        Manager.addSystem(new InputSystem());
        Manager.addSystem(new InheritanceSystem());


        for(var sys : Manager.getSystems()){
            sys.start();
        }
   }

    @Override
    public void render() {
        glClearColor(0,0,0,0);
        for(var sys : Manager.getSystems()){
            sys.render();
        }
    }

    @Override
    public void update() {
        for(var sys : Manager.getSystems()){
            sys.update();
        }
    }

    @Override
    public void end() {
        for(var sys : Manager.getSystems()){
            sys.end();
        }
    }

    @Override
    protected void tick() {

    }

    @Override
    public void onEvent(Event event) {
            if(event.getClass() == KeyboardEvent.class) {
                KeyboardEvent kv = (KeyboardEvent) event;
                if (kv.getKey() == GLFW_KEY_SPACE) {
                    StateManager.changeState(new GameState());
                }
            }
    }
}