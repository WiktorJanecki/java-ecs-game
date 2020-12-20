package managers;

import state.State;

public class StateManager {
    static State current;
    static State next;


    public static void changeState(State state){
        setNext(state);
        current.end();
        ShaderManager.onStateChange();
        Manager.cleanUp();
        current = next;
        current.start();
    }

    public static State getCurrent(){
        return current;
    }
    public static void setCurrent(State state){
        current = state;
        current.start();
    }
    static void setNext(State nextState){
        next = nextState;
    }
}
