package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
//            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
//            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
//            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
//            mealRestController.create(new Meal(null, SecurityUtil.authUserId(), LocalDateTime.now(), "Description",  SecurityUtil.authUserCaloriesPerDay()));
//            Meal meal =   mealRestController.get(3);
//            mealRestController.update(meal, 5);
//            Meal meal = mealRestController.create(new Meal(null, 2, LocalDateTime.now(), "Description",  SecurityUtil.authUserCaloriesPerDay()));
//            mealRestController.update(meal, 8);
        }
    }
}
