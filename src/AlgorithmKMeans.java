import Models.Cluster;
import Models.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by larsh on 21-6-2016.
 */
public class AlgorithmKMeans {

    private int TOTAL_CLUSTERS = 4;
    private int TOTAL_ITERATIONS = 5;

    private List<Customer> customers;
    private List<Cluster> clusters;

    public AlgorithmKMeans() {
        this.customers = new ArrayList<>();
        this.clusters = new ArrayList<>();
    }

    public void init() {
        DataReader reader = new DataReader();
        customers = reader.readData();

        for (int i = 0; i < TOTAL_CLUSTERS; i++) {
            Cluster cluster = new Cluster(i);
            Customer centroid = Customer.createRandomCentroid();
            cluster.setCentroid(centroid);
            clusters.add(cluster);
        }

        plotClusters();
    }

    private void plotClusters() {
        for (int i = 0; i < TOTAL_CLUSTERS; i++) {
            Cluster cluster = clusters.get(i);
            cluster.plotCluster();
        }
    }

    public void calculate() {
        boolean done = false;
        int iteration = 0;

        while (!done) {
            clearClusters();

            List<Customer> lastCentroids = getCentroids();

            assignCluster();

            calculateCentroids();

            iteration++;

            List<Customer> currentCentroids = getCentroids();

            double distance = 0;
            for (int i = 0; i < lastCentroids.size(); i++) {
                distance += Customer.distance(lastCentroids.get(i).getPreferences(), currentCentroids.get(i).getPreferences());
            }

            System.out.println("\n");
            System.out.println("Iteration:\t" + iteration);
            System.out.println("Centroid distance:\t " + distance);
            plotClusters();

            calculateSSE(clusters);

            if (iteration==50)
                System.out.println("stop");
            if (distance == 0)
                done = true;
        }
    }

    private void calculateSSE(List<Cluster> clusters) {
        double sse = 0;
        int index = 0;

        for (Cluster cluster : clusters) {
            List<Customer> customers = cluster.getCustomers();
            for (Customer customer : customers) {
                sse += calculateEuclidean(cluster.getCentroid().getPreferences(), customer.getPreferences());
                index++;
            }
            System.out.println("SSE: " + sse);
        }
    }

    private double calculateEuclidean(double[] centroid, double[] customer) {
        double distance = 0;

        for (int i = 0; i < centroid.length; i++) {
            distance += (centroid[i] - customer[i]) * (centroid[i] - customer[i]);
        }
        distance = Math.sqrt(distance);

        return distance;
    }

    private void clearClusters() {
        for (Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    private List getCentroids() {
        List<Customer> centroids = new ArrayList<>(TOTAL_CLUSTERS);

        for (Cluster cluster : clusters) {
            Customer centroid = cluster.getCentroid();
            Customer customer = new Customer(centroid.getPreferences());
            centroids.add(customer);
        }

        return centroids;
    }

    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min = max;
        int clusterId = 0;
        double distance = 0.0;

        for (Customer customer : customers) {
            min = max;
            for (int i = 0; i < TOTAL_CLUSTERS; i++) {
                Cluster cluster = clusters.get(i);
                distance = Customer.distance(customer.getPreferences(), cluster.getCentroid().getPreferences());
                if (distance < min) {
                    min = distance;
                    clusterId = i;
                }
            }
            customer.setCluster(clusterId);
            clusters.get(clusterId).addCustomer(customer);
        }
    }

    private void calculateCentroids() {
        for (Cluster cluster : clusters) {
            double[] preferences = new double[customers.get(0).getPreferences().length];
            List<Customer> customers = cluster.getCustomers();

            for (int i = 0; i < preferences.length; i++) {
                double sum = 0;
                for (Customer customer : customers) {
                    sum += customer.getPreferences()[i];
                }
                preferences[i] = sum / customers.size();
            }
            Customer centroid = new Customer(preferences);
            cluster.setCentroid(centroid);
        }
    }
}
