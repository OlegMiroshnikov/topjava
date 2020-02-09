package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoInMemoryImpl implements MealDao {

    private static List<Meal> meals;

    public MealDaoInMemoryImpl() {
        this.meals = MealsUtil.createList();
    }

    @Override
    public List<Meal> getAll() {
        return this.meals;
    }

    @Override
    public void addMeal(Meal meal) {
        Integer maxId = meals.stream()
                .mapToInt(m->m.getId())
                .max()
                .orElse(0);
        meal.setId(new AtomicInteger(maxId).incrementAndGet());
        meals.add(meal);
    }

    @Override
    public Optional<Meal> getMealById(int id) {
        return meals.stream()
                .filter(m->m.getId() == id)
                .findFirst();
    }

    @Override
    public void updateMeal(Meal meal) {
        Optional<Meal> optionalMeal = getMealById(meal.getId());
        if (optionalMeal.isPresent()) {
            Meal editMeal = optionalMeal.get();
            editMeal.setDateTime(meal.getDateTime());
            editMeal.setDescription(meal.getDescription());
            editMeal.setCalories(meal.getCalories());
        }
    }

    @Override
    public void removeMeal(int id) {
        meals.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .ifPresent(m -> meals.remove(m));
    }
}
