package com.food.test.Restaurants.Utils;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CSVFileWriter uses opencsv library see pom.xml
 */
public class CSVFileWriter {

    public static void writeDataToCSVFile(String fileName, String[] strLines) throws IOException {
        List<String[]> csvData = new ArrayList<>();
        String[] header = {"name", "age"};
        csvData.add(header);
        csvData.add(strLines);
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            writer.writeAll(csvData);
        }
    }

    //Test, move this to Junits
    private static List<String[]> createCsvDataSimple() {
        String[] header = {"id", "name", "age"};
        String[] record1 = {"1", "Steve", "65"};
        String[] record2 = {"2", "Bill", "59"};

        List<String[]> list = new ArrayList<>();
        list.add(header);
        list.add(record1);
        list.add(record2);

        return list;
    }

    public static void main(String[] args) throws IOException {
        List<String[]> csvData = createCsvDataSimple();
        try (CSVWriter writer = new CSVWriter(new FileWriter("C:\\temp\\test.csv"))) {
            writer.writeAll(csvData);
        }
    }
}
