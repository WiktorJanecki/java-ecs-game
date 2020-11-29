package entities;

import components.Component;

import java.util.LinkedList;

public class Entity {
    public LinkedList<Component> components = new LinkedList<Component>();
    private int id = 0;
    private int parent = -1;
    
    public Entity(){}
    public int getID() {
        return id;
    }
    public void setID(int id){
        this.id = id;
    }
    public void setParentID(int id){
        this.parent = id;
    }
    public int getParentID(){
        return this.parent;
    }
}
