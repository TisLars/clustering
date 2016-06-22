/**
 * Created by larsh on 21-6-2016.
 */
public class Program {

    private static int TOTAL_ITERATIONS = 2;

    public static void main(String[] args) {
        AlgorithmKMeans clustering = new AlgorithmKMeans();
        while (TOTAL_ITERATIONS > 0) {
            clustering.init();
            clustering.calculate();

            TOTAL_ITERATIONS--;
        }
    }
}
