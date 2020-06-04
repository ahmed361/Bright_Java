/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pij.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import pij.services.Statistique;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class StatistiquesController implements Initializable {

    @FXML
    private AnchorPane table;
    @FXML
    private Pane pane;

    float S;
    float B;
    float N;
    float S2;

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        try {
            statistique();
        } catch (SQLException ex) {
            Logger.getLogger(StatistiquesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void statistique() throws SQLException {

        Statistique apr = new Statistique();
        //*****************LineChaart************************
        Statistique gs1 = new Statistique();

        gs1.calculatereportspermonth();

        pane.getChildren().clear();
        PieChart series = new PieChart();

        /*list.add (new PieChart.Data("en attente",Integer.valueOf( Reclamation.getEtat())));*/
        N = (float) Math.round((Statistique.totaleNT + Statistique.totaleT));
        S = (float) Math.round((Statistique.totaleNT / N) * 100);
        B = (float) Math.round(100 - S );

        series.getData()
                .add(new PieChart.Data("mourouj" + S + "%", Statistique.totaleNT));

        series.getData()
                .add(new PieChart.Data("zahra" + B + "%", Statistique.totaleT));

        pane.getChildren().add(series);

    }


}
