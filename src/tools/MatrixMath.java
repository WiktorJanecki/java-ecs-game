package tools;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MatrixMath {
    public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float sx, float sy, float sz){
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.translate(translation);
        matrix.rotate(rx,new Vector3f(1,0,0));
        matrix.rotate(ry,new Vector3f(0,1,0));
        matrix.rotate(rz,new Vector3f(0,0,1));
        matrix.scale(sx,sy,sz);
        return matrix;
    }
}
