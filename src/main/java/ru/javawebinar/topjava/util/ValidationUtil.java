package ru.javawebinar.topjava.util;


import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationUtil {

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(AbstractBaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(AbstractBaseEntity entity, int id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }

    public static boolean isValidDate(LocalDate localDate) {
        try {
            String formattedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate date = LocalDate.parse(formattedDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(localDate + " is not valid");
        }
        return true;
    }

    public static boolean isValidTime(LocalTime localTime) {
        try {
            String formattedTime = localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime time = LocalTime.parse(formattedTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(localTime + " is not valid");
        }
        return true;
    }
}