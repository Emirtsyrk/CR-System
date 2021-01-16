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

    <link rel="stylesheet"
          href="css/styles.css"
          type="text/css" />
</head>
<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: #48426d">
        <div>
            <!-- request.getContextPath() uygulamanızın kök yolunu döndürür. -->
            <!---->

            <!-- {root}/list'e yönlendiriyor. -->
            <a href="<%=request.getContextPath()%>/list" class="navbar-brand"> Müsteri Yönetimi Sistemi </a>
        </div>

        <ul class="navbar-nav"  style="width: 100%">

            <!-- {root}/list'e (Müşteri listesine) yönlendiriyor. -->
            <li><a href="<%=request.getContextPath()%>/list"
                   class="nav-link">Müşteri Listesi</a></li>

            <!-- {root}/ (Admin giriş sayfasına) yönlendiriyor. -->
            <li style="margin-left: auto"><a href="<%=request.getContextPath()%>/"
                   class="nav-link">Çıkış</a></li>

        </ul>
    </nav>
</header>
<br>

<div class="row">

    <div class="container" style="margin-top: 40px">
        <h3 class="text-center">Müşteriler</h3>
        <hr>
        <div class="container text-left">
            <!-- {root}/new (Müşteri ekleme sayfasına) yönlendiriyor. -->
            <a href="<%=request.getContextPath()%>/new" class="btn btn-success">Yeni Müşteri Ekle</a>
        </div>
        <br>

        <!-- Müşteri tablosu -->
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>#</th>
                <th>Ad Soyad</th>
                <th>Email</th>
                <th>Şehir</th>
                <th>İşlemler</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="user" items="${listUser}">

                <tr>
                    <td><c:out value="${user.id}" /></td>
                    <td><c:out value="${user.name}" /></td>
                    <td><c:out value="${user.email}" /></td>
                    <td><c:out value="${user.country}" /></td>
                    <td><a href="edit?id=<c:out value='${user.id}' />">Düzenle</a>
                        &nbsp;&nbsp;&nbsp;&nbsp; <a
                                href="delete?id=<c:out value='${user.id}' />">Sil</a></td>
                </tr>
            </c:forEach>
            <!-- } -->
            </tbody>

        </table>
    </div>
</div>
</body>
</html>
