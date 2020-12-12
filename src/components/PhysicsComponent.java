package components;

import org.joml.Vector3f;

public class PhysicsComponent extends Component {
    private Vector3f velocity;
    private Vector3f acceleration;
    private float friction;

    public PhysicsComponent(){
        this.velocity = new Vector3f(0,0,0);
        this.acceleration = new Vector3f(0,0,0);
        this.friction = 3f;
    }

    public void increaseVelocity(float x,float y, float z){
        this.velocity.x += x;
        this.velocity.y += y;
        this.velocity.z += z;
    }
    public void increaseAcceleration(float x,float y, float z){
        this.acceleration.x += x;
        this.acceleration.y += y;
        this.acceleration.z += z;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }
    public void setVelocityX(float x){this.velocity.x = x;}
    public void setVelocityY(float y){this.velocity.y = y;}
    public void setVelocityZ(float z){this.velocity.z = z;}

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }
    public Vector3f getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector3f acceleration) {
        this.acceleration = acceleration;
    }
}
