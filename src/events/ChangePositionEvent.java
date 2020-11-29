package events;

public class ChangePositionEvent extends Event {
    public int getMovedEntityID() {
        return movedEntityID;
    }

    public void setMovedEntityID(int movedEntityID) {
        this.movedEntityID = movedEntityID;
    }

    private int movedEntityID;
}
