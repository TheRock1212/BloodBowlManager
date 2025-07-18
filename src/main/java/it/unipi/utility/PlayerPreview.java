package it.unipi.utility;

import it.unipi.dataset.Model.Player;
import it.unipi.dataset.Model.PlayerTemplate;
import it.unipi.dataset.Model.Race;
import it.unipi.utility.connection.Connection;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class PlayerPreview {
    private int id;
    private String newSkills;
    private int templateId;
    private int team;
    private int newMA;
    private int newST;
    private int newAG;
    private int newPA;
    private int newAV;
    private int newVal;
    private String primary;
    private String secondary;
    @FXML public ImageView img;
    public int number;
    public String name;
    public int unspentSPP;
    public int SPP;
    public String skill;
    public int MA;
    public int ST;
    public int AG;
    public int PA;
    public int AV;
    public int NIG;
    public boolean MNG;
    public int val;
    public int TD;
    public int CAS;
    public int K;
    public int CP;
    public int D;
    public int I;
    private int lev;
    private boolean journey;
    public String position;
    public int season;
    public boolean status;

    public PlayerPreview(TemplateImage ti, Player p) throws IOException {
        this.id = p.getId();
        this.templateId = p.getTemplate();
        this.number = p.getNumber();
        this.name = p.getName();
        this.img = new ImageView();
        this.img.setImage(new Image(getClass().getResource("/it/unipi/bloodbowlmanager/img/" + ti.getUrl() + ".png").toExternalForm()));
        this.unspentSPP = p.getUnspentSPP();
        this.SPP = p.getSpp();
        this.newSkills = p.getSkill();
        if(ti.getSkill().isEmpty())
            this.skill = p.getSkill();
        else
            this.skill = ti.getSkill() + "," + p.getSkill();
        this.MA = ti.getMa() + p.getMaInc() - p.getMaDec();
        this.ST = ti.getSt() + p.getStInc() - p.getStDec();
        this.AG = ti.getAg() - p.getAgInc() + p.getAgDec();
        this.PA = ti.getPa() - p.getPaInc() + p.getPaDec();
        this.AV = ti.getAv() + p.getAvInc() - p.getAvDec();
        this.NIG = p.getNig();
        this.MNG = p.isMng();
        Connection.params.put("id", ti.getRace());
        String data = Connection.getConnection("/api/v1/race/haslcl", Connection.GET, null);
        if("true".equals(data) && ti.maxQty > 10)
            this.val = p.getValue();
        else
            this.val = ti.getCost() + p.getValue();
        this.TD = p.getTd();
        this.CAS = p.getCas();
        this.K = p.getKill();
        this.CP = p.getCp();
        this.D = p.getDef();
        this.I = p.getInter();
        this.lev = p.getLevel();
        this.position = ti.getPosition();
        this.newMA = p.getMaInc();
        this.newST = p.getStInc();
        this.newAG = p.getAgInc();
        this.newPA = p.getPaInc();
        this.newAV = p.getAvInc();
        this.newVal = p.getValue();
        this.primary = ti.primary;
        this.secondary = ti.secondary;
        this.journey = p.isJourney();
        this.team = p.getTeam();
        this.season = p.getSeason();
        this.status = p.isStatus();
    }

    public PlayerPreview(PlayerPreview pp) {
        this.id = pp.getId();
        this.templateId = pp.getTemplateId();
        this.number = pp.getNumber();
        this.name = pp.getName();
        this.img = pp.getImg();
        this.unspentSPP = pp.getUnspentSPP();
        this.SPP = pp.getSPP();
        this.newSkills = pp.getNewSkills();
        this.skill = pp.getSkill();
        this.MA = pp.getMA();
        this.ST = pp.getST();
        this.AG = pp.getAG();
        this.PA = pp.getPA();
        this.AV = pp.getAV();
        this.val = pp.getVal();
        this.TD = pp.getTD();
        this.CAS = pp.getCAS();
        this.K = pp.getK();
        this.CP = pp.getCP();
        this.D = pp.getD();
        this.I = pp.getI();
        this.lev = pp.getLev();
        this.position = pp.getPosition();
        this.newMA = pp.getNewMA();
        this.newST = pp.getNewST();
        this.newAG = pp.getNewAG();
        this.newPA = pp.getNewPA();
        this.newAV = pp.getNewAV();
        this.newVal = pp.getNewVal();
        this.primary = pp.getPrimary();
        this.secondary = pp.getSecondary();
        this.journey = pp.isJourney();
        this.team = pp.getTeam();
        this.season = pp.getSeason();
        this.status = pp.isStatus();
    }

    public PlayerPreview(PlayerTemplate pt, Player p) throws Exception {
        this.id = p.getId();
        this.templateId = p.getTemplate();
        this.number = p.getNumber();
        this.name = p.getName();
        this.unspentSPP = p.getUnspentSPP();
        this.SPP = p.getSpp();
        this.newSkills = p.getSkill();
        if(pt.getSkill().isEmpty())
            this.skill = p.getSkill();
        else
            this.skill = pt.getSkill() + "," + p.getSkill();
        this.MA = pt.getMa() + p.getMaInc() - p.getMaDec();
        this.ST = pt.getSt() + p.getStInc() - p.getStDec();
        this.AG = pt.getAg() - p.getAgInc() + p.getAgDec();
        this.PA = pt.getPa() - p.getPaInc() + p.getPaDec();
        this.AV = pt.getAv() + p.getAvInc() - p.getAvDec();
        this.NIG = p.getNig();
        this.MNG = p.isMng();
        Connection.params.put("id", pt.getRace());
        String data = Connection.getConnection("/api/v1/race/haslcl", Connection.GET, null);
        if("true".equals(data) && pt.maxQty > 10)
            this.val = p.getValue();
        else
            this.val = pt.getCost() + p.getValue();
        this.TD = p.getTd();
        this.CAS = p.getCas();
        this.K = p.getKill();
        this.CP = p.getCp();
        this.D = p.getDef();
        this.I = p.getInter();
        this.lev = p.getLevel();
        this.position = pt.getPosition();
        this.newMA = p.getMaInc();
        this.newST = p.getStInc();
        this.newAG = p.getAgInc();
        this.newPA = p.getPaInc();
        this.newAV = p.getAvInc();
        this.newVal = p.getValue();
        this.primary = pt.primary;
        this.secondary = pt.secondary;
        this.journey = p.isJourney();
        this.team = p.getTeam();
        this.season = p.getSeason();
        this.status = p.isStatus();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
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

    public int getUnspentSPP() {
        return unspentSPP;
    }

    public void setUnspentSPP(int unspentSPP) {
        this.unspentSPP = unspentSPP;
    }

    public int getSPP() {
        return SPP;
    }

    public void setSPP(int SPP) {
        this.SPP = SPP;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getMA() {
        return MA;
    }

    public void setMA(int MA) {
        this.MA = MA;
    }

    public int getST() {
        return ST;
    }

    public void setST(int ST) {
        this.ST = ST;
    }

    public int getAG() {
        return AG;
    }

    public void setAG(int AG) {
        this.AG = AG;
    }

    public int getPA() {
        return PA;
    }

    public void setPA(int PA) {
        this.PA = PA;
    }

    public int getAV() {
        return AV;
    }

    public void setAV(int AV) {
        this.AV = AV;
    }

    public int getNIG() {
        return NIG;
    }

    public void setNIG(int NIG) {
        this.NIG = NIG;
    }

    public boolean isMNG() {
        return MNG;
    }

    public void setMNG(boolean MNG) {
        this.MNG = MNG;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getTD() {
        return TD;
    }

    public void setTD(int TD) {
        this.TD = TD;
    }

    public int getCAS() {
        return CAS;
    }

    public void setCAS(int CAS) {
        this.CAS = CAS;
    }

    public int getK() {
        return K;
    }

    public void setK(int K) {
        this.K = K;
    }

    public int getCP() {
        return CP;
    }

    public void setCP(int CP) {
        this.CP = CP;
    }

    public int getD() {
        return D;
    }

    public void setD(int D) {
        this.D = D;
    }

    public int getI() {
        return I;
    }

    public void setI(int I) {
        this.I = I;
    }

    public int getLev() {
        return lev;
    }

    public void setLev(int lev) {
        this.lev = lev;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNewSkills() {
        return newSkills;
    }

    public void setNewSkills(String newSkills) {
        this.newSkills = newSkills;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public int getNewMA() {
        return newMA;
    }

    public void setNewMA(int newMA) {
        this.newMA = newMA;
    }

    public int getNewST() {
        return newST;
    }

    public void setNewST(int newST) {
        this.newST = newST;
    }

    public int getNewAG() {
        return newAG;
    }

    public void setNewAG(int newAG) {
        this.newAG = newAG;
    }

    public int getNewPA() {
        return newPA;
    }

    public void setNewPA(int newPA) {
        this.newPA = newPA;
    }

    public int getNewAV() {
        return newAV;
    }

    public void setNewAV(int newAV) {
        this.newAV = newAV;
    }

    public int getNewVal() {
        return newVal;
    }

    public void setNewVal(int newVal) {
        this.newVal = newVal;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getSecondary() {
        return secondary;
    }

    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }

    public boolean isJourney() {
        return journey;
    }

    public void setJourney(boolean journey) {
        this.journey = journey;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
