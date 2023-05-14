package hu.alkfejl.view;

import hu.alkfejl.controller.UserController;
import hu.alkfejl.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/update_pwd")
public class UpdatePwd extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dbURL = getServletContext().getInitParameter( "dbPath" );
        UserController uc = UserController.getInstance( dbURL );

        String authToken = null;
        for ( Cookie cookie : req.getCookies() ) {
            if ( "token".equals( cookie.getName() ) ) {
                authToken = cookie.getValue();
                break;
            }
        }

        if ( null == authToken ) {
            resp.sendRedirect( "index.jsp" );
            return;
        }

        String oldpwd = req.getParameter( "oldpwd" );
        String newpwd = req.getParameter( "newpwd" );

        resp.setContentType( "text/html" );

        if ( parameterInvalid( oldpwd ) || parameterInvalid( newpwd ) ) {

            resp.getWriter().println( "Bad request" );
            RequestDispatcher rd = req.getRequestDispatcher( "profile.jsp" );
            rd.include( req, resp );
            resp.setStatus( 400 );
            return;
        }

        User user = uc.getUserByToken( authToken );
        if ( oldpwd.equals( user.getPassword() ) ) {
            user.setPassword( newpwd );
            uc.updateUser( user );
            resp.sendRedirect( "home.jsp" );
            return;
        }

        resp.getWriter().println( "Bad password" );
        RequestDispatcher rd = req.getRequestDispatcher( "profile.jsp" );
        rd.include( req, resp );
        resp.setStatus( 401 );
    }

    private boolean parameterInvalid( String parameter ) {
        return parameter == null || parameter.isEmpty();
    }
}
