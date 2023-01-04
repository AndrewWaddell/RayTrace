import java.util.ArrayList;

public class Rays {
    int numRays; // number of rays
    Matrix2d points; // coordinates of rays
    Matrix2d unit; // unit vectors of rays
    Matrix2d COB[]; // change of basis matrix
    boolean[] blocked; // has each ray hit a blocker?
    ArrayList<double[][]> pointsAcc; // accumulated points
    ArrayList<double[][]> unitAcc; // accumulated unit vectors
    ArrayList<double[][]> lengthsAcc; // accumulated lengths of each ray
    ArrayList<double[][]> origins; // index of ray in lengthsAcc
    boolean[] inside; // whether an array is inside a shape

    public void combineSources(ArrayList<Source> sources){
        // builds a single instance of rays based on all sources in the scene
        numRays = 0;
        for (Source source : sources){
             numRays += source.points.numCols;
        }
        points = new Matrix2d(new int[]{3,numRays});
        unit = new Matrix2d(new int[]{3,numRays});
        int pointer = 0;
        for (int i=0;i< sources.size();i++){
            for (int j=0;j<3;j++) {
                System.arraycopy(
                        sources.get(i).points.vals[j],
                        0,
                        points.vals[j],
                        pointer,
                        sources.get(i).points.numCols
                );
            }
        }
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
    public void update(boolean[] index, Shape shape){
        // update location and direction of rays at given index to intersecting given shape
        // saves existing values into accumulated list arrays before overwriting values
        // checks if rays have hit a blocker


    }
}
