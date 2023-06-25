package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Евгений", "Пригожин", (byte) 62);
        userService.saveUser("Чвк", "Вагнер", (byte) 25);
        userService.saveUser("Си", "Цзиньпин", (byte) 70);
        userService.saveUser("Сергей", "Шойгу", (byte) 68);

        userService.removeUserById(3);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

        Util.closeSessionFactory();
    }
}
