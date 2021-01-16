<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>User Management Application</title>
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

            <li style="margin-left: auto"><a href="<%=request.getContextPath()%>/" class="nav-link">Çıkış</a></li>

        </ul>
    </nav>
</header>
<div class="container col-md-5" style="margin-top: 40px">
    <div class="card">
        <div class="card-body">
            <c:if test="${user != null}">
            <form action="update" method="post">
                </c:if>
                <c:if test="${user == null}">
                <form action="insert" method="post">
                    </c:if>

                    <caption>
                        <h2>
                            <c:if test="${user != null}">
                                Düzenle
                            </c:if>
                            <c:if test="${user == null}">
                                Yeni Müşteri Ekle
                            </c:if>
                        </h2>
                    </caption>

                    <c:if test="${user != null}">
                        <input type="hidden" name="id" value="<c:out value='${user.id}' />" />
                    </c:if>

                    <fieldset class="form-group">
                        <label>Ad Soyad</label> <input type="text"
                                                        value="<c:out value='${user.name}' />" class="form-control"
                                                        name="name" required="required" autocomplete="off">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Email</label> <input type="text"
                                                         value="<c:out value='${user.email}' />" class="form-control"
                                                         name="email" autocomplete="off">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Sehir</label> <input type="text"
                                                           value="<c:out value='${user.country}' />" class="form-control"
                                                           name="country" autocomplete="off">
                    </fieldset>

                    <button type="submit" class="btn btn-success">Kaydet</button>
                </form>
        </div>
    </div>
</div>
</body>
</html>
