package managers;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import systems.onSecond;
import tools.Git;


import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

public class WindowManager implements onSecond {

    public static int WIDTH = 1280, HEIGHT = 720;
    static long window;

    /**
     *  Completely empty
     */
    public WindowManager(){}

    /**
     * Create window with custom title
     * @param width width of the window (must be greater than 0)
     * @param height height of the window (must be greater than 0)
     * @param title title of the window (only on windowed mode)
     */
    public static void createWindow(int width, int height, String title){
        if(width > 0 && height > 0) {
            WIDTH = width;
            HEIGHT = height;
            window = glfwCreateWindow(WIDTH, HEIGHT, title, NULL, NULL);

            //Check is the window well
            if(window == 0){
                System.err.println("Failed to create a window");
            }

        }else{
            System.err.println("Failed to create window (window width and height must be greater than 0)!");
            window = glfwCreateWindow(WIDTH, HEIGHT, "", NULL, NULL);
        }
            setWindowAttributes();
    }

    /**
     * Create window with default title "dreamRPG"
     * @param width width of the window (must be greater than 0)
     * @param height height of the window (must be greater than 0)
     */
    public static void createWindow(int width, int height){
        if(width > 0 && height > 0){
        WIDTH = width;
        HEIGHT = height;
        window = glfwCreateWindow(WIDTH, HEIGHT, "dreamRPG", NULL, NULL);

        //Check is the window well
        if(window == 0){
            System.err.println("Failed to create a window");
        }

        }else{
            System.err.println("Failed to create window (window width and height must be greater than 0)!");
            window = glfwCreateWindow(WIDTH, HEIGHT, "", NULL, NULL);
        }
        setWindowAttributes();
    }

    /**
     * Set default openGL configuration etc,
     */
    private static void setWindowAttributes(){
        //Center window position
        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window,(videoMode.width() - WIDTH) / 2 ,(videoMode.height() - HEIGHT) /2);

        //Pop the window
        glfwShowWindow(window);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glViewport(0,0,WIDTH,HEIGHT);
        glfwSwapInterval(0);
    }

    public static long getWindow(){
        return window;
    }

    /**
     * Create string from git current branch name and total commit count
     * @return string title
     */
    public static String getDeveloperTitle(){
        String title = "test";
        String space = "               "; //15 spaces
        try {
            String branch = Git.getCurrentGitBranch();
            String count = Git.getTotalCommitCount();
            title = "TEST"+space+"branch: " + branch +space+"commit: " + count;
        }
        catch(Exception e){
            System.out.println("Failed to create developer title");
        }
        return title;
    }
    private static String title = WindowManager.getDeveloperTitle();
    @Override
    public void onSecond() {
        glfwSetWindowTitle(WindowManager.getWindow(), (title+ "               FPS : " + "" + TimeManager.getFPS()));
    }
}
