import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by larsh on 15-6-2016.
 */
public class Customer {

    private double[] preferences;
    private Centroid centroid;

    public Customer(int totalOffers) {
        preferences = new double[totalOffers];
    }

    public void addPreference(int offer, int bought) {
        preferences[offer] = bought;
    }

    public void findClosestDistance(ArrayList<Centroid> centroids) {
        double bestDistance = Double.POSITIVE_INFINITY;
        EuclideanDistance euclidean = new EuclideanDistance();

        for (int i = 0; i < centroids.size(); i++) {
            double currentDistance = euclidean.calculate(centroids.get(i).getPreferences(), this.getPreferences());
            if (currentDistance < bestDistance) {
                bestDistance = currentDistance;
                centroid = centroids.get(i);
            }
        }
    }

    public String getPreferencesToString() {
        return Arrays.toString(preferences);
    }

    public double[] getPreferences() {
        return preferences;
    }

    public Centroid getCentroid() {
        return centroid;
    }
}
