package com.food.test.Restaurants;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Restaurant {
    public String name;
    public Location location;
    public Food food;

    public Restaurant(String name, Location location, Food food) {
        this.name = name;
        this.location = location;
        this.food = food;
    }

    public Location getLocation() {
        return location;
    }

    public Food getFood() {
        return food;
    }

    public String getName() {
        return name;
    }

    public static void main(String[] args) {

        List<Restaurant> restaurantList = Arrays.asList(
          new Restaurant("DesiDistrict",new Location("Austin"), new Food("Pizza")),
                new Restaurant("Dominos",new Location("Houston"), new Food("Chineze Biryani")),
                new Restaurant("pizzahut",new Location("Dallas"), new Food("Pizza"))
        );

    //output, Desidistrict and pizzahut

       List<String> namesOfRestaurants = restaurantList.stream().sorted(Comparator.comparing(restaurant -> restaurant.getLocation().getAddress()))
                .filter(restaurant -> restaurant.getFood().getName().equals("Pizza"))
                .map(Restaurant::getName).collect(Collectors.toList());

        System.out.println(namesOfRestaurants);
    }
}
