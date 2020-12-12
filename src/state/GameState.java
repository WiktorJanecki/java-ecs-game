package state;


import components.*;
import entities.Entity;
import entities.Player;
import managers.Manager;
import org.joml.Vector3f;
import shapes.Quad;
import systems.*;

public class GameState extends State {

    private Entity entity;
    private Entity entity2;
    private Entity entity3;
    private Entity entity4;
    private Entity camera;
    private Player player;

    @Override
    public void start(){

        entity = new Entity();
        entity2 = new Entity();
        entity3 = new Entity();
        entity4 = new Entity();
        camera = new Entity();
        player = new Player();
        Manager.addEntity(entity);
        Manager.addEntity(entity2);
        Manager.addEntity(entity3);
        Manager.addEntity(entity4);
        Manager.addEntity(camera);
        Manager.addEntity(player);

        Manager.addComponent(camera,new TransformComponent(new Vector3f(0,0,5f),0,0,0,1,1,1));
        Manager.addComponent(camera,new CameraComponent(0,0,0));

        Manager.addComponent(entity,new MeshComponent(Quad.vertices,Quad.textureCoords,Quad.indieces));
        Manager.addComponent(entity,new TransformComponent(new Vector3f(2,0,0),0,0,0,1,2,1));
        Manager.addComponent(entity,new PhysicsComponent());
        Manager.addComponent(entity,new TextureComponent("texture"));

        entity2.setParentID(entity.getID());
        Manager.addComponent(entity2,new MeshComponent(Quad.vertices,Quad.textureCoords,Quad.indieces));
        Manager.addComponent(entity2,new TransformComponent(new Vector3f(-3f,0,0),0,0,0,1,1,1));
        Manager.addComponent(entity2,new TextureComponent("texture"));

        Manager.addComponent(entity3,new MeshComponent(Quad.vertices,Quad.textureCoords,Quad.indieces));
        Manager.addComponent(entity3,new TransformComponent(new Vector3f(2,1,0),0,0,0,1,2,1));
        Manager.addComponent(entity3,new TextureComponent("texture"));

        entity4.setParentID(entity3.getID());
        Manager.addComponent(entity4,new MeshComponent(Quad.vertices,Quad.textureCoords,Quad.indieces));
        Manager.addComponent(entity4,new TransformComponent(new Vector3f(-3f,1,0),0,0,0,1,1,1));
        Manager.addComponent(entity4,new TextureComponent("texture"));

        Manager.addComponent(player,new MeshComponent(Quad.vertices,Quad.textureCoords,Quad.indieces));
        Manager.addComponent(player,new TransformComponent(new Vector3f(0,0,0),0,0,0,1,1,1));
        Manager.addComponent(player,new PhysicsComponent());
        Manager.addComponent(player,new TextureComponent("texture"));

        RenderSystem rsys = new RenderSystem();
        InheritanceSystem insys = new InheritanceSystem();
        PhysicsSystem psys = new PhysicsSystem();
        InputSystem isys = new InputSystem();
        MovementSystem msys = new MovementSystem();

        Manager.addSystem(rsys);
        Manager.addSystem(insys);
        Manager.addSystem(psys);
        Manager.addSystem(msys);
        Manager.addSystem(isys);

        //after loading all components
        for(var sys : Manager.getSystems()){
            sys.start();
        }
    }

    @Override
    public void render() {
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
}
