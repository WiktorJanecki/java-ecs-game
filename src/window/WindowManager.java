package window;

import org.lwjgl.opengl.GL;
import tools.git;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

public class WindowManager {

    int WIDTH = 1280, HEIGHT = 720;
    long window;

    public WindowManager(){

    }
    //with title
    public void createWindow(int width, int height, String title){
        this.WIDTH = width;
        this.HEIGHT = height;
        this.window = glfwCreateWindow(WIDTH, HEIGHT, title, NULL, NULL);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
    }
    //without title
    public void createWindow(int width, int height){
        this.WIDTH = width;
        this.HEIGHT = height;
        this.window = glfwCreateWindow(WIDTH, HEIGHT, "dreamRPG", NULL, NULL);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
    }
    public long getWindow(){
        return this.window;
    }
    public String getDeveloperTitle(){
        String title = "test";
        String space = "               ";

        try {
            String branch = git.getCurrentGitBranch();
            String count = git.getTotalCommitCount();
            title = "TEST"+space+"branch: " + branch +space+"commit: " + count;
        }
        catch(Exception e){
            System.out.println("Failed to create developer title");
        }
        return title;
    }
}
