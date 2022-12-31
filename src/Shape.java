public class Shape {
    float refractiveIndex;
    public Shape(Matrix2d points,Matrix2d connectivity){
        // creates shape with given triangles, default refractive index
        refractiveIndex = 1.52F;
    }
    public Shape(Matrix2d points,Matrix2d connectivity,float refractiveIndexIn){
        // create shape with given triangles and refractive index
        refractiveIndex = refractiveIndexIn;
    }
    public Shape(String filename){
        // import shape from mesh file
    }
    public void traceLowRes(){
        // Does any ray intersect with this shape?
        // Don't waste time tracing if no rays intersect

    }

}
