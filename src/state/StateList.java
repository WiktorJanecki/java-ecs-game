package state;

import java.util.ArrayList;
import java.util.List;

public class StateList {
    public List<State> states = new ArrayList<State>();
    private int stateIndex = 0;
    public boolean ISSTATECHANGING = false;
    public int CHANGINGSTATEINDEX = 0;

    public void cleanUp() {
        for(State state:states){
            state.end();
        }
    }

    public int addState(State state){
        states.add(state);
        stateIndex++;
        return stateIndex - 1;
    }
    public State getState(int index){
        return states.get(index);
    }
    public void changeState(int index){ISSTATECHANGING = true;CHANGINGSTATEINDEX = index;}
}
