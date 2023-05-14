<%@ page import="hu.alkfejl.controller.UserController" %>
<%@ page import="hu.alkfejl.model.User" %><%--
  Created by IntelliJ IDEA.
  User: sagodiz
  Date: 2023. 04. 20.
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  UserController uc = UserController.getInstance( pageContext.getServletContext().getInitParameter( "dbPath" ) );
  String authToken = null;
  for ( Cookie cookie : request.getCookies() ) {
    if ( "token".equals( cookie.getName() ) ) {
      authToken = cookie.getValue();
      break;
    }
  }

  if ( null == authToken ) {
    response.sendRedirect( "index.jsp" );
    return;
  }

  User user = uc.getUserByToken( authToken );
  if ( null == user ) {
    response.getWriter().println( "Token is invalid" );
    response.setStatus( 400 );
    return;
  }
%>
<html>
<head>
    <title>Home von <%= user.getUsername()%></title>
</head>
<body>
<a href="profile.jsp">
  <button>
    Profile
  </button>
</a>

<a href="scorings.jsp">
  <button>
    Scorings
  </button>
</a>
</body>
</html>
