<%--
  Created by IntelliJ IDEA.
  User: emirt
  Date: 7.02.2021
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">

    <title>Kayit Ol</title>
</head>
<body>
<div class="row">
    <div class="container" style="margin-top: 120px">
        <h3 class="text-center" style="margin-bottom: 24px">Kayıt Ol</h3>

        <div class="container col-md-5">
            <div class="card">
                <div class="card-body">
                    <form action="signup" method="post">
                        <fieldset class="form-group">
                            <label>Kullanıcı</label>
                            <input type="text" class="form-control" name="name" required="required" autocomplete="off" >
                        </fieldset>

                        <h3 style="font-size: 14px; color: red"><c:out value='${userNameMessage}' /></h3>

                        <fieldset class="form-group">
                            <label>Parola</label>
                            <input type="password" class="form-control" name="password">
                        </fieldset>

                        <fieldset class="form-group">
                            <label>Parola (Tekrar)</label>
                            <input type="password" class="form-control" name="passwordRepeat">
                        </fieldset>

                        <h3 style="font-size: 14px; color: red"><c:out value='${passwordMessage}' /></h3>

                        <button type="submit" class="btn btn-primary" style="width: 100%">Kayıt Ol</button>
                    </form>
                    <hr style="margin: 24px 0">
                    <form action="index.jsp" method="post">
                        <button type="submit" class="btn btn-outline-primary"  style="width: 100%">Giriş Yap!</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>