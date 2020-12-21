package managers;

import com.sun.tools.jconsole.JConsoleContext;
import state.State;
import systems.onStateChange;

public class StateManager{
    static State current;
    static State next;


    public static void changeState(State state){
        setNext(state);
        current.end();
        for(var ent : Manager.getInterfacesImplementations(onStateChange.class)){
            ((onStateChange)ent).onStateChange();
        }
        Manager.cleanUp();
        setCurrent(next);
    }

    public static State getCurrent(){
        return current;
    }
    public static void setCurrent(State state){
        current = state;
        current.start();
        Manager.init();
    }
    static void setNext(State nextState){
        next = nextState;
    }
}
