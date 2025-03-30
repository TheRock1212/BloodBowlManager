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

public class TeamDao {

    /**
     * Restutisce il resul set dei team
     *
     * @param id identificatore del team. Se è 0 si prendono tutti i team
     * @return
     * @throws SQLException
     */
    public static synchronized List<Team> getTeam(int id, int league) throws SQLException {
        String sql = "SELECT * FROM team";
        if (id == 0) {
            sql += " WHERE league = ?";
        } else
            sql += " WHERE id = ?";
        PreparedStatement ps = App.getConnection().prepareStatement(sql);
        if (id == 0)
            ps.setInt(1, league);
        else
            ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        List<Team> teams = new ArrayList<>();
        while (rs.next()) {
            teams.add(new Team(rs));
        }
        ps.close();
        rs.close();
        return teams;
    }

    /**
     * Aggiunge un nuovo team nel database
     * @param t il team da aggiungere
     * @throws SQLException
     */
    public static synchronized void addTeam(Team t) throws SQLException{
        PreparedStatement ps = App.getConnection().prepareStatement("INSERT INTO team(coach, name, race, league, ngiocatori, nreroll, apothecary, cheerleader, assistant, DF, treasury, G, W, N, L, TDScored, TDConceded, CASInflicted, CASSuffered, PTS, value, round, journeyman, ready, active, cards) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setString(1, t.coach);
        ps.setString(2, t.name);
        ps.setInt(3, t.getRace());
        ps.setInt(4, t.getLeague());
        ps.setInt(5, t.ngiocatori);
        ps.setInt(6, t.nreroll);
        ps.setBoolean(7, t.apothecary);
        ps.setInt(8, t.cheerleader);
        ps.setInt(9, t.assistant);
        ps.setInt(10, t.df);
        ps.setInt(11, t.treasury);
        ps.setInt(12, t.g);
        ps.setInt(13, t.w);
        ps.setInt(14, t.n);
        ps.setInt(15, t.l);
        ps.setInt(16, t.tdScored);
        ps.setInt(17, t.tdConceded);
        ps.setInt(18, t.casInflicted);
        ps.setInt(19, t.casSuffered);
        ps.setInt(20, t.points);
        ps.setInt(21, t.value);
        ps.setInt(22, t.getRound());
        ps.setInt(23, t.getJourneyman());
        ps.setBoolean(24, t.isReady());
        ps.setBoolean(25, t.isActive());
        ps.setInt(26, t.getCards());
        ps.executeUpdate();
        ps.close();

        Statement st = App.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID()");
        while(rs.next())
            t.setId(rs.getInt(1));
        t.sponsor = "none";
        st.close();
        rs.close();
    }

    /**
     * Elimina team e tutti i suoi giocatori
     * @param id id del team da eliminare
     * @throws SQLException
     */
    public static synchronized void removeTeam(int id) throws SQLException{
        PreparedStatement p = App.getConnection().prepareStatement("DELETE FROM player WHERE team = ?");
        p.setInt(1, id);
        p.executeUpdate();
        p.close();
        PreparedStatement ps = App.getConnection().prepareStatement("DELETE FROM team WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Aggiorna team
     * @param t team da aggiornare
     * @param player indica cosa aggiornare: true per player o false per staff
     * @throws SQLException
     */
    public static synchronized void updateTeam(Team t, boolean player) throws SQLException {
        if(player) {
            PreparedStatement ps = App.getConnection().prepareStatement("UPDATE team SET ngiocatori = ?, treasury = ?, value = ?, ready = ?, sponsor = ?, cards = ?, stadium = ? WHERE id = ?");
            ps.setInt(1, t.ngiocatori);
            ps.setInt(2, t.treasury);
            ps.setInt(3, t.value);
            ps.setBoolean(4, t.isReady());
            ps.setString(5, t.sponsor);
            ps.setInt(6, t.getCards());
            ps.setString(7, t.getStadium());
            ps.setInt(8, t.getId());
            ps.executeUpdate();
            ps.close();
        }
        else {
            PreparedStatement ps = App.getConnection().prepareStatement("UPDATE team SET nreroll = ?,  apothecary = ?, cheerleader = ?, assistant = ?, treasury = ?, value = ?, sponsor = ?, cards = ?, stadium = ? WHERE id = ?");
            ps.setInt(1, t.nreroll);
            ps.setInt(3, t.cheerleader);
            ps.setBoolean(2, t.apothecary);
            ps.setInt(4, t.assistant);
            ps.setInt(5, t.treasury);
            ps.setInt(6, t.value);
            ps.setString(7, t.sponsor);
            ps.setInt(8, t.getCards());
            ps.setString(9, t.getStadium());
            ps.setInt(10, t.getId());
            ps.executeUpdate();
            ps.close();
        }
    }

    /**
     * Restituisce il nome di un team
     * @param id id del team
     * @return nome del team
     * @throws SQLException
     */
    public static synchronized String getName(int id) throws SQLException {
        PreparedStatement st = App.getConnection().prepareStatement("SELECT name FROM team WHERE id = ?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        rs.next();
        String result = rs.getString(1);
        rs.close();
        st.close();
        return result;
    }

    /**
     * Prende gli id dei team appartenti ad un girone
     * @param gr il numero del girone, se vale 0 la lega ha un unico girone
     * @param l lega
     * @return un array contenti gli id dei team
     * @throws SQLException
     */
    public static synchronized int[] getTeams(int gr, League l) throws SQLException {
        String sql = "SELECT id FROM team WHERE league = ?";
        int nteam = 0;
        if(gr == 0)
            nteam = l.getNTeams();
        else {
            PreparedStatement ps = App.getConnection().prepareStatement("SELECT COUNT(*) FROM team WHERE league = ? AND round = ?");
            ResultSet rs = ps.executeQuery();
            rs.next();
            nteam = rs.getInt(1);
            rs.close();
            ps.close();
            sql += "AND round = ?";
        }
        int[] teams = new int[nteam];
        PreparedStatement ps = App.getConnection().prepareStatement(sql);
        ps.setInt(1, l.getId());
        if(gr != 0)
            ps.setInt(2, gr);
        ResultSet rs = ps.executeQuery();
        int i = 0;
        while(rs.next()) {
            teams[i++] = rs.getInt(1);
        }
        rs.close();
        ps.close();
        return teams;
    }

    public static synchronized void updateResult(Team t) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("UPDATE team SET ngiocatori = ?, treasury = ?, DF = ?, G = ?, W = ?, N = ?, L = ?, TDScored = ?, TDConceded = ?, CASInflicted = ?, CASSuffered = ?, PTS = ?, value = ?, ready = ? WHERE id = ?");
        int i = 0;
        ps.setInt(++i, t.ngiocatori);
        ps.setInt(++i, t.treasury);
        ps.setInt(++i, t.df);
        ps.setInt(++i, t.g);
        ps.setInt(++i, t.w);
        ps.setInt(++i, t.n);
        ps.setInt(++i, t.l);
        ps.setInt(++i, t.tdScored);
        ps.setInt(++i, t.tdConceded);
        ps.setInt(++i, t.casInflicted);
        ps.setInt(++i, t.casSuffered);
        ps.setInt(++i, t.points);
        ps.setInt(++i, t.value);
        ps.setBoolean(++i, t.isReady());
        ps.setInt(++i, t.getId());
        ps.executeUpdate();
        ps.close();
    }

    public static synchronized void saveSponsor(int id, String sponsor, int cards) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("UPDATE team SET sponsor = ?, cards = ? WHERE id = ?");
        ps.setString(1, sponsor);
        ps.setInt(2, cards);
        ps.setInt(3, id);
        ps.executeUpdate();
        ps.close();
    }

    public static synchronized String getCoach(int id) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("SELECT coach FROM team WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String result = rs.getString(1);
        rs.close();
        ps.close();
        return result;
    }

    public static boolean isLineman(int team, int template) throws SQLException {
        String sql = "SELECT 1 FROM team WHERE id = ? AND journeyman = ?";
        PreparedStatement ps = App.getConnection().prepareStatement(sql);
        int i = 0;
        ps.setInt(++i, team);
        ps.setInt(++i, template);
        ResultSet rs = ps.executeQuery();
        boolean result = rs.next();
        rs.close();
        ps.close();
        return result;
    }

    public static List<String> getNames(int league) throws SQLException {
        String sql = "SELECT name FROM team WHERE league = ?";
        PreparedStatement ps = App.getConnection().prepareStatement(sql);
        int i = 0;
        ps.setInt(++i, league);
        ResultSet rs = ps.executeQuery();
        List<String> names = new ArrayList<>();
        while(rs.next()) {
            names.add(rs.getString(1));
        }
        rs.close();
        ps.close();
        return names;
    }

    public static int getTreasury(String name) throws SQLException {
        String sql = "SELECT treasury FROM team WHERE name = ?";
        PreparedStatement ps = App.getConnection().prepareStatement(sql);
        int i = 0;
        ps.setString(++i, name);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int result = rs.getInt(1);
        rs.close();
        ps.close();
        return result;

    }

    public static Team getTeam(String name) throws SQLException {
        String sql = "SELECT * FROM team WHERE name = ?";
        PreparedStatement ps = App.getConnection().prepareStatement(sql);
        int i = 0;
        ps.setString(++i, name);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Team team = new Team(rs);
        rs.close();
        ps.close();
        return team;
    }
}
