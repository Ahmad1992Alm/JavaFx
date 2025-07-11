import java.util.List;

public class CellSpace {
    private static int counter = 1;

    private String id;
    private List<Polygon> polygons;
    private StatePoint state;

    public CellSpace() {
        this.id = "Room" + counter++;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(List<Polygon> polygons) {
        this.polygons = polygons;
    }

    public StatePoint getState() {
        return state;
    }

    public void setState(StatePoint state) {
        this.state = state;
    }

    public StatePoint calculateState() {
        if (this.state == null) {
            StatePoint state = new StatePoint();
            state.setPosition(computeCentroid(this));
            this.state = state;
        }
        return state;
    }

    private Vector3d computeCentroid(CellSpace cellspace) {
        List<Polygon> polys = cellspace.getPolygons();
        double x = 0;
        double y = 0;
        double z = 0;
        int count = 0;
        if (polys != null) {
            for (Polygon poly : polys) {
                for (Vector3d v : poly.getVertices()) {
                    x += v.x;
                    y += v.y;
                    z += v.z;
                    count++;
                }
            }
        }
        return new Vector3d((float)(x / count), (float)(y / count), (float)(z / count));
    }
}
