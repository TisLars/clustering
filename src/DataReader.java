import Models.Customer;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by larsh on 21-6-2016.
 */
public class DataReader {

    public ArrayList<Customer> readData() {
        String csvFile = getClass().getResource("WineData.csv").getFile();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        ArrayList<Customer> customers = new ArrayList<>();
        int count = 0;

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] value = line.split(cvsSplitBy);

//                double prefs[] = new double[2];
//                prefs[0] = Double.parseDouble(value[0]);
//                prefs[1] = Double.parseDouble(value[1]);
//
//                customers.add(new Customer(prefs));

                for (int i = 0; i < value.length; i++) {
                    if (i >= customers.size())
                        customers.add(new Customer(32));

                    customers.get(i).addPreference(count, Integer.parseInt(value[i]));
                }
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return customers;
    }
}
