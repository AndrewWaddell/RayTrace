import java.util.ArrayList;

public class Scene {
    //Source[] sources;
    Rays rays;
    ArrayList<Source> sources = new ArrayList<Source>();
    ArrayList<Shape> shapes = new ArrayList<Shape>();
    double refractiveIndex = 1;

    public void trace(int loopLimit){
        // Traces rays from sources to sensors until no rays are left

        rays = new Rays(sources);
        for (int i=0;i<loopLimit;i++){ // prevents closed loop bouncing
            if (rays.areActive()){
                traceStep();
            }
        }

    }
    public void traceStep(){
        // executes a single step within the trace. All rays bounce to the next shape.
        rays.createNewBasis();
        Matrix2d distance = new Matrix2d(new int[]{rays.numRays,shapes.size()}); // each ray->shape distance
        distance.fillWithItem(Double.POSITIVE_INFINITY); // assume ray doesn't intersect with shape
        Matrix2d[] normals = new Matrix2d[shapes.size()];
        for (int i=0;i<shapes.size();i++){
            shapes.get(i).changeOfBasis(rays);
            if (shapes.get(i).traceLowRes(rays)){
                Matrix2d[] distanceOutput = shapes.get(i).traceDistance(rays);
                distance.insertCol(distanceOutput[0],i);
                normals[i] = distanceOutput[1];
            }
        }
        BooleanArray closestShapes = distance.minRowIndex(); // for each ray, which shape intersection is the closest?
        for (int i=0;i<rays.numRays;i++){
            for (int j=0;j<shapes.size();j++){
                if(closestShapes.vals[i][j]){
                    rays.update(
                            i,
                            distance.vals[i][j],
                            normals[j].indexCol(i),
                            shapes.get(j).refractiveIndex,
                            refractiveIndex,
                            shapes.get(j).BLOCKER);
                }
            }
        }

    }
    public void plot(){
        // plots all objects within the scene onto the canvas for the user to see
        // Plots shapes and rays onto 3D canvas
        // Plots sensor readings onto 2D canvas

        // for now, this function must plot 3 lines onto the 3D canvas
        // I can describe the lines in any way necesary, but for now I assume I
        // need to give the initial point 1 and the second point 2
        // each column is each line, and each row is each dimension x,y,z
        // for example the first lines goes between (0,0,1) and (3,1,0)

        Matrix2d[] points3d = new Matrix2d[2];
        points3d[0] = new Matrix2d(new double[][] {{0,1,5,3,4},{0,0,0,0,0},{1,1,0.5F,0,0}});
        points3d[1] = new Matrix2d(new double[][] {{3,4,2,6,7},{1,1,1,1,1},{0,0,0.5F,1,1}});
        points3d[0].print();
        System.out.println("------");
        points3d[1].print();

        // tip: access matrix values with point1.vals

        // to plot the surface, we plot R^2, plot however you want
        // please use Matrix2d class instead of double[][]
        // I have done this stuff a few times in different languages, so I
        // can help with the process, e.g. grid of points, convert polar to cartesian etc

        Camera camera = new Camera();

        Matrix2d COBinv = new Matrix2d(new double[][]{{1,0,0},{0,1,0},{0,0,0}});
        COBinv.insertCol(camera.direction,2);
        Matrix2d COB = COBinv.inverse3by3();
        for (int i=0; i<points3d[0].numCols;i++){ // which line
            Matrix2d points2d = new Matrix2d(new int[]{2,2});
            for (int j=0;j<2;j++){ // beginning and end
                Matrix2d point3d = COB.multiply(points3d[j].indexCol(i).subtract(camera.location));
                double z = point3d.indexRow(2).vals[0][0];
                for (int k=0;k<2;k++) {
                    points2d.vals[j][k] = point3d.vals[k][0] * camera.invTanTheta / z;
                }
            }
            int x1 = (int)points2d.vals[0][0];
            int y1 = (int)points2d.vals[1][0];
            int x2 = (int)points2d.vals[0][1];
            int y2 = (int)points2d.vals[1][1];

        }

    }
}
