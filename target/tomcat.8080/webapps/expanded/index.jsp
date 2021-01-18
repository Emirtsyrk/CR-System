<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Müsteri Yönetim Sistemi</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">



    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" >
</head>
<body>


<br>

<div class="row">

    <div class="container" style="margin-top: 120px">
        <h3 class="text-center">Admin Paneli</h3>


        <div class="container col-md-5">
            <div class="card">
                <div class="card-body">
                        <form action="auth" method="post">
                            <fieldset class="form-group">
                                <label>Kullanıcı</label> <input type="text"
                                                                value="<c:out value='${admin.name}' />" class="form-control"
                                                                name="name" required="required" autocomplete="off" >
                            </fieldset>

                            <fieldset class="form-group">
                                <label>Parola</label> <input type="password"
                                                                 value="<c:out value='${admin.email}' />" class="form-control"
                                                                 name="email">
                            </fieldset>



                            <button type="submit" class="btn btn-success">Giriş</button>
                        </form>
                </div>
            </div>
        </div>



        <!-- <hr>
        <div class="container text-left">

            <a href="<%=request.getContextPath()%>/new" class="btn btn-success">Add
                New User</a>
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Country</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>


    <%--            <c:out value="${listUser}"/>
            <c:forEach var="user" items="${listUser}">

                <tr>
                    <td><c:out value="${user.id}" /></td>
                    <td><c:out value="${user.name}" /></td>
                    <td><c:out value="${user.email}" /></td>
                    <td><c:out value="${user.country}" /></td>
                    <td><a href="edit?id=<c:out value='${user.id}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp; <a
                                href="delete?id=<c:out value='${user.id}' />">Delete</a></td>
                </tr>
            </c:forEach>--%>

            </tbody>

        </table>-->
    </div>
</div>
</body>
</html>
