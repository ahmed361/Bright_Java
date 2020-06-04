/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pij.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javax.swing.JOptionPane;
import pij.services.Role;
import pij.services.TextFieldValidation;
import pij.utils.connectionDB;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class CreateAccountController implements Initializable {

    @FXML
    private Label lblLogin;
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private TextField text_email_user;
    @FXML
    private TextField text_username_user;
    @FXML
    private PasswordField text_password_user;
    @FXML
    private AnchorPane window_signup;
    @FXML
    private Button btn_signup;
    @FXML
    private AnchorPane window_otp;
    @FXML
    private ComboBox<Role> cboRole;
    @FXML
    private Label lblEmailError;
    @FXML
    private Label lblPasswordError;
    @FXML
    private PasswordField text_password_confirm_user;
    @FXML
    private ComboBox<String> cboDomain;

    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<Role> role;
    private String roleId;
    private String domain;

    Connection con = connectionDB.getInstance().getCnx();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        role = FXCollections.observableArrayList();
        try {
            pst = con.prepareStatement("Select * from Role");
            rs = pst.executeQuery();
            while (rs.next()) {
                role.add(new Role(rs.getString(1), rs.getString(2)));
            }
            cboRole.setItems(role);
        } catch (SQLException ex) {
            Logger.getLogger(CreateAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cboRole.setConverter(new StringConverter<Role>() {
            @Override
            public String toString(Role object) {
                return object.getRoleName();
            }

            @Override
            public Role fromString(String string) {
                return null;
            }
        });

        cboRole.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                // Alert alert = new Alert(Alert.AlertType.INFORMATION, newValue.getRoleID(), ButtonType.OK);
                // alert.show();
                roleId = newValue.getRoleID();
            }
        });
        /////////////
        cboDomain.getItems().addAll("santé", "sociale", "bénévolat");
        cboDomain.setEditable(true);
        cboDomain.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                // Alert alert = new Alert(Alert.AlertType.INFORMATION, newValue.getRoleID(), ButtonType.OK);
                // alert.show();
                domain = cboDomain.getValue();
            }
        });
    }

    @FXML
    private void openLoginScene(MouseEvent event) throws IOException {
        //load the fxml file:
        Parent root = FXMLLoader.load(getClass().getResource("/pij/views/Login1.fxml"));
        //get current scene:
        Scene accountScene = lblLogin.getScene();
        //place the new scene at the bottom:
        root.translateYProperty().set(accountScene.getHeight());
        //add to our holder pain(stack pane):
        StackPane rootPane = (StackPane) accountScene.getRoot();
        rootPane.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame KeyFrame = new KeyFrame(Duration.seconds(2), keyValue);
        timeline.getKeyFrames().add(KeyFrame);

        timeline.play();
        timeline.setOnFinished((ActionEvent event2) -> {
            rootPane.getChildren().remove(anchorpane);
        });
    }

    private void signup(MouseEvent event) throws IOException {
        try {
            String username = text_username_user.getText();
            String email = text_email_user.getText();
            String password = text_password_user.getText();

            Statement statement = con.createStatement();
            int status = statement.executeUpdate("insert into users (username,email,password)"
                    + " values('" + username + "','" + email + "','" + password + "')");
            if (status > 0) {
                System.out.println("user registered");
                //add you loading or delays - ;-)
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();

                //stage.setMaximized(true);
                stage.close();

                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/pij/views/Dashboard.fxml")));
                stage.setScene(scene);
                stage.show();

                // Random();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void signup2(ActionEvent event) throws IOException {
        boolean isValidEmail = TextFieldValidation.isValidEmail(text_email_user, lblEmailError, "invalid email please try again!");
        boolean isPasswordMatched = TextFieldValidation.isPasswordMatched(text_password_user, text_password_confirm_user, lblPasswordError, "password does not match!");
        if (isValidEmail) {
            try {
                //  lblEmailError.setText("valid email!)");
                // lblPasswordError.setText("password matched");
                String insert = "insert into Users(username, email, password, RoleID, domain) Values(?,?,?,?,?)";
                pst = con.prepareStatement(insert);
                pst.setString(1, text_username_user.getText());
                pst.setString(2, text_email_user.getText());
                pst.setString(3, text_password_user.getText());
                pst.setString(4, roleId);
                pst.setString(5, domain);

                int i = pst.executeUpdate();
                if (i == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "User registered successfully!", ButtonType.OK);
                    alert.show();
                    /**
                     * Node node = (Node) event.getSource(); Stage stage =
                     * (Stage) node.getScene().getWindow();
                     * //stage.setMaximized(true); stage.close(); Scene scene =
                     * new
                     * Scene(FXMLLoader.load(getClass().getResource("/pij/views/Dashboard.fxml")));
                     * stage.setScene(scene); stage.show();*
                     */

                    Node node = (Node) event.getSource();
                    Stage stage1 = (Stage) node.getScene().getWindow();
                    stage1.close();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/pij/views/Home_1.fxml"));
                    Parent root = (Parent) loader.load();

                   // HomeController HomeController = loader.getController();
                  //  HomeController.current_user_info(text_username_user.getText());
                    
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CreateAccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
