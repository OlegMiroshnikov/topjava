package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInMemoryImpl;
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

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.ENGLISH);

    private MealDao mealDao = null;

    public MealServlet() {
        super();
        mealDao = new MealDaoInMemoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = request.getParameter("action");
        action = action == null ? "": action;
        String idParameter = request.getParameter("id");
            switch (action.toLowerCase()) {
                case "delete":
                    if (idParameter != null) {
                        log.debug("meals delete by id = " + idParameter);
                        mealDao.remove(Integer.parseInt(idParameter));
                    }
                    response.sendRedirect(request.getContextPath() + "/meals");
                    break;
                case "edit":
                    if (idParameter != null) {
                        log.debug("meals edit by id = " + idParameter);
                        Meal meal = mealDao.getById(Integer.parseInt(idParameter));
                        if (meal != null) {
                            Meal mealTo = new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
                            request.setAttribute("meal", mealTo);
                            request.getRequestDispatcher("/mealedit.jsp").forward(request, response);
                        }
                    }
                    break;
                case "insert":
                    log.debug("adding a new meal");
                    Meal mealTo = new Meal(0, LocalDateTime.now(), "", 0);
                    request.setAttribute("meal", mealTo);
                    request.getRequestDispatcher("/mealedit.jsp").forward(request, response);
                    break;
                default:
                    log.debug("get all meals");
                    List<MealTo> mealsTo = MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
                    request.setAttribute("meals", mealsTo);
                    request.getRequestDispatcher("/meals.jsp").forward(request, response);
            }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idParameter = request.getParameter("id");
        int id = (idParameter == null || Integer.parseInt(idParameter) == 0) ? 0 : Integer.parseInt(idParameter);
        Meal meal = new Meal(
                id,
                LocalDateTime.parse(request.getParameter("dateTime").trim(), dateTimeFormatter),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );
        if (id == 0) {
            mealDao.add(meal);
        } else {
            mealDao.update(meal);
        }
        response.sendRedirect(request.getContextPath() + "/meals");
    }
}
