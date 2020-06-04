/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pij.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pij.entities.Membre;
import pij.entities.Users;
import pij.utils.connectionDB;

/**
 *
 * @author AHMED
 */
public class crud_membre {

    Connection con = connectionDB.getInstance().getCnx();

    public List<Membre> readAll() throws SQLException {
        List<Membre> arr = new ArrayList<>();
        Statement ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from membre");
        while (rs.next()) {
            int id = rs.getInt(1);
            String nom = rs.getString(2);
            String prenom = rs.getString(3);
            String ville = rs.getString(4);
            String email = rs.getString(5);
            int tel = rs.getInt(6);

            Membre a = new Membre(id, nom, prenom, ville, email, tel);
            arr.add(a);
        }
        return arr;

    }

    public void ajouter(Membre a) throws SQLException {
        try {
            Statement ste = con.createStatement();
            String requeteInsert = "INSERT INTO `refugees`.`membre` ( `nom`, `prenom`, `ville`, `email`,`tel`) VALUES ( '" + a.getNom() + "', '" + a.getPrenom() + "', '" + a.getVille() + "', '" + a.getEmail() + "', '" + a.getTel() + "');";
            ste.executeUpdate(requeteInsert);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifier(int id, String nom, String prenom, String ville,  String email, int tel) {
        try {
            String query = "UPDATE Membre SET nom='" + nom + "',prenom='" + prenom + "',ville='" + ville + "',email='" + email + "',tel='" + tel + "' WHERE id='" + id + "'";
            Statement ste = con.createStatement();
            ste.executeUpdate(query);
            System.out.println("Le membre a été modifié avec succés");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    
        public void delete(int id) throws SQLException {
        try {
            String query = "DELETE FROM membre WHERE id=" + id;
            Statement ste = con.createStatement();
            ste.executeUpdate(query);
            System.out.println("le membre de l'id = " + id + "a été supprimée ");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
