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
    <h1 style="margin: 32px 0">Müşteri Listesi</h1>

    <form action="selectAllDepartment" method="post" style="display: flex; justify-content: flex-end; align-items: center">
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
            <th>Kullanıcı</th>
            <th>Müşteri Ad Soyad</th>
            <th>Email</th>
            <th>Tel</th>
            <th>Ürün</th>
            <th>Kategori</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="customer" items="${listCustomers}">
            <tr>
                <td><c:out value="${customer.userName}" /></td>
                <td><c:out value="${customer.name}" /></td>
                <td><c:out value="${customer.email}" /></td>
                <td><c:out value="${customer.phone}" /></td>
                <td><c:out value="${customer.product}" /></td>
                <td><c:out value="${customer.department}" /></td>
            </tr>
        </c:forEach>

        </tbody>

    </table>
</div>
</body>
</html>
