package hu.alkfejl.controller;

import hu.alkfejl.dao.ScoringDAO;
import hu.alkfejl.dao.ScoringDAOSqlite;
import hu.alkfejl.dao.UserDAO;
import hu.alkfejl.dao.UserDAOSqlite;
import hu.alkfejl.model.Scoring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoringController {
    private ScoringDAO dao;

    private static volatile Map<String, ScoringController> instancesMap = new HashMap<>();
    private static final Object object = new Object();
    private ScoringController( String dbURL, String dbClass ) {
        dao = ScoringDAOSqlite.getInstance( dbURL, dbClass );
    }

    /**
     * Get an instance with fix db engine: org.sqlite.JDBC
     * {@link hu.alkfejl.controller.UserController#getInstance(String, String)} is called.
     *
     * @param dbURL URL of the db.
     * */
    public static ScoringController getInstance( String dbURL ) {
        return getInstance( dbURL, "org.sqlite.JDBC" );
    }

    /**
     * Get an instance for the database and the handling engine.
     * @param dbURL URL of the db. jdbc:whatever: must be the prefix
     * @param dbClass the handling class to load dynamicly, e.g. org.sqlite.JDBC
     * */
    public static ScoringController getInstance( String dbURL, String dbClass ) {
        String combinedKey = dbURL + dbClass;

        if ( instancesMap.containsKey( combinedKey ) ) {
            return instancesMap.get( combinedKey );
        }

        synchronized ( object ) {
            if ( instancesMap.containsKey( combinedKey ) ) {
                return instancesMap.get( combinedKey );
            }
            var newInstance = new ScoringController(dbURL, dbClass);
            instancesMap.put( combinedKey, newInstance );

            return newInstance;
        }
    }

    /**
     * Get the scores saved by the provided user.
     * @param username the username whose scores are queried.
     * @return a list containing the scores saved by the user.
     * */
    public List<Scoring> getScoringForUsername( String username ) {
        return dao.getScoringForUsername( username );
    }

    /**
     * Save a scoring.
     * @param scoring object to save
     * @return true is the object is saved, false otherwise.
     * */
    boolean saveScoringForUsername( Scoring scoring ) {
        return dao.saveScoringForUsername( scoring );
    }
}
