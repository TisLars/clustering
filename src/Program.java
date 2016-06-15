import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by larsh on 15-6-2016.
 */
public class Program {

    static int amountClusters;
    static int amountIterations;
    static ArrayList<Customer> customers;
    static ArrayList<Centroid> centroids;
    static boolean canContinue = true;

    public static void main(String[] args) {
        ReadData reader = new ReadData();
        customers = reader.readData();

        try {
            amountClusters = initializeClusters();
            amountIterations = initializeIterations();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < amountIterations; i++) {
            randomizeCentroids(amountClusters, customers);
            for (int j = 0; j < 15; j++) {
                if (canContinue)
                    clusteringAlgorithm();
                else
                    break;
            }
        }
    }

    private static void randomizeCentroids(int amountClusters, ArrayList<Customer> customers) {
        centroids = new ArrayList<>();
        Random generator = new Random();
        for (int i = 0; i < amountClusters; i++) {
            centroids.add(new Centroid(customers.get(generator.nextInt(32))));
        }
    }

    private static void clusteringAlgorithm() {
        for (Customer customer : customers) {
            customer.findClosestDistance(centroids);
        }

        ArrayList<Centroid> oldCentroids = new ArrayList<>();
        for (Centroid centroid : centroids) {
            oldCentroids.add(new Centroid(centroid.getPreferences()));
        }

        for (Centroid centroid : centroids) {
            centroid.calculateMean(customers);
        }

        canContinue = compareCentroids(oldCentroids, centroids);
    }

    private static boolean compareCentroids(ArrayList<Centroid> oldCentroids, ArrayList<Centroid> centroids) {
        for (int i = 0; i < centroids.size(); i++) {
            EuclideanDistance euclidean = new EuclideanDistance();

            double distance = euclidean.calculate(oldCentroids.get(i).getPreferences(), centroids.get(i).getPreferences());
            System.out.println(distance);
            if (distance > 0.05) {
                return true;
            }
        }
        return false;
    }

    private static int initializeClusters() throws IOException {
        System.out.print("amount of clusters: ");
        int value = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            value = Integer.parseInt(br.readLine());
        } catch (NumberFormatException nfe) {
            System.err.println("Invalid Format!");
        }

        return value;
    }

    private static int initializeIterations() throws IOException {
        System.out.print("amount of iterations: ");
        int value = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            value = Integer.parseInt(br.readLine());
        } catch (NumberFormatException nfe) {
            System.err.println("Invalid Format!");
        }

        return value;
    }
}
