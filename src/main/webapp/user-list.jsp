<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Müşteriler</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>

<body>
<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: #48426d">
        <div>
            <a href="<%=request.getContextPath()%>/list" class="navbar-brand"> Müsteri Yönetimi Sistemi </a>
        </div>

        <ul class="navbar-nav"  style="width: 100%">
            <li><a href="<%=request.getContextPath()%>/list"
                   class="nav-link">Müşteri Listesi</a></li>

            <li style="margin-left: auto">
                <form action="logout" method="post" style="margin: 0">
                    <button type="submit" class="btn btn-danger" >Çıkış Yap</button>
                </form>
            </li>

        </ul>
    </nav>
</header>
<br>

<div class="row">

    <div class="container" style="margin-top: 40px">
        <% String username = (String) session.getAttribute("username"); %>
        <h2 style="margin-bottom: 24px">
            Merhaba, <%=username%><br>
        </h2>

        <h3 class="text-center">Müşteriler</h3>
        <hr>

        <form action="selectDepartment" method="post" style="display: flex; justify-content: flex-end; align-items: center">
            <label style="margin: 0 12px 0 0;">Kategori: </label>
            <select name="department" id="department" style="padding: 6px; outline: none; margin-right: 12px; border-radius: 4px;">
                <option value="">Hepsi</option>
                <c:forEach var="department" items="${listDepartment}">
                    <option value="<c:out value="${department}" />"><c:out value="${department}" /></option>
                </c:forEach>
            </select>
            <button type="submit" class="btn btn-primary">Ara</button>
        </form>

        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Ad Soyad</th>
                <th>Email</th>
                <th>Tel</th>
                <th>Ürün</th>
                <th>Kategori</th>
                <th>İşlemler</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="customer" items="${listCustomers}">
                <tr>
                    <td><c:out value="${customer.name}" /></td>
                    <td><c:out value="${customer.email}" /></td>
                    <td><c:out value="${customer.phone}" /></td>
                    <td><c:out value="${customer.product}" /></td>
                    <td><c:out value="${customer.department}" /></td>
                    <td style="display: flex; justify-content: space-between;">
                        <a href="edit?id=<c:out value='${customer.id}' />">Düzenle</a>
                        <a href="delete?id=<c:out value='${customer.id}' />">Sil</a>
                    </td>
                </tr>
            </c:forEach>


            <!-- } -->
            </tbody>

        </table>
        <div class="container text-left" style="padding: 0; margin-top: 24px">
            <a href="<%=request.getContextPath()%>/new" class="btn btn-success">Yeni Müşteri Ekle</a>
        </div>
    </div>
</div>

</body>
</html>