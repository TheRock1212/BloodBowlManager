package it.unipi.dataset;

import com.mysql.cj.xdevapi.PreparableStatement;
import it.unipi.bloodbowlmanager.App;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Player {
    private int id;
    public int number;
    public String name;
    private int template;
    private int team;
    public int unspentSPP;
    public int spp;
    public String skill;
    private int maInc;
    private int stInc;
    private int agInc;
    private int paInc;
    private int avInc;
    private int maDec;
    private int stDec;
    private int agDec;
    private int paDec;
    private int avDec;
    public int nig;
    public boolean mng;
    public int value;
    public int td;
    public int cas;
    public int kill;
    public int cp;
    public int def;
    public int inter;
    private int level;
    private boolean status;
    public boolean journey;

    public Player() {

    }

    public Player(int id, int number, String name, int template, int team, int unspentSPP, int spp, String skill, int maInc, int stInc, int agInc, int paInc, int avInc, int maDec, int stDec, int agDec, int paDec, int avDec, int nig, boolean mng, int value, int td, int cas, int kill, int cp, int def, int inter, int level, boolean status, boolean journey) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.template = template;
        this.team = team;
        this.unspentSPP = unspentSPP;
        this.spp = spp;
        this.skill = skill;
        this.maInc = maInc;
        this.stInc = stInc;
        this.agInc = agInc;
        this.paInc = paInc;
        this.avInc = avInc;
        this.maDec = maDec;
        this.stDec = stDec;
        this.agDec = agDec;
        this.paDec = paDec;
        this.avDec = avDec;
        this.nig = nig;
        this.mng = mng;
        this.value = value;
        this.td = td;
        this.cas = cas;
        this.kill = kill;
        this.cp = cp;
        this.def = def;
        this.inter = inter;
        this.level = level;
        this.status = status;
        this.journey = journey;
    }

    public Player(int number, String name, int template, int team, int unspentSPP, int spp, String skill, int maInc, int stInc, int agInc, int paInc, int avInc, int maDec, int stDec, int agDec, int paDec, int avDec, int nig, boolean mng, int value, int td, int cas, int kill, int cp, int def, int inter, int level, boolean status, boolean journey) {
        this.number = number;
        this.name = name;
        this.template = template;
        this.team = team;
        this.unspentSPP = unspentSPP;
        this.spp = spp;
        this.skill = skill;
        this.maInc = maInc;
        this.stInc = stInc;
        this.agInc = agInc;
        this.paInc = paInc;
        this.avInc = avInc;
        this.maDec = maDec;
        this.stDec = stDec;
        this.agDec = agDec;
        this.paDec = paDec;
        this.avDec = avDec;
        this.nig = nig;
        this.mng = mng;
        this.value = value;
        this.td = td;
        this.cas = cas;
        this.kill = kill;
        this.cp = cp;
        this.def = def;
        this.inter = inter;
        this.level = level;
        this.status = status;
        this.journey = journey;
    }

    public Player(int template, int team, int unspentSPP, int spp, String skill, int maInc, int stInc, int agInc, int paInc, int avInc, int maDec, int stDec, int agDec, int paDec, int avDec, int nig, boolean mng, int value, int td, int cas, int kill, int cp, int def, int inter, int level, boolean status, boolean journey) {
        this.template = template;
        this.team = team;
        this.unspentSPP = unspentSPP;
        this.spp = spp;
        this.skill = skill;
        this.maInc = maInc;
        this.stInc = stInc;
        this.agInc = agInc;
        this.paInc = paInc;
        this.avInc = avInc;
        this.maDec = maDec;
        this.stDec = stDec;
        this.agDec = agDec;
        this.paDec = paDec;
        this.avDec = avDec;
        this.nig = nig;
        this.mng = mng;
        this.value = value;
        this.td = td;
        this.cas = cas;
        this.kill = kill;
        this.cp = cp;
        this.def = def;
        this.inter = inter;
        this.level = level;
        this.status = status;
        this.journey = journey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getUnspentSPP() {
        return unspentSPP;
    }

    public void setUnspentSPP(int unspentSPP) {
        this.unspentSPP = unspentSPP;
    }

    public int getSpp() {
        return spp;
    }

    public void setSpp(int spp) {
        this.spp = spp;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getMaInc() {
        return maInc;
    }

    public void setMaInc(int maInc) {
        this.maInc = maInc;
    }

    public int getStInc() {
        return stInc;
    }

    public void setStInc(int stInc) {
        this.stInc = stInc;
    }

    public int getAgInc() {
        return agInc;
    }

    public void setAgInc(int agInc) {
        this.agInc = agInc;
    }

    public int getPaInc() {
        return paInc;
    }

    public void setPaInc(int paInc) {
        this.paInc = paInc;
    }

    public int getAvInc() {
        return avInc;
    }

    public void setAvInc(int avInc) {
        this.avInc = avInc;
    }

    public int getMaDec() {
        return maDec;
    }

    public void setMaDec(int maDec) {
        this.maDec = maDec;
    }

    public int getStDec() {
        return stDec;
    }

    public void setStDec(int stDec) {
        this.stDec = stDec;
    }

    public int getAgDec() {
        return agDec;
    }

    public void setAgDec(int agDec) {
        this.agDec = agDec;
    }

    public int getPaDec() {
        return paDec;
    }

    public void setPaDec(int paDec) {
        this.paDec = paDec;
    }

    public int getAvDec() {
        return avDec;
    }

    public void setAvDec(int avDec) {
        this.avDec = avDec;
    }

    public int getNig() {
        return nig;
    }

    public void setNig(int nig) {
        this.nig = nig;
    }

    public boolean isMng() {
        return mng;
    }

    public void setMng(boolean mng) {
        this.mng = mng;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getTd() {
        return td;
    }

    public void setTd(int td) {
        this.td = td;
    }

    public int getCas() {
        return cas;
    }

    public void setCas(int cas) {
        this.cas = cas;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getInter() {
        return inter;
    }

    public void setInter(int inter) {
        this.inter = inter;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isJourney() {
        return journey;
    }

    public void setJourney(boolean journey) {
        this.journey = journey;
    }

    public void addPlayer(Player[] pp) throws SQLException {
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

    public void deletePlayer(int id) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("DELETE FROM player WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public void deletePlayers(int team) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("DELETE FROM player WHERE team = ?");
        ps.setInt(1, team);
    }

    public ResultSet getPlayers(int team, boolean alive) throws SQLException {
        PreparedStatement ps;
        if(alive)
            ps = App.getConnection().prepareStatement("SELECT * FROM player WHERE team = ? AND status = 1 ORDER BY number ASC");
        else
            ps = App.getConnection().prepareStatement("SELECT * FROM player WHERE team = ?");
        ps.setInt(1, team);
        return ps.executeQuery();
    }

    public void removePlayer(int id) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("UPDATE player SET status = 0 WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public void addPlayer() throws SQLException{
        PreparedStatement ps = App.getConnection().prepareStatement("INSERT INTO player(number, name, player_template, team, unspentSPP, SPP, new_skill, MA_inc, ST_inc, AG_inc, PA_inc, AV_inc, MA_dec, ST_dec, AG_dec, PA_dec, AV_dec, NIG, MNG, val, TD, CAS, K, CP, D, I, lev, status, isjourney) VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setInt(1, getNumber());
        ps.setString(2, getName());
        ps.setInt(3, getTemplate());
        ps.setInt(4, getTeam());
        ps.setInt(5, getUnspentSPP());
        ps.setInt(6, getSpp());
        ps.setString(7, getSkill());
        ps.setInt(8, getMaInc());
        ps.setInt(9, getStInc());
        ps.setInt(10, getAgInc());
        ps.setInt(11, getPaInc());
        ps.setInt(12, getAvInc());
        ps.setInt(13, getMaDec());
        ps.setInt(14, getStDec());
        ps.setInt(15, getAgDec());
        ps.setInt(16, getPaDec());
        ps.setInt(17, getAvDec());
        ps.setInt(18, getNig());
        ps.setBoolean(19, isMng());
        ps.setInt(20, getValue());
        ps.setInt(21, getTd());
        ps.setInt(22, getCas());
        ps.setInt(23, getKill());
        ps.setInt(24, getCp());
        ps.setInt(25, getDef());
        ps.setInt(26, getInter());
        ps.setInt(27, getLevel());
        ps.setBoolean(28, isStatus());
        ps.setBoolean(29, isJourney());
        ps.executeUpdate();

        Statement st = App.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID()");
        while(rs.next())
            id = rs.getInt(1);
    }
}
