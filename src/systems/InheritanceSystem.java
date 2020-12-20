package systems;

import components.TransformComponent;
import events.ChangePositionEvent;
import events.Event;
import managers.Manager;
import entities.Entity;
import org.joml.Vector3f;

public class InheritanceSystem extends System implements onEvent {
    @Override
    public void start() {
        for(var ent : Manager.arrayOfEntitiesWith(TransformComponent.class)){
            if(ent.getParentID() >= 0){
                try{
                    TransformComponent Ctransf = Manager.getComponent(ent,TransformComponent.class);
                    TransformComponent Ptransf = Manager.getComponent(Manager.getEntity(ent.getParentID()),TransformComponent.class);
                    Ctransf.setPosition( new Vector3f(Ctransf.getRelativePosition().x+Ptransf.getPosition().x,Ctransf.getRelativePosition().y+Ptransf.getPosition().y,Ctransf.getRelativePosition().z+Ptransf.getPosition().z));
                } catch (Exception e) {
                    java.lang.System.err.println(e);
                }
            }
            else{
                try {
                    TransformComponent transf = Manager.getComponent(ent,TransformComponent.class);
                    transf.setPosition(transf.getRelativePosition());
                } catch (Exception e) {
                    java.lang.System.err.println(e);
                }

            }
        }
    }

    @Override
    public void render() {

    }

    @Override
    public void update() {

    }

    @Override
    public void end() {

    }

    private void calculatePositionForMovedObject(Entity entity){
        for(var ent: Manager.arrayOfEntitiesWith(TransformComponent.class)){
            if(entity.getID() == ent.getParentID()){
                try{
                    TransformComponent Ctransf = Manager.getComponent(ent,TransformComponent.class);
                    TransformComponent Ptransf = Manager.getComponent(entity,TransformComponent.class);
                    Ctransf.setPosition( new Vector3f(Ctransf.getRelativePosition().x+Ptransf.getPosition().x,Ctransf.getRelativePosition().y+Ptransf.getPosition().y,Ctransf.getRelativePosition().z+Ptransf.getPosition().z));
                } catch (Exception e) {
                    java.lang.System.err.println(e);
                }
            }
        }
    }

    @Override
    public void onEvent(Event event) {
        if(event.getClass() == ChangePositionEvent.class) {
            ChangePositionEvent cev = (ChangePositionEvent) event;
            try {
                calculatePositionForMovedObject(Manager.getEntity(cev.getMovedEntityID()));
            } catch (Exception e) {
                java.lang.System.err.println(e);
            }
        }
    }
}
