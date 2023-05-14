package hu.alkfejl.dao;

import hu.alkfejl.model.User;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static hu.alkfejl.util.SQLiteQueries.*;

public class UserDAOSqlite implements UserDAO {
    private final String dbURL;
    private final String dbClass;

    private static volatile Map<String, UserDAOSqlite> instancesMap = new HashMap<>();
    private static final Object object = new Object();
    
    private UserDAOSqlite( String dbURL, String dbClass ) {
        this.dbURL = dbURL;
        this.dbClass = dbClass;

        try {
            Class.forName( dbClass );
        } catch (ClassNotFoundException e) {
            System.err.println( "The class for handling db could not be loaded" + dbClass );
        }
    }

    public static UserDAOSqlite getInstance( String dbURL, String dbClass ) {
        String combinedKey = dbURL + dbClass;

        if ( instancesMap.containsKey( combinedKey ) ) {
            return instancesMap.get( combinedKey );
        }

        synchronized ( object ) {
            if ( instancesMap.containsKey( combinedKey ) ) {
                return instancesMap.get( combinedKey );
            }
            var newInstance = new UserDAOSqlite(dbURL, dbClass);
            instancesMap.put( combinedKey, newInstance );

            return newInstance;
        }
    }

    @Override
    public boolean registerUser(User user) {
        boolean result = false;

        try( Connection connection = DriverManager.getConnection( dbURL );
             PreparedStatement ps = connection.prepareStatement( REGISTER_USER )
        ) {
            int index = 1;
            ps.setString( index++, user.getUsername() );
            ps.setString( index++, user.getPassword() );
            ps.setString( index++, user.getToken() );

            int affected = ps.executeUpdate();
            result = 1 == affected;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public User getUserByName(String username) {
        return queryUser( GET_USER_BY_NAME, username );
    }

    @Override
    public User getUserByToken(String token) {
        return queryUser( GET_USER_BY_TOKEN, token );
    }

    @Override
    public boolean updateUser(User user) {
        boolean result;
        try ( Connection connection = DriverManager.getConnection( dbURL );
            PreparedStatement ps = connection.prepareStatement( UPDATE_USER )
        ) {
            int index = 1;
            ps.setString( index++, user.getPassword() );
            ps.setString( index++, user.getToken() );
            ps.setString( index++, user.getUsername() );

            int affectedRows = ps.executeUpdate();
            result = 1 == affectedRows;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private User queryUser( String QUERY, String condition ) {
        User queriedUser = null;
        try( Connection connection = DriverManager.getConnection( dbURL );
             PreparedStatement ps = connection.prepareStatement( QUERY )
        ) {
            int index = 1;
            ps.setString( index++, condition );

            ResultSet rs = ps.executeQuery();
            int queried = 0;
            while ( rs.next() ) {
                ++queried;
                queriedUser = new User();
                queriedUser.setUsername( rs.getString( "username" ) );
                queriedUser.setPassword( rs.getString("password" ) );
                queriedUser.setToken( rs.getString( "token" ) );
            }
            if ( queried > 1 ) {
                throw new RuntimeException( "There were multiple users with value: " + condition );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return queriedUser;
    }
}
