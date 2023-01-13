public class Main {
    public static void main(String[] args) {


        Scene opticalBench = new Scene();
        opticalBench.sources.add(new Source());
        opticalBench.shapes.add(new Shape());
        opticalBench.trace(1);

        // testing:
        System.out.println("ray points");
        for (int i=0;i<opticalBench.rays.pointsAcc.size();i++){
            System.out.println(i);
            opticalBench.rays.unitAcc.get(i).print();
        }
        opticalBench.plot();

//        MainScreen mainScreen = new MainScreen();

    }
}