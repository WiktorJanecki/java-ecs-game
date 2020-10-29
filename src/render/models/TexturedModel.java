package render.models;

public class TexturedModel {
    private RawModel model;
    private int texture;

    public RawModel getModel() {
        return model;
    }

    public int getTexture() {
        return texture;
    }

    public TexturedModel(RawModel model, int texture){
        this.model = model;
        this.texture = texture;
    }
}
