package entities;

import org.joml.Vector3f;

public class Camera {
    private Vector3f position = new Vector3f(0,0,0);
    private float pitch;
    private float yaw;
    private float roll;

    public Camera(){}

    public Vector3f getPosition() {
        return position;
    }

    public void increasePosition(Vector3f position){
        this.position.x += position.x;
        this.position.y += position.y;
        this.position.z += position.z;
    }
    public void increaseRotation(float pitch, float yaw, float roll){
        this.pitch += pitch;
        this.yaw   += yaw;
        this.roll  += roll;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }
}
