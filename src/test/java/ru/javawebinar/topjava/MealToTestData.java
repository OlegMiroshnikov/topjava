package ru.javawebinar.topjava;

import ru.javawebinar.topjava.to.MealTo;

import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;

public class MealToTestData {
    public static TestMatcher<MealTo> MEAL_TO_MATCHER = TestMatcher.usingFieldsComparator(MealTo.class, "user");

    public static final MealTo MEAL1_TO = new MealTo(MEAL1_ID, of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500, false);
    public static final MealTo MEAL2_TO = new MealTo(MEAL1_ID + 1, of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000, false);
    public static final MealTo MEAL3_TO = new MealTo(MEAL1_ID + 2, of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500, false);
    public static final MealTo MEAL4_TO = new MealTo(MEAL1_ID + 3, of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100, true);
    public static final MealTo MEAL5_TO = new MealTo(MEAL1_ID + 4, of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 500, true);
    public static final MealTo MEAL6_TO = new MealTo(MEAL1_ID + 5, of(2020, Month.JANUARY, 31, 13, 0), "Обед", 1000, true);
    public static final MealTo MEAL7_TO = new MealTo(MEAL1_ID + 6, of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 510, true);
    public static final MealTo ADMIN_MEAL1_TO = new MealTo(ADMIN_MEAL_ID, of(2020, Month.JANUARY, 31, 14, 0), "Админ ланч", 510, true);
    public static final MealTo ADMIN_MEAL2_TO = new MealTo(ADMIN_MEAL_ID + 1, of(2020, Month.JANUARY, 31, 21, 0), "Админ ужин", 1500, true);

    public static final List<MealTo> MEALS_TO = List.of(MEAL7_TO, MEAL6_TO, MEAL5_TO, MEAL4_TO, MEAL3_TO, MEAL2_TO, MEAL1_TO);

}
