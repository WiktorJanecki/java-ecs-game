package entities;

import components.Component;

import java.util.LinkedList;

public class Entity {
    public LinkedList<Component> components = new LinkedList<Component>();

    public Entity(){}

    public int getID() {
        return 0;
    }
}
