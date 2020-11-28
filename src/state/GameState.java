package state;


import components.MeshComponent;
import components.TextureComponent;
import entities.Entity;
import managers.Manager;
import render.shaders.StaticShader;
import shapes.Quad;
import systems.RenderSystem;

public class GameState extends State {

    private long window;
    private StaticShader shader; //Shader for mvp movement
    private Entity entity;
    Manager manager= new Manager();;

    @Override
    public void start(long window, StaticShader shader, StateList list){
        this.window = window;
        this.shader = shader;

        entity = new Entity();
        manager.addEntity(entity);

        manager.addComponent(entity,new MeshComponent(Quad.vertices,Quad.textureCoords,Quad.indieces));
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
