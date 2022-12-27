public class Main {
    public static void main(String[] args) {
        Matrix2d mat1 = new Matrix2d();
        Matrix2d mat2 = new Matrix2d();
        Matrix2d mat3 = new Matrix2d();

        int[] size = {2,2};
        float[][] vals1 = {{3,7},{4,9}};
        float[][] vals2 = {{6,2},{5,8}};
        mat1.create(vals1);
        mat2.create(vals2);
        mat3.create(mat1.multiply(mat2));
        mat3.print();

    }
    public static void traceFrame(){
        // trace function but only one iteration
    }
}