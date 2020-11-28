package components;

public class TextureComponent extends Component{
    private String path;

    public TextureComponent(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
