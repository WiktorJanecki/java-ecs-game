package systems;

import components.PhysicsComponent;
import components.TransformComponent;
import entities.Entity;
import events.ChangePositionEvent;
import managers.Manager;
import managers.TimeManager;

public class PhysicsSystem extends System {

    private final static float gravity = 10f;
    private final static float maxVelocity = 2;

    @Override
    public void start() {

    }

    @Override
    public void render() {

    }

    @Override
    public void update() {
        for(Entity ent : Manager.arrayOfEntitiesWith(PhysicsComponent.class)){
            if(Manager.hasComponent(ent,TransformComponent.class)){
                calculatePhysics(ent);
            }
        }
    }

    @Override
    public void end() {

    }
    private void calculatePhysics(Entity ent){
        try {
            PhysicsComponent pc = (PhysicsComponent) Manager.getComponent(ent,PhysicsComponent.class);
            TransformComponent tc = (TransformComponent) Manager.getComponent(ent,TransformComponent.class);

            float dt = TimeManager.getDT();

            //pc.increaseVelocity(0,-gravity*dt,0); for gravity

            pc.increaseVelocity(pc.getAcceleration().x*dt,pc.getAcceleration().y*dt);
            if(pc.getVelocity().x > 0){
                pc.increaseVelocity(dt*-pc.getFriction(),0);
            }
            if(pc.getVelocity().x < 0){
                pc.increaseVelocity(dt*pc.getFriction(),0);
            }

            if(pc.getVelocity().y > 0){
                pc.increaseVelocity(0,dt*-pc.getFriction());
            }
            if(pc.getVelocity().y < 0){
                pc.increaseVelocity(0,dt*pc.getFriction());
            }

            if(pc.getVelocity().x > maxVelocity){
                pc.setVelocityX(maxVelocity);
            }
            if(pc.getVelocity().x < -maxVelocity){
                pc.setVelocityX(-maxVelocity);
            }

            if(pc.getVelocity().y > maxVelocity){
                pc.setVelocityY(maxVelocity);
            }
            if(pc.getVelocity().y < -maxVelocity){
                pc.setVelocityY(-maxVelocity);
            }

            if(pc.getVelocity().x*dt != 0 && pc.getVelocity().y*dt != 0){
                ChangePositionEvent ev = new ChangePositionEvent();
                ev.setMovedEntityID(ent.getID());
                Manager.initEvent(ev);
            }
            tc.increasePosition(pc.getVelocity().x*dt,pc.getVelocity().y*dt);


        } catch (Exception e) {
            java.lang.System.err.println(e);
        }
    }
}
