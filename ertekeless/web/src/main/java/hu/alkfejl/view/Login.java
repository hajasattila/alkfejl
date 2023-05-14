package hu.alkfejl.view;

import hu.alkfejl.controller.UserController;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter( "username" );
        String password = req.getParameter( "password" );

        resp.setContentType( "text/html" );

        if ( parameterInvalid( username ) || parameterInvalid( password ) ) {

            resp.getWriter().println( "Bad request" );
            RequestDispatcher rd = req.getRequestDispatcher( "index.jsp" );
            rd.include( req, resp );
            resp.setStatus( 400 );
            return;
        }

        String dbURL = getServletContext().getInitParameter( "dbPath" );
        UserController uc = UserController.getInstance( dbURL );

        String authToken = uc.loginUser( username, password );
        if ( authToken == null ) {
            resp.getWriter().println( "Invalid username - password pair" );
            RequestDispatcher rd = req.getRequestDispatcher( "index.jsp" );
            rd.include( req, resp );
            resp.setStatus( 401 );
            return;
        }

        resp.addCookie( new Cookie( "token", authToken ) );
        resp.sendRedirect( "home.jsp" );
    }

    private boolean parameterInvalid( String parameter ) {
        return parameter == null || parameter.isEmpty();
    }
}
