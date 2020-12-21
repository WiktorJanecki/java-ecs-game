package systems;

import components.MeshComponent;
import components.TextureComponent;
import components.TransformComponent;
import entities.EntityFlags;
import managers.Manager;
import managers.ShaderManager;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;
import managers.shaders.StaticShader;
import tools.MatrixMath;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.stbi_load;

public class RenderSystem extends System {

    private StaticShader shader = ShaderManager.getShader();

    public RenderSystem(){}

    @Override
    public void start() {

    }

    @Override
    public void render() {
        shader.start();
        for(var ent : Manager.arrayOfEntitiesWith(MeshComponent.class)){
            if(Manager.hasFlag(ent, EntityFlags.RENDERER_DEFAULT)) {
                try {
                    if (Manager.hasComponent(ent, TransformComponent.class)) {
                        renderWithShader(Manager.getComponent(ent, MeshComponent.class), Manager.getComponent(ent, TransformComponent.class));
                    } else {
                        renderShaderless(Manager.getComponent(ent, MeshComponent.class));
                    }
                } catch (Exception e) {
                    java.lang.System.err.println(e);
                }
            }
        }
        shader.stop();
    }

    @Override
    public void update() {

    }

    @Override
    public void end() {

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
}
