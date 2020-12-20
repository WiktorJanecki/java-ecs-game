package systems;

import components.CameraComponent;
import components.MeshComponent;
import components.TextureComponent;
import components.TransformComponent;
import managers.Manager;
import managers.ShaderManager;
import managers.WindowManager;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;
import systems.shaders.StaticShader;
import tools.MatrixMath;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.stbi_load;

public class RenderSystem extends System {

    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();

    private StaticShader shader = ShaderManager.getShader();

    public RenderSystem(){}

    @Override
    public void start() {
        for(var ent : Manager.arrayOfEntitiesWith(MeshComponent.class)){
            try {
                if(Manager.hasComponent(ent,MeshComponent.class)) {
                    MeshComponent mesh = Manager.getComponent(ent, MeshComponent.class);
                    int vaoID = createVAO();
                    bindIndicesBuffer(mesh.getIndices());
                    storeDataInAttributeList(0,2,mesh.getPositions());
                    storeDataInAttributeList(1,2,mesh.getTextures());
                    unbindVAO();
                    mesh.setVaoID(vaoID);
                    mesh.setVertexCount(mesh.getIndices().length);
                    mesh.setPositions(null);
                    mesh.setTextures(null);
                    mesh.setIndices(null);
                    if(Manager.hasComponent(ent,TextureComponent.class)) {
                        TextureComponent txt = Manager.getComponent(ent, TextureComponent.class);
                        mesh.setTextureID(loadTexture(txt.getPath()));
                    }
                }
            }
            catch(Exception e){
                java.lang.System.err.println(e);
            }
        }
    }

    @Override
    public void render() {
        shader.start();
        for(var ent : Manager.arrayOfEntitiesWith(MeshComponent.class)){
            try {
                if(Manager.hasComponent(ent,TransformComponent.class)){
                    renderWithShader(Manager.getComponent(ent,MeshComponent.class),Manager.getComponent(ent,TransformComponent.class));
                }
                else {
                    renderShaderless(Manager.getComponent(ent, MeshComponent.class));
                }
            }
            catch(Exception e){
                java.lang.System.err.println(e);
            }
        }
        shader.stop();
    }

    @Override
    public void update() {

    }

    @Override
    public void end() {
        for(int vao:vaos){
            glDeleteVertexArrays(vao);
        }
        for(int vbo:vbos){
            glDeleteBuffers(vbo);
        }
        for(int texture:textures){
            glDeleteTextures(texture);
        }
        shader.cleanUp();
    }

    private void renderShaderless(MeshComponent mesh){
        glBindVertexArray(mesh.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, mesh.getTextureID());
        glDrawElements(GL_TRIANGLES,mesh.getVertexCount(),GL_UNSIGNED_INT,0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
    private void renderWithShader(MeshComponent mesh, TransformComponent transf){

        glBindVertexArray(mesh.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        Matrix4f transformationMatrix = MatrixMath.createTransformationMatrix(transf.getPosition(),transf.getRotationX(),transf.getRotationY(),transf.getRotationZ(),transf.getScaleX(),transf.getScaleY(),transf.getScaleZ());
        shader.loadTransformationMatrix(transformationMatrix);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, mesh.getTextureID());
        glDrawElements(GL_TRIANGLES,mesh.getVertexCount(),GL_UNSIGNED_INT,0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    /**
     * Prepare screen before rendering
     * Clear, depth test and color
     */
    private int createVAO(){
        int vaoID = glGenVertexArrays();
        vaos.add(vaoID);
        glBindVertexArray(vaoID);
        return vaoID;
    }
    private void unbindVAO(){
        glBindVertexArray(0);
    }

    private void storeDataInAttributeList(int attributeNumber,int coordinateSize, float[]data){
        int vboID = glGenBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ARRAY_BUFFER,vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber,coordinateSize,GL_FLOAT,false,0,0);
        glBindBuffer(GL_ARRAY_BUFFER,0);
    }

    public int loadTexture(String path) {

        int textureID;
        int width, height;
        ByteBuffer image;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            image = stbi_load("res/"+path+".png", w, h, comp, 4);
            if (image == null) {
                java.lang.System.err.println("Failed to load texture file: "+path+"\n");
            }
            width = w.get();
            height = h.get();
        }

        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        textures.add(textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST); //sets MINIFICATION filtering to nearest
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST); //sets MAGNIFICATION filtering to nearest
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

        return textureID;
    }



    private void bindIndicesBuffer(int[] indices){
        int vboID = glGenBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
    }

    private FloatBuffer storeDataInFloatBuffer(float[]data){
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(data.length);
            buffer.put(data);
            buffer.flip();
            return buffer;
        }
    }
    private IntBuffer storeDataInIntBuffer(int[]data) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer buffer = stack.mallocInt(data.length);
            buffer.put(data);
            buffer.flip();
            return buffer;
        }
    }
}
