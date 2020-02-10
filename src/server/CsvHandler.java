package server;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.List;

public class CsvHandler {

    // key used by our simulated Physical Devices
    private static final String KEY = "5468697349734150617373776f726431";

    public static List read(String filePath){
        File file = new File(filePath);

        try {
            FileReader fileReader = new FileReader(file);
            CSVReader reader = new CSVReader(fileReader);
            List<String[]> csv = reader.readAll();
            reader.close();
            return csv;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void generateSimulationData(String filePath, int serverCount, int startPort){
        // create our simulation data

        File file = new File(filePath);

        try {
            FileWriter fileWriter = new FileWriter(file);

            CSVWriter writer = new CSVWriter(fileWriter);

            String[] header = {"Physical Address", "Port", "Key"};
            writer.writeNext(header);

            for(int i = 0; i < serverCount; i++){
                String[] line = {"127.0.0.1", String.format("%d", startPort + i), KEY};
                writer.writeNext(line);
            }

            // close the file
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
