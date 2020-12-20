package managers.shaders;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20C.*;

public abstract class ShaderProgram {
    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public ShaderProgram(String vertexFile, String fragmentFile){
        vertexShaderID = loadShader(vertexFile,GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile,GL_FRAGMENT_SHADER);
        programID = glCreateProgram();
        glAttachShader(programID,vertexShaderID);
        glAttachShader(programID,fragmentShaderID);
        bindAttributes();
        glLinkProgram(programID);
        glValidateProgram(programID);
        getAllUniformLocations();
    }

    protected int getUniformLocation(String uniformName){
        return glGetUniformLocation(programID,uniformName);
    }

    protected abstract void getAllUniformLocations();

    public void start(){
        glUseProgram(programID);
    }
    public void stop(){
        glUseProgram(0);
    }
    public void cleanUp(){
        stop();
        glDetachShader(programID,vertexShaderID);
        glDetachShader(programID,fragmentShaderID);
        glDeleteShader(fragmentShaderID);
        glDeleteShader(vertexShaderID);
        glDeleteProgram(programID);
    }

    protected abstract void bindAttributes();
    protected void bindAttribute(int attribute, String variableName){
        glBindAttribLocation(programID,attribute,variableName);
    }

    protected void loadFloat(int location, float value){
        glUniform1f(location, value);
    }
    protected void loadVector(int location, Vector3f value){
        glUniform3f(location,value.x,value.y,value.z);
    }
    protected void loadBoolean(int location, boolean value){
        glUniform1f(location,value ? 1 : 0);
    }
    protected  void loadMatrix(int location, Matrix4f value){
        value.get(matrixBuffer);
        glUniformMatrix4fv(location, false, matrixBuffer);
    }

    /**
     * Load shader from file
     * @param file file to shader
     * @param type GL shader type
     * @return
     */
    private static int loadShader(String file, int type){
        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
    }
}
