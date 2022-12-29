public class Main {
    public static void main(String[] args) {
        Matrix2d mat1 = new Matrix2d();
        Matrix2d mat2 = new Matrix2d();
        Matrix2d mat3 = new Matrix2d();

        int[] size = {2,2};
        float[][] vals1 = {{2,13},{-9,11},{3,17}};
        float[][] vals2 = {{6,2},{5,8}};
        mat1.create(vals1);
        mat2.create(vals2);
        mat1.print();
        System.out.println("-");
        mat3.create(mat1.transpose());
        mat3.print();

    }
    public static void traceFrame(){
        // trace function but only one iteration
    }
}