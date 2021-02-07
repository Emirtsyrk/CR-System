package com.example.musteri_yonetimi.dao;

import com.example.musteri_yonetimi.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerDAO {

    private String jdbcURL = "jdbc:mysql://us-cdbr-east-03.cleardb.com:3306/heroku_76b0a109eee6ba5?reconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    // Database sahibinin bilgileri.
    private String jdbcUsername = "be76a684cf14f1";
    private String jdbcPassword = "6c75e8bb";

    private static final String SELECT_CUSTOMER_BY_ID = "SELECT id, name, email,phone, product, department, userID FROM customers WHERE id =?";
    private static final String SELECT_ALL_CUSTOMERS = "SELECT users.name AS 'userName', customers.name AS 'name', customers.email AS 'email', customers.phone AS 'phone',  customers.product AS 'product', customers.department AS 'department'\n" +
            "FROM customers\n" +
            "INNER JOIN users ON users.id=customers.userID";
    private static final String DELETE_CUSTOMER_SQL = "DELETE FROM customers WHERE id = ?;";
    private static final String UPDATE_CUSTOMER_SQL = "UPDATE customers SET name = ?, email= ?, phone =?, product =?, department =? WHERE id = ?;";
    private static final String SELECT_CUSTOMERS_BY_USER = "SELECT * FROM customers WHERE userID = ?";
    private static final String SELECT_ALL_CUSTOMERS_BY_USER_AND_DEPARTMENT = "SELECT users.name AS 'userName', customers.name AS 'name', customers.email AS 'email', customers.phone AS 'phone',  customers.product AS 'product', customers.department AS 'department'\n" +
            "FROM customers\n" +
            "INNER JOIN users ON users.id=customers.userID WHERE department = ?";
    private static final String SELECT_CUSTOMERS_BY_USER_AND_DEPARTMENT = "SELECT * FROM customers WHERE userID = ? AND department = ?";
    private static final String GET_ALL_DEPARTMENT_LIST = "SELECT department FROM customers";
    private static final String GET_DEPARTMENT_LIST = "SELECT department FROM customers WHERE userID = ?";
    private static final String INSERT_CUSTOMER_SQL = "INSERT INTO customers" + "  (id, name, email, phone, product, department, userID) VALUES "
            + " (?, ?, ?, ?, ?, ?, ?);";

    public CustomerDAO() {}

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

    public void insertCustomer(Customer customer) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUSTOMER_SQL)) {
            preparedStatement.setString(1, customer.getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setString(5, customer.getProduct());
            preparedStatement.setString(6, customer.getDepartment());
            preparedStatement.setString(7, customer.getUserID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Customer selectCustomer(String id) {

        Customer customer = null;

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUSTOMER_BY_ID);) {
            preparedStatement.setString(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String product = rs.getString("product");
                String department = rs.getString("department");
                String userID = rs.getString("userID");


                customer = new Customer(id, name, email, phone, product, department, userID);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return customer;
    }

    public List<Customer> selectAllCustomers() {

        List<Customer> customers = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CUSTOMERS);) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String product = rs.getString("product");
                String department = rs.getString("department");
                String userName = rs.getString("userName");

                customers.add(new Customer(null, name, email, phone, product, department, null, userName));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return customers;
    }


    public List<Customer> selectCustomersByUser(String userID) {

        List<Customer> customers = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUSTOMERS_BY_USER);) {
            preparedStatement.setString(1, userID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String product = rs.getString("product");
                String department = rs.getString("department");

                customers.add(new Customer(id, name, email, phone, product,department , userID));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return customers;
    }

    public List<Customer> findCustomersByUserAndDepartment(String department) {

        List<Customer> customers = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CUSTOMERS_BY_USER_AND_DEPARTMENT);) {
            preparedStatement.setString(1, department);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String product = rs.getString("product");
                String userName = rs.getString("userName");

                customers.add(new Customer(null, name, email, phone, product, department, null, userName));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return customers;
    }

    public List<Customer> findCustomersByUserAndDepartment(String userID, String department) {

        List<Customer> customers = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUSTOMERS_BY_USER_AND_DEPARTMENT);) {
            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, department);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String product = rs.getString("product");

                customers.add(new Customer(id, name, email, phone, product,department, userID));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return customers;
    }

    public List<String> getDepartmentList() {
        List<String> departmentList = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_DEPARTMENT_LIST);) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String department = rs.getString("department");
                departmentList.add(department);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        List<String> listWithoutDuplicates = departmentList.stream()
                .distinct()
                .collect(Collectors.toList());

        return listWithoutDuplicates;
    }

    public List<String> getDepartmentList(String userID) {
        List<String> departmentList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_DEPARTMENT_LIST);) {
            preparedStatement.setString(1, userID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String department = rs.getString("department");
                departmentList.add(department);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        List<String> listWithoutDuplicates = departmentList.stream()
                .distinct()
                .collect(Collectors.toList());

        return listWithoutDuplicates;
    }


    public boolean deleteCustomer(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER_SQL);) {
            statement.setInt(1, id);

            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateCustomer(Customer customer) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER_SQL);) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getProduct());
            statement.setString(5, customer.getDepartment());
            statement.setString(6, customer.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
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