package it.unipi.dataset.Dao;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Model.Player;
import it.unipi.dataset.Model.PlayerTemplate;
import it.unipi.dataset.Model.Team;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlayerDao {

    /**
     * Aggiunge un insieme di giocatori al database
     * @param pp array contenente i giocatori
     * @throws SQLException
     */
    public static synchronized void addPlayer(List<Player> pp) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("INSERT INTO player(number, name, player_template, team, unspentSPP, SPP, new_skill, MA_inc, ST_inc, AG_inc, PA_inc, AV_inc, MA_dec, ST_dec, AG_dec, PA_dec, AV_dec, NIG, MNG, val, TD, CAS, K, CP, D, I, lev, status, isjourney) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        for(Player p : pp) {
            if(p == null)
                break;
            ps.setInt(1, p.getNumber());
            ps.setString(2, p.getName());
            ps.setInt(3, p.getTemplate());
            ps.setInt(4, p.getTeam());
            ps.setInt(5, p.getUnspentSPP());
            ps.setInt(6, p.getSpp());
            ps.setString(7, p.getSkill());
            ps.setInt(8, p.getMaInc());
            ps.setInt(9, p.getStInc());
            ps.setInt(10, p.getAgInc());
            ps.setInt(11, p.getPaInc());
            ps.setInt(12, p.getAvInc());
            ps.setInt(13, p.getMaDec());
            ps.setInt(14, p.getStDec());
            ps.setInt(15, p.getAgDec());
            ps.setInt(16, p.getPaDec());
            ps.setInt(17, p.getAvDec());
            ps.setInt(18, p.getNig());
            ps.setBoolean(19, p.isMng());
            ps.setInt(20, p.getValue());
            ps.setInt(21, p.getTd());
            ps.setInt(22, p.getCas());
            ps.setInt(23, p.getKill());
            ps.setInt(24, p.getCp());
            ps.setInt(25, p.getDef());
            ps.setInt(26, p.getInter());
            ps.setInt(27, p.getLevel());
            ps.setBoolean(28, p.isStatus());
            ps.setBoolean(29, p.isJourney());
            ps.addBatch();
        }
        ps.executeBatch();
        ps.close();
    }

    /**
     * Elimina il giocatore dal database
     * @param id id del giocatore
     * @throws SQLException
     */
    public static synchronized void deletePlayer(int id) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("DELETE FROM player WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Funzione che cerca l'elenco dei giocatori di un team
     * @param team id del team
     * @param alive se deve cercare solo giocatori vivi o anche licenziati/deceduti
     * @return elenco di giocatori
     * @throws SQLException
     */
    public static synchronized List<Player> getPlayers(int team, boolean alive) throws SQLException {
        PreparedStatement ps;
        String sql = "SELECT * FROM player WHERE team = ?";
        if(alive)
            sql += " AND status = 1 ORDER BY number ASC";
        ps = App.getConnection().prepareStatement(sql);
        ps.setInt(1, team);
        ResultSet rs = ps.executeQuery();
        List<Player> players = new ArrayList<>();
        while(rs.next()) {
            players.add(new Player(rs));
        }
        ps.close();
        rs.close();
        return players;
    }

    /**
     * Licenzia o uccide il giocatore
     * @param id del giocatore
     * @throws SQLException
     */
    public static synchronized void removePlayer(int id) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("UPDATE player SET status = 0 WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Aggiunge un unico giocatore
     * @param p il giocatore da aggiungere
     * @throws SQLException
     */
    public static synchronized void addPlayer(Player p) throws SQLException{
        PreparedStatement ps = App.getConnection().prepareStatement("INSERT INTO player(number, name, player_template, team, unspentSPP, SPP, new_skill, MA_inc, ST_inc, AG_inc, PA_inc, AV_inc, MA_dec, ST_dec, AG_dec, PA_dec, AV_dec, NIG, MNG, val, TD, CAS, K, CP, D, I, lev, status, isjourney) VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setInt(1, p.getNumber());
        ps.setString(2, p.getName());
        ps.setInt(3, p.getTemplate());
        ps.setInt(4, p.getTeam());
        ps.setInt(5, p.getUnspentSPP());
        ps.setInt(6, p.getSpp());
        ps.setString(7, p.getSkill());
        ps.setInt(8, p.getMaInc());
        ps.setInt(9, p.getStInc());
        ps.setInt(10, p.getAgInc());
        ps.setInt(11, p.getPaInc());
        ps.setInt(12, p.getAvInc());
        ps.setInt(13, p.getMaDec());
        ps.setInt(14, p.getStDec());
        ps.setInt(15, p.getAgDec());
        ps.setInt(16, p.getPaDec());
        ps.setInt(17, p.getAvDec());
        ps.setInt(18, p.getNig());
        ps.setBoolean(19, p.isMng());
        ps.setInt(20, p.getValue());
        ps.setInt(21, p.getTd());
        ps.setInt(22, p.getCas());
        ps.setInt(23, p.getKill());
        ps.setInt(24, p.getCp());
        ps.setInt(25, p.getDef());
        ps.setInt(26, p.getInter());
        ps.setInt(27, p.getLevel());
        ps.setBoolean(28, p.isStatus());
        ps.setBoolean(29, p.isJourney());
        ps.executeUpdate();
        ps.close();

        Statement st = App.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID()");
        while(rs.next())
            p.setId(rs.getInt(1));
        rs.close();
        st.close();
    }

    /**
     * Aggiorna record del giocatore
     * @param p giocatore da aggiornare
     * @param preview se true indica i dati relativi alla preview, se false all'anagrafica
     * @throws SQLException
     */
    public static synchronized void updatePlayer(Player p, boolean preview) throws SQLException{
        PreparedStatement ps;
        if(preview) {
            ps = App.getConnection().prepareStatement("UPDATE player SET number = ?, name = ?, unspentSPP = ?, new_skill = ?, MA_inc = ?, ST_inc = ?, AG_inc = ?, PA_inc = ?, AV_inc = ?, val = ?, lev = ? WHERE id = ?");
            ps.setInt(1, p.number);
            ps.setString(2, p.getName());
            ps.setInt(3, p.unspentSPP);
            ps.setString(4, p.getSkill());
            ps.setInt(5, p.getMaInc());
            ps.setInt(6, p.getStInc());
            ps.setInt(7, p.getAgInc());
            ps.setInt(8, p.getPaInc());
            ps.setInt(9, p.getAvInc());
            ps.setInt(10, p.value);
            ps.setInt(11, p.getLevel());
            ps.setInt(12, p.getId());
        }
        else {
            ps = App.getConnection().prepareStatement("UPDATE player SET name = ?, number = ? WHERE id = ?");
            ps.setString(1, p.getName());
            ps.setInt(2, p.number);
            ps.setInt(3, p.getId());
        }
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Conta il numero di giocatori per la posizione
     * @param template id del template
     * @param team id del team
     * @return il numero di giocatori per quel template
     * @throws SQLException
     */
    public static synchronized int getPositional(int template, int team) throws SQLException {
        PreparedStatement st = App.getConnection().prepareStatement("SELECT COUNT(*) FROM player WHERE player_template = ? AND team = ? AND status = 1 AND isjourney = 0");
        st.setInt(1, template);
        st.setInt(2, team);
        ResultSet rs = st.executeQuery();
        rs.next();
        int num = rs.getInt(1);
        rs.close();
        st.close();
        return num;
    }

    /**
     * Cerca tutti i numeri dei giocatori presenti nel roster
     * @param t team
     * @return array con tutti i numeri dei giocatori
     * @throws SQLException
     */
    public static synchronized int[] getPlayerNumbers(Team t) throws SQLException {
        PreparedStatement st = App.getConnection().prepareStatement("SELECT number FROM player WHERE team = ? AND status = 1 AND isjourney = 0");
        st.setInt(1, t.getId());
        ResultSet rs = st.executeQuery();
        int[] nrs = new int[t.ngiocatori];
        int i = 0;
        while(rs.next()) {
            nrs[i++] = rs.getInt(1);
        }
        st.close();
        rs.close();
        return nrs;
    }

    /**
     * Cerca tutti i nomi dei giocatori presenti nel roster
     * @param t team
     * @return array con tutti i nomi
     * @throws SQLException
     */
    public static synchronized String[] getPlayerNames(Team t) throws SQLException {
        PreparedStatement st = App.getConnection().prepareStatement("SELECT name FROM player WHERE team = ? AND status = 1 AND isjourney = 0");
        st.setInt(1, t.getId());
        ResultSet rs = st.executeQuery();
        String[] names = new String[t.ngiocatori];
        int i = 0;
        while(rs.next()) {
            names[i++] = rs.getString(1);
        }
        st.close();
        rs.close();
        return names;
    }

    /**
     * Prende tutti i journeyman di un team
     * @param t team
     * @return array contente i journeyman
     * @throws SQLException
     */
    public static synchronized Player[] getJourneymans(Team t) throws SQLException {
        PreparedStatement st = App.getConnection().prepareStatement("SELECT COUNT(*) from player WHERE team = ? AND status = 1 AND isjourney = 1");
        st.setInt(1, t.getId());
        ResultSet rs = st.executeQuery();
        rs.next();
        System.out.println(rs.getInt(1));
        if(rs.getInt(1) == 0)
            return null;
        Player[] journeymans = new Player[rs.getInt(1)];
        st = App.getConnection().prepareStatement("SELECT * from player WHERE team = ? AND status = 1 AND isjourney = 1");
        st.setInt(1, t.getId());
        rs = st.executeQuery();
        int i = 0;
        while(rs.next()) {
            journeymans[i++] = new Player(rs);
        }
        st.close();
        rs.close();
        return journeymans;
    }

    /**
     * Aggiorna i giocatori che hanno partecipato ad una partita
     * @param players
     * @throws SQLException
     */
    public static synchronized void updateResults(List<Player> players) throws SQLException {
        PreparedStatement ps;
        for(Player p : players) {
            ps = App.getConnection().prepareStatement("UPDATE player SET unspentSPP = ?, SPP = ?, MA_dec = ?, ST_dec = ?, AG_dec = ?, PA_dec = ?, AV_dec = ?, NIG = ?, MNG = ?, val = ?, TD = ?, CAS = ?, K = ?, CP = ?, D = ?, I = ? WHERE id = ?");
            int i = 0;
            ps.setInt(++i, p.unspentSPP);
            ps.setInt(++i, p.spp);
            ps.setInt(++i, p.getMaDec());
            ps.setInt(++i, p.getStDec());
            ps.setInt(++i, p.getAgDec());
            ps.setInt(++i, p.getPaDec());
            ps.setInt(++i, p.getAvDec());
            ps.setInt(++i, p.nig);
            ps.setBoolean(++i, p.mng);
            ps.setInt(++i, p.value);
            ps.setInt(++i, p.td);
            ps.setInt(++i, p.cas);
            ps.setInt(++i, p.kill);
            ps.setInt(++i, p.cp);
            ps.setInt(++i, p.def);
            ps.setInt(++i, p.inter);
            ps.setInt(++i, p.getId());
            ps.executeUpdate();
            ps.close();
        }
    }

    /**
     * Setta status dei giocatori a 0
     * @param players id dei giocatori morti
     * @throws SQLException
     */
    public static synchronized void setDead(List<Integer> players) throws SQLException {
        if(players.isEmpty())
            return;
        PreparedStatement ps;
        for(Integer p : players) {
            ps = App.getConnection().prepareStatement("UPDATE player SET status = 0 WHERE id = ?");
            ps.setInt(1, p);
            ps.executeUpdate();
            ps.close();
        }
    }

    /**
     * Prende i giocatori che possono giocare la partita
     * @param team id del team
     * @return lista dio giocatori disponibili
     * @throws SQLException
     */
    public static synchronized List<Player> getStarting(int team) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("SELECT * FROM player WHERE status = 1 AND MNG = 0 AND team = ?");
        ps.setInt(1, team);
        ResultSet rs = ps.executeQuery();
        List<Player> starting = new ArrayList<>();
        while(rs.next()) {
            starting.add(new Player(rs));
        }
        rs.close();
        ps.close();
        return starting;
    }

    /**
     * Aggiorna i giocatori che saltano la partita
     * @param t team
     * @throws SQLException
     */
    public static synchronized void setMng(Team t) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("SELECT * FROM player WHERE status = 1 AND MNG = 1 AND team = ?");
        ps.setInt(1, t.getId());
        ResultSet rs = ps.executeQuery();
        List<Player> starting = new ArrayList<>();
        while(rs.next()) {
            starting.add(new Player(rs));
        }
        List<PlayerTemplate> pt = new ArrayList<>();
        int value = 0;
        for(Player p : starting) {
            pt.add(PlayerTemplateDao.getPlayer(p.getTemplate()));
        }
        for(int i = 0; i< starting.size(); i++) {
            value += starting.get(i).value + pt.get(i).cost;
        }
        t.value += value;
        TeamDao.updateTeam(t, true);
        ps = App.getConnection().prepareStatement("UPDATE player SET MNG = 0 WHERE team = ?");
        ps.setInt(1, t.getId());
        ps.executeUpdate();
        ps.close();
    }

    public static synchronized int countPlayers(int team) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("SELECT COUNT(*) FROM player WHERE status = 1 AND MNG = 0 AND team = ?");
        ps.setInt(1, team);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int cont = rs.getInt(1);
        ps.close();
        rs.close();
        return cont;
    }

    public static synchronized void deletePlayers(int team) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("DELETE FROM player WHERE team = ?");
        ps.setInt(1, team);
        ps.executeUpdate();
        ps.close();
    }

    public static synchronized List<Player> getPlayers(int league) throws SQLException {
        List<Player> players = new ArrayList<>();
        PreparedStatement ps = App.getConnection().prepareStatement("SELECT P.* FROM player P JOIN team T ON P.team = T.id WHERE T.league = ?");
        ps.setInt(1, league);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            players.add(new Player(rs));
        }
        rs.close();
        ps.close();
        return players;
    }
}
