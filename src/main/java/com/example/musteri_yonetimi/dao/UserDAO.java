package com.example.musteri_yonetimi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.musteri_yonetimi.model.User;

/**
    DAO veri tasarım şablonu kullanıldı.

    Data Access Objects (DAO) tasarım şablonu ile,
    kullanılan veritabanına erişim ve veri depolama-edinme işlemi daha soyutlaştırılarak,
    diğer katmanların veritabanına olan bağımlılıkları azaltılır.
 */

public class UserDAO {
    // Database bağlantı URL'i
    private String jdbcURL = "jdbc:mysql://127.0.0.1:3306/demo?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";


    // Database sahibinin bilgileri.
    private String jdbcUsername = "root";
    private String jdbcPassword = "1234";



    /**
        Database'e müşteri ekleme komutu (SQL)
        (? ,?, ?) yer tutuculardır, sırasıyla (name, email, country)
     * */
    private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (name, email, country) VALUES "
            + " (?, ?, ?);";

    /**
        Müşteri seçme komutu (SQL).
        Müsteri bilgileri düzenlerken gerekiyor.
     */
    private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id =?";

    /**
        Tüm müşterileri çekme komutu (SQL).
        Müşteri tablasunu oluştururken gerekiyor.
     */
    private static final String SELECT_ALL_USERS = "select * from users";

    /** Müşteri silme komutu (SQL). */
    private static final String DELETE_USERS_SQL = "delete from users where id = ?;";

    /** Müşteri bilgilerini güncelleme komutu (SQL). */
    private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ?, country =? where id = ?;";

    public UserDAO() {
        /** Yapıcı metod (Constructor) */
    }

    /** "mysql-connector-java-8.0.13.jar" kütüphanesi kullanarak bağlantıyı gerçekleştirdik. */
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    public void insertUser(User user) throws SQLException {

        /** try-catch, bir hata oluşursa bağlantı otomatik kapanacak. */
        try (Connection connection = getConnection();

             /**
                PreparedStatement, parametreli sorguyu yürütmek için kullanılır.
                Oluşturduğumuz connection objesi ile ifade oluşturduk.
              */
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {

                /**
                    setString metodu;
                    yer tutucularla ayrılan yeri dolduruyor.
                 */
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, user.getCountry());

                /**
                    executeUpdate() metodu;
                    INSERT, UPDATE veya DELETE gibi bir SQL ifadesini çalıştırır.
                 */
                preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public User selectUser(int id) {
        /**
             Oluşturduğumuz User class'ından "user" nesnesi oluşturduk.
             Bu method "user" nesnesini döndürecek.
         */
        User user = null;

        /** Bağlantı kuruluyor. */
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
                preparedStatement.setInt(1, id);
                System.out.println(preparedStatement);
                ResultSet rs = preparedStatement.executeQuery();

           /** "user" nesnemizi database'den çektiğimiz bilgileri ekledik */
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");

                /** User yapıcı bize yeni nesne dönderdi.*/
                user = new User(id, name, email, country);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return user;
    }

    public List<User> selectAllUsers() {

        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
                 System.out.println(preparedStatement);
                 ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return users;
    }

    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
                statement.setInt(1, id);

                /**
                    Silme işlemi gerçekleştiyse "statement.executeUpdate()" "1" dönderir.
                    gerçekleşmez ise "-1" dönderir.
                 */
                rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getEmail());
                statement.setString(3, user.getCountry());
                statement.setInt(4, user.getId());

                /**
                     Güncelleme işlemi gerçekleştiyse "statement.executeUpdate()" "1" dönderir.
                     gerçekleşmez ise "-1" dönderir.
                 */
                rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }


    /**
        SQL kaynaklı hataları ekrana bastırır.
     */
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
