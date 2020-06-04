/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pij.services;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pij.entities.Users;
import pij.utils.connectionDB;

/**
 *
 * @author AHMED
 */
public class crud_association {

    Connection con = connectionDB.getInstance().getCnx();

    public List<Users> readAll() throws SQLException {
        List<Users> arr = new ArrayList<>();
        Statement ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from users where RoleID='Association'");
        while (rs.next()) {
            int id = rs.getInt(1);
            String username = rs.getString(2);
            String email = rs.getString(3);
            String domain = rs.getString(6);

            Users a = new Users(id, username, email, domain);
            arr.add(a);
        }
        return arr;

    }

    public void ajouter(Users a) throws SQLException {
        try {
            Statement ste = con.createStatement();
            String requeteInsert = "INSERT INTO `refugees`.`users` ( `username`, `email`, `domain`, `password`, `RoleID`) VALUES ( '" + a.getUsername() + "', '" + a.getEmail() + "', '" + a.getDomain() + "', '" + a.getPassword()+ "', '" + a.getRoleID()+ "');";
            ste.executeUpdate(requeteInsert);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int id) throws SQLException {
        try {
            String query = "DELETE FROM users WHERE id=" + id;
            Statement ste = con.createStatement();
            ste.executeUpdate(query);
            System.out.println("l'association de l'id = " + id + "a été supprimée ");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    /**
     * public void modifier(int id, String nom, String domaine, String mail) {
     * try { String query = "UPDATE sers SET nom='" + nom + "',domaine='" +
     * domaine + "',mail='" + mail + "' WHERE id='" + id + "'"; Statement ste =
     * con.createStatement(); ste.executeUpdate(query);
     * System.out.println("L'association a été modifié avec succés"); } catch
     * (SQLException ex) { System.out.println(ex.getMessage()); }
     *
     * }*
     */
}
