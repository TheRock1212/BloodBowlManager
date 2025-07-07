package it.unipi.utility;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.ResultDao;
import it.unipi.dataset.Dao.TeamDao;
import it.unipi.dataset.Model.Result;
import it.unipi.utility.connection.Connection;
import it.unipi.utility.json.JsonExploiter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Fixture {
    private ArrayList<Integer> teams;
    private Pair[] giornata;
    private String data;

    public Fixture() {
        teams = new ArrayList<>();
    }

    /**
     * Genera calendario con algoritmo round robin per ciascun girone
     * @param n il numero di giornate da generare
     * @throws SQLException
     */
    public void RoundRobin(int n, boolean collapse) throws Exception {
        Result r = new Result();
        int gironi = App.getLeague().getRound();
        if(collapse)
            gironi = 1;
        for(int gr = 0; gr < gironi; gr++) {
            List<Integer> tms;
            if(gironi == 1) {
                Connection.params.put("gr", gr);
                Connection.params.put("league", App.getLeague().getId());
                data = Connection.getConnection("/api/v1/team/id", Connection.GET, null);
                tms = JsonExploiter.getListFromJson(Integer.class, data);
            } else {
                Connection.params.put("gr", gr + 1);
                Connection.params.put("league", App.getLeague().getId());
                data = Connection.getConnection("/api/v1/team/id", Connection.GET, null);
                tms = JsonExploiter.getListFromJson(Integer.class, data);
            }

            for(int team : tms) {
                teams.add(team);
            }
            if(tms.size() % 2 != 0)
                teams.add(0);
            Collections.shuffle(teams);
            giornata = new Pair[App.getLeague().getTeams() / 2];
            List<Pair[]> giornate = new ArrayList<>();
            for(int i = 0; i < n; i++) {
               for(int home = 0, away = teams.size() - 1; home < teams.size() / 2; home++, away--) {
                   giornata[home] = new Pair(teams.get(home), teams.get(away));
                   System.out.println(giornata[home].toString());
               }
               changePairings();
               giornate.add(giornata);
            }
            Connection.params.put("league", App.getLeague().getId());
            Connection.getConnection("/api/v1/result/insert", Connection.POST, JsonExploiter.toJson(giornate));
        }
    }

    private void changePairings() {
        int[] tms = new int[teams.size()];
        for(int i = 0; i < tms.length; i++) {
            tms[i] = teams.get(i);
        }
        int tmp = tms[teams.size() - 1];
        int dest[] = tms;
        teams.clear();
        System.arraycopy(tms, 1, dest, 2, tms.length - 2);
        dest[1] = tmp;
        for(int t : dest)
            teams.add(t);
    }
}