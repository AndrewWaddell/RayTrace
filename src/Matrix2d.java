import javax.sound.sampled.EnumControl;

public class Matrix2d {
    int[] size;
    int numRows;
    int numCols;
    float[][] vals;
    public Matrix2d(Object... Varin){
        // Matrix2d(array) creates matrix from an array
        // Matrix2d("empty",size) creates empty matrix of given size
        if (Varin[0] instanceof float[]) { // matrix is given
            vals = (float[][]) Varin;
            numRows = vals.length;
            numCols = vals[0].length;
            size = new int[]{numRows, numCols};
        } else if (Varin[0] instanceof String) { // create empty matrix
            size = (int[]) Varin[1];
            numRows = size[0];
            numCols = size[1];
            vals = new float[numRows][numCols];
        }
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
        // result = this + mat2
        float[][] result = new float[numRows][numCols];
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                result[i][j] = vals[i][j] + mat2.vals[i][j];
            }
        }
        return result;
    }
    public float[][] subtract(Matrix2d mat2){
        // result = this - mat2
        float[][] result = new float[numRows][numCols];
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                result[i][j] = vals[i][j] - mat2.vals[i][j];
            }
        }
        return result;
    }
    public void multiplyBy(float value){
        // multiply each element in array by value
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                vals[i][j] = vals[i][j]*value;
            }
        }
    }
    public float[][] multiplyPiecewise(Matrix2d mat2){
        // multiply each element in matrix with each in mat2
        // both matrices must be the same dimensions
        float[][] result = new float[numRows][numCols];
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                result[i][j] = vals[i][j] * mat2.vals[i][j];
            }
        }
        return result;
    }
    public float[][] raisePower(int POWER){
        Matrix2d ones = new Matrix2d("empty",size);
        ones.fillWithItem(1F);
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

        Matrix2d Rx = new Matrix2d(new float[][]{{1,0,0},{0,0,-1},{0,1,0}}); // rotation matrix 90deg about x-axis
        Matrix2d Ry = new Matrix2d(new float[][]{{0,0,1},{0,1,0},{-1,0,0}}); // rotation matrix 90deg about y-axis

        float[] xAxis = {1,0,0};
        boolean[] whereX = findCol(xAxis);
        boolean[] whereNotX = new boolean[whereX.length];
        for (int i=0;i<whereX.length;i++){
            whereNotX[i] = !whereX[i];
        }

        Matrix2d withX = new Matrix2d(indexCol(whereX));
        Matrix2d withoutX = new Matrix2d(indexCol(whereNotX));


        float[][] rotatedX = Ry.multiply(withX); // rotate x-axis vectors about y-axis
        float[][] rotatedNotX = Rx.multiply(withoutX); // rotate all other vectors about x-axis

        float[][] rotated = new float[numRows][numCols];


        return rotatedX;
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
    public void insertCol(Matrix2d inMat,boolean[] index){
        // insert inMat columns into this matrix where index is true
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                if (index[j]){
                    vals[i][j] = inMat.vals[i][j];
                }
            }
        }
    }
}
