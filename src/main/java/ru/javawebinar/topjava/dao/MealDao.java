package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.Optional;

public interface MealDao {

    List<Meal> getAll();
    Optional<Meal> getMealById(int id);
    void addMeal(Meal meal);
    void updateMeal(Meal meal);
    void removeMeal(int id);
}
