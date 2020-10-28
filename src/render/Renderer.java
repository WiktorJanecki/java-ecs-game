package render;

import render.models.RawModel;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.glClear;
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
        glDrawArrays(GL_TRIANGLES,0,model.getVertexCount());
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    };
}
