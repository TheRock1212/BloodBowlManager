package it.unipi.dataset.Dao;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Model.Result;
import it.unipi.utility.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ResultDao {

    /**
     * Aggiunge i risultati contenuti in pairs
     * @param pairs Contiene tutti gli accoppiamenti
     * @throws SQLException
     */
    public static synchronized void addResult(Pair[] pairs) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("INSERT INTO results(league, teamH, teamA) VALUES(?, ?, ?)");
        for(Pair pair : pairs) {
            ps.setInt(1, App.getLeague().getId());
            ps.setInt(2, pair.home);
            ps.setInt(3, pair.away);
            ps.addBatch();
        }
        ps.executeBatch();
        ps.close();
    }

    /**
     * Ottiene tutti i risultati per quella lega
     * @return lista dei risultati
     * @throws SQLException
     */
    public static synchronized List<Result> getResults() throws SQLException {
        PreparedStatement s = App.getConnection().prepareStatement("SELECT * FROM results WHERE league = ?");
        s.setInt(1, App.getLeague().getId());
        ResultSet rs = s.executeQuery();
        List<Result> results = new ArrayList<>();
        while(rs.next()) {
            results.add(new Result(rs));
        }
        rs.close();
        s.close();
        return results;
    }
}
