package hu.alkfejl.view;

import hu.alkfejl.controller.UserController;
import hu.alkfejl.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registration")
public class Registration extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter( "username" );
        String password = req.getParameter( "password" );

        resp.setContentType( "text/html" );

        if ( parameterInvalid( username ) || parameterInvalid( password ) ) {

            resp.getWriter().println( "Bad request" );
            RequestDispatcher rd = req.getRequestDispatcher( "reg.jsp" );
            rd.include( req, resp );
            resp.setStatus( 400 );
            return;
        }

        String dbURL = getServletContext().getInitParameter( "dbPath" );
        UserController uc = UserController.getInstance( dbURL );

        if ( uc.getUserByName( username ) != null ) {
            resp.getWriter().println( "Username already taken" );
            RequestDispatcher rd = req.getRequestDispatcher( "reg.jsp" );
            rd.include( req, resp );
            return;
        }

        User user = new User();
        user.setUsername( username );
        user.setPassword( password );

        if ( uc.registerUser( user ) ) {
            resp.sendRedirect( "index.jsp" );
            return;
        }

        resp.getWriter().println( "Stg went wrong.. Contact a deity!" );
        RequestDispatcher rd = req.getRequestDispatcher( "reg.jsp" );
        rd.include( req, resp );
    }

    private boolean parameterInvalid( String parameter ) {
        return parameter == null || parameter.isEmpty();
    }
}
