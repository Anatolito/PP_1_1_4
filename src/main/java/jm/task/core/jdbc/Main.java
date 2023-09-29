package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        Util.getConnection();
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Raffaello", "Santi", (byte) 37);
        userService.saveUser("Leonardo", "da Vinci", (byte) 67);
        userService.saveUser("Michelangelo", "di Buonarroti", (byte) 88);
        userService.saveUser("Donato", "di Betto Bardi", (byte) 80);

        userService.removeUserById(1);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
