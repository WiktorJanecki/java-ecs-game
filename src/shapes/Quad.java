package shapes;

public class Quad {
    public static float[] vertices = {
            -0.5f, 0.5f,
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f,
    };
    public static float[] textureCoords = {
            0,0,
            0,1,
            1,1,
            1,0
    };
    public static int[] indieces = {
        0,1,3,
        3,1,2
    };
}
