package managers;

import entities.Entity;
import components.Component;
import systems.System;

import java.util.LinkedList;

public class Manager extends Throwable {
    LinkedList<Entity> entities = new LinkedList<>();
    LinkedList<System> systems = new LinkedList<>();

    public void addEntity(Entity entity){
        entities.push(entity);
    }
    public void clearEntities(Entity entity){
        entities.clear();
    }

    public void addComponent(Entity entity,Component component){
        if(! this.hasComponent(entity,component.getClass())){
            entity.components.push(component);
        }
    }
    public void removeComponent(Entity entity, Class<? extends Component> cls){
        entity.components.removeIf(component -> component.getClass() == cls);
    }
    public <T extends Component> T getComponent(Entity entity, Class<? extends Component> cls) throws Exception{
        for(var comp : entity.components){
            if(comp.getClass() == cls){
                return (T) comp;
            }
        }
        throw new Exception("Failed to find "+cls.getName()+" component in entity with id:" + entity.getID());
    }

    public boolean hasComponent(Entity entity, Class<? extends Component> cls){
        for(var component : entity.components){
            if(component.getClass() == cls){
                return true;
            }
        }
        return false;
    }
    public LinkedList<Entity> arrayOfEntitiesWith(Component component, Class<? extends Component> cls){
        LinkedList<Entity> list = new LinkedList<>();
        for(var ent : entities){
            if(hasComponent(ent,cls)){
                list.push(ent);
            }
        }
        return list;
    }

    public void addSystem(System system){
        for(var sys : systems){
            if(sys.getClass() == system.getClass()){
                return;
            }
        }
        systems.push(system);
    }
    public <T extends System> void removeSystem(System system){
        systems.remove(system);
    }
    public <T extends System> T getSystems(Class<? extends System> cls) throws Exception{
        for(System sys : systems){
            if(sys.getClass() == cls){
                return (T) sys;
            }
        }
        throw new Exception("Failed to get " + cls.getName());
    }
}
