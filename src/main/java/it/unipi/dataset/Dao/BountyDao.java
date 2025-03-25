package it.unipi.dataset.Dao;

import it.unipi.dataset.Model.Bounty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BountyDao {

    public static synchronized void insert(Connection con, Bounty bounty) throws SQLException {
        String sql = "INSERT INTO bounty(team, player, reward) VALUES(?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        int i = 0;
        ps.setInt(++i, bounty.getTeam());
        ps.setInt(++i, bounty.getPlayer());
        ps.setInt(++i, bounty.getReward());
        ps.executeUpdate();
        ps.close();
    }

    public static synchronized void delete(Connection con, Bounty bounty) throws SQLException {
        String sql = "DELETE FROM bounty WHERE team = ? AND player = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        int i = 0;
        ps.setInt(++i, bounty.getTeam());
        ps.setInt(++i, bounty.getPlayer());
        ps.executeUpdate();
        ps.close();
    }

    public static List<Bounty> getAllBounties(Connection con) throws SQLException {
        List<Bounty> bounties = new ArrayList<>();
        String sql = "SELECT * FROM bounty";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            bounties.add(new Bounty(rs));
        }
        ps.close();
        rs.close();
        return bounties;
    }
}
