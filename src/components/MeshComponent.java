package components;

public class MeshComponent extends Component {
    private int vaoID;

    public int getVaoID() {
        return vaoID;
    }

    public void setVaoID(int vaoID) {
        this.vaoID = vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    public float[] getPositions() {
        return positions;
    }

    public void setPositions(float[] positions) {
        this.positions = positions;
    }

    public float[] getTextures() {
        return textures;
    }

    public void setTextures(float[] textures) {
        this.textures = textures;
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public int getTextureID() {
        return textureID;
    }

    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }

    private int vertexCount;
    private int textureID;

    float[] positions,textures;
    int[] indices;

    public MeshComponent(float[] positions,float[] textures,int[] indices){
        this.positions = positions;
        this.textures = textures;
        this.indices = indices;
    }
}
