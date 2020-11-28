package components;

import org.joml.Vector3f;

public class TransformComponent extends Component {
    public Vector3f position;
    public int rotationX,rotationY,rotationZ;
    public int scaleX, scaleY,scaleZ;

    public TransformComponent(Vector3f position, int rotationX, int rotationY, int rotationZ, int scaleX, int scaleY, int scaleZ) {
        this.position = position;
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.rotationZ = rotationZ;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
    }

    public void increasePosition(float x, float y, float z){
        this.position.x += x;
        this.position.y += y;
        this.position.z += z;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public int getRotationX() {
        return rotationX;
    }

    public void setRotationX(int rotationX) {
        this.rotationX = rotationX;
    }

    public int getRotationY() {
        return rotationY;
    }

    public void setRotationY(int rotationY) {
        this.rotationY = rotationY;
    }

    public int getRotationZ() {
        return rotationZ;
    }

    public void setRotationZ(int rotationZ) {
        this.rotationZ = rotationZ;
    }

    public int getScaleX() {
        return scaleX;
    }

    public void setScaleX(int scaleX) {
        this.scaleX = scaleX;
    }

    public int getScaleY() {
        return scaleY;
    }

    public void setScaleY(int scaleY) {
        this.scaleY = scaleY;
    }

    public int getScaleZ() {
        return scaleZ;
    }

    public void setScaleZ(int scaleZ) {
        this.scaleZ = scaleZ;
    }
}
