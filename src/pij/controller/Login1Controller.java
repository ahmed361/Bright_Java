/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pij.controller;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import pij.utils.connectionDB;



public class Login1Controller implements Initializable {
    @FXML
    private TabPane tabPaneLogin;
    @FXML
    private Tab tabAdmin;
    @FXML
    private Tab tabUser;
    @FXML
    private Label lblCreateAccount;
    @FXML
    private Pane slidingPane;
    @FXML
    private Label lblAdmin;
    @FXML
    private Label lblUser;
    @FXML
    private Label lblStatus;
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField textUsername;
    @FXML
    private PasswordField textPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Label btnForgot;
    @FXML
    private Label lblErrors;
    @FXML
    private Label lbl_close;

    Connection con = connectionDB.getInstance().getCnx();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void openAdminTab(MouseEvent event) {
        TranslateTransition toLeftTransition = new TranslateTransition(new Duration(500), lblStatus);
        toLeftTransition.setToX(slidingPane.getTranslateX());
        toLeftTransition.play();
        toLeftTransition.setOnFinished((ActionEvent event2) -> {
            lblStatus.setText("ADMIN");
        });
        tabPaneLogin.getSelectionModel().select(tabAdmin);
    }

    @FXML
    private void openUserTab(MouseEvent event) {
        TranslateTransition toRightAnimation = new TranslateTransition(new Duration(500), lblStatus);
        toRightAnimation.setToX(slidingPane.getTranslateX() + (slidingPane.getPrefWidth() - lblStatus.getPrefWidth()));
        toRightAnimation.play();
        toRightAnimation.setOnFinished((ActionEvent event1) -> {
            lblStatus.setText("USER");
        });
        tabPaneLogin.getSelectionModel().select(tabUser);
    }

    @FXML
    private void openCreateAccountScene(MouseEvent event) throws IOException {
        //load the fxml file:
        Parent root = FXMLLoader.load(getClass().getResource("/pij/views/CreateAccount_1.fxml"));
        //get current scene:
        Scene loginScene = lblAdmin.getScene();
        //place the new scene at the bottom:
        root.translateYProperty().set(loginScene.getHeight());
        //add to our holder pain(stack pane):
        rootPane.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame KeyFrame = new KeyFrame(Duration.seconds(2), keyValue);
        timeline.getKeyFrames().add(KeyFrame);

        timeline.play();
        timeline.setOnFinished((ActionEvent event2) -> {
            rootPane.getChildren().remove(anchorPane);
        });
    }

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    public void handleButtonAction(MouseEvent event) {
        if (event.getSource() == lbl_close) {
            System.exit(0);
        }
        if (event.getSource() == btnLogin) {
            //login here
            if (logIn().equals("Success")) {
                try {

                    //add you loading or delays - ;-)
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    //stage.setMaximized(true);
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/pij/views/Home_1.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

            }
        }
    }

    private String logIn() {
        String status = "Success";
        String email = textUsername.getText();
        String password = textPassword.getText();
        if (email.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Error";
        } else {
            //query
            String sql = "SELECT * FROM admins Where email = ? and password = ?";
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    setLblError(Color.TOMATO, "Enter Correct Email/Password");
                    status = "Error";
                } else {
                    setLblError(Color.GREEN, "Login Successful..Redirecting..");
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }

        return status;
    }

    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }

}
