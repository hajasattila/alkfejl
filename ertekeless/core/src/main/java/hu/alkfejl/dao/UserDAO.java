package hu.alkfejl.dao;

import hu.alkfejl.model.User;

public interface UserDAO {
    boolean registerUser( final User user );
    User getUserByName( final String username );
    User getUserByToken( final String token );
    boolean updateUser( final User user );
}
