package managers;

import entities.Entity;
import components.Component;
import events.*;
import state.State;
import systems.System;
import systems.onEvent;

import java.util.LinkedList;

public class Manager extends Throwable {
    private static LinkedList<Entity> entities = new LinkedList<>();
    private static LinkedList<System> systems = new LinkedList<>();
    private static LinkedList<Object> instances = new LinkedList<>();
    private static int lastID = -1;

    public static void init(){
        for(var sys : systems){
            instances.push(sys);
        }
        instances.push(new ShaderManager());
        instances.push(new StateManager());
        instances.push(new TimeManager());
        instances.push(new WindowManager());

        instances.push(StateManager.getCurrent());
    }

    public static void addEntity(Entity entity){
        entity.setID(generateID());
        entities.push(entity);
    }
    public static Entity getEntity(int id) throws Exception{
        for(var ent : entities){
            if(ent.getID() == id){
                return ent;
            }
        }
        throw new Exception("Cannot find an entity with id: "+id);
    }
    public static void clearEntities(Entity entity){
        entities.clear();
    }

    public static void addComponent(Entity entity,Component component){
        if(! hasComponent(entity,component.getClass())){
            entity.components.push(component);
        }
    }
    public static void removeComponent(Entity entity, Class<? extends Component> cls){
        entity.components.removeIf(component -> component.getClass() == cls);
    }
    public static <T extends Component> T getComponent(Entity entity, Class<? extends Component> cls) throws Exception{
        for(var comp : entity.components){
            if(comp.getClass() == cls){
                return (T) comp;
            }
        }
        throw new Exception("Failed to find "+cls.getName()+" component in entity with id:" + entity.getID());
    }

    public static boolean hasComponent(Entity entity, Class<? extends Component> cls){
        for(var component : entity.components){
            if(component.getClass() == cls){
                return true;
            }
        }
        return false;
    }
    public static LinkedList<Entity> arrayOfEntitiesWith(Class<? extends Component> cls){
        LinkedList<Entity> list = new LinkedList<>();
        for(var ent : entities){
            if(hasComponent(ent,cls)){
                list.push(ent);
            }
        }
        return list;
    }
    public static void addFlag(Entity entity, String flag){
        entity.flags.push(flag);
    }

    public static void removeFlag(Entity entity, String flag){
        entity.flags.remove(flag);
    }
    public static boolean hasFlag(Entity entity,String flag){
        boolean ans = false;
        for(var f : entity.flags){
            if(f == flag){
                ans = true;
            }
        }
        return ans;
    }

    public static void addSystem(System system){
        for(var sys : systems){
            if(sys.getClass() == system.getClass()){
                return;
            }
        }
        systems.push(system);
    }
    public static <T extends System> void removeSystem(System system){
        systems.remove(system);
    }
    public static <T extends System> T getSystem(Class<? extends System> cls) throws Exception{
        for(System sys : systems){
            if(sys.getClass() == cls){
                return (T) sys;
            }
        }
        throw new Exception("Failed to get " + cls.getName());
    }
    public static LinkedList<System> getSystems(){
        return systems;
    }


    public static void initEvent(Event event){
        for(var ent: getInterfacesImplementations(onEvent.class)){
            ((onEvent) ent).onEvent(event);
        }
    }

    public static LinkedList<Object> getInterfacesImplementations(Class<?> cls){
        LinkedList<Object> list = new LinkedList<>();
        for(var el : instances){
            if(cls.isInstance(el)){
                list.push(el);
            }
        }
        return list;
    }


    private static int generateID(){
        lastID++;
        return lastID;
    }
    public static void cleanUp(){
        entities.clear();
        systems.clear();
        instances.clear();
        lastID = -1;
    }
}
