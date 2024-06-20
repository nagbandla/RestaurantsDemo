package com.food.test.Restaurants.service;

import com.food.test.Restaurants.Utils.CSVFileWriter;
import com.food.test.Restaurants.controllers.UserController;
import com.food.test.Restaurants.model.User;
import com.food.test.Restaurants.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

@Service
public class UserServiceImpl implements UserService {
    private final Logger logger =
            LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String FILE_NAME = "C:\\temp\\test.csv";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CSVFileService csvFileService;

    @Autowired
    private WriteDBGetAck writeDBGetAck;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        logger.info(" saveUser " + Thread.currentThread().getName());
        //write logic to write csv file
        csvFileService.writeToCSVFile(user);

        //2nd task writing db data as acknowledgement to caller
        User finalUser = null;
        try {
            CompletableFuture<User> future = writeDBGetAck.getAckAfterWriteToDB(user);
            finalUser = future.get(6, TimeUnit.SECONDS);
        } catch (Exception e) {
            //This needs to be updated with more specific exceptions and logging
            e.printStackTrace();
        }
        logger.info("====ACK====" + finalUser.getName() + " " + finalUser.getAge());

        return finalUser;
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

    @Async("asyncTaskExecutor")
    public CompletableFuture<User> getAckAfterWriteToDB(User user) throws Exception {
        logger.info(" getAckAfterWriteToDB  2nd task " + Thread.currentThread().getName());
        Thread.sleep(5000);
        User finalUser = userRepository.save(user);
        return CompletableFuture.completedFuture(finalUser);
    }


}

