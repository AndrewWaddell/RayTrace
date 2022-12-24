public class Shape {
    public static void changeOfBasis(){
        //can I talk to other classes?

    }
    public static void matrixManipulation(int s){
        int[][] sqMatrix = new int[s][s];
        System.out.println("My zeroed matrix:");
        for (int i = 0; i < s; i++){
            for (int j = 0; j<s;j++){
                System.out.print(sqMatrix[i][j]+"\t");
            }
            System.out.println();
        }
    }
}
