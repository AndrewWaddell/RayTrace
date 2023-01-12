import java.util.ArrayList;

public class Rays {
    int numRays; // number of rays
    Matrix2d points; // coordinates of rays
    Matrix2d unit; // unit vectors of rays
    Matrix2d[] COB; // change of basis matrix
    Matrix2d pointsCOB; // points in terms of the change of basis
    boolean[] blocked; // has each ray hit a blocker?
    ArrayList<Matrix2d> pointsAcc = new ArrayList<>(); // accumulated points
    ArrayList<Matrix2d> unitAcc = new ArrayList<>(); // accumulated unit vectors
    ArrayList<Double> distancesAcc = new ArrayList<>(); // accumulated lengths of each ray
    ArrayList<Integer> origins = new ArrayList<>(); // index of ray in lengthsAcc
    boolean[] inside; // whether an array is inside a shape

    public Rays(ArrayList<Source> sources){
        // builds a single instance of rays based on all sources in the scene
        numRays = 0;
        for (Source source : sources){
             numRays += source.points.numCols;
        }
        points = new Matrix2d(new int[]{3,numRays});
        unit = new Matrix2d(new int[]{3,numRays});
        inside = new boolean[numRays];
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
        pointsAcc.add(points);
        unitAcc.add(unit);
        for (int i=0;i<numRays;i++){
            distancesAcc.add(0D);
            origins.add(i);
        }
        blocked = new boolean[numRays];
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
        // now find the points in terms of the new basis
        for (int i=0;i<numRays;i++){
            pointsCOB.insertCol(points.multiply(COB[i]),i);
        }
    }
    public void update(int i,double d, Matrix2d normal, double nShape, double nScene, boolean blocker){
        // update location and direction of ith ray upon intersection with a shape
        // d is distance to the shape intersection
        // normal is the normal of the shape the ray is intersecting
        // if refractive index of shape (nShape) is 0, this means it is a mirror
        // nScene is refractive index of atmosphere
        //
        // saves existing values into accumulated list arrays before overwriting values
        // checks if rays have hit a blocker
        points.insertCol(points.indexCol(i).add(unit.indexCol(i).multiplyBy(d)),i); // p = p + d*u
        if (nShape>0){
            unit.insertCol(refract(i,normal,nShape,nScene),i);
            inside[i] = !inside[i];
        } else {
            unit.insertCol(reflect(i,normal),i);
        }
        pointsAcc.add(points.indexCol(i));
        unitAcc.add(unit.indexCol(i));
        distancesAcc.add(i,d);
        distancesAcc.add(0D);
        blocked[i] = blocker;
    }
    public Matrix2d reflect(int i,Matrix2d normal){
        // calculate mirror bounce of ray onto plane (defined by normal vector)
        // Implement snell's law in vector form
        // angle of incidence is theta1
        // angle of reflection is theta2
        // v_reflect = l + 2 cos(theta1) n
        // l is the ray vector, n is the normal of the plane, pointing in the direction of the incoming ray
        // n direction is flipped if n points wrong way at beginning
        Matrix2d l = unit.indexCol(i).normCol();
        Matrix2d n; // corrected normal pointing towards incoming ray
        if (cosTheta1(l,normal)<0){ // cos(theta1) must be positive.
            n = normal.multiplyBy(-1);
        } else{
            n = normal;
        }
        return l.add(n.multiplyBy(2*cosTheta1(l,n)));
    }
    public Matrix2d refract(int i,Matrix2d normal,double nShape,double nScene){
        // implements snell's law for refraction at shape boundary, with plane defined by normal
        // implemented in vector form
        // angle of incidence is theta1
        // angle of reflection is theta2
        // ratio r of refractive indexes depends on whether we are entering or exiting shape (r = n1/n2)
        // l is the ray vector, n is the normal of the plane, pointing in the direction of the incoming ray
        // n direction is flipped if n points wrong way at beginning
        //
        // v_refract = rl + (r*cos(theta1)-cos(theta2))n
        Matrix2d l = unit.indexCol(i).normCol();
        Matrix2d n; // corrected normal pointing towards incoming ray
        if (cosTheta1(l,normal)<0){ // cos(theta1) must be positive.
            n = normal.multiplyBy(-1);
        } else{
            n = normal;
        }
        double r;
        if (inside[i]){
            r = nShape/nScene;
        } else{
            r = nScene/nShape;
        }
        return l.multiplyBy(r).add(n.multiplyBy(r*cosTheta1(l,n)-cosTheta2(r,l,n)));
    }
    public double cosTheta1(Matrix2d l,Matrix2d n){
        // cos of angle of incidence using the dot product:
        // n is the normal of the plane (normalised)
        // l is the unit vector of the ray (normalised)
        // cos(theta1) = -n.l
        return n.multiplyBy(-1).dot(l);
    }
    public double cosTheta2(double r,Matrix2d l,Matrix2d n){
        // cos of angle of refraction
        return Math.sqrt(1 - r*r * (1 - cosTheta1(l,n)*cosTheta1(l,n)));
    }
    public boolean areActive(){
        // if there are any rays left that haven't hit a blocker, sensor or boundary,
        // then return true
        // once all rays have finished their journey, return false
        for (boolean ray : blocked){
            if (ray){
                return true;
            }
        }
        return false;
    }
}
