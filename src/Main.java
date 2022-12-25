public class Main {
    public static void main(String[] args) {
        Matrix2d x = new Matrix2d();
        int[] size = {2,2};
        x.create(size);

        int rowIndex = 1;
        float[] row = {1,2};
        float[] col = {3,9};
        x.populateRow(rowIndex,row);
        x.populateCol(rowIndex,col);
        x.print();
    }
    public static void traceFrame(){
        // trace function but only one iteration
    }
}