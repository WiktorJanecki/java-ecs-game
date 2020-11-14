package input;

import static org.lwjgl.glfw.GLFW.glfwGetKey;

public class Keyboard {
    private long window;

    public Keyboard(long window){
        this.window = window;
    }

    public boolean isKeyDown(int key){
        if(glfwGetKey(window, key) == 1){
            return true;
        }else{
            return false;
        }
    }
    public static boolean isKeyDown(int key, long window){
        if(glfwGetKey(window, key) == 1){
            return true;
        }else{
            return false;
        }
    }
}
