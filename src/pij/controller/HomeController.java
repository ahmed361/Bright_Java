/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pij.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pij.entities.Membre;
import pij.entities.Users;
import pij.services.crud_association;
import pij.services.crud_membre;
import pij.utils.connectionDB;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class HomeController implements Initializable {

    Connection con = connectionDB.getInstance().getCnx();
    crud_membre c = new crud_membre();
    List<Membre> listA = new ArrayList<>();

    private Label label;
    @FXML
    private Label label_username;
    @FXML
    private JFXButton Ajouter;
    @FXML
    private JFXButton Modifier;
    @FXML
    private JFXButton Supprimer;
    @FXML
    private TableColumn<Membre, Integer> colid;
    @FXML
    private TableColumn<Membre, String> colnom;
    @FXML
    private TableColumn<Membre, String> colprenom;
    @FXML
    private TableColumn<Membre, String> colville;
    @FXML
    private TableColumn<Membre, Integer> coltel;
    @FXML
    private TableColumn<Membre, String> colemail;
    @FXML
    private JFXTextField txt_prenom;
    @FXML
    private JFXTextField txt_tel;
    @FXML
    private JFXTextField txt_ville;
    @FXML
    private JFXTextField txt_email;
    @FXML
    private TableView<Membre> table;
    @FXML
    private JFXTextField txt_nom;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            afficher();
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void current_user_info(String text) {
        label_username.setText(text);
    }

    @FXML
    private void Ajouter(ActionEvent event) throws SQLException {
        Membre a1 = new Membre(txt_nom.getText(), txt_prenom.getText(), txt_ville.getText(), txt_email.getText(),Integer.valueOf(txt_tel.getText()));
        c.ajouter(a1);
        afficher();
    }

    @FXML
    private void Modifier(ActionEvent event) throws SQLException {
        Membre a2 = table.getSelectionModel().getSelectedItem();
        c.modifier(a2.getId(), txt_nom.getText(), txt_prenom.getText(), txt_ville.getText(), txt_email.getText(), Integer.valueOf(txt_tel.getText()));
        afficher();
    }
    

   /** private void modifier2(MouseEvent event) throws SQLException {
        Membre a2 = table.getSelectionModel().getSelectedItem();

        txt_nom.setText(a2.getNom());
        txt_prenom.setText(a2.getPrenom());
        txt_ville.setText(a2.getVille());
        txt_email.setText(a2.getEmail());
        txt_email.setText(String.valueOf(a2.getTel()));

        afficher();
        // afficher();
    }**/

    @FXML
    private void Supprimer(ActionEvent event) throws SQLException {
        ObservableList<Membre> SelectedRows, allpeople;
        allpeople = table.getItems();
        // il donne les ligne qui vous avez déja séléctionner
        SelectedRows = table.getSelectionModel().getSelectedItems();

        for (Membre c1 : SelectedRows) {
       allpeople.remove(c1);
       c.delete(c1.getId());
        }
    }

    /**
     * @FXML private void Afficher(ActionEvent event) throws SQLException {
     * listA = c.readAll(); ObservableList<Membre> listmembre =
     * FXCollections.observableArrayList(listA); colid.setCellValueFactory(new
     * PropertyValueFactory<>("id")); colnom.setCellValueFactory(new
     * PropertyValueFactory<>("nom")); colprenom.setCellValueFactory(new
     * PropertyValueFactory<>("prenom")); colville.setCellValueFactory(new
     * PropertyValueFactory<>("ville")); colemail.setCellValueFactory(new
     * PropertyValueFactory<>("email")); coltel.setCellValueFactory(new
     * PropertyValueFactory<>("tel"));
     *
     * table.setItems(listmembre);
    }*
     */
    private void afficher() throws SQLException {
        listA = c.readAll();
        ObservableList<Membre> listmembre = FXCollections.observableArrayList(listA);
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colville.setCellValueFactory(new PropertyValueFactory<>("ville"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        coltel.setCellValueFactory(new PropertyValueFactory<>("tel"));

        table.setItems(listmembre);
    }

    private void modifier2() throws SQLException {
        Membre a2 = table.getSelectionModel().getSelectedItem();
        txt_nom.setText(a2.getNom());
        txt_prenom.setText(a2.getPrenom());
        txt_ville.setText(a2.getVille());
        txt_email.setText(a2.getEmail());
        txt_tel.setText(String.valueOf(a2.getTel()));

        afficher();
        // afficher();
    }




}
