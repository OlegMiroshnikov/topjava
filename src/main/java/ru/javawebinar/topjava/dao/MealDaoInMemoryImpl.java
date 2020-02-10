package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealDaoInMemoryImpl implements MealDao {

    private Map<Integer, Meal> meals;
    private int maxId = 0;

    public MealDaoInMemoryImpl() {
        this.meals = createMap();
        this.maxId = Collections.max(meals.keySet());
    }

    private synchronized int getNextId() {
        maxId++;
        return maxId;
    }

    private Map<Integer, Meal> createMap() {
        Map<Integer, Meal> map = new ConcurrentHashMap<>();
        map.put(1, new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        map.put(2, new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        map.put(3, new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        map.put(4, new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        map.put(5, new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        map.put(6, new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        map.put(7, new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        return map;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public void add(Meal meal) {
        int id = getNextId();
        meal.setId(id);
        meals.put(id, meal);
    }

    @Override
    public Meal getById(int id) {
        return meals.get(id);
    }

    @Override
    public void update(Meal meal) {
        meals.replace(meal.getId(), meal);
    }

    @Override
    public void remove(int id) {
        meals.remove(id);
    }
}
