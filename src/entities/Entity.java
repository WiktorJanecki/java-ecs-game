package entities;

import components.Component;

import java.util.LinkedList;

public class Entity {
    public LinkedList<Component> components = new LinkedList<Component>();
    private int id = 0;
    
    public Entity(){}

    public int getID() {
        return id;
    }
    public void setID(int id){
        this.id = id;
    }
}
