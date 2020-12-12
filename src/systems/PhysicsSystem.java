package systems;

import components.PhysicsComponent;
import components.TransformComponent;
import entities.Entity;
import managers.Manager;
import managers.TimeManager;

public class PhysicsSystem extends System {

    private final static float gravity = 10f;

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

            tc.increasePosition(pc.getVelocity().x*dt,pc.getVelocity().y*dt,pc.getVelocity().z*dt);


        } catch (Exception e) {
            java.lang.System.err.println(e);
        }
    }
}
