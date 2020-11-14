package state;


import entities.Entity;
import org.joml.Vector3f;
import render.Loader;
import render.Renderer;
import render.models.RawModel;
import render.models.TexturedModel;
import render.shaders.StaticShader;
import shapes.Quad;

public class GameState extends State {

    private long window;
    private StaticShader shader; //Shader for mvp movement
    private Renderer renderer;
    private Entity entity;
    private Loader loader;

    @Override
    public void start(long window, StaticShader shader, StateList list){
        this.window = window;
        this.shader = shader;

        loader = new Loader();
        renderer = new Renderer(shader);

        RawModel model = loader.loadToVAO(Quad.vertices,Quad.textureCoords,Quad.indieces);
        int texture = loader.loadTexture("texture");
        TexturedModel texturedModel = new TexturedModel(model,texture);

        entity = new Entity(texturedModel,new Vector3f(0,0,-1),0,0,0,1,1,1);
    }

    @Override
    public void render() {
        renderer.prepare();
        renderer.render(entity,shader);
    }

    @Override
    public void update() {

    }

    @Override
    public void end() {
        shader.cleanUp();
        loader.cleanUp();
    }

    @Override
    protected void tick() {

    }
}