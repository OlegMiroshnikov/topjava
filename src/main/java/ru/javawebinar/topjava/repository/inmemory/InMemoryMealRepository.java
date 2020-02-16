package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public InMemoryMealRepository() {
        MealsUtil.MEALS.forEach(meal -> save(meal, authUserId()));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        log.info("save {} by userId={}", meal, userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            meals.put(meal.getId(), meal);
            repository.put(userId, meals);
            return meal;
        }
        Meal oldMeal = repository.get(userId).get(meal.getId());
        if (oldMeal != null && oldMeal.getUserId().equals(userId)) {
            meal.setUserId(userId);
            meals.put(meal.getId(), meal);
            repository.put(userId, meals);
            return meal;
        }
        return null;
    }

    @Override
    public boolean delete(int id, Integer userId) {
        log.info("delete {} by userId={}", id, userId);
        Meal meal = repository.get(userId).get(id);
        if (meal != null && meal.getUserId().equals(userId)) {
            Meal removedMeal = meals.remove(id);
            if (meals.size() == 0) {
                repository.remove(userId);
            }
            return removedMeal != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, Integer userId) {
        log.info("get {} by userId={}", id, userId);
        Meal meal = repository.get(userId).get(id);
        return meal != null && meal.getUserId().equals(userId) ? meal : null;
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        log.info("getAll by userId={}", userId);
        return repository.entrySet().stream()
                .filter(e -> e.getKey().equals(userId))
                .map(e -> e.getValue())
                .map(Map::values)
                .flatMap(Collection::stream)
                .sorted(comparing(Meal::getDateTime).reversed())
                .collect(toList());
    }

    @Override
    public List<Meal> getAllbyFilter(Integer userId, Predicate<Meal> filter) {
        log.info("getAll by userId={} with filter{}", userId, filter);
        return repository.entrySet().stream()
                .filter(e -> e.getKey().equals(userId))
                .map(e -> e.getValue())
                .map(Map::values)
                .flatMap(Collection::stream)
                .filter(filter)
                .sorted(comparing(Meal::getDateTime).reversed())
                .collect(toList());
    }
}

