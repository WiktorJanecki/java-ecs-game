package systems.shaders;

import components.CameraComponent;
import components.TransformComponent;
import org.joml.Matrix4f;
import tools.MatrixMath;

public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/systems/shaders/vertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/systems/shaders/fragmentShader.glsl";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    /**
     * Set "pointers" for for uniform variables
     * Using them it is easily possible to get or set variables in shader
     */
    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
    }

    /**
     * Share vbo information to shader
     */
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1,"textureCoords");
    }
    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix,matrix);
    }
    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(location_projectionMatrix,matrix);
    }
    public void loadViewMatrix(CameraComponent camera){
        super.loadMatrix(location_viewMatrix,MatrixMath.createViewMatrix(camera));
    }




}