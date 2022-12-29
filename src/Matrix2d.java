import javax.sound.sampled.EnumControl;
import java.util.Arrays;

public class Matrix2d {
    int[] size;
    int numRows;
    int numCols;
    float[][] vals;
    public Matrix2d(Object... Varin){
        // Matrix2d(array) creates matrix from an array
        // Matrix2d("empty",size) creates empty matrix of given size
        if (Varin[0] instanceof float[]) { // matrix is given as a 2d array
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
    public Matrix2d add(Matrix2d mat2){
        // result = this + mat2
        Matrix2d result = new Matrix2d("empty",size);
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                result.vals[i][j] = vals[i][j] + mat2.vals[i][j];
            }
        }
        return result;
    }
    public Matrix2d subtract(Matrix2d mat2){
        // result = this - mat2
        Matrix2d result = new Matrix2d("empty",size);
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                result.vals[i][j] = vals[i][j] - mat2.vals[i][j];
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
    public Matrix2d multiplyPiecewise(Matrix2d mat2){
        // multiply each element in matrix with each in mat2
        // both matrices must be the same dimensions
        Matrix2d result = new Matrix2d("empty",size);
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                result.vals[i][j] = vals[i][j] * mat2.vals[i][j];
            }
        }
        return result;
    }
    public Matrix2d raisePower(int POWER){
        Matrix2d result = new Matrix2d("empty",size);
        result.fillWithItem(1F);
        if (POWER<0){
            for (int power=0;power>POWER;power--) {
                for (int i = 0; i < numRows; i++) {
                    for (int j = 0; j < numCols; j++) {
                        result.vals[i][j] = result.vals[i][j] / vals[i][j];
                    }
                }
            }
        } else if (POWER>0) {
            for (int power=0;power<POWER;power++) {
                for (int i = 0; i < numRows; i++) {
                    for (int j = 0; j < numCols; j++) {
                        result.vals[i][j] = result.vals[i][j] * vals[i][j];
                    }
                }
            }
        }
        return result;
    }
    public Matrix2d multiply(Matrix2d B){
        // multiply matrix A (this) by matrix B: result = A * B
        // number of columns in A must equal number of rows in B
        Matrix2d product = new Matrix2d("empty",new int[]{numRows,B.numCols});
        for (int i=0;i<numRows;i++){
            for (int j=0;j<B.numCols;j++){
                for (int k=0;k<numCols;k++){
                    product.vals[i][j] += vals[i][k] * B.vals[k][j];
                }
            }
        }
        return product;
    }
    public Matrix2d transpose(){
        Matrix2d result = new Matrix2d("empty",size);
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                result.vals[j][i] = vals[i][j];
            }
        }
        return result;
    }
    public Matrix2d rotate3dVector90Deg(){
        // this matrix is a series of column vectors V, each 3 dimensions (rows)
        // rotate all vectors by 90deg in unimportant direction
        // I rotate all vectors about the x-axis
        // however if a vector is the x-axis, then I rotate it about the y-axis

        Matrix2d Rx = new Matrix2d(new float[][]{{1,0,0},{0,0,-1},{0,1,0}}); // rotation matrix 90deg about x-axis
        Matrix2d Ry = new Matrix2d(new float[][]{{0,0,1},{0,1,0},{-1,0,0}}); // rotation matrix 90deg about y-axis

        boolean[] whereX = findCol(new float[]{1,0,0}); // find the index of all instances of x-axis in V
        boolean[] whereNotX = new boolean[whereX.length]; // find index of all other vectors
        for (int i=0;i<whereX.length;i++){
            whereNotX[i] = !whereX[i];
        }

        Matrix2d X = new Matrix2d(indexCol(whereX)); // the collection of x-axis vectors 1,0,0
        Matrix2d others = new Matrix2d(indexCol(whereNotX)); // all other vectors


        Matrix2d rotatedX = Ry.multiply(X); // rotate x-axis vectors about y-axis
        Matrix2d rotatedNotX = Rx.multiply(others); // rotate all other vectors about x-axis

        Matrix2d rotated = new Matrix2d("emtpy",size); // fill in each chunk into 1 matrix
        rotated.insertCol(rotatedX,whereX);
        rotated.insertCol(rotatedNotX,whereNotX);

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
    public Matrix2d indexCol(boolean[] index){
        // output only the columns that are true in index
        int outCols = 0; // number of columns in output
        for (boolean b : index) {
            if (b) {
                outCols++;
            }
        }
        Matrix2d output = new Matrix2d("empty",new int[]{numRows,outCols});
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                if (index[j]){
                    output.vals[i][j] = vals[i][j];
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
    public void concatenateCol(Matrix2d inMat){
        // attach input matrix to the end of this matrix
        // result overwrites this matrix.
        // this matrix and input matrix have the same number of rows
        int newCols = numCols + inMat.numCols;

        float[][] largerArray = Arrays.copyOf(vals,newCols);
        vals = null;
        vals = largerArray;

        for (int i=0;i<numRows;i++){
            for (int j=0;j<inMat.numCols;j++){
                vals[i][numCols+j] = inMat.vals[i][j];
            }
        }
        numCols = newCols;
        size[1] = numCols;
    }
}
