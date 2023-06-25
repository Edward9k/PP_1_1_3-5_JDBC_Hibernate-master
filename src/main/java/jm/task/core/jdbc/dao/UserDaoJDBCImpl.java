package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final static Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(255), " +
                "lastname VARCHAR(255), " +
                "age TINYINT)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableQuery);
            System.out.println("Таблица 'users' успешно создана.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropTableQuery);
            System.out.println("Таблица 'users' успешно удалена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveIntoQuery = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(saveIntoQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println("Пользователь " + name + " успешно сохранен.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String removeQuery = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            System.out.println("Пользователь успешно удален: ID пользователя " + id + ".");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectAllQuery = "SELECT * FROM users";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectAllQuery);

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                user.setId(id);
                userList.add(user);

                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Last Name: " + lastName);
                System.out.println("Age: " + age);
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        String cleanTable = "TRUNCATE TABLE users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(cleanTable);
            System.out.println("Таблица 'users' успешно очищена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
