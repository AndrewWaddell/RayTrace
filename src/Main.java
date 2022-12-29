public class Main {
    public static void main(String[] args) {


        int[] size = {2,2};
        float[][] vals1 = {{1,2},{3,4}};
        Matrix2d mat1 = new Matrix2d(vals1);
        Matrix2d mat2 = new Matrix2d(new float[][]{{5,6},{7,8}});
        Matrix2d matC = mat1.concatenateCol(mat2);
        matC.print();


    }
    public static void traceFrame(){
        // trace function but only one iteration
    }
}