package hu.alkfejl.dao;

import hu.alkfejl.model.Scoring;

import java.util.List;

public interface ScoringDAO {
    List<Scoring> getScoringForUsername(final String username );
    boolean saveScoringForUsername( final Scoring scoring );
}
