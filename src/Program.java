/**
 * Created by larsh on 21-6-2016.
 */
public class Program {

    private static int TOTAL_ITERATIONS = 1;

    public static void main(String[] args) {
        while (TOTAL_ITERATIONS > 0) {
            AlgorithmKMeans clustering = new AlgorithmKMeans();
            clustering.init();
            clustering.calculate();

            TOTAL_ITERATIONS--;
        }
    }
}
