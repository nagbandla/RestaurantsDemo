package com.food.test.Restaurants.service;

import com.food.test.Restaurants.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User  saveUser(User user);
}
