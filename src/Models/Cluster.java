package Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by larsh on 21-6-2016.
 */
public class Cluster {

    public List<Customer> customers;
    public Customer centroid;
    public int id;

    public Cluster(int id) {
        this.id = id;
        this.customers = new ArrayList();
        this.centroid = null;
    }

    public List getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void setCustomer(List customers) {
        this.customers = customers;
    }

    public Customer getCentroid() {
        return centroid;
    }

    public Customer createRandomCentroid(double[] preferences) {
//        double[] centroidPrefs = new double[32];
//
//        for (int i = 0; i < centroidPrefs.length; i++) {
//            centroidPrefs[i] = (Math.random() < .5) ? 0 : 1;
//        }

        Customer centroid = new Customer(preferences);

        return centroid;
    }

    public void setCentroid(Customer centroid) {
        this.centroid = centroid;
    }

    public int getId() {
        return id;
    }

    public void clear() {
        customers.clear();
    }

    public void plotCluster() {
        System.out.println("Cluster: " + id);
        System.out.println("Centroid: " + centroid);
//        System.out.println("Customers: \n");
//        for (Customer c : customers) {
//            System.out.println(c);
//        }
    }
}
