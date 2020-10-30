package render;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import render.models.RawModel;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Loader {

    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();

    /**
     * Load vertices and other arrays to VAO
     * @param vertices
     * @param textureCoords
     * @param indices
     * @return
     */
    public RawModel loadToVAO(float[] vertices,float[] textureCoords , int[] indices){
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0,3,vertices);
        storeDataInAttributeList(1,2,textureCoords);
        unbindVAO();
        return new RawModel(vaoID,indices.length);
    }

    /**
     * Load texture and returns the id of it (PNG)
     * @param path path to png texture relative from res folder
     * @return
     */
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
                System.err.println("Failed to load texture file: "+path+"\n");
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

    /**
     * Delete all buffers
     */
    public void cleanUp(){
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
     * Create unique vertex array object id
     * @return
     */
    private int createVAO(){
        int vaoID = glGenVertexArrays();
        vaos.add(vaoID);
        glBindVertexArray(vaoID);
        return vaoID;
    }

    /**
     * Move vbo to vao
     * @param attributeNumber pointer
     * @param coordinateSize count of coordinates (2 for 2D, 3 for 3D)
     * @param data array of coordinates
     */
    private void storeDataInAttributeList(int attributeNumber,int coordinateSize, float[]data){
        int vboID = glGenBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ARRAY_BUFFER,vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber,coordinateSize,GL_FLOAT,false,0,0);
        glBindBuffer(GL_ARRAY_BUFFER,0);
    }
    private void unbindVAO(){
        glBindVertexArray(0);
    }

    /**
     * Store indices in vao
     * @param indices
     */
    private void bindIndicesBuffer(int[] indices){
        int vboID = glGenBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
    }

    private FloatBuffer storeDataInFloatBuffer(float[]data){
        FloatBuffer buffer= BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
    private IntBuffer storeDataInIntBuffer(int[]data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
