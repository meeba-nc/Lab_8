<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Catalog Example</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <header>
        <div>
            <h1><a href="/">Catalog</a> > ${offering.name}</h1>
        </div>
        <c:if test="${authorized == true}">
            <div class="logout">
                <h3><a href="/logout">Logout</a></h3>
            </div>
        </c:if>
    </header>
    <main>
        <h4>Price: <strong>${offering.price}</strong> points</h4>
        <h4>Category: ${offering.category.name}</h4>
        <ul>
            <c:forEach items="${comments}" var="comment">
                <li>
                    <h3>${comment.author.login}</h3>
                    ${comment.message}
                </li>
            </c:forEach>
            <li>
                <c:choose>
                    <c:when test="${authorized == true}">
                        <form method="post">
                            <ul>
                                <li>
                                    <label>
                                        <textarea name="message" cols="80" rows="5" placeholder="Your message"></textarea>
                                    </label>
                                </li>
                                <li>
                                    <input type="submit" value="Send">
                                </li>
                            </ul>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <h4><a href="/login?fromOffering=${offering.id}">Login</a> to leave a comment</h4>
                    </c:otherwise>
                </c:choose>
            </li>
        </ul>
    </main>
</body>
</html>
