package com.example.musteri_yonetimi.web;

import com.example.musteri_yonetimi.dao.UserDAO;
import com.example.musteri_yonetimi.dao.CustomerDAO;
import com.example.musteri_yonetimi.model.User;
import com.example.musteri_yonetimi.model.Customer;
import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.UUID;


@WebServlet("/")
//@WebServlet(name = "helloServlet", value = "/hello-servlet")

public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private CustomerDAO customerDAO;


    public void init() {
        userDAO = new UserDAO();
        customerDAO = new CustomerDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        HttpSession session = request.getSession();
        String status = (String) ((HttpSession) session).getAttribute("status");

        try {
            switch (action) {
                case "/signup":
                    signup(request, response);
                    break;
                case "/login":
                    login(request, response);
                    break;
                case "/logout":
                    logout(request, response);
                    break;
                case "/list":
                    listCustomers(request, response);
                    break;
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertCustomer(request, response);
                    break;
                case "/delete":
                    deleteUser(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateCustomer(request, response);
                    break;
                case "/selectDepartment":
                    selectDepartment(request, response);
                    break;
                case "/admin":
                    adminHomePage(request, response);
                    break;
                case "/adminCustomerList":
                    adminCustomerList(request, response);
                    break;
                case "/selectAllDepartment":
                    selectAllDepartment(request, response);
                    break;
                default:
                    logout(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void signup(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("passwordRepeat");

        String userID = userDAO.findUserByName(name);
        boolean differentPassword = !password.equals(passwordRepeat);
        boolean isExist = userID != null;

        if (isExist) {
            String message="Bu kullanıcı adı daha önce alındı!";
            request.setAttribute("userNameMessage", message);
        }

        if (differentPassword) {
            String message="Şifreler aynı olmalı!";
            request.setAttribute("passwordMessage", message);

        }

        if (isExist || differentPassword) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
            dispatcher.forward(request, response);

        } else {
            String randomUserID = UUID.randomUUID().toString();
            String hashedPW = BCrypt.hashpw(password, BCrypt.gensalt(12));
            User newUser = new User(randomUserID,name, hashedPW);
            userDAO.insertUser(newUser);

            HttpSession session = request.getSession();

            session.setAttribute("status","user");
            session.setAttribute("username",name);
            session.setAttribute("userID",randomUserID);

            response.sendRedirect("list");
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        String id = userDAO.findUserByName(name);
        String password = request.getParameter("password");

        User user = new User(id, name, password);

        try {
            if (userDAO.validate(user)) {
                HttpSession session = request.getSession();
                session.setAttribute("userID",id);
                session.setAttribute("username",name);
                System.out.println(name.equals("emircantasyurek"));
                if (name.equals("emircantasyurek")) {
                    session.setAttribute("status","admin");
                    response.sendRedirect("admin");
                } else {
                    session.setAttribute("status","user");
                    response.sendRedirect("list");
                }

            } else {
                String message="Giriş bilgileriniz doğru değil!";
                request.setAttribute("message", message);

                RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                dispatcher.forward(request, response);

            }
        } catch (ClassNotFoundException | ServletException e) {
            e.printStackTrace();
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException, ServletException {
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect("index.jsp");
    }


    private void adminHomePage(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException, ServletException {
            HttpSession session = request.getSession();
            String userID = (String) session.getAttribute("userID");
            String status = (String) session.getAttribute("status");

            if (userID == null || !status.equals("admin")) {
                response.sendRedirect("index.jsp");
            } else {
                List<Customer> listCustomers = customerDAO.selectCustomersByUser(userID);
                List<User> listUsers = userDAO.selectAllUsers();

                request.setAttribute("listCustomers", listCustomers);
                request.setAttribute("listUsers", listUsers);

                RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
                dispatcher.forward(request, response);
            }
    }

    private void adminCustomerList(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
        String status = (String) session.getAttribute("status");

        if (userID == null  || !status.equals("admin")) {
            response.sendRedirect("index.jsp");
        } else {
            List<Customer> listCustomers = customerDAO.selectAllCustomers();
            List<String> listDepartment = customerDAO.getDepartmentList();
            List<User> listUsers = userDAO.selectAllUsers();

            request.setAttribute("listCustomers", listCustomers);
            request.setAttribute("listDepartment", listDepartment);
            request.setAttribute("listUsers", listUsers);

            RequestDispatcher dispatcher = request.getRequestDispatcher("admin-customer-list.jsp");
            dispatcher.forward(request, response);
        }
    }


    private void listCustomers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession();

        String userID = (String) session.getAttribute("userID");
        String status = (String) session.getAttribute("status");

        if (userID == null || !status.equals("user")) {
            response.sendRedirect("index.jsp");
        } else {
            List<Customer> listCustomers = customerDAO.selectCustomersByUser(userID);
            List<String> listDepartment = customerDAO.getDepartmentList(userID);

            request.setAttribute("listCustomers", listCustomers);
            request.setAttribute("listDepartment", listDepartment);

            RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
            dispatcher.forward(request, response);
        }

    }


    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
        String status = (String) session.getAttribute("status");

        if (userID == null || !status.equals("user")) {
            response.sendRedirect("index.jsp");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
            dispatcher.forward(request, response);
        }


    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
        String status = (String) session.getAttribute("status");

        if (userID == null || !status.equals("user")) {
            response.sendRedirect("index.jsp");
        }  else {
            String id = (request.getParameter("id"));
            Customer customer = customerDAO.selectCustomer(id);
            request.setAttribute("customer", customer);

            RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
            dispatcher.forward(request, response);
        }
    }




    private void insertCustomer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
        String randomCustomerID = UUID.randomUUID().toString();
        String status = (String) session.getAttribute("status");


        if (userID == null || !status.equals("user")) {
            response.sendRedirect("index.jsp");
        } else {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String product = request.getParameter("product");
            String department = request.getParameter("department");

            Customer customer = new Customer(randomCustomerID, name, email, phone, product, department, userID);
            customerDAO.insertCustomer(customer);
            response.sendRedirect("list");
        }

    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
        String status = (String) session.getAttribute("status");

        if (userID == null || !status.equals("user")) {
            response.sendRedirect("index.jsp");
        } else {
            String id = (String) (request.getParameter("id"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String product = request.getParameter("product");
            String department = request.getParameter("department");

            Customer customer = new Customer(id, name, email, phone, product, department);
            customerDAO.updateCustomer(customer);
            response.sendRedirect("list");
        }

    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
        String status = (String) session.getAttribute("status");

        if (userID == null || !status.equals("user")) {
            response.sendRedirect("index.jsp");
        } else {
            String id = (String)(request.getParameter("id"));
            customerDAO.deleteCustomer(id);
            response.sendRedirect("list");
        }
    }

    private void selectDepartment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");

        if (userID == null) {
            response.sendRedirect("index.jsp");
        }

        String department = (request.getParameter("department"));

        List<Customer> listCustomers;

        if (department == "") {
            listCustomers = customerDAO.selectCustomersByUser(userID);
        } else {
            listCustomers  = customerDAO.findCustomersByUserAndDepartment(userID, department);
        }


        List<String> listDepartment = customerDAO.getDepartmentList(userID);

        request.setAttribute("listCustomers", listCustomers);
        request.setAttribute("listDepartment", listDepartment);

        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
        dispatcher.forward(request, response);

    }

    private void selectAllDepartment(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");

        if (userID == null) {
            response.sendRedirect("index.jsp");
        }

        String department = (request.getParameter("department"));

        List<Customer> listCustomers;

        if (department == "") {
            listCustomers = customerDAO.selectAllCustomers();
        } else {
            listCustomers  = customerDAO.findCustomersByUserAndDepartment(department);
        }


        List<String> listDepartment = customerDAO.getDepartmentList();

        request.setAttribute("listCustomers", listCustomers);
        request.setAttribute("listDepartment", listDepartment);

        RequestDispatcher dispatcher = request.getRequestDispatcher("admin-customer-list.jsp");
        dispatcher.forward(request, response);

    }


}