package it.unipi.dataset.Dao;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Model.League;
import it.unipi.dataset.Model.Team;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LeagueDao {
    /**
     * Aggiunge la lega al database
     * @throws SQLException
     */
    public static synchronized  void addLeague(League l) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("INSERT INTO league(name, nteams, pts_win, pts_tie, pts_loss, pts_td, pts_cas, pts_td_conceded, treasury, round, playoff, tvr, spp_star) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setString(1, l.getName());
        ps.setInt(2, l.getNTeams());
        ps.setInt(3, l.getPtsWin());
        ps.setInt(4, l.getPtsTie());
        ps.setInt(5, l.getPtsLose());
        ps.setBoolean(6, l.isPtsTD());
        ps.setBoolean(7, l.isPtsCAS());
        ps.setBoolean(8, l.isPtsTDConceded());
        ps.setInt(9, l.getTreasury());
        ps.setInt(10, l.getGroups());
        ps.setInt(11, l.getPlayoff());
        ps.setBoolean(12, l.isTvr());
        ps.setBoolean(13, l.isSppStar());
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Seleziona dal database la/e lega/ghe selezionate
     * @param id identifcatore della lega. Se è 0 allora indica l'elenco di leghe
     * @return
     * @throws SQLException
     */
    public static synchronized List<League> getLeagues(int id) throws SQLException {
        PreparedStatement st;
        String sql = "SELECT * FROM league";
        if(id != 0) {
            sql += "WHERE id = ?";
        }
        st = App.getConnection().prepareStatement(sql);
        if(id != 0)
            st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        List<League> leagues = new ArrayList<>();
        while(rs.next()) {
            leagues.add(new League(rs));
        }
        st.close();
        rs.close();
        return leagues;
    }

    /**
     * Elimina le lega selezionata
     * @param id Identificatore della lega
     * @throws SQLException
     */
    public static synchronized void removeLeague(int id) throws SQLException {
        PreparedStatement ps;
        ResultDao.deleteResults(id);
        List<Team> teams = TeamDao.getTeam(0, id);
        for(Team team : teams) {
            PlayerDao.deletePlayers(team.getId());
            TeamDao.removeTeam(team.getId());
        }
        ps = App.getConnection().prepareStatement("DELETE FROM league WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }


}
