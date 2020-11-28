package events;

public class KeyboardEvent extends Event {
    public int key = 0;
    public KeyboardEvent(int key){
        this.key = key;
    }
}
