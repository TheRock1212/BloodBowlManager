package it.unipi.utility;


import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Model.*;
import it.unipi.utility.connection.Connection;
import it.unipi.utility.json.JsonExploiter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.net.MalformedURLException;
import java.util.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PDFManager {
    private static final String[] headerRoster = {"#", "PLAYER NAME", "TYPE", "MA", "ST", "AG", "PA", "AV", "SKILLS", "TotalSPP", "UnspentSPP", "COST", "Season"};
    private static final int nrColumn = 13;
    private static final String TAHOMA = PDFManager.class.getResource("/fonts/Tahoma.ttf").toExternalForm(); //"src/main/resources/it/unipi/bloodbowlmanager/fonts/Tahoma.ttf";
    private static final String TAHOMA_BOLD = PDFManager.class.getResource("/fonts/Tahoma Bold.ttf").toExternalForm();

    public static final String[] catStatPlayer = {"Best Players", "Best Scorers", "Most Vicious", "Best Killers", "Best Passers", "Best Interceptors"};
    public static final String[] catStatTeam = {"Best Offence", "Best Defence", "Most Roughtest", "Most Toughtest", "Best Killers", "Most Passes", "Most Interceptions"};

    private static String data;

    public PDFManager() {
    }

    public static void generatePDF(Team t) throws Exception {
        //PdfWriter writer = new PdfWriter("/Users/aleroc/Desktop/Blood Bowl/GSBowl III/Prova Roster/" + t.coach + ".pdf");
        PdfWriter writer;
        try {
            writer = new PdfWriter("/Users/aleroc/Desktop/Blood Bowl/" + App.getLeague().getName() + "/Roster/" + t.coach + ".pdf");
        } catch (Exception e) {
            //Files.createDirectories(Paths.get("/Users/aleroc/Desktop/Blood Bowl/" + App.getLeague().getName()));
            File roster = new File("/Users/aleroc/Desktop/Blood Bowl/" + App.getLeague().getName() + "/Roster/" + t.coach + ".pdf");
            roster.getParentFile().mkdirs();
            roster.createNewFile();
            writer = new PdfWriter("/Users/aleroc/Desktop/Blood Bowl/" + App.getLeague().getName() + "/Roster/" + t.coach + ".pdf");
        }

        //PdfWriter writer = new PdfWriter(dest + t.coach + ".pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4.rotate());
        PdfFont body = PdfFontFactory.createFont(TAHOMA, PdfEncodings.WINANSI);
        PdfFont head = PdfFontFactory.createFont(TAHOMA_BOLD, PdfEncodings.WINANSI);
        Style h = new Style()
                .setBackgroundColor(ColorConstants.GRAY)
                .setFont(head)
                .setFontColor(ColorConstants.WHITE)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(6);
        Style b = new Style()
                .setBackgroundColor(ColorConstants.WHITE)
                .setFont(body)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(6);
        Style empty = new Style()
                .setBackgroundColor(ColorConstants.GRAY);
        Style inf = new Style()
                .setItalic()
                .setFontColor(ColorConstants.RED);
        Style inc = new Style()
                .setBold()
                .setFontColor(ColorConstants.GREEN);

        Connection.params.put("team", t.getId());
        Connection.params.put("fixture", App.getLeague().getFixture());
        Connection.params.put("league", App.getLeague().getId());
        data = Connection.getConnection("/api/v1/team/foe", Connection.GET, null);
        Team foe = JsonExploiter.getObjectFromJson(Team.class, data);
        Table table = null;
        if(App.getLeague().isPerennial())
            table =new Table(UnitValue.createPercentArray(new float[] {2.56f, 11.82f, 12.85f, 3.06f, 2.56f, 2.56f, 2.56f, 3.06f, 37.7f, 5.00f, 6.69f, 4.69f, 3.64f}));
        else
            table = new Table(UnitValue.createPercentArray(new float[] {2.56f, 11.82f, 12.85f, 3.06f, 2.56f, 2.56f, 2.56f, 3.06f, 37.7f, 6.64f, 7.69f, 5.69f}));
        table.setWidth(UnitValue.createPercentValue(100));
        table.setFixedLayout();
        //table.setMaxWidth(600);
        //table.setMinWidth(1000);
        /*ind.setWidth(UnitValue.createPercentValue(30));
        ind.setFixedLayout();

        ind.addHeaderCell(new Cell().add(new Paragraph("INDUCEMENTS"))).addStyle(h);
        ind.addHeaderCell(new Cell().add(new Paragraph("QTY"))).addStyle(h);
        ind.addHeaderCell(new Cell().add(new Paragraph("COST"))).addStyle(h);*/



        for(String header : headerRoster) {
            if(!App.getLeague().isPerennial() && header.equals(headerRoster[headerRoster.length - 1]))
                break;
            table.addHeaderCell(new Cell().add(new Paragraph(header)).addStyle(h));
        }
        //Generazione Corpo tabella
        Connection.params.put("team", t.getId());
        data = Connection.getConnection("/api/v1/player/starting", Connection.GET, null);
        List<Player> p = JsonExploiter.getListFromJson(Player.class, data);
        Connection.params.put("race", t.getRace());
        data = Connection.getConnection("/api/v1/playerTemplate/template", Connection.GET, null);
        List<PlayerTemplate> pt = JsonExploiter.getListFromJson(PlayerTemplate.class, data);
        List<PlayerPreview> pp = new ArrayList<>();
        for(Player pla : p) {
            for(PlayerTemplate temp : pt) {
                if(pla.getTemplate() == temp.getId()) {
                    pp.add(new PlayerPreview(temp, pla));
                }
            }
        }
        Comparator<PlayerPreview> comp = Comparator.comparingInt(PlayerPreview::getNumber);
        pp.sort(comp);
        for(PlayerPreview player : pp) {
            PlayerTemplate temp = pt.stream().filter(template -> template.getId() == player.getTemplateId()).findFirst().orElse(null);
            for(int i = 0; i < nrColumn; i++) {
                if(!App.getLeague().isPerennial() && i == (nrColumn - 1))
                    break;
                switch(i) {
                    case 0:
                        table.addCell(Integer.toString(player.number)).addStyle(b);
                        break;
                    case 1:
                        table.addCell(player.name).addStyle(b);
                        break;
                    case 2:
                        table.addCell(player.position).addStyle(b);
                        break;
                    case 3:
                        if(player.MA < temp.ma)
                            table.addCell(new Paragraph(Integer.toString(player.MA)).addStyle(inf)).addStyle(b);
                        else if(player.MA > temp.ma)
                            table.addCell(new Paragraph(Integer.toString(player.MA )).addStyle(inc)).addStyle(b);
                        else
                            table.addCell(Integer.toString(player.MA)).addStyle(b);
                        break;
                    case 4:
                        if(player.ST < temp.st)
                            table.addCell(new Paragraph(Integer.toString(player.ST)).addStyle(inf)).addStyle(b);
                        else if(player.ST > temp.st)
                            table.addCell(new Paragraph(Integer.toString(player.ST)).addStyle(inc)).addStyle(b);
                        else
                            table.addCell(Integer.toString(player.ST)).addStyle(b);
                        break;
                    case 5:
                        if(player.AG > temp.ag)
                            table.addCell(new Paragraph(player.AG + "+").addStyle(inf)).addStyle(b);
                        else if(player.AG < temp.ag)
                            table.addCell(new Paragraph(player.AG + "+").addStyle(inc)).addStyle(b);
                        else
                            table.addCell(player.AG + "+").addStyle(b);
                        break;
                    case 6:
                        String plc = player.PA == 7 ? "-" : player.PA + "+";
                        if(player.PA > temp.pa)
                            table.addCell(new Paragraph(plc).addStyle(inf)).addStyle(b);
                        else if(player.PA < temp.pa)
                            table.addCell(new Paragraph(plc).addStyle(inc)).addStyle(b);
                        else
                            table.addCell(plc).addStyle(b);
                        break;
                    case 7:
                        if(player.AV > temp.av)
                            table.addCell(new Paragraph(player.AV + "+").addStyle(inc)).addStyle(b);
                        else if(player.AV < temp.av)
                            table.addCell(new Paragraph(player.AV + "+").addStyle(inf)).addStyle(b);
                        else
                            table.addCell(player.AV + "+").addStyle(b);
                        break;
                    case 8:
                        Paragraph par = new Paragraph();
                        String res = player.skill.replace(player.getNewSkills(), "");
                        par.add(res).addStyle(b);
                        if(!player.getNewSkills().isEmpty()) {
                            par.add(new Text(player.getNewSkills()).setBold()).addStyle(b);
                        }

                        String nig = "";
                        if(player.getNIG() > 0)
                            nig = " - " + player.getNIG() + " NI";
                        par.add(nig);
                        table.addCell(par).addStyle(b);
                        break;
                    case 9:
                        table.addCell(Integer.toString(player.SPP)).addStyle(b);
                        break;
                    case 10:
                        table.addCell(Integer.toString(player.unspentSPP)).addStyle(b);
                        break;
                    case 11:
                        table.addCell(player.val + ".000").addStyle(b);
                        break;
                    case 12:
                        table.addCell(Integer.toString(player.season)).addStyle(b);
                        break;
                }
            }
        }

        Connection.params.put("id", t.getRace());
        data = Connection.getConnection("/api/v1/race/id", Connection.GET, null);
        Race r = JsonExploiter.getObjectFromJson(Race.class, data);
        //table.setSkipFirstHeader()
        Cell raceH = new Cell(1, 2).add(new Paragraph("RACE")).addStyle(h);
        table.addCell(raceH);
        Cell sponsor = new Cell(1, 3).add(new Paragraph(r.name)).addStyle(b);
        table.addCell(sponsor);
       /* Cell teamH = new Cell(1, 4).add(new Paragraph("TEAM NAME")).addStyle(h);
        table.addCell(teamH);
        Cell team = new Cell(1, 2).add(new Paragraph(t.name)).addStyle(b);
        table.addCell(team);
        Cell coachH = new Cell(1, 1).add(new Paragraph("COACH")).addStyle(h);
        table.addCell(coachH);
        Cell coach = new Cell(1, 2).add(new Paragraph(t.coach)).addStyle(b);
        table.addCell(coach);*/
        Cell qtyH = new Cell(1, 2).add(new Paragraph("QTY")).addStyle(h);
        table.addCell(qtyH);
        Cell playerH = new Cell(1, 2).add(new Paragraph("PLAYER ROLE")).addStyle(h);
        table.addCell(playerH);
        Cell costH = new Cell(1, 1).add(new Paragraph("COST")).addStyle(h);
        table.addCell(costH);
        Cell primH = new Cell(1, 1).add(new Paragraph("PRIMARY")).addStyle(h);
        table.addCell(primH);
        Cell secondH = new Cell(1, App.getLeague().isPerennial() ? 2 : 1).add(new Paragraph("SECONDARY")).addStyle(h);
        table.addCell(secondH);

        for(int i = 0; i < 11; i++) {
            switch(i) {
                case 0: {
                    Cell teamH = new Cell(1, 2).add(new Paragraph("TEAM NAME")).addStyle(h);
                    table.addCell(teamH);
                    Cell team = new Cell(1, 3).add(new Paragraph(t.name)).addStyle(b);
                    table.addCell(team);
                    break;
                }
                case 1: {
                    Cell teamH = new Cell(1, 2).add(new Paragraph("COACH")).addStyle(h);
                    table.addCell(teamH);
                    Cell team = new Cell(1, 3).add(new Paragraph(t.coach)).addStyle(b);
                    table.addCell(team);
                    break;
                }
                case 2: {
                    Cell teamH = new Cell(1, 2).add(new Paragraph("DEDICATED FANS")).addStyle(h);
                    table.addCell(teamH);
                    Cell team = new Cell(1, 3).add(new Paragraph(Integer.toString(t.df))).addStyle(b);
                    table.addCell(team);
                    break;
                }
                case 3: {
                    Cell teamH = new Cell(1, 2).add(new Paragraph("REROLL")).addStyle(h);
                    table.addCell(teamH);
                    Cell team = new Cell(1, 3).add(new Paragraph(Integer.toString(t.nreroll))).addStyle(b);
                    table.addCell(team);
                    break;
                }
                case 4: {
                    Cell teamH = new Cell(1, 2).add(new Paragraph("APOTHECARY")).addStyle(h);
                    table.addCell(teamH);
                    Cell team = new Cell(1, 3).add(new Paragraph(t.apothecary ? "SI" : "NO")).addStyle(b);
                    table.addCell(team);
                    break;
                }
                case 5: {
                    Cell teamH = new Cell(1, 2).add(new Paragraph("CHEERLEADERS")).addStyle(h);
                    table.addCell(teamH);
                    Cell team = new Cell(1, 3).add(new Paragraph(Integer.toString(t.cheerleader))).addStyle(b);
                    table.addCell(team);
                    break;
                }
                case 6: {
                    Cell teamH = new Cell(1, 2).add(new Paragraph("ASSISTANT COACH")).addStyle(h);
                    table.addCell(teamH);
                    Cell team = new Cell(1, 3).add(new Paragraph(Integer.toString(t.assistant))).addStyle(b);
                    table.addCell(team);
                    break;
                }
                case 7: {
                    Cell teamH = new Cell(1, 2).add(new Paragraph(App.getLeague().isPerennial() ? "SPONSOR / CARD" : "SPONSOR")).addStyle(h);
                    table.addCell(teamH);
                    Cell team = null;
                    if(App.getLeague().isPerennial()) {
                        team = new Cell(1, 1).add(new Paragraph(t.sponsor)).addStyle(b);
                        table.addCell(team);
                        Cell card = new Cell(1, 2).add(new Paragraph(Integer.toString(t.getCards()))).addStyle(b);
                        table.addCell(card);
                    }
                    else {
                        team = new Cell(1, 3).add(new Paragraph(t.sponsor)).addStyle(b);
                        table.addCell(team);
                    }
                    break;
                }
                case 8: {
                    Cell teamH = new Cell(1, 2).add(new Paragraph("TREASURY")).addStyle(h);
                    table.addCell(teamH);
                    Cell team = new Cell(1, 3).add(new Paragraph(Integer.toString(t.treasury))).addStyle(b);
                    table.addCell(team);
                    break;
                }
                case 9: {
                    Cell teamH = new Cell(1, 2).add(new Paragraph("TEAM VALUE")).addStyle(h);
                    table.addCell(teamH);
                    Cell team = new Cell(1, 3).add(new Paragraph(Integer.toString(t.value))).addStyle(b);
                    table.addCell(team);
                    break;
                }
                case 10: {
                    Cell teamH = new Cell(1, 2).add(new Paragraph("PETTY CASH")).addStyle(h);
                    table.addCell(teamH);
                    Cell team;
                    try {
                        team = new Cell(1, 3).add(new Paragraph(String.valueOf((foe.value - t.value) < 0 ? 0 : Integer.toString(foe.value - t.value)))).addStyle(b);
                    } catch(Exception e) {
                        team = new Cell(1, 3).add(new Paragraph("0")).addStyle(b);
                    }
                    table.addCell(team);
                    break;
                }
            }
            if(i < pt.size()) {
                Connection.params.put("team", t.getId());
                Cell qty = new Cell(1, 2).add(new Paragraph(Connection.getConnection("/api/v1/player/qty", Connection.GET, JsonExploiter.toJson(pt.get(i)))));
                table.addCell(qty);
                Cell pl = new Cell(1, 2).add(new Paragraph(pt.get(i).position)).addStyle(b);
                table.addCell(pl);
                Cell cost = new Cell(1, 1).add(new Paragraph(pt.get(i).cost + ".000")).addStyle(b);
                table.addCell(cost);
                Cell prim = new Cell(1, 1).add(new Paragraph(pt.get(i).primary)).addStyle(b);
                table.addCell(prim);
                Cell second = new Cell(1, App.getLeague().isPerennial() ? 2 : 1).add(new Paragraph(pt.get(i).secondary)).addStyle(b);
                table.addCell(second);
            }
            else {
                Cell qty = new Cell(1, 2).add(new Paragraph()).addStyle(empty);
                table.addCell(qty);
                Cell pl = new Cell(1, 2).add(new Paragraph()).addStyle(empty);
                table.addCell(pl);
                Cell cost = new Cell(1, 1).add(new Paragraph()).addStyle(empty);
                table.addCell(cost);
                Cell prim = new Cell(1, 1).add(new Paragraph()).addStyle(empty);
                table.addCell(prim);
                Cell second = new Cell(1, App.getLeague().isPerennial() ? 2 : 1).add(new Paragraph()).addStyle(empty);
                table.addCell(second);
            }
        }
        if(App.getLeague().isPerennial()) {
            Cell stadH = new Cell(1, 2).add(new Paragraph("STADIUM")).addStyle(h);
            table.addCell(stadH);
            Cell stad = new Cell(1, 1).add(new Paragraph(t.getStadium())).addStyle(b);
            table.addCell(stad);
        }
        Cell rulesH = new Cell(1, App.getLeague().isPerennial() ? 2 : 5).add(new Paragraph(App.getLeague().isPerennial() ? "RULES" : "SPECIAL RULES")).addStyle(h);
        table.addCell(rulesH);
        String res = r.special1;
        if(!r.special2.isEmpty())
            res += ", " + r.special2;
        if(!r.special3.isEmpty())
            res += ", " + r.special3;
        Cell rules = new Cell(1, 5).add(new Paragraph(res)).addStyle(b);
        table.addCell(rules);

        Cell rerollH = new Cell(1, 1).add(new Paragraph("COST REROLL")).addStyle(h);
        table.addCell(rerollH);

        Cell reroll = new Cell(1, App.getLeague().isPerennial() ? 2 : 1).add(new Paragraph(r.costreroll * 2 + ".000")).addStyle(b);
        table.addCell(reroll);


        Connection.params.put("team", t.getId());
        data = Connection.getConnection("/api/v1/playersMng", Connection.GET, null);
        List<Player> pm = JsonExploiter.getListFromJson(Player.class, data);
        if(!pm.isEmpty()) {
            Cell numbersH = new Cell(1, App.getLeague().isPerennial() ? 13 : 12).add(new Paragraph("MISSING PLAYERS")).addStyle(h);
            table.addCell(numbersH);
            //Generazione Corpo tabella
            List<PlayerPreview> ppm = new ArrayList<>();
            for (Player pla : pm) {
                for (PlayerTemplate temp : pt) {
                    if (pla.getTemplate() == temp.getId()) {
                        ppm.add(new PlayerPreview(temp, pla));
                    }
                }
            }
            ppm.sort(comp);

            for(PlayerPreview player : ppm) {
                PlayerTemplate temp = pt.stream().filter(template -> template.getId() == t.getJourneyman()).findFirst().orElse(null);
                for (int i = 0; i < nrColumn; i++) {
                    if(!App.getLeague().isPerennial() && i == (nrColumn - 1))
                        break;
                    switch (i) {
                        case 0:
                            table.addCell(Integer.toString(player.number)).addStyle(b);
                            break;
                        case 1:
                            table.addCell(player.name).addStyle(b);
                            break;
                        case 2:
                            table.addCell(player.position).addStyle(b);
                            break;
                        case 3:
                            if (player.MA < temp.ma)
                                table.addCell(new Paragraph(Integer.toString(player.MA)).addStyle(inf)).addStyle(b);
                            else if (player.MA > temp.ma)
                                table.addCell(new Paragraph(Integer.toString(player.MA)).addStyle(inc)).addStyle(b);
                            else
                                table.addCell(Integer.toString(player.MA)).addStyle(b);
                            break;
                        case 4:
                            if (player.ST < temp.st)
                                table.addCell(new Paragraph(Integer.toString(player.ST)).addStyle(inf)).addStyle(b);
                            else if (player.ST > temp.st)
                                table.addCell(new Paragraph(Integer.toString(player.ST)).addStyle(inc)).addStyle(b);
                            else
                                table.addCell(Integer.toString(player.ST)).addStyle(b);
                            break;
                        case 5:
                            if (player.AG > temp.ag)
                                table.addCell(new Paragraph(player.AG + "+").addStyle(inf)).addStyle(b);
                            else if (player.AG < temp.ag)
                                table.addCell(new Paragraph(player.AG + "+").addStyle(inc)).addStyle(b);
                            else
                                table.addCell(player.AG + "+").addStyle(b);
                            break;
                        case 6:
                            String plc = player.PA == 7 ? "-" : player.PA + "+";
                            if (player.PA > temp.pa)
                                table.addCell(new Paragraph(plc).addStyle(inf)).addStyle(b);
                            else if (player.PA < temp.pa)
                                table.addCell(new Paragraph(plc).addStyle(inc)).addStyle(b);
                            else
                                table.addCell(plc).addStyle(b);
                            break;
                        case 7:
                            if (player.AV > temp.av)
                                table.addCell(new Paragraph(player.AV + "+").addStyle(inc)).addStyle(b);
                            else if (player.AV < temp.av)
                                table.addCell(new Paragraph(player.AV + "+").addStyle(inf)).addStyle(b);
                            else
                                table.addCell(player.AV + "+").addStyle(b);
                            break;
                        case 8:
                            Paragraph par = new Paragraph();
                            String resm = player.skill.replace(player.getNewSkills(), "");
                            par.add(resm).addStyle(b);
                            if (!player.getNewSkills().isEmpty()) {
                                par.add(new Text(player.getNewSkills()).setBold()).addStyle(b);
                            }

                            String nig = "";
                            if (player.getNIG() > 0)
                                nig = " - " + player.getNIG() + " NI";
                            par.add(nig);
                            table.addCell(par).addStyle(b);
                            break;
                        case 9:
                            table.addCell(Integer.toString(player.SPP)).addStyle(b);
                            break;
                        case 10:
                            table.addCell(Integer.toString(player.unspentSPP)).addStyle(b);
                            break;
                        case 11:
                            table.addCell(player.val + ".000").addStyle(b);
                            break;
                        case 12:
                            table.addCell(Integer.toString(player.season)).addStyle(b);
                            break;
                    }
                }
            }

        }

        document.add(table.setFixedLayout());

        if(foe != null && (foe.value - t.value) > 15) {

            document.add(new AreaBreak(PageSize.A4));

            //document.setPageSize(PageSize.A4);

            Table ind = new Table(new float[]{20f, 2.56f, 3.56f, 2.56f, 2.56f, 2.56f, 3.56f, 57f, 6f});
            ind.setWidth(UnitValue.createPercentValue(100));
            ind.addCell(new Cell(1, 7).add(new Paragraph("INDUCEMENTS")).addStyle(h));
            ind.addCell(new Cell().add(new Paragraph("COST")).addStyle(h));
            ind.addCell(new Cell().add(new Paragraph("QTY")).addStyle(h));

            Connection.params.put("petty", foe.value - t.value);
            data = Connection.getConnection("/api/v1/inducements/inducements", Connection.GET, JsonExploiter.toJson(t));
            List<Inducements> inducements = JsonExploiter.getListFromJson(Inducements.class, data);
            for(Inducements i : inducements) {
                ind.addCell(new Cell(1, 7).add(new Paragraph(i.name)).addStyle(b));
                ind.addCell(new Cell().add(new Paragraph(i.cost + ".000")).addStyle(b));
                ind.addCell(new Cell().add(new Paragraph(Integer.toString(i.qty))).addStyle(b));
            }

            Connection.params.put("race", t.getRace());
            Connection.params.put("petty", foe.value - t.value);
            data = Connection.getConnection("/api/v1/inducements/stars", Connection.GET, null);
            List<StarPlayer> stars = JsonExploiter.getListFromJson(StarPlayer.class, data);

            if(!stars.isEmpty()) {
                ind.addCell(new Cell(1, 2).add(new Paragraph("STAR PLAYER")).addStyle(h));
                ind.addCell(new Cell(1, 1).add(new Paragraph("MA")).addStyle(h));
                ind.addCell(new Cell(1, 1).add(new Paragraph("ST")).addStyle(h));
                ind.addCell(new Cell(1, 1).add(new Paragraph("AG")).addStyle(h));
                ind.addCell(new Cell(1, 1).add(new Paragraph("PA")).addStyle(h));
                ind.addCell(new Cell(1, 1).add(new Paragraph("AV")).addStyle(h));
                ind.addCell(new Cell(1, 1).add(new Paragraph("SKILL")).addStyle(h));
                ind.addCell(new Cell().add(new Paragraph("COST")).addStyle(h));

                //Controllo se ci sono star player ingaggiabili a coppia
                HashMap<String, StarPlayer> map = new HashMap<>();
                for(StarPlayer sp : stars) {
                    if("Crumbleberry".equals(sp.name)) {
                        map.put(sp.name, sp);
                    } else if("Grak".equals(sp.name)) {
                        map.put(sp.name, sp);
                    } else if("Dribl".equals(sp.name)) {
                        map.put(sp.name, sp);
                    } else if("Drull".equals(sp.name)) {
                        map.put(sp.name, sp);
                    } else if("Lucien Swift".equals(sp.name)) {
                        map.put(sp.name, sp);
                    } else if("Valen Swift".equals(sp.name)) {
                        map.put(sp.name, sp);
                    }
                }

                //Tolgo i singoli star dalla lista
                if(!map.isEmpty()) {
                    for(StarPlayer sp : map.values()) {
                        stars.remove(sp);
                    }
                }


                for (StarPlayer s : stars) {
                    ind.addCell(new Cell(1, 2).add(new Paragraph(s.name)).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(Integer.toString(s.ma))).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(Integer.toString(s.st))).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.ag + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.pa == 7 ? "-" : s.pa + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.av + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.skill)).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.cost + ".000")).addStyle(b));
                }

                if(map.containsKey("Grak")) {
                    StarPlayer s = map.get("Grak");
                    ind.addCell(new Cell(1, 2).add(new Paragraph(s.name)).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(Integer.toString(s.ma))).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(Integer.toString(s.st))).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.ag + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.pa == 7 ? "-" : s.pa + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.av + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.skill)).addStyle(b));
                    ind.addCell(new Cell(2, 1).add(new Paragraph(s.cost + ".000")).addStyle(b).setVerticalAlignment(VerticalAlignment.MIDDLE));
                    s = map.get("Crumbleberry");
                    ind.addCell(new Cell(1, 2).add(new Paragraph(s.name)).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(Integer.toString(s.ma))).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(Integer.toString(s.st))).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.ag + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.pa == 7 ? "-" : s.pa + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.av + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.skill)).addStyle(b));
                }

                if(map.containsKey("Dribl")) {
                    StarPlayer s = map.get("Dribl");
                    ind.addCell(new Cell(1, 2).add(new Paragraph(s.name)).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(Integer.toString(s.ma))).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(Integer.toString(s.st))).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.ag + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.pa == 7 ? "-" : s.pa + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.av + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.skill)).addStyle(b));
                    ind.addCell(new Cell(2, 1).add(new Paragraph(s.cost + ".000")).addStyle(b).setVerticalAlignment(VerticalAlignment.MIDDLE));
                    s = map.get("Drull");
                    ind.addCell(new Cell(1, 2).add(new Paragraph(s.name)).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(Integer.toString(s.ma))).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(Integer.toString(s.st))).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.ag + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.pa == 7 ? "-" : s.pa + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.av + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.skill)).addStyle(b));
                }

                if(map.containsKey("Lucien Swift")) {
                    StarPlayer s = map.get("Lucien Swift");
                    ind.addCell(new Cell(1, 2).add(new Paragraph(s.name)).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(Integer.toString(s.ma))).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(Integer.toString(s.st))).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.ag + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.pa == 7 ? "-" : s.pa + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.av + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.skill)).addStyle(b));
                    ind.addCell(new Cell(2, 1).add(new Paragraph(s.cost + ".000")).addStyle(b).setVerticalAlignment(VerticalAlignment.MIDDLE));
                    s = map.get("Valen Swift");
                    ind.addCell(new Cell(1, 2).add(new Paragraph(s.name)).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(Integer.toString(s.ma))).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(Integer.toString(s.st))).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.ag + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.pa == 7 ? "-" : s.pa + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.av + "+")).addStyle(b));
                    ind.addCell(new Cell().add(new Paragraph(s.skill)).addStyle(b));
                }
            }

            document.add(ind.setFixedLayout());
        }




        document.close();
    }

    public static void statsPlayer(ObservableList<PlayerStatistic> ps) throws IOException {
        PdfWriter writer;
        try {
            writer = new PdfWriter("/Users/aleroc/Desktop/Blood Bowl/" + App.getLeague().getName() + "/Player Stats.pdf");
        } catch (Exception e) {
            //Files.createDirectories(Paths.get("/Users/aleroc/Desktop/Blood Bowl/" + App.getLeague().getName()));
            File roster = new File("/Users/aleroc/Desktop/Blood Bowl/" + App.getLeague().getName() + "/Player Stats.pdf");
            roster.getParentFile().mkdirs();
            roster.createNewFile();
            writer = new PdfWriter("/Users/aleroc/Desktop/Blood Bowl/" + App.getLeague().getName() + "/Player Stats.pdf");
        }
        //PdfWriter writer = new PdfWriter("/Users/aleroc/Desktop/Blood Bowl/GSBowl III/Prova Misc/Player Stats.pdf");
        //PdfWriter writer = new PdfWriter(dest + t.coach + ".pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        PdfFont body = PdfFontFactory.createFont(TAHOMA, PdfEncodings.WINANSI);
        PdfFont head = PdfFontFactory.createFont(TAHOMA_BOLD, PdfEncodings.WINANSI);
        Style h = new Style()
                .setBackgroundColor(ColorConstants.GRAY)
                .setFont(head)
                .setFontColor(ColorConstants.WHITE)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(16);
        Style sh = new Style()
                .setBackgroundColor(ColorConstants.WHITE)
                .setFont(head)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12);
        Style b = new Style()
                .setBackgroundColor(ColorConstants.WHITE)
                .setFont(body)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12);
        Style first = new Style(b)
                .setBold();
        Style empty = new Style()
                .setBackgroundColor(ColorConstants.WHITE);
        //Table ind = new Table(UnitValue.createPercentArray(new float[] {67, 33, 33}));
        for(String title : catStatPlayer) {

            for(PlayerStatistic p : ps) {
                p.setValue(title);
            }
            FXCollections.sort(ps, Comparator.comparingInt(PlayerStatistic::getValue).reversed());

            Table table = new Table(UnitValue.createPercentArray(new float[]{5f, 30f, 30f, 30f, 5f}));
            table.setWidth(UnitValue.createPercentValue(100));
            table.setFixedLayout();

            table.addCell(new Cell(1, 5).add(new Paragraph(title)).addStyle(h));
            table.addCell(new Paragraph().addStyle(empty));
            table.addCell(new Paragraph("NAME").addStyle(sh));
            table.addCell(new Paragraph("POSITION").addStyle(sh));
            table.addCell(new Paragraph("TEAM").addStyle(sh));
            table.addCell(new Paragraph("#").addStyle(sh));

            int best = 0;
            for (int i = 0; i < 25; i++) {
                if (i == 0)
                    best = ps.get(i).value;
                if (ps.get(i).value == 0)
                    break;
                if(ps.get(i).value == best) {
                    setBodyPS(ps, first, table, i);
                }
                else {
                    setBodyPS(ps, b, table, i);
                }
            }

            document.add(table);
            if(!title.equals("Best Interceptors"))
                document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        }
        document.close();
    }

    private static void setBodyPS(ObservableList<PlayerStatistic> ps, Style first, Table table, int i) throws MalformedURLException {
        if (i == 24 || ps.get(i + 1).value == 0) {
            table.addCell(new Cell().add(new Image(ImageDataFactory.create(ps.get(i).img.getImage().getUrl())).setAutoScale(true)).setBorder(Border.NO_BORDER).setBorderLeft(new SolidBorder(0.5f)).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(ps.get(i).name).addStyle(first)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(ps.get(i).role).addStyle(first)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(ps.get(i).teamName).addStyle(first)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(ps.get(i).value)).addStyle(first)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setBorderRight(new SolidBorder(0.5f)));
        } else {
            table.addCell(new Cell().add(new Image(ImageDataFactory.create(ps.get(i).img.getImage().getUrl())).setAutoScale(true)).setBorder(Border.NO_BORDER).setBorderLeft(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(ps.get(i).name).addStyle(first)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(ps.get(i).role).addStyle(first)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(ps.get(i).teamName).addStyle(first)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(ps.get(i).value)).addStyle(first)).setBorder(Border.NO_BORDER).setBorderRight(new SolidBorder(0.5f)));
        }
    }

    public static void statsTeam(ObservableList<TeamStatistic> ts) throws IOException {
        PdfWriter writer;
        try {
            writer = new PdfWriter("/Users/aleroc/Desktop/Blood Bowl/" + App.getLeague().getName() + "/Team Stats.pdf");
        } catch (Exception e) {
            //Files.createDirectories(Paths.get("/Users/aleroc/Desktop/Blood Bowl/" + App.getLeague().getName()));
            File roster = new File("/Users/aleroc/Desktop/Blood Bowl/" + App.getLeague().getName() + "/Team Stats.pdf");
            roster.getParentFile().mkdirs();
            roster.createNewFile();
            writer = new PdfWriter("/Users/aleroc/Desktop/Blood Bowl/" + App.getLeague().getName() + "/Team Stats.pdf");
        }
        //PdfWriter writer = new PdfWriter("/Users/aleroc/Desktop/Blood Bowl/GSBowl III/Prova Misc/Team Stats.pdf");
        //PdfWriter writer = new PdfWriter(dest + t.coach + ".pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        PdfFont body = PdfFontFactory.createFont(TAHOMA, PdfEncodings.WINANSI);
        PdfFont head = PdfFontFactory.createFont(TAHOMA_BOLD, PdfEncodings.WINANSI);
        Style h = new Style()
                .setBackgroundColor(ColorConstants.GRAY)
                .setFont(head)
                .setFontColor(ColorConstants.WHITE)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(16);
        Style sh = new Style()
                .setBackgroundColor(ColorConstants.WHITE)
                .setFont(head)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12);
        Style b = new Style()
                .setBackgroundColor(ColorConstants.WHITE)
                .setFont(body)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setFontSize(12);
        Style first = new Style(b)
                .setBold();
        Style empty = new Style()
                .setBackgroundColor(ColorConstants.WHITE);
        //Table ind = new Table(UnitValue.createPercentArray(new float[] {67, 33, 33}));
        for(String title : catStatTeam) {

            for(TeamStatistic t : ts) {
                t.setValue(title);
            }
            FXCollections.sort(ts, Comparator.comparingInt(TeamStatistic::getValue));
            if(!title.contains("Toughtest") && !title.contains("Defence"))
                FXCollections.sort(ts, Comparator.comparingInt(TeamStatistic::getValue).reversed());

            Table table = new Table(UnitValue.createPercentArray(new float[]{7f, 45f, 43f, 5f}));
            table.setWidth(UnitValue.createPercentValue(100));
            table.setFixedLayout();

            table.addCell(new Cell(1, 4).add(new Paragraph(title)).addStyle(h));
            table.addCell(new Paragraph().addStyle(empty));
            table.addCell(new Paragraph("NAME").addStyle(sh));
            table.addCell(new Paragraph("COACH").addStyle(sh));
            table.addCell(new Paragraph("#").addStyle(sh));

            int best = 0;
            for (int i = 0; i < App.getLeague().getTeams(); i++) {
                if (i == 0)
                    best = ts.get(i).getValue();
                if (ts.get(i).getValue() == 0)
                    break;
                if(ts.get(i).getValue() == best) {
                    setBodySP(ts, first, table, i);
                }
                else {
                    setBodySP(ts, b, table, i);
                }
            }

            document.add(table);
            if(!title.equals("Most Interceptors"))
                document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        }
        document.close();
    }

    private static void setBodySP(ObservableList<TeamStatistic> ts, Style first, Table table, int i) throws MalformedURLException {
        if (i == App.getLeague().getTeams() - 1 || ts.get(i + 1).getValue() == 0) {
            table.addCell(new Cell().add(new Image(ImageDataFactory.create(ts.get(i).getImg().getUrl())).setAutoScale(true)).setBorder(Border.NO_BORDER).setBorderLeft(new SolidBorder(0.5f)).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(ts.get(i).name).addStyle(first)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(ts.get(i).coach).addStyle(first)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(ts.get(i).getValue())).addStyle(first)).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setBorderRight(new SolidBorder(0.5f)));
        } else {
            table.addCell(new Cell().add(new Image(ImageDataFactory.create(ts.get(i).getImg().getUrl())).setAutoScale(true)).setBorder(Border.NO_BORDER).setBorderLeft(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(ts.get(i).name).addStyle(first)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(ts.get(i).coach).addStyle(first)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(ts.get(i).getValue())).addStyle(first)).setBorder(Border.NO_BORDER).setBorderRight(new SolidBorder(0.5f)));
        }
    }

    public static void standings(ObservableList<Team> rl, List<javafx.scene.image.Image> images) throws IOException, SQLException {
        PdfWriter writer;
        try {
            writer = new PdfWriter("/Users/aleroc/Desktop/Blood Bowl/" + App.getLeague().getName() + "/Classifica.pdf");
        } catch (Exception e) {
            //Files.createDirectories(Paths.get("/Users/aleroc/Desktop/Blood Bowl/" + App.getLeague().getName()));
            File roster = new File("/Users/aleroc/Desktop/Blood Bowl/" + App.getLeague().getName() + "/Classifica.pdf");
            roster.getParentFile().mkdirs();
            roster.createNewFile();
            writer = new PdfWriter("/Users/aleroc/Desktop/Blood Bowl/" + App.getLeague().getName() + "/Classifica.pdf");
        }
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4.rotate());
        PdfFont body = PdfFontFactory.createFont(TAHOMA, PdfEncodings.WINANSI);
        PdfFont head = PdfFontFactory.createFont(TAHOMA_BOLD, PdfEncodings.WINANSI);
        Style h = new Style()
                .setBackgroundColor(ColorConstants.GRAY)
                .setFont(head)
                .setFontColor(ColorConstants.WHITE)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(16);
        Style b = new Style()
                .setBackgroundColor(ColorConstants.WHITE)
                .setFont(body)
                .setFontColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBold()
                .setFontSize(12);
        Style playoff = new Style(b)
                .setBackgroundColor(new DeviceRgb(0, 176, 80));
        Style empty = new Style()
                .setBackgroundColor(ColorConstants.GRAY);

        Table table = new Table(UnitValue.createPercentArray(new float[]{21f, 17f, 4f, 5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f, 7f}));
        table.setWidth(UnitValue.createPercentValue(100));
        table.setFixedLayout();

        //Prima intestazione
        table.addCell(new Cell().add(new Paragraph("TEAM")).addStyle(h));
        table.addCell(new Cell().add(new Paragraph("COACH")).addStyle(h));
        table.addCell(new Cell().add(new Paragraph("")).addStyle(h));
        table.addCell(new Cell().add(new Paragraph("PTS")).addStyle(h));
        table.addCell(new Cell(1, 3).add(new Paragraph("GAMES")).addStyle(h));
        table.addCell(new Cell(1, 3).add(new Paragraph("TD")).addStyle(h));
        table.addCell(new Cell(1, 3).add(new Paragraph("CAS")).addStyle(h));
        table.addCell(new Cell().add(new Paragraph("DLT")).addStyle(h));

        //Seconda intestazione
        table.addCell(new Cell(1, 4).addStyle(empty));
        table.addCell(new Cell().add(new Paragraph("V")).addStyle(h));
        table.addCell(new Cell().add(new Paragraph("N")).addStyle(h));
        table.addCell(new Cell().add(new Paragraph("P")).addStyle(h));
        table.addCell(new Cell().add(new Paragraph("+")).addStyle(h));
        table.addCell(new Cell().add(new Paragraph("-")).addStyle(h));
        table.addCell(new Cell().add(new Paragraph()).addStyle(h));
        table.addCell(new Cell().add(new Paragraph("+")).addStyle(h));
        table.addCell(new Cell().add(new Paragraph("-")).addStyle(h));
        table.addCell(new Cell().add(new Paragraph()).addStyle(h));
        table.addCell(new Cell().add(new Paragraph()).addStyle(h));

        //Corpo
        for(int i = 0; i < App.getLeague().getTeams(); i++) {
            if(i < App.getLeague().getPlayoff()) {
                setBody(rl, images, playoff, table, i);
            }
            else {
                setBody(rl, images, b, table, i);
            }

        }

        document.add(table);
        document.close();
    }

    private static void setBody(ObservableList<Team> rl, List<javafx.scene.image.Image> images, Style b, Table table, int i) throws SQLException, IOException{
        if(i == App.getLeague().getTeams() - 1) {
            table.addCell(new Cell().add(new Paragraph(rl.get(i).name)).addStyle(b).setBorder(Border.NO_BORDER).setBorderLeft(new SolidBorder(0.5f)).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(rl.get(i).coach)).addStyle(b).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Image(ImageDataFactory.create(images.get(i).getUrl())).setAutoScale(true)).addStyle(b).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).points))).addStyle(b).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).w))).addStyle(b).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).n))).addStyle(b).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).l))).addStyle(b).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).tdScored))).addStyle(b).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).tdConceded))).addStyle(b).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).tdNet))).addStyle(b).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).casInflicted))).addStyle(b).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).casSuffered))).addStyle(b).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).casNet))).addStyle(b).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).getDelta()))).addStyle(b).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)).setBorderRight(new SolidBorder(0.5f)));
        }
        else {
            table.addCell(new Cell().add(new Paragraph(rl.get(i).name)).addStyle(b).setBorder(Border.NO_BORDER).setBorderLeft(new SolidBorder(0.5f)));
            table.addCell(new Cell().add(new Paragraph(rl.get(i).coach)).addStyle(b).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Image(ImageDataFactory.create(images.get(i).getUrl())).setAutoScale(true)).addStyle(b).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).points))).addStyle(b).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).w))).addStyle(b).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).n))).addStyle(b).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).l))).addStyle(b).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).tdScored))).addStyle(b).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).tdConceded))).addStyle(b).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).tdNet))).addStyle(b).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).casInflicted))).addStyle(b).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).casSuffered))).addStyle(b).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).casNet))).addStyle(b).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(Integer.toString(rl.get(i).getDelta()))).addStyle(b).setBorder(Border.NO_BORDER).setBorderRight(new SolidBorder(0.5f)));
        }
    }


}
