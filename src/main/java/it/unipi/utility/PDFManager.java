package it.unipi.utility;


import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.ElementPropertyContainer;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.*;
import it.unipi.dataset.Model.*;

import java.util.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class PDFManager {
    private static final String[] headerRoster = {"#", "PLAYER NAME", "TYPE", "MA", "ST", "AG", "PA", "AV", "SKILLS", "TotalSPP", "UnspentSPP", "COST"};
    private static final int nrColumn = 12;
    private static final String TAHOMA = "src/main/resources/fonts/Tahoma.ttf";
    private static final String TAHOMA_BOLD = "src/main/resources/fonts/Tahoma Bold.ttf";

    public PDFManager() {
    }

    public static void generatePDF(Team t) throws IOException, SQLException {
        PdfWriter writer = new PdfWriter("/Users/aleroc/Desktop/Blood Bowl/GSBowl III/Prova Roster/" + t.coach + ".pdf");
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
        //Table table = new Table(nrColumn);
        Table table = new Table(UnitValue.createPercentArray(new float[] {2.56f, 11.82f, 12.85f, 2.56f, 2.56f, 2.56f, 2.56f, 2.56f, 37.7f, 5.64f, 7.69f, 7.69f}));
        table.setWidth(UnitValue.createPercentValue(100));
        table.setFixedLayout();
        //table.setMaxWidth(600);
        //table.setMinWidth(1000);
        for(String header : headerRoster) {
            table.addHeaderCell(new Cell().add(new Paragraph(header)).addStyle(h));
        }
        //Generazione Corpo tabella
        List<Player> p = PlayerDao.getStarting(t.getId());
        List<PlayerTemplate> pt = PlayerTemplateDao.getTemplate(t.getRace());
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
            for(int i = 0; i < nrColumn; i++) {
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
                        table.addCell(Integer.toString(player.MA)).addStyle(b);
                        break;
                    case 4:
                        table.addCell(Integer.toString(player.ST)).addStyle(b);
                        break;
                    case 5:
                        table.addCell(player.AG + "+").addStyle(b);
                        break;
                    case 6:
                        String plc = player.PA == 7 ? "-" : player.PA + "+";
                        table.addCell(plc).addStyle(b);
                        break;
                    case 7:
                        table.addCell(player.AV + "+").addStyle(b);
                        break;
                    case 8:
                        String res = player.skill;
                        if(player.getNIG() > 0)
                            res += " - " + player.getNIG() + " NI";
                        table.addCell(res).addStyle(b);
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
                }
            }
        }
        //table.setSkipFirstHeader()
        Cell raceH = new Cell(1, 2).add(new Paragraph("RACE")).addStyle(h);
        table.addCell(raceH);
        Cell sponsor = new Cell(1, 3).add(new Paragraph(RaceDao.getRace(t.getRace()).name)).addStyle(b);
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
        Cell secondH = new Cell(1, 1).add(new Paragraph("SECONDARY")).addStyle(h);
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
                    Cell teamH = new Cell(1, 2).add(new Paragraph("SPONSOR")).addStyle(h);
                    table.addCell(teamH);
                    Cell team = new Cell(1, 3).add(new Paragraph(t.sponsor)).addStyle(b);
                    table.addCell(team);
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
                    List<Result> res =  ResultDao.getResults();
                    Team foe = null;
                    List<Team> teams = null;
                    for(Result r : res) {
                        if((r.teamH == t.getId() || r.teamA == t.getId()) && !r.isPlayed()) {
                            teams = TeamDao.getTeam(r.teamH == t.getId() ? r.teamA : r.teamH, App.getLeague().getId());
                            break;
                        }
                    }
                    foe = teams.getFirst();
                    Cell team = new Cell(1, 3).add(new Paragraph(Integer.toString(foe.value - t.value))).addStyle(b);
                    table.addCell(team);
                    break;
                }
            }
            if(i < pt.size()) {
                Cell qty = new Cell(1, 2).add(new Paragraph(PlayerDao.getQty(t.getId(), pt.get(i))));
                table.addCell(qty);
                Cell pl = new Cell(1, 2).add(new Paragraph(pt.get(i).position)).addStyle(b);
                table.addCell(pl);
                Cell cost = new Cell(1, 1).add(new Paragraph(pt.get(i).cost + ".000")).addStyle(b);
                table.addCell(cost);
                Cell prim = new Cell(1, 1).add(new Paragraph(pt.get(i).primary)).addStyle(b);
                table.addCell(prim);
                Cell second = new Cell(1, 1).add(new Paragraph(pt.get(i).secondary)).addStyle(b);
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
                Cell second = new Cell(1, 1).add(new Paragraph()).addStyle(empty);
                table.addCell(second);
            }
        }
        Cell rulesH = new Cell(1, 5).add(new Paragraph("SPECIAL RULES")).addStyle(h);
        table.addCell(rulesH);
        Race r = RaceDao.getRace(t.getRace());
        String res = r.special1;
        if(!r.special2.isEmpty())
            res += ", " + r.special2;
        if(!r.special3.isEmpty())
            res += ", " + r.special3;
        Cell rules = new Cell(1, 7).add(new Paragraph(res)).addStyle(b);
        table.addCell(rules);

        document.add(table);

        document.close();
    }
}
