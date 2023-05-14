package com.example.teszt;

import config.ConfigurationHelper;
import dao.DAO;
import dao.DAOImpl;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Order;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloApplication extends Application implements Initializable {
    private static Stage stage;
    @FXML private TableView tableview;
    @FXML private TableColumn<Order, String> customer;
    @FXML private TableColumn<Order, String> title;
    @FXML private TableColumn<Order, Integer> portion;
    @FXML private TableColumn<Order, String> delivery;
    @FXML private TableColumn<Order, String> payment;

    @Override
    public void start(Stage stage) {
        HelloApplication.stage = stage;
        HelloApplication.loadFXML("hello-view.fxml");
        stage.show();

    }

    /*
    create table if not exists TEST(
    ID integer primary key AUTOINCREMENT,
    CUSTOMER text not null,
    TITILE text not null,
    PORTION integer not null,
    DELIVERY text not null,
    PAYMENT text not null
    );
     */

    public static FXMLLoader loadFXML(String fxml){

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxml));
        Scene scene = null;
        try {
            Parent root = loader.load();
            scene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(scene);
        return loader;
    }

    public static void main(String[] args) {
        launch();
    }

    public void addnewOrder(ActionEvent actionEvent) {
        HelloApplication.loadFXML("newOrder.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customer.setCellValueFactory(new PropertyValueFactory<>("name"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        portion.setCellValueFactory(new PropertyValueFactory<>("portion"));
        delivery.setCellValueFactory(new PropertyValueFactory<>("delivery"));
        payment.setCellValueFactory(new PropertyValueFactory<>("payment"));
    }
}