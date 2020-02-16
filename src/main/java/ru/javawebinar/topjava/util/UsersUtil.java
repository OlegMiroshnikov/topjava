package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User("User1", "user1_email", "user1_psw", Role.ROLE_USER),
            new User("User2", "user2_email", "user2_psw", Role.ROLE_USER),
            new User("Admin", "email_admin", "admin_psw", Role.ROLE_ADMIN));
}
