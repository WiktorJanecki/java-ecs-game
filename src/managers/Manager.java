package managers;

import entities.Entity;
import components.Component;
import events.Event;
import systems.System;

import java.util.LinkedList;

public class Manager extends Throwable {
    private static LinkedList<Entity> entities = new LinkedList<>();
    private static LinkedList<System> systems = new LinkedList<>();
    private static LinkedList<Event> events = new LinkedList<>();
    private static LinkedList<Class<? extends Event>> listenings = new LinkedList<>();
    private static int lastID = -1;

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


    public static void listen(Class<? extends Event> cls){
        listenings.push(cls);
    }
    public static void initEvent(Event event){
        events.push(event);
    }
    public static void clearEvents(){
        events.clear();
    }
    public static  <T extends Event> LinkedList<T> getEvent(Class<? extends Event> cls){
        LinkedList<T> list = new LinkedList<>();
        for(var ev : events){
            if(ev.getClass() == cls){
                list.push((T) ev);
            }
        }
        return list;
    }
    public static boolean isListening(Class<? extends Event> cls){
        return listenings.contains(cls);
    }




    private static int generateID(){
        lastID++;
        return lastID;
    }
    public static void cleanUp(){
        entities.clear();
        systems.clear();
        events.clear();
        listenings.clear();
        lastID = -1;
    }
}
