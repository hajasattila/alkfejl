package hu.alkfejl.util;

public class SQLiteQueries {
    public static final String REGISTER_USER = "INSERT INTO User (username, password, token) VALUES(?,?,?);";
    public static final String GET_USER_BY_NAME = "SELECT * FROM User WHERE username = ?;";
    public static final String GET_USER_BY_TOKEN = "SELECT * FROM User WHERE token = ?;";
    public static final String UPDATE_USER = "UPDATE User SET password = ?, token = ? WHERE username = ?;";
    public static final String GET_SCORING_FOR_USERNAME = "SELECT * FROM Scoring where username = ?;";
    public static final String SAVE_SCORING_FOR_USERNAME = "INSERT INTO Scoring(username, title, description) VALUES(?,?,?);";
}
