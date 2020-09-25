package utils;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CSVParser {
    /***
     * This is a CSV Parse class
     */

    public static HashMap<String, String> parseCSV(String fileName) {
        /**
         * This function reads data from CSV file and parse
         * @param fileName CSV
         * @return hashmap (key value)
         */
        HashMap<String, String> hmap = new HashMap<String, String>();
        try (CSVReader csvreader = new CSVReader(new FileReader(fileName))) {
            List<String[]> csvListElements = csvreader.readAll();
            csvreader.close();
            for (String[] array : csvListElements) {
                hmap.put(array[0], array[1]);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hmap;
    }
}
