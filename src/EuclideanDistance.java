/**
 * Created by larsh on 15-6-2016.
 */
public class EuclideanDistance {

    public double calculate(double[] centroid, double[] customer) {
        double distance = 0;

        for (int i = 0; i < centroid.length; i++) {
            distance += (centroid[i] - customer[i]) * (centroid[i] - customer[i]);
        }
        distance = Math.sqrt(distance);

        return distance;
    }
}
