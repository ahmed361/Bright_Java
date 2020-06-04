/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pij.services;

import pij.utils.connectionDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author amena
 */
public class Statistique {

    public static int totaleNT = 0;
    public static int totaleT = 0;

    Connection con = connectionDB.getInstance().getCnx();

    public void calculatereportspermonth() throws SQLException {

        Statement statement = con.createStatement();

        String request = "SELECT * FROM membre ";

        ResultSet rs = statement.executeQuery(request);
        while (rs.next()) {
            if ("mourouj".equals(rs.getString(4))) {
                totaleNT += 1;

            } else if ("zahra".equals(rs.getString(4))) {
                totaleT += 1;

            }

        }

    }

}
