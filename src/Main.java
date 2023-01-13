public class Main {
    public static void main(String[] args) {


        Scene opticalBench = new Scene();
        opticalBench.sources.add(new Source());
        opticalBench.shapes.add(new Shape());
        opticalBench.trace(1);

        // testing:
        System.out.println("ray points");
        opticalBench.rays.pointsAcc.get(3).print();
        opticalBench.plot();

//        MainScreen mainScreen = new MainScreen();

    }
}