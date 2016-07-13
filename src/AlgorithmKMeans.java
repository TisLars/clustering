import Models.Cluster;
import Models.Customer;

import java.util.*;

/**
 * Created by larsh on 21-6-2016.
 */
public class AlgorithmKMeans {

    private final int TOTAL_CLUSTERS;

    private List<Customer> customers;
    private List<Cluster> clusters, bestClusterSolution;
    private double newSumSquaredError, sumSquaredError;

    public AlgorithmKMeans(int amountOfClusters) {
        this.TOTAL_CLUSTERS = amountOfClusters;
        this.customers = new ArrayList<>();
        this.clusters = new ArrayList<>();
    }

    public void init() {
        DataReader reader = new DataReader();
        customers = reader.readData();
        assignCentroids(customers);
    }

    private void assignCentroids(List<Customer> customers) {
        Random random = new Random();
        int n = random.nextInt(customers.size());

        for (int i = 0; i < TOTAL_CLUSTERS; i++) {
            Cluster cluster = new Cluster(i);
            Customer centroid = cluster.createRandomCentroid(customers.get(n).getPreferences());
            n = random.nextInt(customers.size());
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
        sumSquaredError = Double.MAX_VALUE;

        while (!done) {
            clearClusters();

            assignCluster();

            List<Customer> lastCentroids = getCentroids();
            calculateCentroids();

            List<Customer> currentCentroids = getCentroids();

            double distance = 0;
            for (int i = 0; i < lastCentroids.size(); i++) {
                distance += Customer.distance(lastCentroids.get(i).getPreferences(), currentCentroids.get(i).getPreferences());
            }
            plotClusters();

            newSumSquaredError = calculateSSE(clusters);
            if (newSumSquaredError < sumSquaredError) {
                sumSquaredError = newSumSquaredError;
                bestClusterSolution = clusters;
            }

            if (distance == 0)
                done = true;
        }
    }

    private double calculateSSE(List<Cluster> clusters) {
        double sse = 0;

        for (Cluster cluster : clusters) {
            List<Customer> customers = cluster.getCustomers();
            for (Customer customer : customers) {
                sse += calculateEuclidean(cluster.getCentroid().getPreferences(), customer.getPreferences());
            }
        }

        return sse;
    }

    private double calculateEuclidean(double[] centroid, double[] customer) {
        double distance = 0;

        for (int i = 0; i < centroid.length; i++) {
            distance += (centroid[i] - customer[i]) * (centroid[i] - customer[i]);
        }
        distance = Math.sqrt(distance);
//        distance = Math.sqrt(Math.pow((centroid[0] - customer[0]), 2) + Math.pow((centroid[1] - customer[1]), 2));

        return distance;
    }

    private void clearClusters() {
        for (Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    private void removeClusters() {
        clusters.removeAll(clusters);
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
        for (Cluster cluster : clusters) {
            if (cluster.getCustomers().size() == 0) {
                removeClusters();
                assignCentroids(customers);
                assignCluster();
                break;
            }
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

    public void printBestSolution() {
        int count = 1;
        for (Cluster cluster : bestClusterSolution) {
            System.out.println("Cluster(" + count + ") with " + cluster.getCustomers().size() + " customers and a SSE of " + sumSquaredError + ":");

            Map<Integer, Integer> boughtOffers = new HashMap<>();

            for (int i = 0; i < cluster.getCustomers().size(); i++) {
                Customer customer = (Customer) cluster.getCustomers().get(i);
                double[] preferences = customer.getPreferences();

                for (int j = 0; j < preferences.length; j++) {
                    if (boughtOffers.get(j) != null) {
                        boughtOffers.put(j, boughtOffers.get(j).intValue() + (int) preferences[j]);
                    } else {
                        boughtOffers.put(j, (int) preferences[j]);
                    }
                }
            }
            Map<Integer, Integer> sortedOffers = sortByComparator(boughtOffers);
            for (Map.Entry<Integer, Integer> entry : sortedOffers.entrySet()) {
                if (entry.getValue() >= 3) {
                    int offer = entry.getKey() + 1;
                    System.out.println("OFFER " + offer + " \t-> bought " + entry.getValue() + " times");
                }
            }
            count++;
        }
    }

    private static Map<Integer, Integer> sortByComparator(Map<Integer, Integer> unsortMap) {

        // Convert Map to List
        List<Map.Entry<Integer, Integer>> list =
                new LinkedList<>(unsortMap.entrySet());

        // Sort list with comparator, to compare the Map values
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> o1,
                               Map.Entry<Integer, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // Convert sorted map back to a Map
        Map<Integer, Integer> sortedMap = new LinkedHashMap<>();
        for (Iterator<Map.Entry<Integer, Integer>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<Integer, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public List<Cluster> getBestClusterSolution() {
        return bestClusterSolution;
    }

    public double getSumSquaredError() {
        return sumSquaredError;
    }
}
