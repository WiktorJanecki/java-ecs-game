package state;


import components.CameraComponent;
import components.MeshComponent;
import components.TextureComponent;
import components.TransformComponent;
import entities.Entity;
import managers.Manager;
import org.joml.Vector3f;
import shapes.Quad;
import systems.RenderSystem;

public class GameState extends State {

    private Entity entity;
    private Entity camera;

    @Override
    public void start(){

        entity = new Entity();
        camera = new Entity();
        Manager.addEntity(entity);
        Manager.addEntity(camera);

        Manager.addComponent(camera,new TransformComponent(new Vector3f(0,0,5f),0,0,0,1,1,1));
        Manager.addComponent(camera,new CameraComponent(0,0,0));

        Manager.addComponent(entity,new MeshComponent(Quad.vertices,Quad.textureCoords,Quad.indieces));
        Manager.addComponent(entity,new TransformComponent(new Vector3f(0,0,0),0,0,0,1,1,1));
        Manager.addComponent(entity,new TextureComponent("texture"));

        RenderSystem rsys = new RenderSystem();
        Manager.addSystem(rsys);

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
        try{
            TransformComponent transf = Manager.getComponent(entity,TransformComponent.class);
            transf.increaseRotation(0.1f,0.1f,0.1f);
        }
        catch(Exception e){
            System.err.println(e);
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
