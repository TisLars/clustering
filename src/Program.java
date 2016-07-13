import java.util.Scanner;

/**
 * Created by larsh on 21-6-2016.
 */
public class Program {

    private static int TOTAL_ITERATIONS;
    private static int TOTAL_CLUSTERS;
    private static AlgorithmKMeans bestClusters;
    private static double sumSquaredError = Double.MAX_VALUE;

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("How many clusters: ");
        TOTAL_CLUSTERS = reader.nextInt();
        System.out.println("How many iterations: ");
        TOTAL_ITERATIONS = reader.nextInt();

        System.out.println("Run program with parameters: [");
        System.out.println("\t\'clusters\':\t\t" + TOTAL_CLUSTERS);
        System.out.println("\t\'iterations\':\t" + TOTAL_ITERATIONS);
        System.out.println("]");

        while (TOTAL_ITERATIONS > 0) {
            AlgorithmKMeans clustering = new AlgorithmKMeans(TOTAL_CLUSTERS);
            clustering.init();
            clustering.calculate();
            if (clustering.getSumSquaredError() < sumSquaredError)
                bestClusters = clustering;
            TOTAL_ITERATIONS--;
        }

        bestClusters.printBestSolution();
    }
}
