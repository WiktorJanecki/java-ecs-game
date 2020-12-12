package components;

import org.joml.Vector3f;

public class PhysicsComponent extends Component {
    private Vector3f velocity;
    private float friction;

    public PhysicsComponent(){
        this.velocity = new Vector3f(0,0,0);
    }

    public void increaseVelocity(float x,float y, float z){
        this.velocity.x += x;
        this.velocity.y += y;
        this.velocity.z += z;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }
}
