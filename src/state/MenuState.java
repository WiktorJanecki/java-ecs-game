package state;

import components.CameraComponent;
import components.MeshComponent;
import components.TextureComponent;
import components.TransformComponent;
import events.KeyboardEvent;
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


public class MenuState extends State {

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

        RenderSystem rsys = new RenderSystem();
        InputSystem isys = new InputSystem();
        InheritanceSystem insys = new InheritanceSystem();

        Manager.addSystem(rsys);
        Manager.addSystem(isys);
        Manager.addSystem(insys);


        for(var sys : Manager.getSystems()){
            sys.start();
        }

        Manager.listen(KeyboardEvent.class);
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
        for( var ev : Manager.getEvent(KeyboardEvent.class)) {
            KeyboardEvent cev = (KeyboardEvent) ev;
            if (cev.key == GLFW_KEY_SPACE) {
                StateManager.changeState(new GameState());
            }
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
}