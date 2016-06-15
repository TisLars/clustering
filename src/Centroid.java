import java.util.ArrayList;

/**
 * Created by larsh on 15-6-2016.
 */
public class Centroid {

    private double[] preferences;

    public Centroid(Customer customer) {
        this.preferences = customer.getPreferences().clone();
    }

    public Centroid(double[] preferences) {
        this.preferences = preferences.clone();
    }

    public double[] getPreferences() {
        return preferences;
    }

    public void calculateMean(ArrayList<Customer> customers) {
        for (int i = 0; i < preferences.length;i++) {
            int count = 0;
            double sum = 0;

            for (Customer customer : customers){
                if (customer.getCentroid() == this) {
                    sum += customer.getPreferences()[i];
                    count++;
                }
            }

            preferences[i] = sum / count;
        }
    }
}
