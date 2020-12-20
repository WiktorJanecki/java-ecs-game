package managers;

import components.CameraComponent;
import components.TransformComponent;
import org.joml.Matrix4f;
import systems.shaders.StaticShader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11C.glClear;

public class ShaderManager {
    private static Matrix4f projectionMatrix;
    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float  FAR_PLANE = 1000f;
    private static StaticShader shader = new StaticShader();

    private static CameraComponent camera;

    public static StaticShader getShader(){
        return shader;
    }
    public static void start(){
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
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
        for(var ent : Manager.arrayOfEntitiesWith(CameraComponent.class)){
            try{
                camera = Manager.getComponent(ent,CameraComponent.class);
            }
            catch(Exception e){
                java.lang.System.err.println(e);
            }
        }
    }
    public static void prepare(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glClearColor(1,0.5f,0.5f,1);
        shader.start();
        if(camera == null){
            java.lang.System.err.println("Error: Camera not found. There must be a entity with camera component");
        }else {
            shader.loadViewMatrix(camera);
        }
        shader.stop();
    }

    public static void onStateChange() {
        shader.cleanUp();
        shader = new StaticShader();
    }
}
