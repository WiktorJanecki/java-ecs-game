package components;


public class CameraComponent extends Component {
    private float pitch;
    private float yaw;
    private float roll;

    public CameraComponent(float pitch, float yaw, float roll) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    public void increaseRotation(float pitch, float yaw, float roll){
        this.pitch += pitch;
        this.yaw   += yaw;
        this.roll  += roll;
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
