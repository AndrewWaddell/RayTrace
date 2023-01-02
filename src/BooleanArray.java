public class BooleanArray {
    boolean[][] vals;
    public BooleanArray(boolean[][] inVals){
        vals = inVals;
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
}
