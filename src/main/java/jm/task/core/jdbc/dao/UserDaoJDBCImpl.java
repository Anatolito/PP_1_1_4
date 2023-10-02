package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users ("+
            " id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
            " name VARCHAR(45) NOT NULL," +
            " lastName VARCHAR(45) NOT NULL," +
            " age TINYINT)";
    private static final String SAVE_USER = "INSERT INTO users(name,lastName,age)VALUES(?,?,?)";
    private static final String REMOVE_USER = "DELETE FROM users WHERE id=?";
    private static final String GET_USERS = "SELECT * FROM users";
    private static final String CLEAN_TABLE = "DELETE FROM users";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users";

    public UserDaoJDBCImpl() {
        // by the condition of the task
    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.prepareStatement(CREATE_TABLE)){
            statement.executeUpdate(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.prepareStatement(DROP_TABLE)){
            statement.executeUpdate(DROP_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_USERS);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.prepareStatement(CLEAN_TABLE)){
            statement.executeUpdate(CLEAN_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
