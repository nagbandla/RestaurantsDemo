package com.food.test.Restaurants;

import com.food.test.Restaurants.model.User;
import com.food.test.Restaurants.repository.UserRepository;
import com.food.test.Restaurants.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class RestaurantsApplicationTests {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void getAllUsersTest() {
        when(userRepository.findAll()).thenReturn(Stream
                .of(new User(1L, "Steve", 60),
                        new User(2L, "Bill", 55)).collect(Collectors.toList()));
        assertEquals(2, userService.getAllUsers().size());
    }

    @Test
    void saveUserTest() {
        User user = new User(99L, "Test", 70);
        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user, userService.saveUser(user));
    }
}
