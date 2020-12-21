package managers;

import systems.onSecond;

import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;

public class TimeManager {
    private static int fps = 0;
    private static float dt = 0;

    private static long fpsStart = System.currentTimeMillis();
    private static long dtStart = System.currentTimeMillis();

    public static void countDT(){
        dt = (float)(System.currentTimeMillis() - dtStart)/ (float) 1000;
        dtStart = System.currentTimeMillis();
    }

    private static int temporaryFPS = 0;
    public static void countFPS(){
        if(System.currentTimeMillis()-fpsStart >= 1000){
            for(var ent : Manager.getInterfacesImplementations(onSecond.class)){
                ((onSecond)ent).onSecond();
            }
            fps = temporaryFPS;
            temporaryFPS = 0;
            fpsStart = System.currentTimeMillis();
        }
        temporaryFPS++;
    }

    public static int getFPS(){
        return fps;
    }
    public static float getDT(){
        return dt;
    }
}
