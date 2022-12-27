public class Main {
    public static void main(String[] args) {
        Matrix2d mat1 = new Matrix2d();
        Matrix2d mat2 = new Matrix2d();
        Matrix2d mat3 = new Matrix2d();

        int[] size = {2,2};
        mat1.create(size);
        mat2.create(size);
        mat3.create(size);

        float[] row1 = {1,2};
        float[] row2 = {3,4};
        float[] col1 = {5,6};
        float[] col2 = {7,8};

        int row1i = 0;
        int row2i = 1;
        int col1i = 0;
        int col2i = 1;

        mat1.populateRow(row1i,row1);
        mat1.populateRow(row2i,row2);
        mat2.populateCol(col1i,col1);
        mat2.populateCol(col2i,col2);

        System.out.println("Mat1");
        mat1.print();
        System.out.println("Mat2");
        mat2.print();


        mat3.inherit(mat1.add(mat2));
        System.out.println("Addition");
        mat3.print();

        System.out.println("dot multiply");
        mat3.inherit(mat1.multiplyPiecewise(mat1));
        mat3.print();

        System.out.println("divide");
        int power = -2;
        mat3.inherit(mat1.raisePower(power));
        mat3.print();
    }
    public static void traceFrame(){
        // trace function but only one iteration
    }
}