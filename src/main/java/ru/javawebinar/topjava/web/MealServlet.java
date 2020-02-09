package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.ENGLISH);

    private MealDao mealDao = null;

    public MealServlet() {
        super();
        mealDao = MealsUtil.getConnection();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = request.getParameter("action");
        if (action == null) {
            log.debug("get all meals");
            List<MealTo> mealsTo = MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("meals", mealsTo);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
        if (action.equalsIgnoreCase("delete")) {
            String idParameter = request.getParameter("id");
            if (idParameter != null) {
                log.debug("meals delete by id = " + idParameter);
                mealDao.removeMeal(Integer.parseInt(idParameter));
            }
            response.sendRedirect(request.getContextPath() + "/meals");
        }
        if (action.equalsIgnoreCase("edit")) {
            String idParameter = request.getParameter("id");
            if (idParameter != null) {
                log.debug("meals edit by id = " + idParameter);
                Optional<Meal> OptionalMeal = mealDao.getMealById(Integer.parseInt(idParameter));
                if (OptionalMeal.isPresent()) {
                    Meal meal = OptionalMeal.get();
                    Meal mealTo = new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
                    request.setAttribute("meal", meal);
                    request.getRequestDispatcher("/mealedit.jsp").forward(request, response);
                }
            }
        }
        if (action.equalsIgnoreCase("insert")) {
            log.debug("insert new meal");
            Meal mealTo = new Meal(0, LocalDateTime.now(), "", 0);
            request.setAttribute("meal", mealTo);
            request.getRequestDispatcher("/mealedit.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal();
        meal.setDateTime(LocalDateTime.parse(request.getParameter("dateTime").trim(), dateTimeFormatter));
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        String idParameter = request.getParameter("id");
        if (idParameter == null || Integer.parseInt(idParameter) == 0) {
            mealDao.addMeal(meal);
        } else {
            meal.setId(Integer.parseInt(idParameter));
            mealDao.updateMeal(meal);
        }
        response.sendRedirect(request.getContextPath() + "/meals");
    }
}