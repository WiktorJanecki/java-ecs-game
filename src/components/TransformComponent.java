package components;

import org.joml.Vector2f;

public class TransformComponent extends Component {
    public Vector2f absolutePosition;
    public Vector2f relativePosition;
    public float rotationX,rotationY,rotationZ;
    public float scaleX, scaleY,scaleZ;

    public TransformComponent(Vector2f position, int rotationX, int rotationY, int rotationZ, int scaleX, int scaleY ) {
        this.relativePosition = position;
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.rotationZ = rotationZ;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public Vector2f getRelativePosition() {
        return relativePosition;
    }

    public void setRelativePosition(Vector2f relativePosition) {
        this.relativePosition = relativePosition;
    }

    public void increasePosition(float x, float y){
        this.absolutePosition.x += x;
        this.absolutePosition.y += y;
    }
    public void increaseRotation(float rx, float ry, float rz){
        rotationX += rx;
        rotationY += ry;
        rotationZ += rz;
    }

    public Vector2f getPosition() {
        return absolutePosition;
    }

    public void setPosition(Vector2f position) {
        this.absolutePosition = position;
    }

    public float getRotationX() {
        return rotationX;
    }

    public void setRotationX(int rotationX) {
        this.rotationX = rotationX;
    }

    public float getRotationY() {
        return rotationY;
    }

    public void setRotationY(int rotationY) {
        this.rotationY = rotationY;
    }

    public float getRotationZ() {
        return rotationZ;
    }

    public void setRotationZ(int rotationZ) {
        this.rotationZ = rotationZ;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(int scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(int scaleY) {
        this.scaleY = scaleY;
    }

    public float getScaleZ() {
        return scaleZ;
    }

    public void setScaleZ(int scaleZ) {
        this.scaleZ = scaleZ;
    }
}
