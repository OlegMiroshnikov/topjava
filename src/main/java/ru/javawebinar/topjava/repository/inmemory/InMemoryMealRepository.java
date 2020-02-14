package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public InMemoryMealRepository() {
        MealsUtil.MEALS.forEach(meal -> {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
        });
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        log.info("save {} by userId={}", meal, userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        if (meal.getUserId().equals(userId)) {
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id, Integer userId) {
        log.info("delete {} by userId={}", id, userId);
        Meal meal = repository.get(id);
        if (meal != null && meal.getUserId().equals(userId)) {
            return repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, Integer userId) {
        log.info("get {} by userId={}", id, userId);
        Meal meal = repository.get(id);
        return meal != null && meal.getUserId().equals(userId) ? meal : null;
    }

    @Override
    public List<Meal> getAll(Integer userId, Predicate<Meal> filter) {
        log.info("getAll by userId={}", userId);
        return repository.values().stream()
                .filter(m -> m.getUserId().equals(userId))
                .filter(filter)
                .sorted(comparing(Meal::getDateTime).reversed())
                .collect(toList());
    }
}

