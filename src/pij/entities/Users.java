/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pij.entities;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

/**
 *
 * @author AHMED
 */
public class Users extends RecursiveTreeObject<Users>{
    int id;
    String username;
    String email;
    String domain;
    String password;
    String RoleID;
    
    public Users(int id, String username, String email, String domain) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.domain = domain;
    }
    
    public Users(){}

    public Users(String username, String email, String domain) {
        this.username = username;
        this.email = email;
        this.domain = domain;
    }

    public Users(String username, String email, String domain, String password, String RoleID) {
        this.username = username;
        this.email = email;
        this.domain = domain;
        this.password = password;
        this.RoleID = RoleID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleID() {
        return RoleID;
    }

    public void setRoleID(String RoleID) {
        this.RoleID = RoleID;
    }






    
}
