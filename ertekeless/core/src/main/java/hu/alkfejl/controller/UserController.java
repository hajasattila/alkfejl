package hu.alkfejl.controller;

import hu.alkfejl.dao.UserDAO;
import hu.alkfejl.dao.UserDAOSqlite;
import hu.alkfejl.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UserController {
    private UserDAO dao;

    private static volatile Map<String, UserController> instancesMap = new HashMap<>();
    private static final Object object = new Object();
    private UserController( String dbURL, String dbClass ) {
        dao = UserDAOSqlite.getInstance( dbURL, dbClass );
    }

    /**
     * Get an instance with fix db engine: org.sqlite.JDBC
     * {@link hu.alkfejl.controller.UserController#getInstance(String, String)} is called.
     *
     * @param dbURL URL of the db.
     * */
    public static UserController getInstance( String dbURL ) {
        return getInstance( dbURL, "org.sqlite.JDBC" );
    }

    /**
     * Get an instance for the database and the handling engine.
     * @param dbURL URL of the db. jdbc:whatever: must be the prefix
     * @param dbClass the handling class to load dynamicly, e.g. org.sqlite.JDBC
     * */
    public static UserController getInstance( String dbURL, String dbClass ) {
        String combinedKey = dbURL + dbClass;

        if ( instancesMap.containsKey( combinedKey ) ) {
            return instancesMap.get( combinedKey );
        }

        synchronized ( object ) {
            if ( instancesMap.containsKey( combinedKey ) ) {
                return instancesMap.get( combinedKey );
            }
            var newInstance = new UserController(dbURL, dbClass);
            instancesMap.put( combinedKey, newInstance );

            return newInstance;
        }
    }

    /**
     * Registers the user and returns if it was successful
     * @param user - The user to be registered
     * @return boolean value true if teh user was registered successfully
     * */
    public boolean registerUser(User user) {
        return dao.registerUser( user );
    }

    /**
     * Return the user for the username.
     * @param username the name of the user to be queried from db.
     * @return The User object for that username or null if no such user is found.
     * */
    public User getUserByName( String username ) {
        return dao.getUserByName( username );
    }

    /**
     * Return the user for the token.
     * @param token the auth token of a user
     * @return The USer object for the token or null if there was no user with the token.
     * */
    public User getUserByToken( String token ) {
        return dao.getUserByToken( token );
    }

    /**
     * Login the user and get a token.
     * @param username the username.
     * @param password the pwd.
     * @return If the user is logged in, the saved token null otherwise.
     * */
    public String loginUser( String username, String password ) {
        var user = dao.getUserByName( username );
        if ( user == null ) { return null; }
        if ( password.equals( user.getPassword() ) ) {
            String token =  Long.toString( System.currentTimeMillis() + new Random().nextLong() );
            user.setToken( token );
            if ( updateUser( user ) ) {
                return token;
            }
            return null;
        }
        return null;
    }

    /**
     * Update the user based on the username (fix).
     * @param user the user whose attributes are updated.
     * @return true if the values are updated false otherwise.
     * */
    public boolean updateUser( User user ) {
        return dao.updateUser( user );
    }
}
