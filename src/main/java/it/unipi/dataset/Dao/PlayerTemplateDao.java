package it.unipi.dataset.Dao;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Model.PlayerTemplate;
import it.unipi.dataset.Model.Race;
import it.unipi.dataset.Model.Team;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlayerTemplateDao {

    /**
     * Cerca la lista dei giocatori rookie per una determinata razza
     * @param race id della razza
     * @return lista dei template
     * @throws SQLException
     */
    public static synchronized List<PlayerTemplate> getTemplate(int race) throws SQLException {
        PreparedStatement st = App.getConnection().prepareStatement("SELECT * FROM player_template WHERE race = ?");
        st.setInt(1, race);
        ResultSet rs = st.executeQuery();
        List<PlayerTemplate> templates = new ArrayList<>();
        while (rs.next()) {
            templates.add(new PlayerTemplate(rs));
        }
        st.close();
        rs.close();
        return templates;
    }

    /**
     * Cerca il giocatore con la posizione lineman (quello con max_qty > 10)
     * @param race id della razza
     * @return id del lineman
     * @throws SQLException
     */
    public static synchronized int getLineman(int race) throws SQLException {
        PreparedStatement st = App.getConnection().prepareStatement("SELECT id FROM player_template WHERE race = ? AND max_qty > 10");
        st.setInt(1, race);
        ResultSet rs = st.executeQuery();
        rs.next();
        int id = rs.getInt(1);
        st.close();
        rs.close();
        return id;
    }

    /**
     *Cerco il template del jpurneyman
     * @param id del journeyman
     * @return template del journeyman
     * @throws SQLException
     */
    public static synchronized PlayerTemplate getJourneyman(int id) throws SQLException {
        PreparedStatement st = App.getConnection().prepareStatement("SELECT * FROM player_template WHERE id = ?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        rs.next();
        PlayerTemplate template = new PlayerTemplate(rs);
        st.close();
        rs.close();
        return template;
    }

    /**
     * Cerca il template richiesto
     * @param id id del template
     * @return il template
     * @throws SQLException
     */
    public static synchronized PlayerTemplate getPlayer(int id) throws SQLException {
        PreparedStatement st = App.getConnection().prepareStatement("SELECT * FROM player_template WHERE id = ?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        rs.next();
        PlayerTemplate template = new PlayerTemplate(rs);
        st.close();
        rs.close();
        return template;
    }

    static public int getLonerForOutsidePlayer(int id, Team t) throws SQLException {
        String sql = " SELECT * FROM player_template pt LEFT JOIN race r ON pt.race = r.id WHERE pt.id = ? ";
        PreparedStatement st = App.getConnection().prepareStatement(sql);
        int i = 0;
        st.setInt(++i, id);
        ResultSet rs = st.executeQuery();
        rs.next();
        Race race = new Race(rs);
        PlayerTemplate template = new PlayerTemplate(rs);
        rs.close();
        st.close();
        if(template.getRace() == t.getRace() || template.skill.contains("Loner"))
            return 0;
        Race raceTeam = RaceDao.getRace(t.getRace());
        if(raceTeam.getFamily().equals(race.getFamily()))
            return 2;
        return 3;
    }
}
