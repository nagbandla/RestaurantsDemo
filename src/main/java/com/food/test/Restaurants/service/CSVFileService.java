package com.food.test.Restaurants.service;

import com.food.test.Restaurants.Utils.CSVFileWriter;
import com.food.test.Restaurants.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CSVFileService {
    private final Logger logger =
            LoggerFactory.getLogger(CSVFileService.class);
    private static final String FILE_NAME = "C:\\temp\\test.csv";

    @Async("asyncTaskExecutor")
    public void testAsyncMethod() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("CSVFileService "+Thread.currentThread().getName());

    }

    @Async("asyncTaskExecutor")
    public void writeToCSVFile(User user) {
        logger.info(" writeToCSVFile - 1st task " + Thread.currentThread().getName());
        String name = user.getName();
        String age = String.valueOf(user.getAge());
        String[] lineStr = {name, age};
        try {
            Thread.sleep(5000);
            CSVFileWriter.writeDataToCSVFile(FILE_NAME, lineStr);
        } catch (Exception e) {
            //This needs to be replaced to write to the logger using e.getMessage
            e.printStackTrace();
        }

    }
}
