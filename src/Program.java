/**
 * Created by larsh on 21-6-2016.
 */
public class Program {

    private static int TOTAL_ITERATIONS = 100;
    private static AlgorithmKMeans bestClusters;
    private static double sumSquaredError = Double.MAX_VALUE;

    public static void main(String[] args) {
        while (TOTAL_ITERATIONS > 0) {
            AlgorithmKMeans clustering = new AlgorithmKMeans();
            clustering.init();
            clustering.calculate();
            if (clustering.getSumSquaredError() < sumSquaredError)
                bestClusters = clustering;
            TOTAL_ITERATIONS--;
        }

        bestClusters.printBestSolution();
    }
}
