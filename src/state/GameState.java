package state;


public class GameState extends State {

    public long window;

    @Override
    public void start(long window){
        this.window = window;
    }

    @Override
    public void render() {

    }

    @Override
    public void update() {

    }

    @Override
    protected void tick() {

    }
}
