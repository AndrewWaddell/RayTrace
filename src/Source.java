public class Source {
    // used to generate a group of rays
    Matrix2d points;
    Matrix2d unit;
    public Source(){
        // creates the default source
        points = new Matrix2d(new double[][]{{0,1,2},{0,0,0},{0,0,0}});
        unit = new Matrix2d(new double[][]{{0,0,0},{0,0,0},{1,1,1}});
    }
}
