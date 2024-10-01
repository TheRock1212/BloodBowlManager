package it.unipi.utility;

public class Pair {
    public int home, away;

    public Pair(int home, int away) {
        this.home = home;
        this.away = away;
    }

    @Override
    public String toString() {
        return home + " vs " + away;
    }
}
