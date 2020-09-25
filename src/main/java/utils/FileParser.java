package utils;

import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FileParser {
    /**
     * This class parse input txt File, ignores lines starts with SZ[record, [record,], creates key value pairs. Based on number
     * of transactions, generates csv files with list of key and values.
     *
     * @param fileName:
     * @return generates CSV files based on transactions
     */

    // Parse input file to create key value harsh maps and splits transactions
    public static List<Map> parseFile(String fileName) {
        /**
         * This method parse input file and creates hash map
         */
        String tmpString;
        HashMap<String, String> hmap = new HashMap<String, String>();
        List<Map> listOfMaps = new ArrayList();
        int count = 0;
        try (Scanner s = new Scanner(new FileReader(fileName))) {
            while (s.hasNext()) {
                final String word = s.nextLine();
                tmpString = StringUtils.normalizeSpace(word.replaceAll("SZ\\[record", ""));
                tmpString = StringUtils.normalizeSpace(tmpString.replaceAll("\\[record", ""));
                tmpString = StringUtils.normalizeSpace(tmpString.replaceAll("\\]", ""));
                String[] listOfStrings = tmpString.split("\\s+", 2);
                if (listOfStrings.length == 2) {
                    hmap.put(listOfStrings[0], listOfStrings[1]);
                }
                if (count > 0) {
                    if (word.contains("SZ")) {
                        listOfMaps.add(hmap);
                        hmap = new HashMap<String, String>();
                    }
                }
                count++;
            }
            listOfMaps.add(hmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return listOfMaps;
    }

    public static List<String> writeListOfHasMapsToCSV(List<Map> listOfHashMaps) {
        /**
         * This method writes list of Hash Maps to CSV
         */
        Iterator it = listOfHashMaps.iterator();
        List<String> outputFileList = new ArrayList<>();
        int count = 0;
        while (it.hasNext()) {
            String fileName = "target/SampleOutput_" + count + "_" + new Date().getTime() + ".csv";
            outputFileList.add(fileName);
            writeHashMapToCSV((HashMap<String, String>) it.next(), fileName);
            count++;
        }
        return outputFileList;
    }

    public static void writeHashMapToCSV(HashMap<String, String> hashMap, String fileName) {
        /**
         * This method writes hash Map to CSV
         */
        FileWriter writer;
        try {
            writer = new FileWriter(fileName, true);
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                writer.write((String) pair.getKey());
                writer.write(",");
                writer.write((String) pair.getValue());
                writer.write("\r\n");
                it.remove();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
