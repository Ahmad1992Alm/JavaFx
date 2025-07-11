import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.PerspectiveCamera;
import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class CellSpaceApp extends Application {
    private Group root = new Group();

    @Override
    public void start(Stage stage) {
        Button button = new Button("Load CellSpace");
        button.setOnAction(e -> loadCellSpace());
        root.getChildren().add(button);

        Scene scene = new Scene(root, 800, 600, true);
        scene.setFill(Color.LIGHTGRAY);
        scene.setCamera(new PerspectiveCamera());

        stage.setTitle("CellSpace Viewer");
        stage.setScene(scene);
        stage.show();
    }

    private void loadCellSpace() {
        Group cellGroup = new Group();
        float s = 100; // half size of cube

        // Build CellSpace with six polygons
        Vector3d v000 = new Vector3d(-s, -s, -s);
        Vector3d v100 = new Vector3d(s, -s, -s);
        Vector3d v110 = new Vector3d(s, s, -s);
        Vector3d v010 = new Vector3d(-s, s, -s);

        Vector3d v001 = new Vector3d(-s, -s, s);
        Vector3d v101 = new Vector3d(s, -s, s);
        Vector3d v111 = new Vector3d(s, s, s);
        Vector3d v011 = new Vector3d(-s, s, s);

        List<Polygon> faces = new ArrayList<>();
        faces.add(new Polygon(Arrays.asList(v000, v100, v110, v010))); // front
        faces.add(new Polygon(Arrays.asList(v101, v001, v011, v111))); // back
        faces.add(new Polygon(Arrays.asList(v100, v101, v111, v110))); // right
        faces.add(new Polygon(Arrays.asList(v001, v000, v010, v011))); // left
        faces.add(new Polygon(Arrays.asList(v001, v101, v100, v000))); // top
        faces.add(new Polygon(Arrays.asList(v010, v110, v111, v011))); // bottom

        CellSpace cell = new CellSpace();
        cell.setPolygons(faces);
        cell.calculateState();

        for (Polygon poly : faces) {
            cellGroup.getChildren().add(createFace(poly));
        }

        cellGroup.setTranslateX(400);
        cellGroup.setTranslateY(300);
        cellGroup.getTransforms().addAll(new Rotate(45, Rotate.Y_AXIS), new Rotate(20, Rotate.X_AXIS));

        root.getChildren().add(cellGroup);
    }

    private MeshView createFace(Polygon poly) {
        List<Vector3d> vs = poly.getVertices();
        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(
            vs.get(0).x, vs.get(0).y, vs.get(0).z,
            vs.get(1).x, vs.get(1).y, vs.get(1).z,
            vs.get(2).x, vs.get(2).y, vs.get(2).z,
            vs.get(3).x, vs.get(3).y, vs.get(3).z
        );
        mesh.getTexCoords().addAll(0,0, 1,0, 1,1, 0,1);
        mesh.getFaces().addAll(
            0,0, 1,1, 2,2,
            0,0, 2,2, 3,3
        );
        MeshView view = new MeshView(mesh);
        view.setCullFace(CullFace.BACK);
        view.setMaterial(new PhongMaterial(Color.LIGHTBLUE));
        return view;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
