package components;


import org.joml.Vector3f;

public class CameraComponent extends Component {
    private Vector3f position;
    private float pitch;
    private float yaw;
    private float roll;

    public CameraComponent(Vector3f position, float pitch, float yaw, float roll) {
        this.position = position;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    public void increaseRotation(float pitch, float yaw, float roll){
        this.pitch += pitch;
        this.yaw   += yaw;
        this.roll  += roll;
    }
    public Vector3f getPosition(){
        return this.position;
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
