package com.example.musteri_yonetimi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.musteri_yonetimi.model.User;
import org.mindrot.jbcrypt.BCrypt;

/**
    DAO veri tasarım şablonu kullanıldı.

    Data Access Objects (DAO) tasarım şablonu ile,
    kullanılan veritabanına erişim ve veri depolama-edinme işlemi daha soyutlaştırılarak,
    diğer katmanların veritabanına olan bağımlılıkları azaltılır.
 */

public class UserDAO {
    // Database bağlantı URL'i
    private String jdbcURL = "jdbc:mysql://us-cdbr-east-03.cleardb.com:3306/heroku_76b0a109eee6ba5?reconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    // Database sahibinin bilgileri.
    private String jdbcUsername = "be76a684cf14f1";
    private String jdbcPassword = "6c75e8bb";

    private static final String VALIDATE_USER = "SELECT * from users WHERE name = ?";
    private static final String SELECT_USER_BY_ID = "SELECT id, name, password FROM users WHERE id =?";
    private static final String SELECT_ALL_USERS = "SELECT users.name AS 'username', COUNT(customers.id) AS 'numberOfCustomer'" +
            "FROM customers\n" +
            "INNER JOIN users ON users.id=customers.userID\n" +
            "GROUP BY userID;";
    private static final String CHECK_USER_BY_NAME = "SELECT id, name, password FROM users WHERE name =?";
    private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (id, name, password) VALUES "
            + " (?, ?, ?);";

    public UserDAO() {}

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public String findUserByName(String name) throws SQLException {
        String userID = null;

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(CHECK_USER_BY_NAME);) {
            preparedStatement.setString(1, name);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String username = rs.getString("name");
                if (username.equals(name)) {
                    userID = id;
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return userID;
    }


    public void insertUser(User user) throws SQLException {
        System.out.println(INSERT_USERS_SQL);


        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public User selectUser(String id) {

        User user = null;

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String password = rs.getString("password");

                user = new User(id, name, password);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return user;
    }

    public List<User> selectAllUsers() {

        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("username");
                String numberOfCustomer = rs.getString("numberOfCustomer");
                System.out.println(name + " - " +numberOfCustomer);
                users.add(new User(null, name, null, numberOfCustomer));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return users;
    }

    public boolean validate(User user) throws ClassNotFoundException {
        boolean status = false;


        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(VALIDATE_USER);) {
            preparedStatement.setString(1, user.getName());

            ResultSet rs = preparedStatement.executeQuery();
            status = rs.next();

            String hashedPW = rs.getString("password");
            status = status && BCrypt.checkpw(user.getPassword(), hashedPW);

        } catch (SQLException e) {
            printSQLException(e);
        }
        return status;
    }



    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

}