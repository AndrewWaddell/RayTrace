public class Main {
    public static void main(String[] args) {


        int[] size = {2,2};
        float[][] vals1 = {{2,13},{-9,11},{3,17}};
        Matrix2d mat1 = new Matrix2d("empty",size);
        Matrix2d mat2 = new Matrix2d(vals1);
        mat1.print();
        mat2.print();

    }
    public static void traceFrame(){
        // trace function but only one iteration
    }
}