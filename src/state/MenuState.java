package state;

import components.CameraComponent;
import components.MeshComponent;
import components.TextureComponent;
import components.TransformComponent;
import events.Event;
import events.KeyboardEvent;
import managers.Manager;
import managers.ShaderManager;
import managers.StateManager;
import org.joml.Vector2f;
import entities.*;
import org.joml.Vector3f;
import shapes.Quad;
import systems.InheritanceSystem;
import systems.InputSystem;
import systems.RenderSystem;
import systems.onEvent;


import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.opengl.GL11.glClearColor;


public class MenuState extends State implements onEvent {

    private Entity entity = new Entity();
    private Entity camera = new Entity();


    @Override
    public void start() {
        Manager.addEntity(entity);
        Manager.addEntity(camera);

        Manager.addComponent(entity,new TransformComponent(new Vector2f(0,0),0,0,0,1,1));
        Manager.addComponent(entity,new MeshComponent(Quad.vertices,Quad.textureCoords,Quad.indieces));
        Manager.addComponent(entity,new TextureComponent("menu"));

        Manager.addComponent(camera, new CameraComponent(new Vector3f(0,0,1),0,0,0));

        Manager.addSystem(new RenderSystem());
        Manager.addSystem(new InputSystem());
        Manager.addSystem(new InheritanceSystem());

        ShaderManager.start();

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
        ShaderManager.prepare();
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