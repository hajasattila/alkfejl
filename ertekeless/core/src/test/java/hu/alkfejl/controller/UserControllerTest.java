package hu.alkfejl.controller;

import hu.alkfejl.model.User;
public class UserControllerTest {
    public static void main( String[] args ) {

        System.out.println( "Getting controller" );
        var userController = UserController.getInstance( "jdbc:sqlite:/home/sagodiz/alkfejl/ertekeles/core/src/main/resources/ertekeles.sqlite",
                "org.sqlite.JDBC" );

        System.out.println( "No user query" );
        assert null == userController.getUserByName( "Jani" );

        System.out.println( "No token query" );
        assert null == userController.getUserByToken( "Token" );

        System.out.println( "Create User" );
        var Jani = new User();
        Jani.setUsername( "Jani" );
        Jani.setPassword( "Jani1" );
        System.out.println( "Register user" );
        assert userController.registerUser( Jani );

        System.out.println( "Query User good" );
        var queriedByName = userController.getUserByName( "Jani" );
        assert queriedByName.getUsername().equals( "Jani" );

        System.out.println( "Login user bad" );
        var token = userController.loginUser( "Jani", "Jani11" );
        assert token == null;

        System.out.println( "Login user good" );
        var tokenGood = userController.loginUser( "Jani", "Jani1" );
        assert tokenGood != null;

        var queriedByToken = userController.getUserByToken( tokenGood );
        assert queriedByToken.getUsername().equals( "Jani" );

    }
}
