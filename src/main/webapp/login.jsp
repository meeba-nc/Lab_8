<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Catalog Example</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <header>
        <h1><a href="/">Catalog</a> > Login</h1>
    </header>
    <main>
        <form method="post">
            <ul>
                <li>
                    <label>
                        <strong>Login</strong>
                        <br>
                        <input type="text" name="login">
                    </label>
                </li>
                <li>
                    <label>
                        <strong>Password</strong>
                        <br>
                        <input type="password" name="password">
                    </label>
                </li>
                <c:if test="${invalidCredentials == true}">
                    <li>
                        <strong class="warning">Invalid login or password</strong>
                    </li>
                </c:if>
                <li>
                    <input type="submit" value="Login">
                </li>
            </ul>
        </form>
        No account? <a href="/registration">Register here</a>
    </main>
</body>
</html>
