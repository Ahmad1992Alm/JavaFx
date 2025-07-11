import java.util.List;

public class Polygon {
    private List<Vector3d> vertices;

    public Polygon(List<Vector3d> vertices) {
        this.vertices = vertices;
    }

    public List<Vector3d> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vector3d> vertices) {
        this.vertices = vertices;
    }
}
