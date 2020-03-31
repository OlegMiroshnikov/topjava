package ru.javawebinar.topjava.web;

import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.MEALS_TO;
import static ru.javawebinar.topjava.MealToTestData.MEAL_TO_MATCHER;
import static ru.javawebinar.topjava.UserTestData.*;

class RootControllerTest extends AbstractControllerTest {

    @Test
    void getUsers() throws Exception {
        perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users",
                        new AssertionMatcher<List<User>>() {
                            @Override
                            public void assertion(List<User> actual) throws AssertionError {
                                USER_MATCHER.assertMatch(actual, ADMIN, USER);
                            }
                        }
                ));
    }

    @Test
    void getMeals() throws Exception {
        perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                .andExpect(model().attribute("meals",
                        new AssertionMatcher<List<MealTo>>() {
                            @Override
                            public void assertion(List<MealTo> actual) throws AssertionError {
                                MEAL_TO_MATCHER.assertMatch(actual, MEALS_TO);
                            }
                        }
                ));

//                .andExpect(model().attribute("meals", new ArrayList<>(MEALS_TO)));

//                .andExpect(model().attribute("meals", contains(MEALS_TO)));

//                .andExpect(model().attribute("meals", hasItem(
//                        allOf(
//                                hasProperty("id", is(MEAL1_TO.getId())),
//                                hasProperty("dateTime", is(MEAL1_TO.getDateTime())),
//                                hasProperty("description", is(MEAL1_TO.getDescription())),
//                                hasProperty("calories", is(MEAL1_TO.getCalories())),
//                                hasProperty("excess", is(MEAL1_TO.isExcess()))
//                        )
//                )))
//                .andExpect(model().attribute("meals", hasItem(
//                        allOf(
//                                hasProperty("id", is(MEAL2_TO.getId())),
//                                hasProperty("dateTime", is(MEAL2_TO.getDateTime())),
//                                hasProperty("description", is(MEAL2.getDescription())),
//                                hasProperty("calories", is(MEAL2_TO.getCalories())),
//                                hasProperty("excess", is(MEAL2_TO.isExcess()))
//                        )
//                )))

    }
}