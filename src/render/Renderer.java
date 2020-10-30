package render;

import org.joml.Matrix4f;
import render.models.RawModel;
import render.models.TexturedModel;
import tools.MatrixMath;
import window.WindowManager;
import entities.*;
import render.shaders.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Renderer {
    private Matrix4f projectionMatrix;
    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float  FAR_PLANE = 1000f;

    public Renderer(StaticShader shader){
        createProjectionMatrix();
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void prepare(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0,0.5f,0.5f,1);
    };

    private void createProjectionMatrix(){
        float aspectRatio = (float) WindowManager.WIDTH / (float) WindowManager.HEIGHT;
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00(x_scale);
        projectionMatrix.m11(y_scale);
        projectionMatrix.m22(-((FAR_PLANE + NEAR_PLANE) / frustum_length));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
        projectionMatrix.m33(0);
    }

    public void render(RawModel model){
        glBindVertexArray(model.getVaoID());
        glEnableVertexAttribArray(0);
        glDrawElements(GL_TRIANGLES,model.getVertexCount(),GL_UNSIGNED_INT,0);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
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
    public void render(Entity entity, StaticShader shader){
        RawModel rawModel = entity.getModel().getModel();
        glBindVertexArray(rawModel.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        Matrix4f transformationMatrix = MatrixMath.createTransformationMatrix(entity.getPosition(),entity.getRotationX(),entity.getRotationY(),entity.getRotationZ(),entity.getScaleX(),entity.getScaleY(),entity.getScaleZ());
        shader.loadTransformationMatrix(transformationMatrix);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, entity.getModel().getTexture());
        glDrawElements(GL_TRIANGLES,rawModel.getVertexCount(),GL_UNSIGNED_INT,0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

    }
}
