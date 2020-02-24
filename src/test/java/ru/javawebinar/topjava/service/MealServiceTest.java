package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void create() throws Exception {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, USER1_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER1_ID), newMeal);
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER1_ID);
        service.get(MEAL1_ID, USER1_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(1, USER1_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deletedAnothersMeal() throws Exception {
        service.delete(MEAL1_ID, USER2_ID);
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL1_ID, USER1_ID);
        assertMatch(meal, MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, USER1_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getAnothersMeal() throws Exception {
        service.get(MEAL1_ID, USER2_ID);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER1_ID);
        assertMatch(service.get(MEAL1_ID, USER1_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateAnothesMeal() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER2_ID);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> betweenHalfOpenFilteredList = service.getBetweenHalfOpen(MEAL_DATE_1, MEAL_DATE_2, USER1_ID);
        assertMatch(betweenHalfOpenFilteredList, MEAL2, MEAL1);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(USER1_ID);
        assertMatch(all, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

}