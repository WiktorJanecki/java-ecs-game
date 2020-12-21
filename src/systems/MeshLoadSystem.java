package systems;

import components.MeshComponent;
import components.TextureComponent;
import entities.EntityFlags;
import managers.Manager;
import org.lwjgl.system.MemoryStack;

import javax.swing.text.html.parser.Entity;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.stbi_load;

public class MeshLoadSystem extends System {

    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();

    @Override
    public void start() {
        for(var ent : Manager.arrayOfEntitiesWith(MeshComponent.class)){
            try {
                Manager.addFlag(ent,EntityFlags.RENDERER_DEFAULT);
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
                    if(Manager.hasComponent(ent, TextureComponent.class)) {
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
