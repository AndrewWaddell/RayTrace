import java.util.Arrays;

public class BooleanArray {
    boolean[][] vals;
    int[] size;
    public BooleanArray(boolean[][] inVals){
        vals = inVals;
        size[0] = vals.length;
        size[1] = vals[0].length;
    }
    public BooleanArray(int[] sizeIn){
        vals = new boolean[sizeIn[0]][sizeIn[1]];
        size = sizeIn;
        Arrays.fill(vals,Boolean.FALSE);
    }
    public boolean allFalseRow(int i){
        // every value along row i is false
        for (int j=0;j<vals[0].length;j++){ // through each column
            if (vals[i][j]){ // if any value along row is true
                return false;
            }
        }
        return true; //couldn't find a true, so must be all false
    }
    public Matrix2d numericalIndex(){
        // convert this boolean array into a list of indices of each true item
        // I don't think I need this function since boolean indexing is better
        int xCount = 0;
        int yCount = 0;
        for (int i=0;i<size[0];i++){
            for (int j=0;j<size[1];j++){
                if (vals[i][j]){
                    yCount++;
                }
            }
            xCount++; // this function does not work. Although is it necessary right now?
        }
        for (int i=0;i<size[0];i++){
            for (int j=0;j<size[1];j++){

            }
        }
        return new Matrix2d(new int[]{});
    }
    public boolean[] orCol(){
        // does each column contain any true items
        boolean[] cols = new boolean[size[1]];
        for (int j=0;j<size[1];j++){
            cols[j] = false;
            for (int i=0;i<size[0];i++){
                if (vals[i][j]){
                    cols[j] = true;
                }
            }
        }
        return cols;
    }
    public boolean[] orRow(){
        // does each row contain any true items
        boolean[] rows = new boolean[size[0]];
        for (int i=0;i<size[0];i++){
            rows[i] = false;
            for (int j=0;j<size[0];j++){
                if (vals[i][j]){
                    rows[i] = true;
                }
            }
        }
        return rows;
    }
}
