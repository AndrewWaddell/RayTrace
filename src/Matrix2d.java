import javax.sound.sampled.EnumControl;

public class Matrix2d {
    boolean created = false;
    int[] size;
    int numRows;
    int numCols;
    float[][] vals;
    public void createEmpty(int[] sizeInput){
        size = sizeInput;
        numRows = size[0];
        numCols = size[1];
        vals = new float[numRows][numCols];
        created = true;
    }
    public void create(float[][] inMat){
        vals = inMat;
        numRows = inMat.length;
        numCols = inMat[0].length;
        size = new int[]{numRows,numCols};
        created = true;
    }
    public void populateRow(int i, float[] row){
        // i is the index of the row that we want to insert into the matrix
        // row contains all the values in the row
        if (!created){
            System.out.println("Matrix not created");
            return;
        }
        vals[i] = row;
    }
    public void populateCol(int j, float[] col){
        // j is the index of the column that we want to insert into the matrix
        // col contains all the values in the column
        if (!created){
            System.out.println("Matrix not created");
            return;
        }
        if (col.length != numRows){
            System.out.println("Column is incorrect length");
            return;
        }
        for(int i=0;i<numRows;i++){
            vals[i][j] = col[i];
        }
    }
    public void fillWithItem(float item) {
        if (!created){
            System.out.println("Matrix not created");
            return;
        }
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                vals[i][j] = item;
            }
        }
    }
    public void print(){
        if (!created){
            System.out.println("Matrix not created");
            return;
        }
        for (int i=0;i<numRows;i++){
            for(int j=0;j<numCols;j++){
                String val = String.valueOf(vals[i][j]);
                System.out.print(val+'\t');
            }
            System.out.println();
        }
    }
    public float[][] add(Matrix2d mat2){
        if (!created){
            System.out.println("Matrix not created");
            float[][] zero = new float[1][1];
            return zero;
        }
        float[][] result = new float[numRows][numCols];
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                result[i][j] = vals[i][j] + mat2.vals[i][j];
            }
        }
        return result;
    }
    public float[][] subtract(Matrix2d mat2){
        if (!created){
            System.out.println("Matrix not created");
            float[][] zero = new float[1][1];
            return zero;
        }
        float[][] result = new float[numRows][numCols];
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                result[i][j] = vals[i][j] - mat2.vals[i][j];
            }
        }
        return result;
    }
    public void addValue(float value){
        if (!created){
            System.out.println("Matrix not created");
            return;
        }
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                vals[i][j] = value;
            }
        }
    }
    public void multiplyBy(float value){
        if (!created){
            System.out.println("Matrix not created");
            return;
        }
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                vals[i][j] = vals[i][j]*value;
            }
        }
    }
    public float[][] multiplyPiecewise(Matrix2d mat2){
        if (!created){
            System.out.println("Matrix not created");
            float[][] zero = new float[1][1];
            return zero;
        }
        float[][] result = new float[numRows][numCols];
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                result[i][j] = vals[i][j] * mat2.vals[i][j];
            }
        }
        return result;
    }
    public float[][] raisePower(int POWER){
        if (!created){
            System.out.println("Matrix not created");
            float[][] zero = new float[1][1];
            return zero;
        }
        Matrix2d ones = new Matrix2d();
        ones.createEmpty(size);
        float one = 1;
        ones.fillWithItem(one);
        float[][] result = ones.vals;
        if (POWER<0){
            for (int power=0;power>POWER;power--) {
                for (int i = 0; i < numRows; i++) {
                    for (int j = 0; j < numCols; j++) {
                        result[i][j] = result[i][j] / vals[i][j];
                    }
                }
            }
        } else if (POWER>0) {
            result = vals; // is this referencing the same object?
            for (int power=1;power<POWER;power++) {
                for (int i = 0; i < numRows; i++) {
                    for (int j = 0; j < numCols; j++) {
                        result[i][j] = result[i][j] * vals[i][j];
                        this.print(); // these values shouldn't be changing right?
                    }
                }
            }
        }
        return result;
    }

}
