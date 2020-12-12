package events;

public class KeyboardEvent extends Event {
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    private int key = 0;
    public KeyboardEvent(int key){
        this.key = key;
    }
}
