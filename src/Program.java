/**
 * Created by larsh on 21-6-2016.
 */
public class Program {

    private static int TOTAL_ITERATIONS = 100;

    public static void main(String[] args) {
        while (TOTAL_ITERATIONS > 0) {
            System.out.println("### ITERATION " + TOTAL_ITERATIONS + " ###");
            AlgorithmKMeans clustering = new AlgorithmKMeans();
            clustering.init();
            clustering.calculate();

            System.out.println("### END ###\n\n");
            TOTAL_ITERATIONS--;
        }
    }
}
