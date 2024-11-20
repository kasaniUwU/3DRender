import java.util.ArrayList;
import java.util.List;

public class Camera {
    public Vector3 position;
    public Vector3 rotation;
    public Vector3 scale;
    public Camera(){
    }
    /*
    rendering hapens in 3 steps
    1.translation
    2.rotation
    3,scaling
     */
    public List<List<int[]>> renderMesh(Mesh a){
        List<Vector3> points=(List<Vector3>)a.points;
        int[] xPoly = new int[points.size()];
        int[] yPoly = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            Vector3 point = points.get(i);
            points.set(i, Vector3.subtract(point, position));
        }

        // Rotate around X axis
        for (int i = 0; i < points.size(); i++) {
            Vector3 point = points.get(i);
            points.set(i, new Vector3(
                    point.x,
                    Math.cos(rotation.x) * point.y - Math.sin(rotation.x) * point.z,
                    Math.sin(rotation.x) * point.y + Math.cos(rotation.x) * point.z
            ));
        }

        // Rotate around Y axis
        for (int i = 0; i < points.size(); i++) {
            Vector3 point = points.get(i);
            points.set(i, new Vector3(
                    Math.cos(rotation.y) * point.x + Math.sin(rotation.y) * point.z,
                    point.y,
                    Math.cos(rotation.y) * point.z - Math.sin(rotation.y) * point.x
            ));
        }

        // Rotate around Z axis
        for (int i = 0; i < points.size(); i++) {
            Vector3 point = points.get(i);
            points.set(i, new Vector3(
                    Math.cos(rotation.z) * point.x - Math.sin(rotation.z) * point.y,
                    Math.sin(rotation.z) * point.x + Math.cos(rotation.z) * point.y,
                    point.z
            ));
        }

        // Project 3D points onto 2D plane
        for (int i = 0; i < points.size(); i++) {
            Vector3 point = points.get(i);
            if (point.z != 0) { // Avoid division by zero
                xPoly[i] = (int) ((point.x / point.z) * scale.x + scale.x / 2);
                yPoly[i] = (int) ((point.y / point.z) * scale.y + scale.y / 2);
            } else {
                xPoly[i] = (int) (point.x * scale.x + scale.x / 2);
                yPoly[i] = (int) (point.y * scale.y + scale.y / 2);
            }
        }
        List<List<int[]>> out=new ArrayList<>();
        for (List<Integer> face : a.faces) {
            int[] tempX = new int[face.size()];
            int[] tempY = new int[face.size()];

            for (int j = 0; j < face.size(); j++) {
                int vertexIndex = face.get(j) - 1; // Assuming faces are 1-indexed
                tempX[j] = xPoly[vertexIndex];
                tempY[j] = yPoly[vertexIndex];
            }

            List<int[]> facePoints = new ArrayList<>();
            facePoints.add(tempX);
            facePoints.add(tempY);
            out.add(facePoints);
        }

        return out;
    }
    public List<List<Vector3>> renderMeshPoints(Mesh a){
        List<Vector3> points=(List<Vector3>)a.points;
        int[] xPoly = new int[points.size()];
        int[] yPoly = new int[points.size()];
        int[] zPoly = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            Vector3 point = points.get(i);
            points.set(i, Vector3.subtract(point, position));
        }

        // Rotate around X axis
        for (int i = 0; i < points.size(); i++) {
            Vector3 point = points.get(i);
            points.set(i, new Vector3(
                    point.x,
                    Math.cos(rotation.x) * point.y - Math.sin(rotation.x) * point.z,
                    Math.sin(rotation.x) * point.y + Math.cos(rotation.x) * point.z
            ));
        }

        // Rotate around Y axis
        for (int i = 0; i < points.size(); i++) {
            Vector3 point = points.get(i);
            points.set(i, new Vector3(
                    Math.cos(rotation.y) * point.x + Math.sin(rotation.y) * point.z,
                    point.y,
                    Math.cos(rotation.y) * point.z - Math.sin(rotation.y) * point.x
            ));
        }

        // Rotate around Z axis
        for (int i = 0; i < points.size(); i++) {
            Vector3 point = points.get(i);
            points.set(i, new Vector3(
                    Math.cos(rotation.z) * point.x - Math.sin(rotation.z) * point.y,
                    Math.sin(rotation.z) * point.x + Math.cos(rotation.z) * point.y,
                    point.z
            ));
        }

        // Project 3D points onto 2D plane
        for (int i = 0; i < points.size(); i++) {
            Vector3 point = points.get(i);
            if (point.z != 0) { // Avoid division by zero
                xPoly[i] = (int) point.x;
                yPoly[i] = (int) point.y;
                zPoly[i] = (int) point.z;
            }
        }
        List<List<Vector3>> out=new ArrayList<>();
        for (List<Integer> face : a.faces) {
            Vector3[] tempX = new Vector3[face.size()];

            for (int j = 0; j < face.size(); j++) {
                int vertexIndex = face.get(j) - 1; // Assuming faces are 1-indexed
                tempX[j] = new Vector3( xPoly[vertexIndex],yPoly[vertexIndex],zPoly[vertexIndex]);
            }

            List<Vector3> facePoints = new ArrayList<>();

            out.add(List.of(tempX));
        }

        return out;
    }
}
