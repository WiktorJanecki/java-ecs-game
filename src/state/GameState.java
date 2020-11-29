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
import systems.shaders.StaticShader;

public class GameState extends State {

    private long window;
    private StaticShader shader; //Shader for mvp movement
    private Entity entity;
    private Entity camera;
    Manager manager= new Manager();;

    @Override
    public void start(){
        this.window = window;
        this.shader = shader;

        entity = new Entity();
        camera = new Entity();
        manager.addEntity(entity);
        manager.addEntity(camera);

        manager.addComponent(camera,new TransformComponent(new Vector3f(0,0,5f),0,0,0,1,1,1));
        manager.addComponent(camera,new CameraComponent(0,0,0));

        manager.addComponent(entity,new MeshComponent(Quad.vertices,Quad.textureCoords,Quad.indieces));
        manager.addComponent(entity,new TransformComponent(new Vector3f(0,0,0),0,0,0,1,1,1));
        manager.addComponent(entity,new TextureComponent("texture"));

        RenderSystem rsys = new RenderSystem(manager);
        manager.addSystem(rsys);

        //after loading all components
        for(var sys : manager.getSystems()){
            sys.start();
        }
    }

    @Override
    public void render() {
        for(var sys : manager.getSystems()){
            sys.render();
        }
    }

    @Override
    public void update() {
        for(var sys : manager.getSystems()){
            sys.update();
        }
        try{
            TransformComponent transf = manager.getComponent(entity,TransformComponent.class);
            transf.increaseRotation(0.1f,0.1f,0.1f);
        }
        catch(Exception e){
            System.err.println(e);
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
