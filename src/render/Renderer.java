package render;

import render.models.RawModel;
import render.models.TexturedModel;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Renderer {
    public Renderer(){}

    public void prepare(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0,0.5f,0.5f,1);
    };
    public void render(RawModel model){
        glBindVertexArray(model.getVaoID());
        glEnableVertexAttribArray(0);
        glDrawElements(GL_TRIANGLES,model.getVertexCount(),GL_UNSIGNED_INT,0);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    };
    public void render(TexturedModel model){
        RawModel rawModel = model.getModel();
        glBindVertexArray(rawModel.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, model.getTexture());
        glDrawElements(GL_TRIANGLES,rawModel.getVertexCount(),GL_UNSIGNED_INT,0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    };
}
