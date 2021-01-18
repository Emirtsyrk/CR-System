package com.example.musteri_yonetimi;

import com.example.musteri_yonetimi.dao.UserDAO;
import com.example.musteri_yonetimi.model.User;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")

public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertUser(request, response);
                    break;
                case "/delete":
                    deleteUser(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateUser(request, response);
                    break;
                default:
                    listUser(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    /**
     RequestDispatcher kullanıcıdan bilgi almak için kullanılıyor.
     * */

    private void listUser(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException, ServletException {
            List<User> listUser = userDAO.selectAllUsers();
            request.setAttribute("listUser", listUser);
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
    }


    /**
         Servlet path /new olduğunda çalışıyor.
         "user-form.jsp" dosyasını çalıştırıyor.
     */
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
            dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, ServletException, IOException {
            int id = Integer.parseInt(request.getParameter("id"));
            User existingUser = userDAO.selectUser(id);             // Müşteriyi "id" ile bul
            RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
            request.setAttribute("user", existingUser);          // Müşteriyi sayfaya parametre olarak gönder.
            dispatcher.forward(request, response);                  // Müşteri bilgisini kullanıcıya geri gönder.

    }

    // Servlet path /insert olduğunda çalışıyor.
    private void insertUser(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String country = request.getParameter("country");
            User newUser = new User(name, email, country);          // Yapıcı metodla yeni müşteri oluştur
            userDAO.insertUser(newUser);                            // müşteriyi ekle
            response.sendRedirect("list");                       // "/list" servlet path'i çalıştır.
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String country = request.getParameter("country");

            User user = new User(id, name, email, country);         // Yapıcı metodla yeni müşteri oluştur
            userDAO.updateUser(user);                               // müşteriyi güncelle
            response.sendRedirect("list");                       // "/list" servlet path'i çalıştır.
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException {
            int id = Integer.parseInt(request.getParameter("id"));
            userDAO.deleteUser(id);                                 // müşteriyi sil
            response.sendRedirect("list");                       // "/list" servlet path'i çalıştır.
    }

}