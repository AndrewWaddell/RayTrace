import java.util.ArrayList;

public class Rays {
    int numRays; // number of rays
    Matrix2d points; // coordinates of rays
    Matrix2d unit; // unit vectors of rays
    Matrix2d COB[]; // change of basis matrix
    ArrayList<double[][]> pointsAcc; // accumulated points
    ArrayList<double[][]> unitAcc; // accumulated unit vectors
    ArrayList<double[][]> lengthsAcc; // accumulated lengths of each ray
    ArrayList<double[][]> origins; // index of ray in lengthsAcc
    boolean[] inside; // whether an array is inside a shape

    public void addSources(){
        // builds rays based on all sources in the scene

        // for now, create dummy rays for testing
        numRays = 3;
        points = new Matrix2d(new double[][]{{0,1,2},{0,0,0},{0,0,0}});
        unit = new Matrix2d(new double[][]{{0,0,0},{0,0,0},{1,1,1}});
    }
    public void createNewBasis(){
        // creates non-unique basis for each ray where the third dimension
        // is in the direction of the ray.
        Matrix2d orth1 = unit.rotate3dVector90Deg(); // first orthogonal vector
        Matrix2d orth2 = orth1.cross(unit); // second orthogonal vector

        Matrix2d[] P = new Matrix2d[numRays]; // refer to create_cob.png
        COB = new Matrix2d[numRays]; // from new basis to old basis
        for (int i=0;i<numRays;i++){
            Matrix2d orth1i = orth1.indexCol(i);
            Matrix2d orth2i = orth2.indexCol(i);
            Matrix2d uniti = unit.indexCol(i);
            P[i] = orth1i.concatenateCol(orth2i.concatenateCol(uniti));
            Matrix2d INV = P[i].inverse3by3();
            COB[i] = INV.normCol();
        }
    }

}
