package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.*;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;


@Controller
public class MealRestController {
    protected final Logger log = getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal get(int id) {
        log.info("get {} by userId={}", id, authUserId());
        return service.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {} by userId={}", meal, authUserId());
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete {} by userId={}", id, authUserId());
        service.delete(id, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={} by userId={}", meal, id, authUserId());
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }

    public List<MealTo> getAll() {
        log.info("getAll by userId={}", authUserId());
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId(), meal -> true), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getByDateTime(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        log.info("get from date{} and time {} to date{} and time {}", startDate, startTime, endDate, endTime);
        if (isValidDate(startDate) && isValidTime(startTime) && isValidDate(endDate) && isValidTime(endTime)) {
            return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId(),
                    meal -> DateTimeUtil.isBetweenDateInclusive(meal.getDate(), startDate, endDate) &&
                            DateTimeUtil.isBetweenTimeInclusive(meal.getTime(), startTime, endTime)),
                    SecurityUtil.authUserCaloriesPerDay());
        } else {
            return new ArrayList<>();
        }
    }
}