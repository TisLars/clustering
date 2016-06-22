package Models;

/**
 * Created by larsh on 21-6-2016.
 */
public class Customer {

    private double[] preferences;
    private int clusterNumber = 0;

    public Customer(int totalOffers) {
        this.preferences = new double[totalOffers];
    }

    public Customer(double[] preferences) {
        this.preferences = preferences;
    }

    public void addPreference(int offer, int bought) {
        preferences[offer] = bought;
    }

    public double[] getPreferences() {
        return preferences;
    }

    public void setCluster(int c) {
        this.clusterNumber = c;
    }

    public int getCluster() {
        return this.clusterNumber;
    }

    public void setPreferences(double[] preferences) {
        this.preferences = preferences;
    }

    public static double distance(double[] customers, double[] centroids) {
        double distance = 0;
        for (int i = 0; i < centroids.length; i++) {
            distance += Math.pow(customers[i] - centroids[i], 2);
        }

        return Math.sqrt(distance);
    }

    public static Customer createRandomCentroid() {
        double[] centroidPrefs = new double[32];

        for (int i = 0; i < centroidPrefs.length; i++) {
            centroidPrefs[i] = (Math.random() < .5) ? 0 : 1;
        }
        return new Customer(centroidPrefs);
    }

    public String toString() {
        String customerStr = "";
        customerStr += "[";
        for (int i = 0; i < preferences.length; i++) {
            customerStr += preferences[i];
            customerStr += (i < preferences.length - 1) ? "," : "]";
        }
        return customerStr;
    }
}
