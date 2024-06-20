package com.food.test.Restaurants.service;

import com.food.test.Restaurants.model.User;
import com.food.test.Restaurants.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
@Service
public class WriteDBGetAck {

    private final Logger logger =
            LoggerFactory.getLogger(WriteDBGetAck.class);

    @Autowired
    private UserRepository userRepository;

    @Async("asyncTaskExecutor")
    public CompletableFuture<User> getAckAfterWriteToDB(User user) throws Exception {
        logger.info(" getAckAfterWriteToDB  2nd task " + Thread.currentThread().getName());
        Thread.sleep(5000);
        User finalUser = userRepository.save(user);
        return CompletableFuture.completedFuture(finalUser);
    }

}
