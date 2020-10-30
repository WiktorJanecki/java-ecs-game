package render.shaders;

import entities.Camera;
import org.joml.Matrix4f;
import tools.MatrixMath;

public class StaticShader extends ShaderProgram{

    private static final String VERTEX_FILE = "src/render/shaders/vertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/render/shaders/fragmentShader.glsl";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1,"textureCoords");
    }
    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix,matrix);
    }
    public void loadProjectionMatrix(Matrix4f matrix){super.loadMatrix(location_projectionMatrix,matrix); }
    public void loadViewMatrix(Camera camera){
        super.loadMatrix(location_viewMatrix,MatrixMath.createViewMatrix(camera));
    }




}