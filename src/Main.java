public class Main {
    public static void main(String[] args) {


        int[] size = {2,2};
        float[][] vals1 = {{2,13},{-9,11}};
        Matrix2d c = new Matrix2d(new float[][]{{3},{4}});
        Matrix2d mat1 = new Matrix2d(vals1);
        mat1.concatenateCol(c);
        mat1.print();


    }
    public static void traceFrame(){
        // trace function but only one iteration
    }
}