public class Main {
    public static void main(String[] args) {
        Matrix2d mat1 = new Matrix2d();
        Matrix2d mat2 = new Matrix2d();
        Matrix2d mat3 = new Matrix2d();

        int[] size = {2,2};
        float[] row = {3,3};
        int[] num = {0,1,2};

        mat1.createEmpty(size);
        mat1.populateRow(num[0],row);
        mat1.populateRow(num[1],row);
        System.out.println("three squared is not 81");
        mat1.raisePower(num[2]);
        mat1.print();


    }
    public static void traceFrame(){
        // trace function but only one iteration
    }
}