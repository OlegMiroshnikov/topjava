package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    private static final Logger log = LoggerFactory.getLogger(MealServiceTest.class);

    @Autowired
    private MealService service;
    @Autowired
    private MealRepository repository;


    public static class AllTestExecutionTime extends TestWatcher {
        Map<String, Long> testsExecutionTime = new HashMap<>();

        public void addTestTime(String test, Long time) {
            testsExecutionTime.put(test, time);
        }

        @Override
        protected void finished(Description description) {
            log.info("All tests time in nanosekonds: {}", testsExecutionTime);
        }
    }

    public class TestExecutionTime extends TestWatcher {
        long startTimeTest;
        long endTimeTest;

        @Override
        protected void starting(Description description) {
            startTimeTest = System.nanoTime();
        }

        @Override
        protected void finished(Description description) {
            endTimeTest = System.nanoTime();
            long testDuration = endTimeTest - startTimeTest;
            log.info("{}: duration of the test = {} ns", description.getMethodName(), testDuration);
            allTestExecutionTime.addTestTime(description.getMethodName(), testDuration);
        }
    }

    @ClassRule
    public static AllTestExecutionTime allTestExecutionTime = new AllTestExecutionTime();
    @Rule
    public TestExecutionTime testExecutionTime = new TestExecutionTime();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        Assert.assertNull(repository.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage(is("Not found entity with id=1"));
        service.delete(1, USER_ID);
    }

    @Test
    public void deleteNotOwn() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage(is("Not found entity with id="+ MEAL1_ID));
        service.delete(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = getCreated();
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void get() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        MEAL_MATCHER.assertMatch(actual, ADMIN_MEAL1);
    }

    @Test
    public void getNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage(is("Not found entity with id=1"));
        service.get(1, USER_ID);
    }

    @Test
    public void getNotOwn() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage(is("Not found entity with id="+ MEAL1_ID));
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        MEAL_MATCHER.assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    public void updateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage(is("Not found entity with id="+ MEAL1_ID));
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void getAll() throws Exception {
        MEAL_MATCHER.assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void getBetweenInclusive() throws Exception {
        MEAL_MATCHER.assertMatch(service.getBetweenInclusive(
                LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30), USER_ID),
                MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void getBetweenWithNullDates() throws Exception {
        MEAL_MATCHER.assertMatch(service.getBetweenInclusive(null, null, USER_ID), MEALS);
    }

}