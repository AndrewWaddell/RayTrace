import java.util.ArrayList;

public class Rays {
    int numRays; // number of rays
    Matrix2d points; // coordinates of rays
    Matrix2d unit; // unit vectors of rays
    ArrayList<float[][]> pointsAcc; // accumulated points
    ArrayList<float[][]> unitAcc; // accumulated unit vectors
    ArrayList<float[][]> lengthsAcc; // accumulated lengths of each ray
    ArrayList<float[][]> origins; // index of ray in lengthsAcc
    boolean[] inside; // whether an array is inside a shape

    public Matrix2d[] createNewBasis(){
        // creates non-unique basis for each ray where the third dimension
        // is in the direction of the ray.
        Matrix2d orth1 = unit.rotate3dVector90Deg(); // first orthogonal vector
        Matrix2d orth2 = orth1.cross(unit); // second orthogonal vector

        Matrix2d[] COB = new Matrix2d[numRays]; // array of change of basis matrices, each matrix for each ray
        for (int i=0;i<numRays;i++){
            // construct each matrix using orth1 orth2 and unit
        }

        return COB;
    }

}
