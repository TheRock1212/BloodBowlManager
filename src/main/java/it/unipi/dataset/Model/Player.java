package it.unipi.dataset.Model;

import it.unipi.bloodbowlmanager.App;
import it.unipi.utility.PlayerPreview;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    public int season;

    public Player() {

    }

    public Player(int nbrJr, String journeyman, int id, int team, int unspentSPP, String s, int i, int maInc, int stInc, int agInc, int paInc, int avInc, int maDec, int stDec, int agDec, int paDec, int avDec, int nig, boolean mng, int value, int td, int cas, int kill, int cp, int def, int inter, int level, boolean status, boolean journey) {

    }

    public Player(int id, int number, String name, int template, int team, int unspentSPP, int spp, String skill, int maInc, int stInc, int agInc, int paInc, int avInc, int maDec, int stDec, int agDec, int paDec, int avDec, int nig, boolean mng, int value, int td, int cas, int kill, int cp, int def, int inter, int level, boolean status, boolean journey, int season) {
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
        this.season = season;
    }

    public Player(int number, String name, int template, int team, int unspentSPP, int spp, String skill, int maInc, int stInc, int agInc, int paInc, int avInc, int maDec, int stDec, int agDec, int paDec, int avDec, int nig, boolean mng, int value, int td, int cas, int kill, int cp, int def, int inter, int level, boolean status, boolean journey, int season) {
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
        this.season = season;
    }

    public Player(int template, int team, int unspentSPP, int spp, String skill, int maInc, int stInc, int agInc, int paInc, int avInc, int maDec, int stDec, int agDec, int paDec, int avDec, int nig, boolean mng, int value, int td, int cas, int kill, int cp, int def, int inter, int level, boolean status, boolean journey, int season) {
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
        this.season = season;
    }

    public Player(PlayerPreview pp) {
        this.id = pp.getId();
        this.number = pp.number;
        this.name = pp.name;
        this.template = pp.getTemplateId();
        this.team = App.getTeam().getId();
        this.unspentSPP = pp.unspentSPP;
        this.spp = pp.SPP;
        this.skill = pp.getNewSkills();
        this.maInc = pp.getNewMA();
        this.stInc = pp.getNewST();
        this.agInc = pp.getNewAG();
        this.paInc = pp.getNewPA();
        this.avInc = pp.getNewAV();
        this.nig = pp.getNIG();
        this.mng = pp.isMNG();
        this.value = pp.getNewVal();
        this.td = pp.TD;
        this.cas = pp.CAS;
        this.kill = pp.K;
        this.cp = pp.CP;
        this.def = pp.D;
        this.inter = pp.I;
        this.level = pp.getLev();
        this.status = pp.status;
    }

    public Player(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.number = rs.getInt("number");
        this.name = rs.getString("name");
        this.template = rs.getInt("player_template");
        this.team = rs.getInt("team");
        this.unspentSPP = rs.getInt("unspentSPP");
        this.spp = rs.getInt("SPP");
        this.skill = rs.getString("new_skill");
        this.maInc = rs.getInt("MA_inc");
        this.stInc = rs.getInt("ST_inc");
        this.agInc = rs.getInt("AG_inc");
        this.paInc = rs.getInt("PA_inc");
        this.avInc = rs.getInt("AV_inc");
        this.maDec = rs.getInt("MA_dec");
        this.stDec = rs.getInt("ST_dec");
        this.agDec = rs.getInt("AG_dec");
        this.paDec = rs.getInt("PA_dec");
        this.avDec = rs.getInt("AV_dec");
        this.nig = rs.getInt("NIG");
        this.mng = rs.getBoolean("MNG");
        this.value = rs.getInt("val");
        this.td = rs.getInt("TD");
        this.cas = rs.getInt("CAS");
        this.kill = rs.getInt("K");
        this.cp = rs.getInt("CP");
        this.def = rs.getInt("D");
        this.inter = rs.getInt("I");
        this.level = rs.getInt("lev");
        this.status = rs.getBoolean("status");
        this.journey = rs.getBoolean("isjourney");
        this.season = rs.getInt("season");
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

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }
}
