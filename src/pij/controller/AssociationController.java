/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pij.controller;

import com.sun.mail.util.logging.MailHandler;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Authenticator;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.*;
import javax.mail.internet.AddressException;

import pij.entities.Users;
import pij.services.crud_association;
import pij.utils.connectionDB;

/**
 * FXML Controller class
 *
 * @author Brahim
 */
public class AssociationController implements Initializable {

    Connection con = connectionDB.getInstance().getCnx();
    crud_association c = new crud_association();
    List<Users> listA = new ArrayList<>();
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    @FXML
    private TableView<Users> table;
    @FXML
    private TableColumn<Users, String> colusername;
    @FXML
    private TableColumn<Users, String> coldomain;
    @FXML
    private TableColumn<Users, String> colemail;
    @FXML
    private TableColumn<Users, String> colid;
    @FXML
    private Button Supprimer;
    @FXML
    private Button Ajouter;
    private TextField txt_nom;
    @FXML
    private TextField txt_domaine;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_username;
    @FXML
    private TextField txt_password;
        String to;
        String subject;
        String from;
        String msg;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            afficher();
        } catch (SQLException ex) {
            Logger.getLogger(AssociationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void afficher() throws SQLException {
        listA = c.readAll();
        ObservableList<Users> listAsoociation = FXCollections.observableArrayList(listA);
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colusername.setCellValueFactory(new PropertyValueFactory<>("username"));
        coldomain.setCellValueFactory(new PropertyValueFactory<>("domain"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));

        table.setItems(listAsoociation);
    }

    @FXML
    private void Supprimer(ActionEvent event) throws SQLException {
        ObservableList<Users> SelectedRows, allpeople;

        allpeople = table.getItems();
        // il donne les ligne qui vous avez déja séléctionner
        SelectedRows = table.getSelectionModel().getSelectedItems();

        for (Users a1 : SelectedRows) {
            allpeople.remove(a1);
            c.delete(a1.getId());
        }
    }

    @FXML
    private void Ajouter(ActionEvent event) throws Exception {

        Users a1 = new Users(txt_username.getText(), txt_email.getText(), txt_domaine.getText(), txt_password.getText(), "Association");
        c.ajouter(a1);
        afficher();

      /**  to=txt_email.getText();
        from="ahmedouertani1@esprit.tn";
        subject="test";
        msg="test test";
        
        MailHandler mail=new MailHandler();
        mail.composeMail(to,subject,msg);*

        sendmail("ahmedouertani00@gmail.com");**/
              String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "ahmedouertani00@gmail.com";
        String password = "azertyahmed";
 
        // outgoing message information
        String mailTo = "ahmedouertani00@gmail.com";
        String subject = "Hello my friend";
        String message = "Hi guy, Hope you are doing well. Duke.";
 
        PlainTextEmailSender mailer = new PlainTextEmailSender();
 
        try {
            mailer.sendPlainTextEmail(host, port, mailFrom, password, mailTo,
                    subject, message);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Failed to sent email.");
            ex.printStackTrace();
        }
    }

    
    
    public class PlainTextEmailSender {
 
    public void sendPlainTextEmail(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message) throws AddressException,
            MessagingException {
 
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
 properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
     // msg.setSentDate(new Date());
        // set plain text message
        msg.setText(message);
 
        // sends the e-mail
        Transport.send(msg);
 
    }
    }
}