package systems;

import components.MeshComponent;
import components.TransformComponent;
import entities.EntityFlags;
import managers.Manager;
import managers.ShaderManager;
import managers.shaders.StaticShader;
import org.joml.Matrix4f;
import tools.MatrixMath;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class TerrainRendererSystem extends System {
    StaticShader shader = ShaderManager.getShader();
    @Override
    public void start() {

    }

    @Override
    public void render() {
        shader.start();
        for(var ent : Manager.arrayOfEntitiesWith(MeshComponent.class)){
            if(Manager.hasFlag(ent, EntityFlags.RENDERER_TERRAIN)) {
                try {
                    if (Manager.hasComponent(ent, TransformComponent.class)) {
                        MeshComponent mesh = Manager.getComponent(ent, MeshComponent.class);
                        TransformComponent transf =  Manager.getComponent(ent, TransformComponent.class);
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
                    } else {
                        java.lang.System.err.println("Entity with terrain flag must have a transform component");
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
}
