package it.unipi.utility;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.ResultDao;
import it.unipi.dataset.Dao.TeamDao;
import it.unipi.dataset.Model.Result;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Fixture {
    private ArrayList<Integer> teams;
    private Pair[] giornata;

    public Fixture() {
        teams = new ArrayList<>();
    }

    /**
     * Genera calendario con algoritmo round robin per ciascun girone
     * @param n il numero di giornate da generare
     * @throws SQLException
     */
    public void RoundRobin(int n, boolean collapse) throws SQLException {
        Result r = new Result();
        int gironi = App.getLeague().getRound();
        if(collapse)
            gironi = 1;
        for(int gr = 0; gr < gironi; gr++) {
            int[] tms;
            if(gironi == 1)
                tms = TeamDao.getTeams(gr, App.getLeague());
            else
                tms = TeamDao.getTeams(gr + 1, App.getLeague());

            for(int team : tms) {
                teams.add(team);
            }
            if(tms.length % 2 != 0)
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
            ResultDao.addResult(giornate);
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