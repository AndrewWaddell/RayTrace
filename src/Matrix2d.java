

public class Matrix2d {
    int[] size;
    int numRows;
    int numCols;
    float[][] vals;
    public Matrix2d(float[][] inputValues){
        vals = inputValues;
        numRows = vals.length;
        numCols = vals[0].length;
        size = new int[]{numRows, numCols};
    }
    public Matrix2d(int[] emptySize){
        size = emptySize;
        numRows = size[0];
        numCols = size[1];
        vals = new float[numRows][numCols];
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
        Matrix2d result = new Matrix2d(size);
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                result.vals[i][j] = vals[i][j] + mat2.vals[i][j];
            }
        }
        return result;
    }
    public Matrix2d subtract(Matrix2d mat2){
        // result = this - mat2
        Matrix2d result = new Matrix2d(size);
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                result.vals[i][j] = vals[i][j] - mat2.vals[i][j];
            }
        }
        return result;
    }
    public Matrix2d multiplyBy(float value){
        // multiply each element in array by value, return output
        Matrix2d output = new Matrix2d(size);
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                output.vals[i][j] = vals[i][j]*value;
            }
        }
        return output;
    }
    public Matrix2d multiplyPiecewise(Matrix2d mat2){
        // multiply each element in matrix with each in mat2
        // both matrices must be the same dimensions
        Matrix2d result = new Matrix2d(size);
        for (int i=0;i<numRows;i++){
            for (int j=0;j<numCols;j++){
                result.vals[i][j] = vals[i][j] * mat2.vals[i][j];
            }
        }
        return result;
    }
    public Matrix2d raisePower(int POWER){
        Matrix2d result = new Matrix2d(size);
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
        Matrix2d product = new Matrix2d(new int[]{numRows,B.numCols});
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
        int[] newSize = new int[2];
        newSize[0] = size[1];
        newSize[1] = size[0];
        Matrix2d result = new Matrix2d(newSize);
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

        Matrix2d X = indexCol(whereX); // the collection of x-axis vectors 1,0,0
        Matrix2d others = indexCol(whereNotX); // all other vectors


        Matrix2d rotatedX = Ry.multiply(X); // rotate x-axis vectors about y-axis
        Matrix2d rotatedNotX = Rx.multiply(others); // rotate all other vectors about x-axis

        Matrix2d rotated = new Matrix2d(size); // fill in each chunk into 1 matrix
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
        Matrix2d output = new Matrix2d(new int[]{numRows,outCols});
        int jOut = 0;
        for (int i=0;i<numRows;i++){
            jOut = 0;
            for (int j=0;j<numCols;j++){
                if (index[j]){
                    output.vals[i][jOut] = vals[i][j];
                    jOut++;
                }
            }
        }
        return output;
    }
    public Matrix2d indexCol(int j){
        // output column specified in index j as a new matrix
        Matrix2d column = new Matrix2d(new int[]{numRows,1});
        for (int i=0;i<numRows;i++){
            column.vals[i][0] = vals[i][j];
        }
        return column;
    }
    public Matrix2d indexRow(boolean[] index){
        // return only rows specified true in index
        int outRows = 0; // number of rows in output
        for (boolean b : index) {
            if (b) {
                outRows++;
            }
        }
        Matrix2d output = new Matrix2d(new int[]{outRows,numCols});
        int iOut = 0;
        for (int i=0;i<numRows;i++){
            if (index[i]) {
                for (int j = 0; j < numCols; j++) {
                    output.vals[iOut][j] = vals[i][j];
                }
                iOut++;
            }
        }
        return output;
    }
    public Matrix2d indexRow(int i){
        // return only row specified in i as a new matrix
        Matrix2d output = new Matrix2d(new int[]{1,numCols});
        output.vals[0] = vals[i];
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
    public Matrix2d concatenateCol(Matrix2d inMat){
        // attach input matrix to the end of this matrix.
        // this matrix and input matrix have the same number of rows
        int newCols = numCols + inMat.numCols;
        float[][] newArray = new float[numRows][newCols];
        for (int i=0;i<numRows;i++){
            System.arraycopy(vals[i],0,newArray[i],0,numCols);
        }
        for (int i=0;i<numRows;i++){
            System.arraycopy(inMat.vals[i],0,newArray[i],numCols,inMat.numCols);
        }
        return new Matrix2d(newArray);
    }
    public Matrix2d cross(Matrix2d b){
        // cross product of vector a (this) and vector b, result = a cross b
        // each matrix is a series of column vectors with 3 rows
        // each matrix has the same number of columns
        Matrix2d result = new Matrix2d(size);
        for (int j=0;j<numCols;j++){
            result.vals[0][j] = vals[1][j]*b.vals[2][j] - vals[2][j]*b.vals[1][j];
            result.vals[1][j] = vals[2][j]*b.vals[0][j] - vals[0][j]*b.vals[2][j];
            result.vals[2][j] = vals[0][j]*b.vals[1][j] - vals[1][j]*b.vals[0][j];

        }
        return result;
    }
    public boolean isMaxCol(int i,int query){
        // is the value at the given index (i,query) the maximum along its row?
        for (int j=0;j<numCols;j++){
            if (vals[i][j] > vals[i][query]){ // if any value I choose is larger than query
                return false; // then the query is not the largest
            }
        }
        return true; // we couldn't find a value larger than query
    }
    public boolean isMinCol(int i,int query){
        // is the value at the given index (i,query) the minimum along its row?
        for (int j=0;j<numCols;j++){
            if (vals[i][j] < vals[i][query]){ // if any value I choose is smaller than query
                return false; // then the query is not the smallest
            }
        }
        return true; // we couldn't find a value smaller than query
    }

    public int minColIndex(int i){
        // what is the index of the minimum value along row i
        int minIndex = 0;
        for (int j=0;j<numCols;j++){
            if (vals[i][j] < vals[i][minIndex]){
                minIndex = j;
            }
        }
        return minIndex;
    }
    public int maxColIndex(int i){
        // what is the index of the maximum value along row i
        int maxIndex = 0;
        for (int j=0;j<numCols;j++){
            if (vals[i][j] > vals[i][maxIndex]){
                maxIndex = j;
            }
        }
        return maxIndex;
    }
    public float signedArea(){
        // signed area of 2D triangle A->B->C
        // this matrix contains each point in each column
        // calculated using determinant formula
        // also known as a ccw formula

        // return (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y);
        float term1 = (vals[0][1]-vals[0][0])*(vals[1][2]-vals[1][0]);
        float term2 = (vals[0][2]-vals[0][0])*(vals[1][1]-vals[1][0]);
        return term1-term2;
    }
    public float cosTheta(){
        // imagine triangle DEF
        // Angle DEF is theta, (about E)
        // each point D,E,F is a column in this matrix in order
        // first row is x, second is y

        // uses formula for two vectors A,B
        // dot(A,B) = |A|*|B|*cos(theta)
        // A is from E to D
        // B is from E to F
        Matrix2d D = this.indexCol(0);
        Matrix2d E = this.indexCol(1);
        Matrix2d F = this.indexCol(2);

        Matrix2d A = D.subtract(E);
        Matrix2d B = F.subtract(E);

        return (A.dot(B)/(A.magnitude()*B.magnitude()));

    }
    public float dot(Matrix2d B){
        // dot product column vectors A,B
        // product = A dot B
        // this matrix is A
        float product = 0;

        if (numRows==1){
            if (B.numRows==1){
                for (int i=0;i<numCols;i++){
                    product += vals[0][i] * B.vals[0][i];
                }
            } else {
                for (int i=0;i<numCols;i++){
                    product += vals[0][i] * B.vals[i][0];
                }
            }
        } else {
            if (B.numRows==1){
                for (int i=0;i<numCols;i++){
                    product += vals[1][0] * B.vals[0][i];
                }
            } else {
                for (int i=0;i<numCols;i++){
                    product += vals[i][0] * B.vals[i][0];
                }
            }
        }
        return product;
    }
    public float magnitude(){
        // length of column vector
        double radicand = 0;
        for (int i=0;i<numRows;i++){
            radicand += vals[i][0] * vals[i][0];
        }
        double answerD = java.lang.Math.sqrt(radicand);
        float answerF = (float)answerD;
        return answerF;
    }
    public Matrix2d inverse3by3(){
        // this matrix is of size 3x3
        // calculate the inverse

        // cofactor matrix uses the determinant of each minor, the values in the rows
        // and columns other than each element. So first I remove rows,columns
        Matrix2d cofactors = new Matrix2d(new int[]{3,3});
        for (int i=0;i<3;i++){
            boolean[] rowIndex = new boolean[3];
            for (int k=0;k<3;k++){
                rowIndex[k] = k!=i; // remove only this row
            }
            Matrix2d rowRemoved = this.indexRow(rowIndex);
            for (int j=0;j<3;j++){
                boolean[] colIndex = new boolean[3];
                for (int k=0;k<3;k++){
                    colIndex[k] = k!=j; // remove only this column
                }
                Matrix2d minorMatrix = rowRemoved.indexCol(colIndex);
                cofactors.vals[i][j] = minorMatrix.det2by2() * (float)java.lang.Math.pow(-1,i+j);
            }
        }
        Matrix2d adjoint = cofactors.transpose();
        Matrix2d firstRow = this.indexRow(0);
        float determinant = firstRow.dot(cofactors.indexRow(0));
        return adjoint.multiplyBy(1/determinant);
    }
    public float det2by2(){
        // find the determinant of a 2 x 2 matrix
        return ((vals[0][0]*vals[1][1]) - (vals[0][1]*vals[1][0]));
    }

    public Matrix2d normCol(){
        // normalise columns of matrix
        Matrix2d normalised = new Matrix2d(size);
        for (int j=0;j<numCols;j++){
            float sum = 0;
            for (int i=0;i<numRows;i++){
                sum += vals[i][j];
            }
            for (int i=0;i<numRows;i++){
                normalised.vals[i][j] = vals[i][j]/sum;
            }
        }
        return normalised;
    }
}
