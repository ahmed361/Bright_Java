/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pij.entities;

/**
 *
 * @author AHMED
 */
public class Membre {

    private int id;
    private String nom;
    private String prenom;
    private String ville;
    private String email;
    private int tel;

    public Membre(int id, String nom, String prenom, String ville, String email, int tel) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.ville = ville;
        this.email = email;
        this.tel = tel;
    }

    public Membre(String nom, String prenom, String ville, String email, int tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.ville = ville;
        this.email = email;
        this.tel = tel;
    }

    public Membre(String nom, String prenom, String ville, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.ville = ville;
        this.email = email;
    }
    

    public Membre() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }



    

}
