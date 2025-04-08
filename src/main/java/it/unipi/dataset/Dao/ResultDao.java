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
     * @param giornate Contiene il calendario
     * @throws SQLException
     */
    public static synchronized void addResult(List<Pair[]> giornate) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("INSERT INTO results(league, teamH, teamA) VALUES(?, ?, ?)");
        for(Pair[] giornata : giornate) {
            for (Pair pair : giornata) {
                ps.setInt(1, App.getLeague().getId());
                ps.setInt(2, pair.home);
                ps.setInt(3, pair.away);
                ps.addBatch();
            }
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

    /**
     * Aggiunge un risultato completo
     * @param result risultato da aggiungere
     * @throws SQLException
     */
    public static synchronized void setResult(Result result) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("UPDATE results SET tdH = ?, tdA = ?, casH = ?, casA = ?, killH = ?, killA = ?, cpH = ?, cpA = ?, decH = ?, decA = ?, intH = ?, intA = ?, dfH = ?, dfA = ?, winH = ?, winA = ?, played = 1, date = ? WHERE id = ?");
        int i = 0;
        ps.setInt(++i, result.tdh);
        ps.setInt(++i, result.tda);
        ps.setInt(++i, result.cash);
        ps.setInt(++i, result.casa);
        ps.setInt(++i, result.killh);
        ps.setInt(++i, result.killa);
        ps.setInt(++i, result.cph);
        ps.setInt(++i, result.cpa);
        ps.setInt(++i, result.dech);
        ps.setInt(++i, result.deca);
        ps.setInt(++i, result.inth);
        ps.setInt(++i, result.inta);
        ps.setInt(++i, result.dfh);
        ps.setInt(++i, result.dfa);
        ps.setInt(++i, result.winh);
        ps.setInt(++i, result.wina);
        ps.setDate(++i, result.date);
        ps.setInt(++i, result.getId());
        ps.executeUpdate();
        ps.close();
    }

    public static synchronized void deleteResults(int league) throws SQLException {
        PreparedStatement s = App.getConnection().prepareStatement("DELETE FROM results WHERE league = ?");
        s.setInt(1, league);
        s.executeUpdate();
    }

    public static boolean isLastOfRegular(int league, int team) throws SQLException {
        String sql = "SELECT COUNT(*) AS STILLTOBE FROM results WHERE date IS NULL AND league = ? AND (teamH = ? OR teamA = ?)";
        PreparedStatement ps = App.getConnection().prepareStatement(sql);
        int i = 0;
        ps.setInt(++i, league);
        ps.setInt(++i, team);
        ps.setInt(++i, team);
        ResultSet rs = ps.executeQuery();
        boolean result = false;
        while(rs.next()) {
            result = rs.getInt(1) == 0;
        }
        rs.close();
        ps.close();
        return result;
    }

    static public boolean isAllPlayed(int league) throws SQLException {
        boolean result = true;
        String sql = " SELECT * FROM results WHERE league = ? AND played = 0 ";
        PreparedStatement ps = App.getConnection().prepareStatement(sql);
        int i = 0;
        ps.setInt(++i, league);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            result = false;
            break;
        }
        ps.close();
        rs.close();
        return result;
    }
}
