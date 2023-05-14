<%@ page import="hu.alkfejl.controller.UserController" %>
<%@ page import="hu.alkfejl.model.User" %>
<%@ page import="hu.alkfejl.controller.ScoringController" %>
<%@ page import="hu.alkfejl.model.Scoring" %><%--
  Created by IntelliJ IDEA.
  User: sagodiz
  Date: 2023. 04. 20.
  Time: 16:53
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
    <title>Scorings von <%=user.getUsername()%> </title>
</head>
<body>
Scorings saved by You: <br/>
<%
  ScoringController sc = ScoringController.getInstance( pageContext.getServletContext().getInitParameter( "dbPath" ) );
  for( Scoring scoring : sc.getScoringForUsername( user.getUsername() ) ) {
    out.println( scoring.getTitle() + "  -  " + scoring.getDescription() + "<br/>");
  }
%>
</body>
</html>
