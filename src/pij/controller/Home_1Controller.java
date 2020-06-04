/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pij.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author AHMED
 */
public class Home_1Controller implements Initializable {

    @FXML
    private JFXButton button1;
    @FXML
    private JFXButton button2;
    @FXML
    private VBox pnl_scroll;
    @FXML
    private JFXButton button3;

    public void initialize(URL url, ResourceBundle rb) {

    }
    

    private void refreshNodes(String node) {
        pnl_scroll.getChildren().clear();

        Node[] nodes = new Node[1];

        try {
            nodes[0] = (Node) FXMLLoader.load(getClass().getResource(node));
            pnl_scroll.getChildren().add(nodes[0]);

        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void go_to_membre(ActionEvent event) {
        refreshNodes("/pij/views/Home.fxml");

    }

    @FXML
    private void go_to_statistiques(ActionEvent event) {
        refreshNodes("/pij/views/Statistiques.fxml");

    }

    @FXML
    private void go_to_dashboard(ActionEvent event) throws IOException {
                                        
                    Node node = (Node) event.getSource();
                    Stage stage1 = (Stage) node.getScene().getWindow();
                    stage1.close();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/pij/views/Dashboard.fxml"));
                    Parent root = (Parent) loader.load();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
    }

}
