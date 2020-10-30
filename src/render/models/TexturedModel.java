package render.models;

public class TexturedModel {
    private RawModel model;
    private int texture;

    /**
     * Model with vertices and texture, extends RawModel
     * @param model The source model, is used to get vertices from
     * @param texture The texture id
     */
    public TexturedModel(RawModel model, int texture){
        this.model = model;
        this.texture = texture;
    }

    public RawModel getModel() {
        return model;
    }

    public int getTexture() {
        return texture;
    }
}
