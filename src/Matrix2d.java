import javax.sound.sampled.EnumControl;

public class Matrix2d {
    boolean created = false;
    int[] size;
    int numRows;
    int numCols;
    float[][] vals;
    public void create(int[] sizeInput){
        size = sizeInput;
        numRows = size[0];
        numCols = size[1];
        vals = new float[numRows][numCols];
        created = true;
    }
    public void inherit(float[][] inMat){
        vals = inMat;
    }
    public void populateRow(int i, float[] row){
        // i is the index of the row that we want to insert into the matrix
        // row contains all the values in the row
        if (!created){
            System.out.println("Matrix doesn't exist");
            return;
        }
        if (row.length != numCols){
            System.out.println("Row is incorrect length");
            return;
        }
        for(int j=0;j<numCols;j++){
            vals[i][j] = row[j];
        }
    }
    public void populateCol(int j, float[] col){
        // j is the index of the column that we want to insert into the matrix
        // col contains all the values in the column
        if (!created){
            System.out.println("Matrix doesn't exist");
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
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                vals[i][j] = item;
            }
        }
    }
    public void print(){
        for (int i=0;i<numRows;i++){
            for(int j=0;j<numCols;j++){
                String val = String.valueOf(vals[i][j]);
                System.out.print(val+'\t');
            }
            System.out.println();
        }
    }
    public float[][] add(Matrix2d mat2){
        float[][] result = new float[numRows][numCols];
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                result[i][j] = vals[i][j] + mat2.vals[i][j];
            }
        }
        return result;
    }
    public float[][] multiplyPiecewise(Matrix2d mat2){
        float[][] result = new float[numRows][numCols];
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                result[i][j] = vals[i][j] * mat2.vals[i][j];
            }
        }
        return result;
    }
    public float[][] raisePower(int POWER){
        Matrix2d ones = new Matrix2d();
        ones.create(size);
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
            result = vals;
            for (int power=1;power<POWER;power++) {
                for (int i = 0; i < numRows; i++) {
                    for (int j = 0; j < numCols; j++) {
                        result[i][j] = result[i][j] * vals[i][j];
                    }
                }
            }
        }
        return result;
    }

}
