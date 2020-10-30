package render.models;

public class RawModel {
    private int vaoID;
    private int vertexCount;


    /**
     * The simplest model with only vertices
     * @param vaoID Vertex array object id
     * @param vertexCount count of vertices
     */
    public RawModel(int vaoID, int vertexCount) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
