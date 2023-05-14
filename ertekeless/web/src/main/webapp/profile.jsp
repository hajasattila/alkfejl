<%@ page import="hu.alkfejl.controller.UserController" %>
<%@ page import="hu.alkfejl.model.User" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.lang.reflect.Method" %><%--
  Created by IntelliJ IDEA.
  User: sagodiz
  Date: 2023. 04. 20.
  Time: 16:26
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
    <title>Profile von <%= user.getUsername()%></title>
</head>
<body>
  <form action="update_pwd" method="post">
    <label><%=user.getUsername()%></label><br/>
    <label>Old password:</label><input required type="password" name="oldpwd" placeholder="old password"/><br/>
    <label>New password:</label><input required type="password" name="newpwd" placeholder="new password"/><br/>
    <input type="submit" value="Update Me Password"/>
  </form>
</body>
</html>
