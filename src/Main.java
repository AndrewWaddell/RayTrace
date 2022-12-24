public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        // My first comment is to check that git will receive the changes.
        int size = 2;
        Shape myshape = new Shape();
        myshape.matrixManipulation(size);
        Matrix2d x = new Matrix2d();
        int i = 2;
        float[] row = {1,2};
        int[] size2 = {2,2};
        x.populateRow(i,row);
        x.create(size2);
        x.populateRow(i,row);
    }
    public static void traceFrame(){
        // trace function but only one iteration
    }
}