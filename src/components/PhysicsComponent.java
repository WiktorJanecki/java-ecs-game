package components;

import org.joml.Vector2f;

public class PhysicsComponent extends Component {
    private Vector2f velocity;
    private Vector2f acceleration;
    private float friction;

    public PhysicsComponent(){
        this.velocity = new Vector2f(0,0);
        this.acceleration = new Vector2f(0,0);
        this.friction = 3f;
    }

    public void increaseVelocity(float x,float y){
        this.velocity.x += x;
        this.velocity.y += y;
    }
    public void increaseAcceleration(float x,float y){
        this.acceleration.x += x;
        this.acceleration.y += y;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }
    public void setVelocityX(float x){this.velocity.x = x;}
    public void setVelocityY(float y){this.velocity.y = y;}

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }
    public Vector2f getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2f acceleration) {
        this.acceleration = acceleration;
    }
}
