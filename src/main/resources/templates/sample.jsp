<%@ page import="cafe.app.user.controller.dto.UserResponse" %>
<%@ page import="http.session.HttpSession" %>

<%
    HttpSession httpSession = request.getHttpSession();
    UserResponse user = (UserResponse) httpSession.getAttribute("user");
    int num = 100;
%>

<html>
<head>
</head>
<body>
<h1>
    <%=num%>
</h1>
</body>
</html>
