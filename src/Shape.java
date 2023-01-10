public class Shape {
    double refractiveIndex;
    Matrix2d points;
    Matrix2d connectivity; // dim 1 = triangle, dim 2 = 3 points
    Matrix2d[] pointsCOB; // points in terms of new basis, for multiple basis sets
    public Shape(Matrix2d pointsIn,Matrix2d connectivityIn){
        // creates shape with given triangles, default refractive index
        refractiveIndex = 1.52F;
        points = pointsIn;
        connectivity = connectivityIn;
    }
    public Shape(Matrix2d pointsIn,Matrix2d connectivityIn,double refractiveIndexIn){
        // create shape with given triangles and refractive index
        refractiveIndex = refractiveIndexIn;
        points = pointsIn;
        connectivity = connectivityIn;
    }
    public Shape(String filename){
        // import shape from mesh file
        refractiveIndex = 1.52F;
    }
    public Shape(){
        //creates default shape for testing
        refractiveIndex = 1.52F;
        points = new Matrix2d(new double[][]{{1.5,1.5,0,5},{1,-1,1,1},{1,1,0.5,6}});
        connectivity = new Matrix2d(new double[][]{{0,1,2},{0,1,3}}); // each triangle in each row
    }
    public void changeOfBasis(Rays rays){
        // outputs points in terms of new basis from change of basis matrix COB
        // outputs matrix array. one matrix for one ray. Each matrix column for each shape point
        pointsCOB = new Matrix2d[rays.numRays];
        for (int i=0;i<rays.numRays;i++){ // each ray
            pointsCOB[i] = new Matrix2d(new int[]{3,points.numCols});
            for (int j=0;j<points.numCols;j++){ // each shape point
                pointsCOB[i].insertCol(rays.COB[i].multiply(points.indexCol(j)),j);
            }
        }
    }
    public boolean traceLowRes(Rays rays){
        // Does any ray intersect with (or nearby) this shape?
        // Don't waste time tracing if rays aren't even nearby
        double[] xMinMax;
        double[] yMinMax;
        double xCoord;
        double yCoord;
        for (int i=0;i<rays.numRays;i++){
            xCoord = rays.pointsCOB.vals[0][i];
            yCoord = rays.pointsCOB.vals[1][i];
            xMinMax = pointsCOB[i].minMaxRow(0);
            yMinMax = pointsCOB[i].minMaxRow(1);
            boolean xAboveMin = xCoord>=xMinMax[0];
            boolean xBelowMax = xCoord<=xMinMax[1];
            boolean yAboveMin = yCoord>=yMinMax[0];
            boolean yBelowMax = yCoord<=yMinMax[1];
            if (xAboveMin && xBelowMax && yAboveMin && yBelowMax){
                return true; // if it works for one ray, we trace object.
            }

        }
        return false;
    }
    public double[] traceDistance(Rays rays){
        // find the distance to each shape, for each ray
        boolean[] xy = {true,true,false}; // ignore z dimension
        BooleanArray interior = new BooleanArray(new int[]{rays.numRays,connectivity.numRows}); // each ray triangle pair
        for (int i=0;i<rays.numRays;i++){
            for (int j=0;j<connectivity.numRows;j++){
                interior.vals[i][j] = triangleInterior(
                        pointsCOB[i].indexCol(connectivity.vals[j]).indexRow(xy),
                        rays.pointsCOB.indexRow(xy));
            }
        }
        Matrix2d normals = triangleNormals(connectivity.indexRow(interior.orCol()));
        // up to here
        Matrix2d distance = new Matrix2d(interior.size);
        distance.fillWithItem(Double.POSITIVE_INFINITY);

        return new double[]{0};
    }
    public boolean triangleInterior(Matrix2d points,Matrix2d Q){
        // determines whether query point Q is within the triangle
        // defined by points A,B,C forming each column (in any order)
        // first row is x-axis, second row, is y-axis
        // query is 1 column, x&y rows
        // If query lies on edge, it is considered outside

        // Since many points will be outliers, I optimise by rejecting early.

        // Is the query within the box?
        Matrix2d allPoints = points.concatenateCol(Q); // query column index = 3
        if (allPoints.isMaxCol(0,3)){ // is query maximum x
            return false; //
        }
        if (allPoints.isMinCol(0,3)){ // is query minimum x
            return false;
        }
        if (allPoints.isMaxCol(1,3)){ // is query maximum y
            return false; //
        }
        if (allPoints.isMinCol(1,3)){ // is query minimum y
            return false;
        }

        // Define A as triangle point with minimum x, B as maximum x
        int AIndex = points.minColIndex(0);
        int BIndex = points.maxColIndex(0);
        int CIndex = 0;
        for (int i=0;i<3;i++){
            if (i!=AIndex){
                if (i!=BIndex){
                    CIndex = i;
                }
            }
        }
        Matrix2d A = points.indexCol(AIndex);
        Matrix2d B = points.indexCol(BIndex);
        Matrix2d C = points.indexCol(CIndex);


        // is the signed area of triangle ACB the same polarity as AQB?
        // in other words:
        // does a triangle point upwards or downwards?
        // and,
        // do they both point in the same direction?
        Matrix2d ACB = A.concatenateCol(C.concatenateCol(B));
        Matrix2d AQB = A.concatenateCol(Q.concatenateCol(B));
        double ACBsa = ACB.signedArea(); // calculate only once
        if (ACBsa>0F){ // C points upwards
            if (AQB.signedArea()<=0F){ // Q points downwards or flat
                return false; // Q is outside triangle
            }
        } else if (ACBsa<0F) { // C points downwards
            if (AQB.signedArea()>=0F){ // Q points upwards
                return false; // Q outside triangle
            }
        } else { // ACB signed area == 0
            return false; // triangle is side on, ray skims past
        }

        // The remaining triangle region is bounded by two angles
        // first angle about A
        Matrix2d QAB = Q.concatenateCol(A.concatenateCol(B));
        Matrix2d CAB = C.concatenateCol(A.concatenateCol(B));
        // since each angle is within (0,90deg),
        // comparing the cos of both angles == comparing both angles
        if (QAB.cosTheta() > CAB.cosTheta()){
            return false;
        }
        // second angle about B
        Matrix2d ABQ = A.concatenateCol(B.concatenateCol(Q));
        Matrix2d ABC = A.concatenateCol(B.concatenateCol(C));
        if (ABQ.cosTheta() > ABC.cosTheta()){
            return false;
        }
        return true; // query is bounded by both angles
    }
    public Matrix2d triangleNormals(Matrix2d triangles){
        // determine the normal vector of the plane that 3 points sit on.
        // input is a reduced form of connectivity where it only contains
        // the triangles who have ray intersections.
        // plane is spanned by two vectors: v1, v2
        // v1 and v2 are built by connecting the points p
        // cross product v1 and v2 to get answer
        Matrix2d normals = new Matrix2d(new int[]{3,triangles.numRows});
        for (int i=0;i<triangles.numRows;i++){
            Matrix2d p = points.indexCol(triangles.vals[i]);
            Matrix2d v1 = p.indexCol(2).subtract(p.indexCol(0));
            Matrix2d v2 = p.indexCol(1).subtract(p.indexCol(0));
            normals.insertCol(v1.cross(v2).normCol(),i);
        }
        return normals;
    }
}
