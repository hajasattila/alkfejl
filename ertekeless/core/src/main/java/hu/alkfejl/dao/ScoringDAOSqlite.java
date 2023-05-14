package hu.alkfejl.dao;

import hu.alkfejl.model.Scoring;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hu.alkfejl.util.SQLiteQueries.GET_SCORING_FOR_USERNAME;
import static hu.alkfejl.util.SQLiteQueries.SAVE_SCORING_FOR_USERNAME;

public class ScoringDAOSqlite implements ScoringDAO {
    private final String dbURL;
    private final String dbClass;

    private static volatile Map<String, ScoringDAOSqlite> instancesMap = new HashMap<>();
    private static final Object object = new Object();

    private ScoringDAOSqlite( String dbURL, String dbClass ) {
        this.dbURL = dbURL;
        this.dbClass = dbClass;

        try {
            Class.forName( dbClass );
        } catch (ClassNotFoundException e) {
            System.err.println( "The class for handling db could not be loaded" + dbClass );
        }
    }

    public static ScoringDAOSqlite getInstance( String dbURL, String dbClass ) {
        String combinedKey = dbURL + dbClass;

        if ( instancesMap.containsKey( combinedKey ) ) {
            return instancesMap.get( combinedKey );
        }

        synchronized ( object ) {
            if ( instancesMap.containsKey( combinedKey ) ) {
                return instancesMap.get( combinedKey );
            }
            var newInstance = new ScoringDAOSqlite(dbURL, dbClass);
            instancesMap.put( combinedKey, newInstance );

            return newInstance;
        }
    }

    @Override
    public List<Scoring> getScoringForUsername(String username) {
        List<Scoring> scorings = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection( dbURL );
            PreparedStatement ps = connection.prepareStatement( GET_SCORING_FOR_USERNAME )
        ) {
            ps.setString( 1, username );
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ) {
                var scoring = new Scoring();
                scoring.setUser( rs.getString( "username" ) );
                scoring.setDescription( rs.getString( "description" ) );
                scoring.setTitle( rs.getString( "title" ) );

                scorings.add( scoring );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return scorings;
    }

    @Override
    public boolean saveScoringForUsername( Scoring scoring ) {
        boolean result;
        try( Connection connection = DriverManager.getConnection( dbURL );
            PreparedStatement ps = connection.prepareStatement( SAVE_SCORING_FOR_USERNAME )
        ) {
            int index = 1;
            ps.setString( index++, scoring.getUser() );
            ps.setString( index++, scoring.getTitle() );
            ps.setString( index++, scoring.getDescription() );

            int affectedRows = ps.executeUpdate();
            result = 1 == affectedRows;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
