package com.food.test.Restaurants.service;

import com.food.test.Restaurants.Utils.CSVFileWriter;
import com.food.test.Restaurants.model.User;
import com.food.test.Restaurants.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

@Service
public class UserServiceImpl implements UserService {

    private static final String FILE_NAME = "C:\\temp\\test.csv";
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {

        //write logic to write csv file
        writeToCSVFile(user);

        //2nd task writing db data as acknowledgement to caller
        User finalUser = null;
        CompletableFuture<User> future = getAckAfterWriteToDB(user);
        try {
            finalUser = future.get(1,TimeUnit.SECONDS);
        } catch (Exception e) {
            //This needs to be updated with more specific exceptions and logging
            e.printStackTrace();
        }

        return finalUser;
    }

    @Async("asyncTaskExecutor")
    public void writeToCSVFile(User user) {
            String name = user.getName();
            String age = String.valueOf(user.getAge());
            String[] lineStr = {name, age};
            try {
                CSVFileWriter.writeDataToCSVFile(FILE_NAME, lineStr);
            } catch (IOException e) {
                //This needs to be replaced to write to the logger using e.getMessage
                e.printStackTrace();
            }

    }
    @Async("asyncTaskExecutor")
    public CompletableFuture<User> getAckAfterWriteToDB(User user){
        User finalUser = userRepository.save(user);
        return CompletableFuture.completedFuture(finalUser);
    }


}

