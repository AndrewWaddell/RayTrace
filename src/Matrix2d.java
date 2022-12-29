import javax.sound.sampled.EnumControl;

public class Matrix2d {
    int[] size;
    int numRows;
    int numCols;
    float[][] vals;
    public void Matrix2d(float[][] inMat){
        vals = inMat;
        numRows = inMat.length;
        numCols = inMat[0].length;
        size = new int[]{numRows,numCols};
    }
    public void createEmpty(int[] sizeInput){
        size = sizeInput;
        numRows = size[0];
        numCols = size[1];
        vals = new float[numRows][numCols];
    }

    public void populateRow(int i, float[] row){
        // i is the index of the row that we want to insert into the matrix
        // row contains all the values in the row
        vals[i] = row;
    }
    public void populateCol(int j, float[] col){
        // j is the index of the column that we want to insert into the matrix
        // col contains all the values in the column
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
    public float[][] subtract(Matrix2d mat2){
        float[][] result = new float[numRows][numCols];
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                result[i][j] = vals[i][j] - mat2.vals[i][j];
            }
        }
        return result;
    }
    public void addValue(float value){
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                vals[i][j] = value;
            }
        }
    }
    public void multiplyBy(float value){
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                vals[i][j] = vals[i][j]*value;
            }
        }
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
        ones.createEmpty(size);
        float one = 1;
        ones.fillWithItem(one);
        float[][] result = ones.vals.clone();
        if (POWER<0){
            for (int power=0;power>POWER;power--) {
                for (int i = 0; i < numRows; i++) {
                    for (int j = 0; j < numCols; j++) {
                        result[i][j] = result[i][j] / vals[i][j];
                    }
                }
            }
        } else if (POWER>0) {
            for (int power=0;power<POWER;power++) {
                for (int i = 0; i < numRows; i++) {
                    for (int j = 0; j < numCols; j++) {
                        result[i][j] = result[i][j] * vals[i][j];
                    }
                }
            }
        }
        return result;
    }
    public float[][] multiply(Matrix2d inMat){
        // multiply this matrix A by input matrix B: result = A * B
        if (numCols != inMat.numRows){
            System.out.println("Matrix dimensions not compatible for multiplication");
            float[][] zero = new float[1][1];
            return zero;
        }
        float[][] product = new float[numRows][inMat.numCols];
        for (int i=0;i<numRows;i++){
            for (int j=0;j<inMat.numCols;j++){
                for (int k=0;k<numCols;k++){
                    product[i][j] += vals[i][k] * inMat.vals[k][j];
                }
            }
        }
        return product;
    }
    public float[][] transpose(){
        float[][] result = new float[numCols][numRows];
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                result[j][i] = vals[i][j];
            }
        }
        return result;
    }

    public float[][] rotate3dVector90Deg(){
        // this matrix is a series of column vectors V, each 3 dimensions (rows)
        Matrix2d Rx = new Matrix2d(); // rotation matrix 90deg about x-axis
        float[] row1x = {1,0,0};
        float[] row2x = {0,0,-1};
        float[] row3x = {0,1,0};
        Rx.populateRow(1,row1);
        Rx.populateRow(2,row2);
        Rx.populateRow(3,row3);
        Matrix2d Ry = new Matrix2d(); // rotation matrix 90deg about y-axis
        float[] row1y = {0,0,1};
        float[] row2y = {0,1,0};
        float[] row3y = {-1,0,0};
        Ry.populateRow(1,row1);
        Ry.populateRow(2,row2);
        Ry.populateRow(3,row3);

        float[] xAxis = {1,0,0};
        boolean[] whereX = findCol(xAxis);

        float[][] withX = indexCol(whereX); // do i need to create a matrix?
        float[][] withoutX = indexCol(!whereX);

        withX
        float[][] rotatedX = Ry.multiply(this); // rotate x-axis vectors about y axis
        float[][] rotatednotX = Rx.multiply(this)
        return rotated;
    }
    public boolean[] findCol(float[] col){
        // for a series of column vectors, find those that equal col
        boolean[] found = new boolean[numCols];
        for (int j=0;j<numCols;j++){ // each vector
            found[j] = true; // assume vector is equal
            for (int i=0;i<numRows;i++){ // each vector element
                if (vals[i][j] != col[i]){ // if any element is not equal
                    found[j] = false; // then vector is not equal
                }
            }
        }
        return found;
    }
    public float[][] indexCol(boolean[] index){
        // output only the columns that are true in index
        int outCols = 0; // number of columns in output
        for (int i=0;i<index.length;i++){
            if (index[i]){
                outCols++;
            }
        }
        float[][] output = new float[numRows][outCols];
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                if (index[j]){
                    output[i][j] = vals[i][j];
                }
            }
        }
        return output;
    }
}
