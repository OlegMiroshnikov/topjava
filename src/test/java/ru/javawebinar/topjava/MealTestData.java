package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER1_ID = START_SEQ;
    public static final int USER2_ID = START_SEQ + 1;
    public static final int ADMIN_ID = START_SEQ + 2;
    public static final int MEAL1_ID = START_SEQ + 3;
    public static final int MEAL2_ID = START_SEQ + 4;
    public static final int MEAL3_ID = START_SEQ + 5;
    public static final int MEAL4_ID = START_SEQ + 6;
    public static final int MEAL5_ID = START_SEQ + 7;
    public static final int MEAL6_ID = START_SEQ + 8;
    public static final int MEAL7_ID = START_SEQ + 9;
    public static final int MEAL8_ID = START_SEQ + 10;
    public static final LocalDate MEAL_DATE_1 = LocalDate.parse("2020-01-30");
    public static final LocalDate MEAL_DATE_2 = LocalDate.parse("2020-01-30");

    public static final Meal MEAL1 = new Meal(MEAL1_ID, LocalDateTime.parse("2020-01-30T14:00"), "Обед", 1000);
    public static final Meal MEAL2 = new Meal(MEAL2_ID, LocalDateTime.parse("2020-01-30T20:00"), "Ужин", 500);
    public static final Meal MEAL3 = new Meal(MEAL3_ID, LocalDateTime.parse("2020-01-31T00:00"), "Еда на граничное значение", 100);
    public static final Meal MEAL4 = new Meal(MEAL4_ID, LocalDateTime.parse("2020-01-31T07:00"), "Завтрак", 1000);
    public static final Meal MEAL5 = new Meal(MEAL5_ID, LocalDateTime.parse("2020-01-31T14:00"), "Обед", 500);
    public static final Meal MEAL6 = new Meal(MEAL6_ID, LocalDateTime.parse("2020-01-31T20:00"), "Ужин", 410);
    public static final Meal MEAL7 = new Meal(MEAL7_ID, LocalDateTime.parse("2020-01-30T07:00"), "Завтрак", 500);
    public static final Meal MEAL8 = new Meal(MEAL8_ID, LocalDateTime.parse("2020-01-30T14:00"), "Обед", 1000);

    public static Meal getNew() {
        return new Meal(LocalDateTime.now(), "newDescription", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("UpdatedDescription");
        updated.setCalories(1330);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
//        assertThat(actual).isEqualToIgnoringGivenFields(expected, "");
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }


    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
//        assertThat(actual).usingElementComparatorIgnoringFields("").isEqualTo(expected);
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

}
