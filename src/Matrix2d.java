import javax.sound.sampled.EnumControl;

public class Matrix2d {
    boolean created = false;
    int numRows;
    int numCols;
    float vals;
    public void create(int[] size){
        numRows = size[0];
        numCols = size[1];
        float[][] vals = new float[numRows][numCols];
        created = true;
    }
    public void populateRow(int i, float[] row){
        // i is the index of the row that we want to insert into the matrix
        // row contains all the values in the row
        if (!created){
            System.out.println("0");
            return;
        }
        for(int j=0;j<numCols;j++){
            System.out.println("1");
        }
    }
    public void multiply(){

    }

}
