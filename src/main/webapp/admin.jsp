<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Admin</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">

</head>
<body style="display: flex">
<div style="position: sticky; height: 100vh; top: 0; left: 0; width: 280px; padding: 48px 32px; background-color: #48426d; display: flex; flex-direction: column ">
    <a href="<%=request.getContextPath()%>/admin" class="navbar-brand" style="color: white; margin: 6px 0; display: block"> Kullanıcılar </a>
    <a href="<%=request.getContextPath()%>/adminCustomerList" class="navbar-brand" style="color: white; margin: 6px 0; display: block"> Müşteriler </a>
    <a href="<%=request.getContextPath()%>/logout" class="navbar-brand" style="color: white; margin: 6px 0; display: block; margin-top: auto"> Çıkış Yap</a>
</div>
<div class="container" style="margin-top: 40px">
    <h1 style="margin: 32px 0; ">Kullanıcı Listesi</h1>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Kullanıcı adı</th>
            <th>Müşteri Sayısı</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="user" items="${listUsers}">
            <tr>
                <td><c:out value="${user.name}" /></td>
                <td><c:out value="${user.numberOfCustomer}" /></td>
            </tr>
        </c:forEach>

        </tbody>

    </table>
</div>
</body>
</html>
